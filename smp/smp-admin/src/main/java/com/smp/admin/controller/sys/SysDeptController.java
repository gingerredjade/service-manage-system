package com.smp.admin.controller.sys;

import com.alibaba.fastjson.JSON;
import com.smp.admin.model.po.bus.svcinfo.BusGeoSvcInfoDO;
import com.smp.admin.model.po.sys.SysDeptDO;
import com.smp.admin.service.iface.bus.BusGeoSvcInfoService;
import com.smp.admin.service.iface.sys.SysDeptService;
import com.smp.common.api.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 机构管理Controller
 * @author Hongyu Jiang
 * @since  May. 13 2020
 */
@CrossOrigin
@Controller
@Api(tags = "机构管理", value = "SysDeptController",
	description = "支持机构信息的增加、查询、删除")
@RequestMapping("/dept")
public class SysDeptController {

	private final SysDeptService sysDeptService;
	private final BusGeoSvcInfoService busGeoSvcInfoService;
	@Autowired
	public SysDeptController(SysDeptService sysDeptService,
							 BusGeoSvcInfoService busGeoSvcInfoService) {
		this.sysDeptService = sysDeptService;
		this.busGeoSvcInfoService = busGeoSvcInfoService;
	}

	@PreAuthorize("hasAuthority('sys:dept:view')")
	@ApiOperation(value = "查看机构信息", notes = "查询机构树")
	@RequestMapping(value = "/findTree", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult findTree(HttpServletRequest request) {
		List<SysDeptDO> result = null;
		String name = request.getParameter("name");
		if (name == null || name.length() == 0) {
			result = sysDeptService.findTree();
		} else {
			result = sysDeptService.findTreeByName(name);
		}
		return CommonResultUtil.success(result);
	}

	@PreAuthorize("hasAuthority('sys:dept:add') AND hasAuthority('sys:dept:edit')")
	@ApiOperation(value = "新增机构信息", notes = "新增机构信息")
	@PostMapping("/save")
	@ResponseBody
	public CommonResult save(@RequestBody SysDeptDO record) {
		int flag = sysDeptService.save(record);
		if (flag == 0) {
			return CommonResultUtil.success(flag);
		}
		return  CommonResultUtil.failed();
	}

	@PreAuthorize("hasAuthority('sys:dept:delete')")
	@ApiOperation(value = "删除机构信息", notes = "删除机构信息")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult delete(@RequestBody List<SysDeptDO> records) {

		// 1-- 存储待删除/不能删除的机构
		List<SysDeptDO> toDeleteRecords = new ArrayList<>();
		List<SysDeptDO> notDeleteRecords = new ArrayList<>();

		StringBuilder sb = new StringBuilder();
		// 2-- 过滤：已被服务引用的机构不能删除；含有被服务引用的子机构的父机构不予删除
		for (SysDeptDO record : records) {
			Long deptId = record.getId();
			List<BusGeoSvcInfoDO> svcInfos = busGeoSvcInfoService.listByDeptId(deptId);
			if (svcInfos.isEmpty()) {
				// 如果该机构未被占用，检测其子机构是否被占用：含有被占用的子机构,父、子机构均不予删除。
				List<SysDeptDO> childDeptsUsed = new ArrayList<>();
				checkChildrenDept(childDeptsUsed, record);

				if (childDeptsUsed.size() != 0) {
					notDeleteRecords.add(record);
					sb.append(ResultCode.DEPT_EXIST_USEDCHILD.getMessage());
				} else {
					toDeleteRecords.add(record);
					List<SysDeptDO> depts = new ArrayList<>();
					getAllChildren(depts, record);
					for (SysDeptDO dep : depts) {
						toDeleteRecords.add(dep);
					}
				}
			} else {
				// 如果该机构被引用，不管子机构占用情况，直接不予删除
				notDeleteRecords.add(record);
				sb.append(ResultCode.DEPT_USED.getMessage());
			}
		}

		// 3-- 准备删除提示信息
		String msgStr = sb.toString();

		// 4-- 执行删除
		int resultFlag = sysDeptService.delete(toDeleteRecords);
		if (resultFlag == 0) {
			if (msgStr != "" && msgStr.length() != 0) {
				return CommonResultUtil.success(resultFlag, msgStr);
			} else {
				return CommonResultUtil.success(resultFlag);
			}
		} else {
			return CommonResultUtil.failed();
		}
	}

	@PreAuthorize("hasAuthority('sys:dept:view')")
	@ApiOperation(value = "获取机构信息列表",
		notes = "分页获取机构信息列表")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<PageVO<SysDeptDO>> list(@RequestBody PageRequestVO pageRequest) {
		PageVO<SysDeptDO> curPage = sysDeptService.getPageList(pageRequest);
		System.out.println(JSON.toJSONString(curPage));

		return CommonResultUtil.success(curPage);
	}

	@PreAuthorize("hasAuthority('sys:dept:view')")
	@ApiOperation(value = "获取单个机构的详细信息",
		notes = "根据id获取机构的详细信息")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<SysDeptDO> getItem(@PathVariable Long id) {
		SysDeptDO svcType = sysDeptService.getById(id);
		return CommonResultUtil.success(svcType);
	}

	/**
	 * 获取某机构的所有父级机构
	 * @param record 某机构对象
	 * @param parentDepts 父机构集合
	 */
	private void getParentsDept(List<SysDeptDO> parentDepts, SysDeptDO record) {
		Long curParentId = record.getParentId();
		if (curParentId != null) {
			SysDeptDO curRecord = sysDeptService.getById(curParentId);
			if (curRecord != null) {
				parentDepts.add(curRecord);
				getParentsDept(parentDepts, curRecord);
			}
		}
	}

	/**
	 * 获取某机构的所有父级机构
	 * @param record 某机构对象
	 * @param childDeptsUsed 被占用的子机构集合子机构集合
	 */
	private void checkChildrenDept(List<SysDeptDO> childDeptsUsed, SysDeptDO record) {
		// 检测机构是否含有子机构、子机构是否已被占用
		List<SysDeptDO> children = record.getChildren();
		if (children.size() != 0) {
			for (SysDeptDO dto : children) {
				List<BusGeoSvcInfoDO> curSvcInfos = busGeoSvcInfoService.listByDeptId(dto.getId());
				if (!curSvcInfos.isEmpty()) {
					childDeptsUsed.add(dto);
				}
				checkChildrenDept(childDeptsUsed, dto);
			}
		}
	}

	private void getAllChildren(List<SysDeptDO> depts, SysDeptDO record) {
		List<SysDeptDO> childs = record.getChildren();
		if (childs != null) {
			for (int i=0, len=childs.size(); i<len; i++) {
				depts.add(childs.get(i));
				getAllChildren(depts, childs.get(i));
			}
		}

	}

}

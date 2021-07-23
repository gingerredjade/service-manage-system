package com.smp.admin.controller.bus;

import com.alibaba.fastjson.JSON;
import com.smp.admin.model.po.bus.svcinfo.BusSvcSubjectDO;
import com.smp.admin.model.po.bus.svcinfo.BusSvcTypeDO;
import com.smp.admin.service.iface.bus.BusSvcSubjectService;
import com.smp.admin.service.iface.bus.BusSvcTypeService;
import com.smp.common.api.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务主题信息 管理Controller
 * @author Hongyu Jiang
 * @since May. 16 2020
 */
@CrossOrigin
@Controller
@Api(tags = "服务主题管理", value = "SvcSubjectController",
	description = "支持服务主题的添加、查询、删除等")
@RequestMapping("/subject")
public class SvcSubjectController {

	/**
	 * 采用构造器方式,注入Service层
	 */
	private final BusSvcSubjectService busSvcSubjectService;
	private final BusSvcTypeService busSvcTypeService;
	@Autowired
	public SvcSubjectController(
		BusSvcSubjectService busSvcSubjectService,
		BusSvcTypeService busSvcTypeService) {
		this.busSvcSubjectService = busSvcSubjectService;
		this.busSvcTypeService = busSvcTypeService;
	}


	@PreAuthorize("hasAuthority('svc:subject:add') AND hasAuthority('svc:subject:edit')")
	@ApiOperation(value = "添加服务主题",
		notes = "添加服务主题")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult save(@RequestBody BusSvcSubjectDO record) {

		// 重复注册过滤
		List<BusSvcSubjectDO> existList = busSvcSubjectService.listBySubjectName(record.getName());
		if (existList != null && existList.size() != 0) {
			return CommonResultUtil.failed("违反服务主题唯一性！不允许同名主题重复增加");
		}

		int flag = busSvcSubjectService.save(record);
		if (flag == 0) {
			return CommonResultUtil.success(null, "操作成功");
		}
		return CommonResultUtil.failed();
	}


	@PreAuthorize("hasAuthority('svc:subject:delete')")
	@ApiOperation(value = "删除服务主题",
		notes = "删除已添加的服务主题")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public CommonResult delete(@PathVariable Long id) {
		boolean flag = busSvcSubjectService.deleteSubject(id);
		if (flag) {
			return CommonResultUtil.success(flag);
		}
		return CommonResultUtil.failed();
	}


	@PreAuthorize("hasAuthority('svc:subject:delete')")
	@ApiOperation(value = "删除服务主题",
		notes = "删除已添加的服务主题")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult delete(@RequestBody List<BusSvcSubjectDO> records) {
		// 1-- 存储待删除/不能删除的服务主题列表
		List<BusSvcSubjectDO> toDeleteRecords = new ArrayList<>();
		List<BusSvcSubjectDO> notDeleteRecords = new ArrayList<>();

		// 2-- 过滤：已被服务类型引用的服务
		for (BusSvcSubjectDO record: records) {
			Long subjectId = record.getSubjectId();
			List<BusSvcTypeDO> typeDOs = busSvcTypeService.listBySubjectId(subjectId);
			if (typeDOs.isEmpty()) {
				toDeleteRecords.add(record);
			} else {
				notDeleteRecords.add(record);
			}
		}

		// 3-- 准备删除提示信息
		StringBuilder sb = new StringBuilder();
		if (toDeleteRecords != null && toDeleteRecords.size() != 0) {
			sb.append("【");
			for (BusSvcSubjectDO tss : toDeleteRecords) {
				sb.append(tss.getName() + "，");
			}
			sb.deleteCharAt(sb.lastIndexOf("，"));
			sb.append("】，以上服务主题成功删除。" + System.getProperty("line.separator"));
		}
		if (notDeleteRecords != null && notDeleteRecords.size() != 0 ) {
			sb.append("【");
			for (BusSvcSubjectDO nss : notDeleteRecords) {
				sb.append(nss.getName() + "，");
			}
			sb.deleteCharAt(sb.lastIndexOf("，"));
			sb.append("】，以上服务主题已被服务类型占用，无法删除！" + System.getProperty("line.separator"));
		}
		String msgStr = sb.toString();

		// 4-- 执行删除
		int resultFlag = busSvcSubjectService.delete(toDeleteRecords);
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


	@PreAuthorize("hasAuthority('svc:subject:view')")
	@ApiOperation(value = "获取服务主题列表",
		notes = "分页获取已添加服务主题列表。支持根据以下条件查询服务信息:<br />" +
			"主题名称[name]")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<PageVO<BusSvcSubjectDO>> list(@RequestBody PageRequestVO pageRequest) {
		PageVO<BusSvcSubjectDO> curPage = busSvcSubjectService.getPageList(pageRequest);
		System.out.println(JSON.toJSONString(curPage));

		return CommonResultUtil.success(curPage);
	}


	@PreAuthorize("hasAuthority('svc:subject:view')")
	@ApiOperation(value = "获取服务主题列表",
		notes = "过滤获取已添加服务主题列表。支持根据以下条件查询服务信息:<br />" +
			"主题名称[name]")
	@RequestMapping(value = "/filter", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<List<BusSvcSubjectDO>> filter(@RequestBody UniversalRequestVO requestVO) {
		List<BusSvcSubjectDO> list = busSvcSubjectService.getList(requestVO);
		return CommonResultUtil.success(list);
	}


	@PreAuthorize("hasAuthority('svc:subject:view')")
	@ApiOperation(value = "获取单个服务主题的详细信息",
		notes = "根据id获取服务主题的详细信息")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<BusSvcSubjectDO> getItem(@PathVariable Long id) {
		BusSvcSubjectDO svcSubject = busSvcSubjectService.getById(id);
		return CommonResultUtil.success(svcSubject);
	}


	@PreAuthorize("hasAuthority('svc:subject:view')")
	@ApiOperation(value = "获取注册的服务主题列表",
		notes = "获取全部已注册服务主题列表")
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<List<BusSvcSubjectDO>> getAll() {
		List<BusSvcSubjectDO> list = busSvcSubjectService.listAll();
		return CommonResultUtil.success(list);
	}


}

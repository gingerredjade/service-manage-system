package com.smp.admin.controller.bus;

import com.alibaba.fastjson.JSON;
import com.smp.admin.model.po.bus.svcinfo.BusGeoSvcInfoDO;
import com.smp.admin.model.po.bus.svcinfo.BusSvcStyleDO;
import com.smp.admin.service.iface.bus.BusGeoSvcInfoService;
import com.smp.admin.service.iface.bus.BusSvcStyleService;
import com.smp.common.api.CommonResult;
import com.smp.common.api.CommonResultUtil;
import com.smp.common.api.PageRequestVO;
import com.smp.common.api.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务风格信息 管理Controller
 * @author Hongyu Jiang
 * @since Nov. 18 2020
 */
@CrossOrigin
@Controller
@Api(tags = "服务风格管理", value = "SvcStyleController",
	description = "支持服务风格的添加、查询、删除等")
@RequestMapping("/style")
public class SvcStyleController {

	/**
	 * 采用构造器方式,注入Service层
	 */
	private final BusSvcStyleService busSvcStyleService;
	private final BusGeoSvcInfoService busGeoSvcInfoService;
	@Autowired
	public SvcStyleController(BusSvcStyleService busSvcStyleService,
							  BusGeoSvcInfoService busGeoSvcInfoService) {
		this.busSvcStyleService = busSvcStyleService;
		this.busGeoSvcInfoService = busGeoSvcInfoService;
	}


	@PreAuthorize("hasAuthority('svc:style:add') AND hasAuthority('svc:style:edit')")
	@ApiOperation(value = "添加服务风格",
		notes = "添加服务风格")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult save(@RequestBody BusSvcStyleDO record) {

		// 重复注册过滤
		List<BusSvcStyleDO> existList = busSvcStyleService.listByStyleName(record.getName());
		if (existList != null && existList.size() != 0) {
			return CommonResultUtil.failed("违反服务主题唯一性！不允许同名风格重复增加");
		}

		int flag = busSvcStyleService.save(record);
		if (flag == 0) {
			return CommonResultUtil.success(null, "操作成功");
		}
		return CommonResultUtil.failed();
	}


	@PreAuthorize("hasAuthority('svc:style:delete')")
	@ApiOperation(value = "删除服务风格",
		notes = "删除已添加的服务主风格")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public CommonResult delete(@PathVariable Long id) {
		boolean flag = busSvcStyleService.deleteStyle(id);
		if (flag) {
			return CommonResultUtil.success(flag);
		}
		return CommonResultUtil.failed();
	}


	@PreAuthorize("hasAuthority('svc:style:delete')")
	@ApiOperation(value = "删除服务风格",
		notes = "删除已添加的服务风格")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult delete(@RequestBody List<BusSvcStyleDO> records) {
		// 1-- 存储待删除/不能删除的服务主题列表
		List<BusSvcStyleDO> toDeleteRecords = new ArrayList<>();
		List<BusSvcStyleDO> notDeleteRecords = new ArrayList<>();

		// 2-- 过滤：已被服务引用的服务风格
		for (BusSvcStyleDO record: records) {
			Long styleId = record.getStyleId();
			List<BusGeoSvcInfoDO> geoSvcDOs = busGeoSvcInfoService.listByStyleId(styleId);
			if (geoSvcDOs.isEmpty()) {
				toDeleteRecords.add(record);
			} else {
				notDeleteRecords.add(record);
			}
		}

		// 3-- 准备删除提示信息
		StringBuilder sb = new StringBuilder();
		if (toDeleteRecords != null && toDeleteRecords.size() != 0) {
			sb.append("【");
			for (BusSvcStyleDO tss : toDeleteRecords) {
				sb.append(tss.getName() + "，");
			}
			sb.deleteCharAt(sb.lastIndexOf("，"));
			sb.append("】，以上服务风格成功删除。" + System.getProperty("line.separator"));
		}
		if (notDeleteRecords != null && notDeleteRecords.size() != 0 ) {
			sb.append("【");
			for (BusSvcStyleDO nss : notDeleteRecords) {
				sb.append(nss.getName() + "，");
			}
			sb.deleteCharAt(sb.lastIndexOf("，"));
			sb.append("】，以上服务风格已被服务占用，无法删除！" + System.getProperty("line.separator"));
		}
		String msgStr = sb.toString();

		// 4-- 执行删除
		int resultFlag = busSvcStyleService.delete(toDeleteRecords);
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


	@PreAuthorize("hasAuthority('svc:style:view')")
	@ApiOperation(value = "获取服务风格列表",
		notes = "分页获取已添加服务风格列表")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<PageVO<BusSvcStyleDO>> list(@RequestBody PageRequestVO pageRequest) {
		PageVO<BusSvcStyleDO> curPage = busSvcStyleService.getPageList(pageRequest);
		System.out.println(JSON.toJSONString(curPage));

		return CommonResultUtil.success(curPage);
	}


	@PreAuthorize("hasAuthority('svc:style:view')")
	@ApiOperation(value = "获取单个服务风格的详细信息",
		notes = "根据id获取服务风格的详细信息")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<BusSvcStyleDO> getItem(@PathVariable Long id) {
		BusSvcStyleDO svcSubject = busSvcStyleService.getById(id);
		return CommonResultUtil.success(svcSubject);
	}


	@PreAuthorize("hasAuthority('svc:style:view')")
	@ApiOperation(value = "获取注册的服务风格列表",
		notes = "获取全部已注册服务风格列表")
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<List<BusSvcStyleDO>> getAll() {
		List<BusSvcStyleDO> list = busSvcStyleService.listAll();
		return CommonResultUtil.success(list);
	}


}

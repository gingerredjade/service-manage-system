package com.smp.admin.controller.bus;

import com.alibaba.fastjson.JSON;
import com.smp.admin.model.po.bus.svcinfo.BusGeoSvcInfoDO;
import com.smp.admin.model.po.bus.svcinfo.BusSvcTypeDO;
import com.smp.admin.service.iface.bus.BusGeoSvcInfoService;
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
 * 服务类型信息 管理Controller
 * @author Hongyu Jiang
 * @since May. 16 2020
 */
@CrossOrigin
@Controller
@Api(tags = "服务类型管理", value = "SvcTypeController",
	description = "支持服务类型的注册、查询、删除等")
@RequestMapping("/type")
public class SvcTypeController {

	/**
	 * 采用构造器方式,注入Service层
	 */
	private final BusSvcTypeService busSvcTypeService;
	private final BusGeoSvcInfoService busGeoSvcInfoService;
	@Autowired
	public SvcTypeController(
		BusSvcTypeService busSvcTypeService,
		BusGeoSvcInfoService busGeoSvcInfoService) {
		this.busSvcTypeService = busSvcTypeService;
		this.busGeoSvcInfoService = busGeoSvcInfoService;
	}


	@PreAuthorize("hasAuthority('svc:type:add') AND hasAuthority('svc:type:edit')")
	@ApiOperation(value = "注册服务类型",
		notes = "注册服务类型")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult save(@RequestBody BusSvcTypeDO record) {

		// 重复注册过滤
		List<BusSvcTypeDO> existList = busSvcTypeService.listByNameAndSubjectId(record.getName(), record.getBusSvcSubjectDO().getSubjectId());
		if (existList != null && existList.size() != 0) {
			return CommonResultUtil.failed("违反服务类型唯一性！同一服务主题下不允许同名类型重复增加");
		}

		int flag = busSvcTypeService.save(record);
		if (flag == 0) {
			return CommonResultUtil.success(null, "操作成功");
		}
		return CommonResultUtil.failed();
	}


	@PreAuthorize("hasAuthority('svc:type:delete')")
	@ApiOperation(value = "删除服务类型",
		notes = "删除已注册的服务类型")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public CommonResult delete(@PathVariable Long id) {
		boolean flag = busSvcTypeService.deleteSvcType(id);
		if (flag) {
			return CommonResultUtil.success(flag);
		}
		return CommonResultUtil.failed();
	}


	@PreAuthorize("hasAuthority('svc:type:delete')")
	@ApiOperation(value = "删除服务类型",
		notes = "删除已注册的服务类型")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult delete(@RequestBody List<BusSvcTypeDO> records) {

		// 1-- 存储待删除/不能删除的服务类型列表
		List<BusSvcTypeDO> toDeleteRecords = new ArrayList<>();
		List<BusSvcTypeDO> notDeleteRecords = new ArrayList<>();

		// 2-- 过滤：已被服务引用的类型不能删除
		for (BusSvcTypeDO record : records) {
			Long typeId = record.getTypeId();
			List<BusGeoSvcInfoDO> svcInfos = busGeoSvcInfoService.listBySvcTypeId(typeId);
			if (svcInfos.isEmpty()) {
				toDeleteRecords.add(record);
			} else {
				notDeleteRecords.add(record);
			}
		}

		// 3-- 准备删除提示信息
		StringBuilder sb = new StringBuilder();
		if (toDeleteRecords != null && toDeleteRecords.size() != 0) {
			sb.append("【");
			for (BusSvcTypeDO tst : toDeleteRecords) {
				sb.append(tst.getName() + "，");
			}
			sb.deleteCharAt(sb.lastIndexOf("，"));
			sb.append("】，以上服务类型成功删除。" + System.getProperty("line.separator"));
		}
		if (notDeleteRecords != null && notDeleteRecords.size() != 0 ) {
			sb.append("【");
			for (BusSvcTypeDO nst : notDeleteRecords) {
				sb.append(nst.getName() + "，");
			}
			sb.deleteCharAt(sb.lastIndexOf("，"));
			sb.append("】，以上服务类型已被服务占用，无法删除！" + System.getProperty("line.separator"));
		}
		String msgStr = sb.toString();

		// 4-- 执行删除
		int resultFlag = busSvcTypeService.delete(toDeleteRecords);
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


	@PreAuthorize("hasAuthority('svc:type:view')")
	@ApiOperation(value = "获取注册的服务类型列表",
		notes = "分页获取已注册服务类型列表。支持根据以下条件查询服务信息:<br />" +
			"类型名称[name]、<br />" +
			"主题名称[subjectName]")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<PageVO<BusSvcTypeDO>> list(@RequestBody PageRequestVO pageRequest) {
		PageVO<BusSvcTypeDO> curPage = busSvcTypeService.getPageList(pageRequest);
		System.out.println(JSON.toJSONString(curPage));

		return CommonResultUtil.success(curPage);
	}


	@PreAuthorize("hasAuthority('svc:type:view')")
	@ApiOperation(value = "获取注册的服务类型列表",
		notes = "过滤获取已注册服务类型列表。支持根据以下条件查询服务信息:<br />" +
			"类型名称[name]、<br />" +
			"主题名称[subjectName]")
	@RequestMapping(value = "/filter", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<List<BusSvcTypeDO>> filter(@RequestBody UniversalRequestVO requestVO) {
		List<BusSvcTypeDO> list = busSvcTypeService.getList(requestVO);
		return CommonResultUtil.success(list);
	}


	@PreAuthorize("hasAuthority('svc:type:view')")
	@ApiOperation(value = "获取单个服务类型的详细信息",
		notes = "根据id获取服务类型的详细信息")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<BusSvcTypeDO> getItem(@PathVariable Long id) {
		BusSvcTypeDO svcType = busSvcTypeService.getById(id);
		return CommonResultUtil.success(svcType);
	}


	@PreAuthorize("hasAuthority('svc:type:view')")
	@ApiOperation(value = "获取服务类型列表",
		notes = "获取全部已注册服务类型列表")
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<List<BusSvcTypeDO>> getAll() {
		List<BusSvcTypeDO> list = busSvcTypeService.listAll();
		return CommonResultUtil.success(list);
	}


	@PreAuthorize("hasAuthority('svc:type:view')")
	@ApiOperation(value = "通过服务主题ID获取服务类型列表",
		notes = "通过服务主题ID获取对应的服务类型列表")
	@RequestMapping(value = "/listBySubjectId", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<List<BusSvcTypeDO>> getBySubjectId(
		@RequestParam Long subjectId) {
		List<BusSvcTypeDO> list = busSvcTypeService.listBySubjectId(subjectId);
		return CommonResultUtil.success(list);
	}


}

package com.smp.admin.controller.sys;

import com.alibaba.fastjson.JSON;
import com.smp.admin.constant.enums.StatusEnum;
import com.smp.admin.model.po.sys.SysDictDO;
import com.smp.admin.service.iface.sys.SysDictService;
import com.smp.common.api.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典管理Controller
 * @author Hongyu Jiang
 * @since  May. 13 2020
 */
@CrossOrigin
@Controller
@Api(tags = "字典管理", value = "SysDictController",
	description = "支持字典信息的增加、查询、删除")
@RequestMapping("/dict")
public class SysDictController {

	private final SysDictService sysDictService;
	@Autowired
	public SysDictController(SysDictService sysDictService) {
		this.sysDictService = sysDictService;
	}

	@PreAuthorize("hasAuthority('sys:dict:add') AND hasAuthority('sys:dict:edit')")
	@ApiOperation(value = "添加字典",
		notes = "添加字典")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult save(@RequestBody SysDictDO record) {

		// 判断字典标识是否存在,不允许重复
		if(sysDictService.repeatByLabel(record)) {
			return CommonResultUtil.failed(ResultCode.DICT_EXIST.getCode(), ResultCode.DICT_EXIST.getMessage());
		}

		int flag = sysDictService.save(record);
		if (flag == 0) {
			return CommonResultUtil.success(flag);
		}
		return CommonResultUtil.failed();
	}

	@PreAuthorize("hasAuthority('sys:dict:delete')")
	@ApiOperation(value = "删除字典",
		notes = "删除字典")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult delete(@RequestBody List<SysDictDO> records) {
		int resultFlag = sysDictService.delete(records);
		if (resultFlag == 0) {
			return CommonResultUtil.success(resultFlag);
		} else {
			return CommonResultUtil.failed();
		}
	}

	@PreAuthorize("hasAuthority('sys:dict:view')")
	@ApiOperation(value = "获取字典列表",
		notes = "分页获取字典列表")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<PageVO<SysDictDO>> list(@RequestBody PageRequestVO pageRequest) {
		PageVO<SysDictDO> curPage = sysDictService.getPageList(pageRequest);
		System.out.println(JSON.toJSONString(curPage));

		return CommonResultUtil.success(curPage);
	}

	@PreAuthorize("hasAuthority('sys:dict:view')")
	@ApiOperation(value = "获取单个字典的详细信息",
		notes = "根据id获取字典的详细信息")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<SysDictDO> getItem(@PathVariable Long id) {
		SysDictDO svcSubject = sysDictService.getById(id);
		return CommonResultUtil.success(svcSubject);
	}

	@PreAuthorize("hasAuthority('sys:dict:view')")
	@ApiOperation(value = "通过标签获取字典列表",
		notes = "通过标签获取对应的字典列表")
	@RequestMapping(value = "/findByLabel", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<List<SysDictDO>> getByLabel(@RequestParam String label) {
		List<SysDictDO> list = sysDictService.findByLabel(label, StatusEnum.OK.getCode());
		return CommonResultUtil.success(list);
	}

}

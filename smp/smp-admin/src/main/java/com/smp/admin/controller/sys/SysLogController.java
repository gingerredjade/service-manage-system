package com.smp.admin.controller.sys;

import com.alibaba.fastjson.JSON;
import com.smp.admin.model.po.sys.SysLogDO;
import com.smp.admin.service.iface.sys.SysLogService;
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

import java.util.List;

/**
 * 操作日志管理Controller控制器
 * @author Hongyu Jiang
 * @since  May. 13 2020
 */
@CrossOrigin
@Controller
@Api(tags = "操作日志管理", value = "SysLogController",
	description = "支持操作日志信息的增加、查询、删除")
@RequestMapping("/log")
public class SysLogController {

	/**
	 * 采用构造器方式,注入Service层
	 */
	private final SysLogService sysLogService;
	@Autowired
	public SysLogController(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}

	//@PreAuthorize("hasAuthority('sys:log:add') AND hasAuthority('sys:log:edit')")
	@ApiOperation(value = "添加操作日志",
		notes = "添加操作日志")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult save(@RequestBody SysLogDO record) {
		int flag = sysLogService.save(record);
		if (flag == 0) {
			return CommonResultUtil.success(flag);
		}
		return CommonResultUtil.failed();
	}

	@PreAuthorize("hasAuthority('sys:log:delete')")
	@ApiOperation(value = "删除操作日志",
		notes = "删除操作日志")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult delete(@RequestBody List<SysLogDO> records) {
		int resultFlag = sysLogService.delete(records);
		if (resultFlag == 0) {
			return CommonResultUtil.success(resultFlag);
		} else {
			return CommonResultUtil.failed();
		}
	}

	@PreAuthorize("hasAuthority('sys:log:view')")
	@ApiOperation(value = "获取操作日志列表",
		notes = "分页获取操作日志列表")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<PageVO<SysLogDO>> list(@RequestBody PageRequestVO pageRequest) {
		PageVO<SysLogDO> curPage = sysLogService.getPageList(pageRequest);
		System.out.println(JSON.toJSONString(curPage));

		return CommonResultUtil.success(curPage);
	}

}

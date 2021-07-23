package com.smp.admin.controller.sys;

import com.alibaba.fastjson.JSON;
import com.smp.admin.model.po.sys.SysLoginLogDO;
import com.smp.admin.service.iface.sys.SysLoginLogService;
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
 * 登录日志管理Controller
 * @author Hongyu Jiang
 * @since  May. 13 2020
 */
@CrossOrigin
@Controller
@Api(tags = "登录日志管理", value = "SysLoginLogController",
	description = "支持登录日志的增加、查询、删除")
@RequestMapping("/loginlog")
public class SysLoginLogController {

	private final SysLoginLogService sysLoginLogService;
	@Autowired
	public SysLoginLogController(SysLoginLogService sysLoginLogService) {
		this.sysLoginLogService = sysLoginLogService;
	}

	@PreAuthorize("hasAuthority('sys:loginlog:delete')")
	@ApiOperation(value = "删除登录日志",
		notes = "删除登录日志")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult delete(@RequestBody List<SysLoginLogDO> records) {
		int flag = sysLoginLogService.delete(records);
		if (flag == 0) {
			return CommonResultUtil.success(flag);
		}
		return CommonResultUtil.failed();
	}

	@PreAuthorize("hasAuthority('sys:loginlog:view')")
	@ApiOperation(value = "获取登录日志列表",
		notes = "分页获取登录日志列表")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<PageVO<SysLoginLogDO>> list(@RequestBody PageRequestVO pageRequest) {
		PageVO<SysLoginLogDO> curPage = sysLoginLogService.getPageList(pageRequest);
		System.out.println(JSON.toJSONString(curPage));
		return CommonResultUtil.success(curPage);
	}


}

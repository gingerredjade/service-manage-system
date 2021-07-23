package com.smp.admin.controller.sys;

import com.alibaba.fastjson.JSON;
import com.smp.admin.constant.SysConstants;
import com.smp.admin.model.po.sys.SysMenuDO;
import com.smp.admin.model.po.sys.SysRoleDO;
import com.smp.admin.model.po.sys.SysRoleMenuDO;
import com.smp.admin.service.iface.sys.SysRoleService;
import com.smp.common.api.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理Controller
 * @author Hongyu Jiang
 * @since  May. 13 2020
 */
@CrossOrigin
@Controller
@Api(tags = "角色管理", value = "SysRoleController",
	description = "支持角色信息的增加、查询、删除等管理")
@RequestMapping("/role")
public class SysRoleController {

	private final SysRoleService sysRoleService;
	@Autowired
	public SysRoleController(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	@PreAuthorize("hasAuthority('sys:role:add') AND hasAuthority('sys:role:edit')")
	@ApiOperation(value = "添加角色", notes = "添加角色")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult save(@RequestBody SysRoleDO record) {

		/*
		 * 1-- 检测是否为超级管理员
		 */
		SysRoleDO role = sysRoleService.getById(record.getId());
		if (role != null) {
			if (SysConstants.ADMIN.equalsIgnoreCase(role.getName())) {
				return CommonResultUtil.failed(ResultCode.NO_ADMIN_AUTH.getCode(),
					ResultCode.NO_ADMIN_AUTH.getMessage());
			}
		}

		/*
		 * 2-- 检测角色名是否已存在
		 */
		if ((record.getId() == null || record.getId() == 0) &&
			!sysRoleService.findByName(record.getName()).isEmpty()) {
			return CommonResultUtil.failed(ResultCode.ROLE_EXIST.getCode(),
				ResultCode.ROLE_EXIST.getMessage());
		}

		/*
		 * 3-- 新增角色
		 */
		int flag = sysRoleService.save(record);
		if (flag == 0) {
			return CommonResultUtil.success(flag);
		}
		return CommonResultUtil.failed();
	}


	@PreAuthorize("hasAuthority('sys:role:delete')")
	@ApiOperation(value = "删除角色", notes = "删除角色")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult delete(@RequestBody List<SysRoleDO> records) {
		/*
		 * 1-- 过滤：超级管理员不予删除
		 */
		for (SysRoleDO record : records) {
			SysRoleDO role = sysRoleService.getById(record.getId());
			if (role != null) {
				if (SysConstants.ADMIN.equalsIgnoreCase(role.getName())) {
					return CommonResultUtil.failed(ResultCode.NO_ADMIN_AUTH.getCode(),
						ResultCode.NO_ADMIN_AUTH.getMessage());
				}
			}
		}

		/*
		 * 2-- 执行删除
		 */
		int resultFlag = sysRoleService.delete(records);
		if (resultFlag == 0) {
			return CommonResultUtil.success(resultFlag);
		} else {
			return CommonResultUtil.failed();
		}
	}


	@PreAuthorize("hasAuthority('sys:role:view')")
	@ApiOperation(value = "获取角色列表",
		notes = "分页获取角色列表")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<PageVO<SysRoleDO>> list(@RequestBody PageRequestVO pageRequest) {
		PageVO<SysRoleDO> curPage = sysRoleService.getPageList(pageRequest);
		System.out.println(JSON.toJSONString(curPage));

		return CommonResultUtil.success(curPage);
	}


	@PreAuthorize("hasAuthority('sys:role:view')")
	@ApiOperation(value = "获取单个角色的详细信息",
		notes = "根据id获取角色的详细信息")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<SysRoleDO> getItem(@PathVariable Long id) {
		SysRoleDO role = sysRoleService.getById(id);
		return CommonResultUtil.success(role);
	}


	@PreAuthorize("hasAuthority('sys:role:view')")
	@ApiOperation(value = "获取角色列表",
		notes = "获取全部角色列表")
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<List<SysRoleDO>> getAll() {
		List<SysRoleDO> list = sysRoleService.listAll();
		return CommonResultUtil.success(list);
	}


	@PreAuthorize("hasAuthority('sys:role:view')")
	@ApiOperation(value = "通过角色编号获取角色菜单列表",
		notes = "通过角色编号获取角色菜单列表")
	@RequestMapping(value = "/findRoleMenus", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<List<SysMenuDO>> findRoleMenus(@RequestParam Long roleId) {
		return CommonResultUtil.success(sysRoleService.findRoleMenus(roleId));
	}


	@PreAuthorize("hasAuthority('sys:role:view')")
	@ApiOperation(value = "保存角色菜单",
		notes = "保存角色菜单")
	@RequestMapping(value = "/saveRoleMenus", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<Integer> saveRoleMenus(@RequestBody List<SysRoleMenuDO> records) {

		for (SysRoleMenuDO record : records) {
			SysRoleDO sysRole = sysRoleService.getById(record.getRoleId());
			if (SysConstants.ADMIN.equalsIgnoreCase(sysRole.getName())) {
				// 如果是超级管理员，不允许修改
				return CommonResultUtil.failed(ResultCode.NO_ADMINROLEMENU_STATUS.getCode(),
					ResultCode.NO_ADMINROLEMENU_STATUS.getMessage());
			}
		}
		int flag = sysRoleService.saveRoleMenus(records);
		if (flag == 0) {
			return CommonResultUtil.success(flag);
		} else {
			return CommonResultUtil.failed();
		}
	}


}


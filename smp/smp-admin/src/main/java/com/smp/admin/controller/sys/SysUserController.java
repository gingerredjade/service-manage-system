package com.smp.admin.controller.sys;

import com.alibaba.fastjson.JSON;
import com.smp.admin.constant.SysConstants;
import com.smp.admin.constant.enums.StatusEnum;
import com.smp.admin.model.po.sys.SysUserDO;
import com.smp.admin.model.po.sys.SysUserRoleDO;
import com.smp.admin.service.iface.sys.SysUserService;
import com.smp.admin.util.StatusUtil;
import com.smp.common.api.*;
import com.smp.common.util.password.PasswordUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 用户管理Controller控制器
 * @author Hongyu Jiang
 * @since  May. 13 2020
 */
@CrossOrigin
@Controller
@Api(tags = "用户管理", value = "SysUserController",
	description = "支持用户信息的增加、查询、删除")
@RequestMapping("/user")
public class SysUserController {

	private final SysUserService sysUserService;
	@Autowired
	public SysUserController(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@PreAuthorize("hasAuthority('sys:user:add') AND hasAuthority('sys:user:edit')")
	@ApiOperation(value = "添加用户", notes = "添加用户")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult save(@RequestBody SysUserDO record) {

		/*
		 * 1-- 检测是否为超级管理员
		 */
		SysUserDO user = sysUserService.getById(record.getId());
		/*if (user != null) {
			if (SysConstants.ADMIN.equalsIgnoreCase(user.getUsername())) {
				// 超级管理员不允许修改
				return CommonResultUtil.failed(ResultCode.NO_ADMIN_AUTH.getCode(),
					ResultCode.NO_ADMIN_AUTH.getMessage());
			}
		}*/

		/*
		 * 2-- 检测是否修改密码
		 */
		if (record.getPassword() != null) {
			String salt = PasswordUtils.getSalt();
			if (user == null) {
				// 新增用户
				if (sysUserService.getByName(record.getUsername()) != null) {
					// 检测是否重名，不允许重名
					return CommonResultUtil.failed(ResultCode.USER_EXIST.getCode(),
						ResultCode.USER_EXIST.getMessage());
				}
				// TODO.. 参Timo\coral 使用的是Shiro的密码加密     mango用的Spring Security
				// 对密码进行加密
				String password = PasswordUtils.encode(record.getPassword(), salt);
				record.setSalt(salt);
				record.setPassword(password);
			} else {
				// 修改用户，且修改了密码
				if (!record.getPassword().equals(user.getPassword())) {
					// 对密码进行加密
					String password = PasswordUtils.encode(record.getPassword(), salt);
					record.setSalt(salt);
					record.setPassword(password);
				}
			}
		}

		/*
		 * 3-- 执行用户新增
		 */
		int flag = sysUserService.save(record);
		if (flag == 0) {
			return CommonResultUtil.success(flag);
		}
		return CommonResultUtil.failed();
	}

	@PreAuthorize("hasAuthority('sys:user:delete')")
	@ApiOperation(value = "删除用户", notes = "删除用户")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult delete(@RequestBody List<SysUserDO> records) {
		for (SysUserDO record : records) {
			SysUserDO sysUser = sysUserService.getById(record.getId());
			if (sysUser != null && SysConstants.ADMIN.equalsIgnoreCase(sysUser.getUsername())) {
				// 超级管理员不允许删除
				return CommonResultUtil.failed(ResultCode.NO_ADMIN_AUTH.getCode(),
					ResultCode.NO_ADMIN_AUTH.getMessage());
			}
		}

		int resultFlag = sysUserService.delete(records);
		if (resultFlag == 0) {
			return CommonResultUtil.success(resultFlag);
		} else {
			return CommonResultUtil.failed();
		}
	}

	@PreAuthorize("hasAuthority('sys:user:view')")
	@ApiOperation(value = "通过用户名查找用户", notes = "通过用户名查找用户")
	@RequestMapping(value = "/findByUsername", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<SysUserDO> findByUserName(@RequestParam String userName) {
		SysUserDO user = sysUserService.getByName(userName);
		return CommonResultUtil.success(user);
	}

	@PreAuthorize("hasAuthority('sys:user:view')")
	@ApiOperation(value = "查找用户的菜单权限标识集合",
		notes = "通过用户名查找用户的菜单权限标识集合")
	@RequestMapping(value = "/findPermissions", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<Set<String>> findPermissions(@RequestParam String userName) {
		return CommonResultUtil.success(sysUserService.getPermissions(userName));
	}

	@PreAuthorize("hasAuthority('sys:user:view')")
	@ApiOperation(value = "查找用户的用户角色集合",
		notes = "通过用户编号查找用户的用户角色集合")
	@RequestMapping(value = "/findUserRoles", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<List<SysUserRoleDO>> findUserRoles(@RequestParam Long userId) {
		return CommonResultUtil.success(sysUserService.findUserRoles(userId));
	}

	@PreAuthorize("hasAuthority('sys:user:view')")
	@ApiOperation(value = "获取用户列表",
		notes = "分页获取用户列表")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<PageVO<SysUserDO>> list(@RequestBody PageRequestVO pageRequest) {
		PageVO<SysUserDO> curPage = sysUserService.getPageList(pageRequest);
		System.out.println(JSON.toJSONString(curPage));

		return CommonResultUtil.success(curPage);
	}

	@PreAuthorize("hasAuthority('sys:user:view')")
	@ApiOperation(value = "获取用户列表",
		notes = "获取全部用户列表")
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<List<SysUserDO>> getAll() {
		List<SysUserDO> list = sysUserService.listAll();
		return CommonResultUtil.success(list);
	}

	@PreAuthorize("hasAuthority('sys:user:view')")
	@ApiOperation(value = "获取单个用户的详细信息",
		notes = "根据id获取用户的详细信息")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<SysUserDO> getItem(@PathVariable Long id) {
		SysUserDO user = sysUserService.getById(id);
		return CommonResultUtil.success(user);
	}

	@ApiOperation(value = "设置用户状态",
		notes = "设置一条或者多条数据的状态")
	@RequestMapping(value = "/status/{param}", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<String> updateStatus(@PathVariable String param,
									   @RequestParam(value="ids", required = false) List<Long> ids) {
		// 不能修改超级管理员状态
		if (ids.contains(SysConstants.ADMIN_ID)) {
			return CommonResultUtil.failed(ResultCode.NO_ADMIN_STATUS.getCode(),
				ResultCode.NO_ADMIN_STATUS.getMessage());
		}

		// 更新状态
		StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
		if (sysUserService.updateStatus(statusEnum, ids)) {
			return CommonResultUtil.success(statusEnum.getMessage() + "成功");
		} else {
			return CommonResultUtil.failed(statusEnum.getMessage() + "失败，请重新操作");
		}
	}


	public CommonResult updatePassword(@RequestParam String password,
									   @RequestParam String newPassword) {
		// TODO.. 跟权限控制相关，参mango-demo user controller
		return null;
	}


	@ApiOperation(value = "统计用户总数量",
		notes = "统计用户总数量")
	@RequestMapping(value = "/countUserGross", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult countSvcByState() {
		Long userGross = sysUserService.countUserGross();
		return CommonResultUtil.success(userGross);
	}

}

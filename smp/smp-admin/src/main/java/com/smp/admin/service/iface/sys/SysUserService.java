package com.smp.admin.service.iface.sys;

import com.smp.admin.constant.enums.StatusEnum;
import com.smp.admin.model.po.sys.SysUserDO;
import com.smp.admin.model.po.sys.SysUserRoleDO;
import com.smp.admin.service.iface.base.CurdService;

import java.util.List;
import java.util.Set;

/**
 * 用户管理Service
 * @author Hongyu Jiang
 * @since Apr. 27 2020
 */
public interface SysUserService extends CurdService<SysUserDO> {

	/**
	 * 保存用户列表
	 * @param userList 用户实体列表
	 * @return 用户数据列表
	 */
	List<SysUserDO> save(List<SysUserDO> userList);

	/**
	 * 通过用户名查找用户
	 * @param userName 用户名
	 * @return 用户数据
	 */
	SysUserDO getByName(String userName);

	/**
	 * 查找用户的菜单权限标识集合
	 * @param userName 用户名
	 * @return 菜单权限标识集合
	 */
	Set<String> getPermissions(String userName);

	/**
	 * 通过用户编号查找用户的角色集合
	 * @param userId 用户编号
	 * @return 用户数据列表
	 */
	List<SysUserRoleDO> findUserRoles(Long userId);

	/**
	 * 用户名是否重复
	 * @param userDO 用户对象
	 * @return 是否重复
	 */
	Boolean repeatByUserName(SysUserDO userDO);

	/**
	 * 状态（启用，冻结，删除）/批量状态处理
	 * @param statusEnum 数据状态
	 * @param idList 数据ID列表
	 * @return 操作结果
	 */
	Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);

	/**
	 * 查询所有用户信息
	 * @return 用户信息列表
	 */
	List<SysUserDO> listAll();

	/**
	 * 统计所有用户数量
	 * @return 用户数量
	 */
	Long countUserGross();
}

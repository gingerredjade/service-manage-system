package com.smp.admin.service.iface.sys;

import com.smp.admin.model.po.sys.SysMenuDO;
import com.smp.admin.model.po.sys.SysRoleDO;
import com.smp.admin.model.po.sys.SysRoleMenuDO;
import com.smp.admin.service.iface.base.CurdService;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 角色管理Service
 * @author Hongyu Jiang
 * @since Apr. 27 2020
 */
public interface SysRoleService extends CurdService<SysRoleDO> {

	/**
	 * 查询所有角色信息
	 * @return 角色信息列表
	 */
	List<SysRoleDO> listAll();

	/**
	 * 根据名称查询角色信息
	 * @param name 角色名称
	 * @return 角色信息列表
	 */
	List<SysRoleDO> findByName(String name);

	/**
	 * 通过角色编号查询角色对应的菜单集合
	 * @param roleId 角色编号
	 * @return 菜单集合
	 */
	List<SysMenuDO> findRoleMenus(Long roleId);

	/**
	 * 保存角色菜单
	 * @param records 角色菜单信息列表
	 * @return 是否保存成功
	 */
	int saveRoleMenus(List<SysRoleMenuDO> records);

	/**
	 * 判断指定的用户是否存在角色
	 * @param id 用户ID
	 * @return 是否存在角色
	 */
	Boolean existsUserOk(Long id);

	/**
	 * 获取角色列表数据
	 * @param sort 排序对象
	 * @return 角色列表
	 */
	List<SysRoleDO> getListBySortOk(Sort sort);

	/**
	 * 角色标识是否重复
	 * @param role 角色实体类
	 * @return 标识是否重复
	 */
	boolean repeatByName(SysRoleDO role);




}

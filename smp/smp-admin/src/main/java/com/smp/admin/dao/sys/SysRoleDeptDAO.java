package com.smp.admin.dao.sys;

import com.smp.admin.model.po.sys.SysRoleDeptDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 角色和机构的管理DAO接口及其实现
 * 	使用JPA的DAO层用命名法，免去SQL编写。
 *
 * @author Hongyu Jiang
 * @since Apr. 27 2020
 */
public interface SysRoleDeptDAO extends JpaRepository<SysRoleDeptDO, Long> {






}

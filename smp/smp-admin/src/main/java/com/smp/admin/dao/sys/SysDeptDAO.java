package com.smp.admin.dao.sys;

import com.smp.admin.model.po.sys.SysDeptDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 机构管理DAO接口及其实现
 * 	使用JPA的DAO层用命名法，免去SQL编写。
 *
 * @author Hongyu Jiang
 * @since Apr. 27 2020
 */
public interface SysDeptDAO extends JpaRepository<SysDeptDO, Long>, JpaSpecificationExecutor<SysDeptDO> {

	// 通过机构名称查询机构信息列表
	@Query(value = "select dept from SysDeptDO dept where dept.name like %?1%")
	List<SysDeptDO> listByDeptName(String name);



}

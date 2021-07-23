package com.smp.admin.dao.bus;

import com.smp.admin.model.po.bus.svcinfo.BusSvcSubjectDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 服务主题管理DAO接口及其实现
 * 	使用JPA的DAO层用命名法，免去SQL编写。
 *
 * @author Hongyu Jiang
 * @since May. 16 2020
 */
public interface BusSvcSubjectDAO extends JpaRepository<BusSvcSubjectDO, Long> {

	// 根据主题名称模糊查询
	@Query(value = "select ss from BusSvcSubjectDO ss where ss.delFlag=1 and (ss.name like %?1%)")
	Page<BusSvcSubjectDO> listBySvcSubjectName(String name, PageRequest pageRequest);

	// 根据主题名称模糊查询
	@Query(value = "select ss from BusSvcSubjectDO ss where ss.delFlag=1 and (ss.name like %?1%)")
	List<BusSvcSubjectDO> listBySvcSubjectName(String name);

	//usr.userName like %?1% or usr.account like %?1%

	// 通过服务主题名称查询服务主题信息列表
	@Query(value = "select ss from BusSvcSubjectDO ss where ss.name=:name")
	List<BusSvcSubjectDO> findBySubjectName(String name);

}

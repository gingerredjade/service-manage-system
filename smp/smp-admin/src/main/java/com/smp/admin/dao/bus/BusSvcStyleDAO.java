package com.smp.admin.dao.bus;


import com.smp.admin.model.po.bus.svcinfo.BusSvcStyleDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * 服务风格管理DAO接口及其实现
 * 	使用JPA的DAO层用命名法，免去SQL编写。
 *
 * @author Hongyu Jiang
 * @since Nov. 18 2020
 */
public interface BusSvcStyleDAO extends JpaRepository<BusSvcStyleDO, Long> {

	// 根据风格名称模糊查询
	@Query(value = "select ss from BusSvcStyleDO ss where ss.delFlag=1 and (ss.name like %?1%)")
	Page<BusSvcStyleDO> listBySvcStyleName(String name, PageRequest pageRequest);

	// 通过服务风格名称查询服务风格信息列表
	@Query(value = "select ss from BusSvcStyleDO ss where ss.name=:name")
	List<BusSvcStyleDO> findByStyleName(String name);

}

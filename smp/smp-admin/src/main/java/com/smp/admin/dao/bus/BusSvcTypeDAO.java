package com.smp.admin.dao.bus;

import com.smp.admin.model.po.bus.svcinfo.BusSvcTypeDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 服务类型管理DAO接口及其实现
 * 	使用JPA的DAO层用命名法，可免去SQL编写。
 *
 * @author Hongyu Jiang
 * @since May. 16 2020
 */
public interface BusSvcTypeDAO extends JpaRepository<BusSvcTypeDO, Long> {

	// 根据服务主题编号查询服务类型
	@Query("select st from BusSvcTypeDO st where st.busSvcSubjectDO.subjectId=?1")
	List<BusSvcTypeDO> listBySvcSubjectId(Long subjectId);

	// 根据服务类型名称模糊查询
	@Query(value = "select st from BusSvcTypeDO st where st.delFlag=1 and (st.name like %?1%)")
	Page<BusSvcTypeDO> listBySvcTypeName(String name, PageRequest pageRequest);

	// 根据服务类型名称模糊查询
	@Query(value = "select st from BusSvcTypeDO st where st.delFlag=1 and (st.name like %?1%)")
	List<BusSvcTypeDO> listBySvcTypeName(String name);

	// 根据服务主题名称模糊查询服务类型
	@Query(value = "select st from BusSvcTypeDO st where st.delFlag=1 and (st.busSvcSubjectDO.name like %?1%)")
	Page<BusSvcTypeDO> listBySvcSubjectName(String subjectName, PageRequest pageRequest);

	// 根据服务主题名称模糊查询服务类型
	@Query(value = "select st from BusSvcTypeDO st where st.delFlag=1 and (st.busSvcSubjectDO.name like %?1%)")
	List<BusSvcTypeDO> listBySvcSubjectName(String subjectName);

	// 根据服务类型名称和服务主题名称查询服务类型
	@Query(value = "select st from BusSvcTypeDO st where st.delFlag=1 and (st.name like %?1% and st.busSvcSubjectDO.name like %?2%)")
	Page<BusSvcTypeDO> listBySvcTypeAndSubjectName(String name, String subjectName, PageRequest pageRequest);

	// 根据服务类型名称和服务主题名称查询服务类型
	@Query(value = "select st from BusSvcTypeDO st where st.delFlag=1 and (st.name like %?1% and st.busSvcSubjectDO.name like %?2%)")
	List<BusSvcTypeDO> listBySvcTypeAndSubjectName(String name, String subjectName);

	// 根据服务类型名称、服务主题编号查询服务类型
	@Query("select st from BusSvcTypeDO st where st.name=?1 and st.busSvcSubjectDO.subjectId=?2")
	List<BusSvcTypeDO> listByNameAndSubjectId(String name, Long subjectId);
}

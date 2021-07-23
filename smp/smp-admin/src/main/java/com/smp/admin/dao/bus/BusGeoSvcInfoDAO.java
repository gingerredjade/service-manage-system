package com.smp.admin.dao.bus;

import com.smp.admin.model.po.bus.svcinfo.BusGeoSvcInfoDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 地理信息服务信息 管理DAO接口及其实现
 * 	使用JPA的DAO层用命名法，免去SQL编写。
 * 	涉及多条件查询，须继承JpaSpecificationExecutor。
 *
 * @author Hongyu Jiang
 * @since Apr. 29 2020
 */
public interface BusGeoSvcInfoDAO extends JpaRepository<BusGeoSvcInfoDO, Long>, JpaSpecificationExecutor<BusGeoSvcInfoDO> {

	// 通过服务类型编号查询服务信息列表
	@Query(value = "select gsi from BusGeoSvcInfoDO gsi where gsi.svcTypeDO.typeId=:svcTypeId")
	List<BusGeoSvcInfoDO> listBySvcTypeId(Long svcTypeId);

	// 通过服务类型编号查询指定发布状态的服务信息列表
	@Query(value = "select gsi from BusGeoSvcInfoDO gsi where gsi.svcTypeDO.typeId=:svcTypeId and gsi.releaseState=:releaseState")
	List<BusGeoSvcInfoDO> listReleasedBySvcTypeId(Long svcTypeId, Byte releaseState);

	// 通过机构编号查询服务信息列表
	@Query(value = "select gsi from BusGeoSvcInfoDO gsi where gsi.sysDeptDO.id=:deptId")
	List<BusGeoSvcInfoDO> listByDeptId(Long deptId);

	/**
	 * 更新数据审核状态、审核意见
	 * @param status 状态
	 * @param id ID
	 * @param auditOpinion 审核意见
	 * @return 更新数量
	 */
	@Modifying
	@Transactional
	@Query(
		value = "update bus_svc_info_geo set audit_state = ?1, audit_opinion = ?3 where svc_id = ?2 ",
		nativeQuery = true)
	public Integer updateAuditStatus(Byte status, Long id, String auditOpinion);

	/**
	 * 更新数据发布状态
	 * @param status 状态
	 * @param id ID列表
	 * @return 更新数量
	 */
	@Modifying
	@Transactional
	@Query(
		value = "update bus_svc_info_geo set release_state = ?1  where svc_id in ?2 ",
		nativeQuery = true)
	public Integer updateReleaseStatus(Byte status, List<Long> id);


	/**
	 * 根据是否是地理信息服务统计对应的服务数量
	 * @return 统计结果
	 */
	@Query("select svc.isGisSvc, count(svc.isGisSvc) from BusGeoSvcInfoDO svc group by svc.isGisSvc")
	List countByGisOrNo();


	/**
	 * 根据服务主题统计对应的服务数量
	 * @return 统计结果
	 */
	@Query("select svc.svcTypeDO.busSvcSubjectDO.name, count(svc.svcTypeDO.busSvcSubjectDO.name) from BusGeoSvcInfoDO svc group by svc.svcTypeDO.busSvcSubjectDO.name")
	List countBySubject();

	/**
	 * 根据服务类型统计对应的服务数量
	 * @return 统计结果
	 */
	@Query("select svc.svcTypeDO.name, count(svc.svcTypeDO.name) from BusGeoSvcInfoDO svc group by svc.svcTypeDO.name")
	List countByType();

	/**
	 * 根据机构统计对应的服务数量
	 * @return 统计结果
	 */
	@Query("select svc.sysDeptDO.name, count(svc.sysDeptDO.name) from BusGeoSvcInfoDO svc group by svc.sysDeptDO.name")
	List countByDept();


	/**
	 * 统计所有服务数量
	 * @return 统计结果
	 */
	@Query("select count(svc.svcId) from BusGeoSvcInfoDO svc")
	Long countSvcGross();

	/**
	 * 统计 审核通过/待审核 的服务数量
	 * @return 统计结果
	 */
	@Query("select count(svc.svcId) from BusGeoSvcInfoDO svc where svc.auditState=:auditState")
	Long countSvcByAuditState(Byte auditState);

	/**
	 * 统计 未发布/发布 的 服务数量
	 * @return 统计结果
	 */
	@Query("select count(svc.svcId) from BusGeoSvcInfoDO svc where svc.releaseState=:releaseState")
	Long countSvcByReleaseState(Byte releaseState);

	/**
	 * 统计 未发布/发布 and 未审核/审核通过/审核拒绝 的 服务数量
	 * @param releaseState
	 * @param auditState
	 * @return 统计结果
	 */
	@Query("select count(svc.svcId) from BusGeoSvcInfoDO svc where svc.releaseState=:releaseState and svc.auditState=:auditState")
	Long countSvcByReleaseStateAndAuditState(Byte releaseState, Byte auditState);


	/**
	 * 根据条件筛选服务信息
	 * @param terminal 适用终端类型
	 * @param deptId 所属机构编号
	 * @param svcSubjectId 主题编号
	 * @param svcTypeId 类型编号
	 * @param svcName 服务名称
	 * @param svcVersion 服务版本
	 * @return 服务信息列表
	 */
	@Query(value = "select gsi from BusGeoSvcInfoDO gsi where " +
		"gsi.terminal=:terminal " +
		"and gsi.sysDeptDO.id=:deptId " +
		"and gsi.svcTypeDO.typeId=:svcTypeId " +
		"and gsi.svcTypeDO.busSvcSubjectDO.subjectId=:svcSubjectId " +
		"and gsi.svcName=:svcName and gsi.svcVersion=:svcVersion")
	List<BusGeoSvcInfoDO> listByConditions(Integer terminal, Long deptId,
						  Long svcSubjectId, Long svcTypeId,
						  String svcName, String svcVersion);

	// 根据服务风格编号查询服务信息
	@Query("select gsi from BusGeoSvcInfoDO gsi where gsi.svcStyleDO.styleId=?1")
	List<BusGeoSvcInfoDO> listBySvcStyleId(Long styleId);


}

package com.smp.admin.service.iface.bus;

import com.smp.admin.constant.enums.AuditTypeEnum;
import com.smp.admin.constant.enums.ReleaseTypeEnum;
import com.smp.admin.model.po.bus.svcinfo.BusGeoSvcInfoDO;
import com.smp.admin.service.iface.base.CurdService;
import com.smp.common.api.UniversalRequestVO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 地理信息服务信息 管理Service
 * @author Hongyu Jiang
 * @since May. 12 2020
 */
public interface BusGeoSvcInfoService extends CurdService<BusGeoSvcInfoDO> {

	/**
	 * 根据注册信息id删除注册信息
	 * @param id 注册信息id
	 * @return 是否删除成功
	 */
	@Transactional
	boolean deleteSvcInfo(Long id);

	/**
	 * 分页获取注册信息列表
	 * @param pageNum 页码
	 * @param pageSize 每页几条数据
	 * @return 注册信息列表
	 */
	Page<BusGeoSvcInfoDO> listSvcInfos(Integer pageNum, Integer pageSize);

	/**
	 * 查询所有注册信息
	 * @return 注册信息列表
	 */
	List<BusGeoSvcInfoDO> listAll();

	/**
	 * 根据服务类型查询服务信息
	 * @param svcTypeId 服务类型编号
	 * @return 服务信息列表
	 */
	List<BusGeoSvcInfoDO> listBySvcTypeId(Long svcTypeId);

	/**
	 * 根据机构编号查询服务信息
	 * @param deptId 机构编号
	 * @return 服务信息列表
	 */
	List<BusGeoSvcInfoDO> listByDeptId(Long deptId);

	/**
	 * 审核状态（未审核/审核通过/审核拒绝）处理
	 * @param auditTypeEnum 数据审核状态
	 * @param id 数据ID
	 * @param auditOpinion 审核意见
	 * @return 操作结果
	 */
	Boolean updateAuditStatusAndOpinion(AuditTypeEnum auditTypeEnum, Long id, String auditOpinion);

	/**
	 * 发布状态（未发布/已发布）处理
	 * @param ReleaseTypeEnum 数据发布状态
	 * @param id 数据ID
	 * @return 操作结果
	 */
	Boolean updateReleaseStatus(ReleaseTypeEnum ReleaseTypeEnum, List<Long> id);

	/**
	 * 获取全部服务目录
	 * @return 服务目录集合
	 */
	List getCatalogAll();


	/**
	 * 根据条件查询对应服务信息
	 * @param terminal 所属终端类型
	 * @param deptId 机构编号
	 * @param svcSubjectId 主题编号
	 * @param svcTypeId 类型编号
	 * @param svcName 服务名称
	 * @param svcVersion 服务版本
	 * @return 服务信息列表
	 */
	List<BusGeoSvcInfoDO> listByConditions(Integer terminal,
										   Long deptId,
										   Long svcSubjectId, Long svcTypeId,
										   String svcName, String svcVersion);

	/**
	 * 通过服务风格编号获取服务类信息列表
	 * @param styleId 服务风格编号
	 * @return 服务信息列表
	 */
	List<BusGeoSvcInfoDO> listByStyleId(Long styleId);


	/**
	 * 过滤查询
	 * @param requestVO 自定义，统一查询请求
	 * @return 服务信息列表
	 */
	List<BusGeoSvcInfoDO> getList(UniversalRequestVO requestVO);


}

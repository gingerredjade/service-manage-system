package com.smp.admin.service.iface.bus;

import com.smp.admin.model.vo.ServiceStatVO;


/**
 * 服务统计管理Service
 * @author Hongyu Jiang
 * @since Sep. 7 2020
 */
public interface BusSvcStatService {

	/**
	 * 根据服务主题统计对应的服务数量
	 * @return 统计结果
	 */
	ServiceStatVO statBySubject();

	/**
	 * 根据服务类型统计对应的服务数量
	 * @return 统计结果
	 */
	ServiceStatVO statByType();

	/**
	 * 根据机构统计对应的服务数量
	 * @return 统计结果
	 */
	ServiceStatVO statByDept();

	/**
	 * 根据适用终端类型统计对应的服务数量
	 * @return 统计结果
	 */
	ServiceStatVO statByTerminal();

	/**
	 * 根据 地理信息服务/非地理信息服务 统计对应的服务数量
	 * @return 统计结果
	 */
	ServiceStatVO statByGisOrNo();


	/**
	 * 统计所有服务数量
	 * @return 服务数量
	 */
	Long countSvcGross();

	/**
	 * 统计未发布的服务数量
	 * @return 服务数量
	 */
	Long countSvcByUnreleased();

	/**
	 * 统计已发布的服务数量
	 * @return 服务数量
	 */
	Long countSvcByReleased();

	/**
	 * 统计审核通过的服务数量
	 * @return 服务数量
	 */
	Long countSvcByAuditedPass();

	/**
	 * 统计待审核的服务数量
	 * @return 服务数量
	 */
	Long countSvcByUnaudited();

}

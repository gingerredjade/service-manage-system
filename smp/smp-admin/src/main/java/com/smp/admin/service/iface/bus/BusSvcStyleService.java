package com.smp.admin.service.iface.bus;


import com.smp.admin.model.po.bus.svcinfo.BusSvcStyleDO;
import com.smp.admin.service.iface.base.CurdService;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务风格管理Service
 * @author Hongyu Jiang
 * @since Nov. 18 2020
 */
public interface BusSvcStyleService extends CurdService<BusSvcStyleDO> {

	/**
	 * 分页获取服务主风格信息列表
	 * @param pageNum 页码
	 * @param pageSize 每页几条数据
	 * @return 服务风格信息列表
	 */
	Page<BusSvcStyleDO> listSvcStyle(Integer pageNum, Integer pageSize);

	/**
	 * 根据服务风格id删除服务风格信息
	 * @param id 服务主题id
	 * @return 是否删除成功
	 */
	@Transactional
	boolean deleteStyle(Long id);

	/**
	 * 查询所有服务风格信息
	 * @return 服务风格信息列表
	 */
	List<BusSvcStyleDO> listAll();

	/**
	 * 根据服务风格名称查询对应的服务风格信息
	 * @param name 服务风格名称
	 * @return 服务风格信息列表
	 */
	List<BusSvcStyleDO> listByStyleName(String name);

}

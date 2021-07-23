package com.smp.admin.service.iface.bus;

import com.smp.admin.model.po.bus.svcinfo.BusSvcTypeDO;
import com.smp.admin.service.iface.base.CurdService;
import com.smp.common.api.UniversalRequestVO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务类型管理Service
 * @author Hongyu Jiang
 * @since May. 16 2020
 */
public interface BusSvcTypeService extends CurdService<BusSvcTypeDO> {

	/**
	 * 分页获取服务类型信息列表
	 * @param pageNum 页码
	 * @param pageSize 每页几条数据
	 * @return 服务类型信息列表
	 */
	Page<BusSvcTypeDO> listSvcTypes(Integer pageNum, Integer pageSize);


	/**
	 * 通过服务主题编号获取服务类型信息列表
	 * @param subjectId 服务主题编号
	 * @return 服务类型信息列表
	 */
	List<BusSvcTypeDO> listBySubjectId(Long subjectId);

	/**
	 * 查询所有服务类型信息
	 * @return 服务类型信息列表
	 */
	List<BusSvcTypeDO> listAll();

	/**
	 * 根据服务类型信息id删除服务类型信息
	 * @param id 服务类型信息id
	 * @return 是否删除成功
	 */
	@Transactional
	boolean deleteSvcType(Long id);


	/**
	 * 根据类型名称、所属主题编号查询对应的类型信息
	 * @param name 类型名称
	 * @param subjectId 主题编号
	 * @return 服务类型信息列表
	 */
	List<BusSvcTypeDO> listByNameAndSubjectId(String name, Long subjectId);

	/**
	 * 过滤查询
	 * @param requestVO 自定义，统一查询请求
	 * @return 服务信息列表
	 */
	List<BusSvcTypeDO> getList(UniversalRequestVO requestVO);
}

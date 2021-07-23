package com.smp.admin.service.iface.bus;

import com.smp.admin.model.po.bus.svcinfo.BusSvcSubjectDO;
import com.smp.admin.service.iface.base.CurdService;
import com.smp.common.api.UniversalRequestVO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务主题管理Service
 * @author Hongyu Jiang
 * @since May. 16 2020
 */
public interface BusSvcSubjectService extends CurdService<BusSvcSubjectDO> {

	/**
	 * 分页获取服务主题信息列表
	 * @param pageNum 页码
	 * @param pageSize 每页几条数据
	 * @return 服务主题信息列表
	 */
	Page<BusSvcSubjectDO> listSvcSubject(Integer pageNum, Integer pageSize);

	/**
	 * 根据服务主题id删除服务主题信息
	 * @param id 服务主题id
	 * @return 是否删除成功
	 */
	@Transactional
	boolean deleteSubject(Long id);

	/**
	 * 查询所有服务主题信息
	 * @return 服务主题信息列表
	 */
	List<BusSvcSubjectDO> listAll();

	/**
	 * 根据服务主题名称查询对应的服务主题信息
	 * @param name 服务主题名称
	 * @return 服务主题信息列表
	 */
	List<BusSvcSubjectDO> listBySubjectName(String name);

	/**
	 * 过滤查询
	 * @param requestVO 自定义，统一查询请求
	 * @return 服务主题列表
	 */
	List<BusSvcSubjectDO> getList(UniversalRequestVO requestVO);

}

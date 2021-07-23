package com.smp.admin.service.iface.sys;

import com.smp.admin.model.po.sys.SysDeptDO;
import com.smp.admin.service.iface.base.CurdService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 机构管理Service
 * @author Hongyu Jiang
 * @since Apr. 27 2020
 */
public interface SysDeptService extends CurdService<SysDeptDO> {

	/**
	 * 查询机构树
	 * @return 返回机构列表
	 */
	List<SysDeptDO> findTree();

	/**
	 * 分页获取机构信息列表
	 * @param pageNum 页码
	 * @param pageSize 每页几条数据
	 * @return 机构信息列表
	 */
	Page<SysDeptDO> listSysDept(Integer pageNum, Integer pageSize);

	/**
	 * 查询机构树
	 * @return 返回机构列表
	 */
	List<SysDeptDO> findTreeByName(String name);

}

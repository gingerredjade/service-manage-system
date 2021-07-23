package com.smp.admin.service.iface.sys;

import com.smp.admin.model.po.sys.SysDictDO;
import com.smp.admin.service.iface.base.CurdService;

import java.util.List;

/**
 * 字典管理Service
 * @author Hongyu Jiang
 * @since Apr. 27 2020
 */
public interface SysDictService extends CurdService<SysDictDO> {


	/**
	 * 根据字典标识(标签名)获取字典数据
	 * @param label 字典标识
	 * @return 字典信息列表
	 */
	List<SysDictDO> findByLabel(String label, Byte delFlag);


	/**
	 * 字典标识是否重复
	 * @param dictDO 字典实体类
	 * @return 是否重复
	 */
	boolean repeatByLabel(SysDictDO dictDO);

}

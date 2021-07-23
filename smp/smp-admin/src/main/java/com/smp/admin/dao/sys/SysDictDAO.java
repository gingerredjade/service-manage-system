package com.smp.admin.dao.sys;

import com.smp.admin.model.po.sys.SysDictDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 字典管理DAO接口及其实现
 * 	使用JPA的DAO层用命名法，免去SQL编写。
 *
 * @author Hongyu Jiang
 * @since Apr. 27 2020
 */
public interface SysDictDAO extends JpaRepository<SysDictDO, Long>, JpaSpecificationExecutor<SysDictDO> {

	/**
	 * 根据字典标识查询字典数据
	 * @param label 字典标识
	 * @param delFlag 状态
	 * @return 字典信息
	 */
	List<SysDictDO> findByLabelAndDelFlag(String label, Byte delFlag);

	/**
	 * 根据标识查询字典数据,且排查指定ID的字典
	 * @param label 字典标识
	 * @param id 字典ID
	 * @return 字典信息
	 */
	SysDictDO findByLabelAndIdNot(String label, Long id);


}

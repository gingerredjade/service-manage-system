package com.smp.admin.dao.sys;

import com.smp.admin.model.po.sys.SysLogDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 操作日志管理DAO接口及其实现
 * 	使用JPA的DAO层用命名法，免去SQL编写。
 *
 * @author Hongyu Jiang
 * @since Apr. 27 2020
 */
public interface SysLogDAO extends JpaRepository<SysLogDO,Long> {

	// 通过用户名获取对应日志信息
	@Query("select log from SysLogDO log where log.userName=?1")
	Page<SysLogDO> listByUserName(String userName, PageRequest pageRequest);

}

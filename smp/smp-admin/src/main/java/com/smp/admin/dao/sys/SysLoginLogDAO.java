package com.smp.admin.dao.sys;

import com.smp.admin.model.po.sys.SysLoginLogDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 登录日志管理DAO接口及其实现
 * 	使用JPA的DAO层用命名法，免去SQL编写。
 *
 * @author Hongyu Jiang
 * @since Apr. 27 2020
 */
public interface SysLoginLogDAO extends BaseRepository<SysLoginLogDO, Long> {

	// 根据用户名称和状态查询登录日志
	@Query("select ll from SysLoginLogDO ll where ll.userName=?1 and ll.status=?2")
	Page<SysLoginLogDO> listByUserNameAndStatus(String userName, String status, PageRequest pageRequest);

	// 根据用户名查询登录日志
	@Query("select ll from SysLoginLogDO ll where ll.userName=?1")
	Page<SysLoginLogDO> listByUserName(String userName, PageRequest pageRequest);

	// 根据状态查询登录日志
	@Query("select ll from SysLoginLogDO ll where ll.status=?1")
	Page<SysLoginLogDO> listByStatus(String status, PageRequest pageRequest);

	@Query("select ll from SysLoginLogDO ll where ll.userName=:userName and ll.status=:status")
	List<SysLoginLogDO> findBuUserNameAndStatus(String userName, String status);


}

package com.smp.admin.service.iface.sys;

import com.smp.admin.model.po.sys.SysLoginLogDO;
import com.smp.admin.service.iface.base.CurdService;

/**
 * 登录日志管理Service
 * @author Hongyu Jiang
 * @since Apr. 27 2020
 */
public interface SysLoginLogService extends CurdService<SysLoginLogDO> {

	/**
	 * 记录登录日志
	 * @param userName 登录用户名
	 * @param ip 登录IP
	 * @return 记录状态
	 */
	int writeLoginLog(String userName, String ip);

}

package com.smp.admin.service.impl.sys;

import com.smp.admin.dao.sys.SysLoginLogDAO;
import com.smp.admin.model.po.sys.SysLoginLogDO;
import com.smp.admin.service.iface.sys.SysLoginLogService;
import com.smp.common.api.PageRequestVO;
import com.smp.common.api.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 登录日志管理Service实现类
 * @author Hongyu Jiang
 * @since  Apr. 27 2020
 */
@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {

	private static Logger logger = LoggerFactory.getLogger(SysLoginLogServiceImpl.class);

	private final SysLoginLogDAO sysLoginLogDAO;
	@Autowired
	public SysLoginLogServiceImpl(SysLoginLogDAO sysLoginLogDAO) {
		this.sysLoginLogDAO = sysLoginLogDAO;
	}

	@Override
	public int save(SysLoginLogDO record) {
		try {
			if (record.getId() == null || record.getId() == 0) {
				// 新增
				Date curDate = new Date();
				record.setCreateTime(curDate);
				record.setLastUpdateTime(curDate);
			} else {
				// 更新
				record.setLastUpdateTime(new Date());
			}
			sysLoginLogDAO.save(record);
			return 0;
		} catch (Exception e) {
			logger.error("save SysLoginLogDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(SysLoginLogDO record) {
		try {
			sysLoginLogDAO.delete(record);
			return 0;
		} catch (Exception e) {
			logger.error("remove SysLoginLogDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(List<SysLoginLogDO> records) {
		try {
			sysLoginLogDAO.deleteInBatch(records);
			return 0;
		} catch (Exception e) {
			logger.error("remove SysLoginLogDO list error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public SysLoginLogDO getById(Long id) {
		Optional<SysLoginLogDO> optional = sysLoginLogDAO.findById(id);
		if(optional != null && optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public PageVO<SysLoginLogDO> getPageList(PageRequestVO pageRequestVO) {
		PageRequest pageRequest = null;

		int pageNum = pageRequestVO.getPageNum();
		int pageSize = pageRequestVO.getPageSize();

		pageRequest = PageRequest.of(pageNum-1, pageSize);

		Page<SysLoginLogDO> page = null;
		String userName = pageRequestVO.getParamValue("userName");
		String status = pageRequestVO.getParamValue("status");

		if (userName != null && userName.length() !=0 &&
			status != null && status.length() != 0) {
			page = sysLoginLogDAO.listByUserNameAndStatus(userName, status, pageRequest);
		} else if (userName != null && userName.length() !=0) {
			page = sysLoginLogDAO.listByUserName(userName, pageRequest);
		} else if (status != null && status.length() != 0) {
			page = sysLoginLogDAO.listByStatus(status, pageRequest);
		} else {
			page = sysLoginLogDAO.findAll(pageRequest);
		}

		List<SysLoginLogDO> content = page.getContent();
		PageVO<SysLoginLogDO> loginLogVO = new PageVO<>();
		loginLogVO.setList(content);
		loginLogVO.setPageNum(page.getNumber()+1);
		loginLogVO.setPageSize(page.getSize());
		loginLogVO.setTotalPage(page.getTotalPages());
		loginLogVO.setTotalRow(page.getTotalElements());

		return loginLogVO;
	}

	@Transactional
	@Override
	public int writeLoginLog(String userName, String ip) {
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.now();
		ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);

		List<SysLoginLogDO> sysLoginLogs = sysLoginLogDAO.
			findBuUserNameAndStatus(userName, SysLoginLogDO.STATUS_ONLINE);
		for (SysLoginLogDO sysLoginLogDO : sysLoginLogs) {
			sysLoginLogDO.setLastUpdateTime(Date.from(zonedDateTime.toInstant()));
			sysLoginLogDO.setStatus(SysLoginLogDO.STATUS_LOGIN);
			sysLoginLogDAO.save(sysLoginLogDO);
		}
		SysLoginLogDO record = new SysLoginLogDO();

		record.setCreateTime(Date.from(zonedDateTime.toInstant()));
		record.setLastUpdateTime(Date.from(zonedDateTime.toInstant()));
		record.setUserName(userName);
		record.setIp(ip);
		record.setStatus(SysLoginLogDO.STATUS_LOGOUT);
		sysLoginLogDAO.save(record);
		record.setStatus(SysLoginLogDO.STATUS_ONLINE);
		sysLoginLogDAO.save(record);
		return 0;
	}
}


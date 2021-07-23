package com.smp.admin.service.impl.sys;

import com.smp.admin.dao.sys.SysLogDAO;
import com.smp.admin.model.po.sys.SysLogDO;
import com.smp.admin.service.iface.sys.SysLogService;
import com.smp.common.api.PageRequestVO;
import com.smp.common.api.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 操作日志管理Service实现类
 * @author Hongyu Jiang
 * @since  Apr. 27 2020
 */
@Service
public class SysLogServiceImpl implements SysLogService {

	private static Logger logger = LoggerFactory.getLogger(SysLogServiceImpl.class);

	private final SysLogDAO sysLogDAO;
	@Autowired
	public SysLogServiceImpl(SysLogDAO sysLogDAO) {
		this.sysLogDAO = sysLogDAO;
	}

	@Override
	public int save(SysLogDO record) {
		try {
			Date curDate = new Date();
			record.setCreateTime(curDate);
			record.setLastUpdateTime(curDate);
			sysLogDAO.save(record);
			return 0;
		} catch (Exception e) {
			logger.error("save SysLogDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(SysLogDO record) {
		try {
			sysLogDAO.delete(record);
			return 0;
		} catch (Exception e) {
			logger.error("remove SysLogDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(List<SysLogDO> records) {
		try {
			sysLogDAO.deleteInBatch(records);
			return 0;
		} catch (Exception e) {
			logger.error("remove SysLogDO list error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public SysLogDO getById(Long id) {
		Optional<SysLogDO> optional = sysLogDAO.findById(id);
		if(optional != null && optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public PageVO<SysLogDO> getPageList(PageRequestVO pageRequestVO) {
		PageRequest pageRequest = null;

		int pageNum = pageRequestVO.getPageNum();
		int pageSize = pageRequestVO.getPageSize();

		pageRequest = PageRequest.of(pageNum-1, pageSize);

		Page<SysLogDO> page = null;
		String userName = pageRequestVO.getParamValue("userName");
		if (userName != null && userName.length() != 0) {
			page = sysLogDAO.listByUserName(userName, pageRequest);
		} else {
			page = sysLogDAO.findAll(pageRequest);
		}

		List<SysLogDO> content = page.getContent();
		PageVO<SysLogDO> logVO = new PageVO<>();
		logVO.setList(content);
		logVO.setPageNum(page.getNumber()+1);
		logVO.setPageSize(page.getSize());
		logVO.setTotalPage(page.getTotalPages());
		logVO.setTotalRow(page.getTotalElements());

		return logVO;
	}
}

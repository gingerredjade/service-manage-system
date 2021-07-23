package com.smp.admin.service.impl.bus;

import com.smp.admin.dao.bus.BusSvcStyleDAO;
import com.smp.admin.model.po.bus.svcinfo.BusSvcStyleDO;
import com.smp.admin.service.iface.bus.BusSvcStyleService;
import com.smp.common.api.PageRequestVO;
import com.smp.common.api.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 服务风格 管理Service实现类
 * @author Hongyu Jiang
 * @since  Nov. 18 2020
 */
@Service
public class BusSvcStyleServiceImpl implements BusSvcStyleService {

	private static final Logger logger = LoggerFactory.getLogger(BusSvcStyleServiceImpl.class);

	/**
	 * 采用构造器方式,注入DAO层&需要的Service层
	 */
	private final BusSvcStyleDAO busSvcStyleDAO;
	@Autowired
	public BusSvcStyleServiceImpl(BusSvcStyleDAO busSvcStyleDAO) {
		this.busSvcStyleDAO = busSvcStyleDAO;
	}


	@Override
	public Page<BusSvcStyleDO> listSvcStyle(Integer pageNum, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNum-1, pageSize);
		return busSvcStyleDAO.findAll(pageRequest);
	}

	@Override
	public boolean deleteStyle(Long id) {
		try {
			busSvcStyleDAO.deleteById(id);
			return true;
		} catch (Exception e) {
			logger.error("remove BusSvcStyleDO error, {}", e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<BusSvcStyleDO> listAll() {
		return busSvcStyleDAO.findAll();
	}

	@Override
	public List<BusSvcStyleDO> listByStyleName(String name) {
		return busSvcStyleDAO.findByStyleName(name);
	}

	@Override
	public int save(BusSvcStyleDO record) {
		try {
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();

			Long subjectId = record.getStyleId();
			BusSvcStyleDO svcStyle = this.getById(subjectId);
			if (svcStyle == null) {
				// 新增
				Date curDate = new Date();
				record.setCreateTime(curDate);
				record.setLastUpdateTime(curDate);
				record.setCreateBy(userName);
				record.setLastUpdateBy(userName);
			} else {
				// 编辑更新
				record.setLastUpdateTime(new Date());
				record.setLastUpdateBy(userName);
			}

			busSvcStyleDAO.save(record);
			return 0;
		} catch (Exception e) {
			logger.error("save BusSvcStyleDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(BusSvcStyleDO record) {
		try {
			busSvcStyleDAO.delete(record);
			return 0;
		} catch (Exception e) {
			logger.error("remove BusSvcStyleDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(List<BusSvcStyleDO> records) {
		try {
			busSvcStyleDAO.deleteInBatch(records);
			return 0;
		} catch (Exception e) {
			logger.error("remove BusSvcStyleDO list error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public BusSvcStyleDO getById(Long id) {
		Optional<BusSvcStyleDO> optional = busSvcStyleDAO.findById(id);
		if(optional != null && optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public PageVO<BusSvcStyleDO> getPageList(PageRequestVO pageRequestVO) {
		PageRequest pageRequest = null;

		int pageNum = pageRequestVO.getPageNum();
		int pageSize = pageRequestVO.getPageSize();

		pageRequest = PageRequest.of(pageNum-1, pageSize);

		Page<BusSvcStyleDO> page = null;
		String name = pageRequestVO.getParamValue("name");
		if (name != null && name.length() != 0) {
			page = busSvcStyleDAO.listBySvcStyleName(name, pageRequest);
		} else {
			page = busSvcStyleDAO.findAll(pageRequest);
		}

		List<BusSvcStyleDO> content = page.getContent();
		PageVO<BusSvcStyleDO> svcStyleVO = new PageVO<>();
		svcStyleVO.setList(content);
		svcStyleVO.setPageNum(page.getNumber()+1);
		svcStyleVO.setPageSize(page.getSize());
		svcStyleVO.setTotalPage(page.getTotalPages());
		svcStyleVO.setTotalRow(page.getTotalElements());

		return svcStyleVO;
	}
}

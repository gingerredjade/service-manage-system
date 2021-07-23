package com.smp.admin.service.impl.bus;

import com.smp.admin.dao.bus.BusSvcSubjectDAO;
import com.smp.admin.model.po.bus.svcinfo.BusSvcSubjectDO;
import com.smp.admin.service.iface.bus.BusSvcSubjectService;
import com.smp.common.api.PageRequestVO;
import com.smp.common.api.PageVO;
import com.smp.common.api.UniversalRequestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 服务主题 管理Service实现类
 * @author Hongyu Jiang
 * @since  May. 16 2020
 */
@Service
public class BusSvcSubjectServiceImpl implements BusSvcSubjectService {

	private static final Logger logger = LoggerFactory.getLogger(BusSvcSubjectServiceImpl.class);

	/**
	 * 采用构造器方式,注入DAO层&需要的Service层
	 */
	private final BusSvcSubjectDAO busSvcSubjectDAO;
	@Autowired
	public BusSvcSubjectServiceImpl(BusSvcSubjectDAO busSvcSubjectDAO) {
		this.busSvcSubjectDAO = busSvcSubjectDAO;
	}


	@Override
	public Page<BusSvcSubjectDO> listSvcSubject(Integer pageNum, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNum-1, pageSize);
		return busSvcSubjectDAO.findAll(pageRequest);
	}

	@Override
	public boolean deleteSubject(Long id) {
		try {
			busSvcSubjectDAO.deleteById(id);
			return true;
		} catch (Exception e) {
			logger.error("remove BusSvcSubjectDO error, {}", e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<BusSvcSubjectDO> listAll() {
		return busSvcSubjectDAO.findAll();
	}

	@Override
	public List<BusSvcSubjectDO> listBySubjectName(String name) {
		return busSvcSubjectDAO.findBySubjectName(name);
	}

	@Override
	public List<BusSvcSubjectDO> getList(UniversalRequestVO requestVO) {
		List<BusSvcSubjectDO> list = null;
		String name = requestVO.getParamValue("name");
		if (name != null && name.length() != 0) {
			list = busSvcSubjectDAO.listBySvcSubjectName(name);
		} else {
			list = busSvcSubjectDAO.findAll();
		}

		return list;
	}

	@Override
	public int save(BusSvcSubjectDO record) {
		try {
			/*Example<BusSvcSubjectDO> example = Example.of(record);
			boolean existed = busSvcSubjectDAO.exists(example);*/

			String userName = SecurityContextHolder.getContext().getAuthentication().getName();

			Long subjectId = record.getSubjectId();
			BusSvcSubjectDO svcSubject = this.getById(subjectId);
			if (svcSubject == null) {
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

			busSvcSubjectDAO.save(record);
			return 0;
		} catch (Exception e) {
			logger.error("save BusSvcSubjectDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(BusSvcSubjectDO record) {
		try {
			busSvcSubjectDAO.delete(record);
			return 0;
		} catch (Exception e) {
			logger.error("remove BusSvcSubjectDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(List<BusSvcSubjectDO> records) {
		try {
			busSvcSubjectDAO.deleteInBatch(records);
			return 0;
		} catch (Exception e) {
			logger.error("remove BusSvcSubjectDO list error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public BusSvcSubjectDO getById(Long id) {
		Optional<BusSvcSubjectDO> optional = busSvcSubjectDAO.findById(id);
		if(optional != null && optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public PageVO<BusSvcSubjectDO> getPageList(PageRequestVO pageRequestVO) {
		PageRequest pageRequest = null;

		int pageNum = pageRequestVO.getPageNum();
		int pageSize = pageRequestVO.getPageSize();

		pageRequest = PageRequest.of(pageNum-1, pageSize);

		Page<BusSvcSubjectDO> page = null;
		String name = pageRequestVO.getParamValue("name");
		if (name != null && name.length() != 0) {
			page = busSvcSubjectDAO.listBySvcSubjectName(name, pageRequest);
		} else {
			page = busSvcSubjectDAO.findAll(pageRequest);
		}

		List<BusSvcSubjectDO> content = page.getContent();
		PageVO<BusSvcSubjectDO> svcSubjectVO = new PageVO<>();
		svcSubjectVO.setList(content);
		svcSubjectVO.setPageNum(page.getNumber()+1);
		svcSubjectVO.setPageSize(page.getSize());
		svcSubjectVO.setTotalPage(page.getTotalPages());
		svcSubjectVO.setTotalRow(page.getTotalElements());

		return svcSubjectVO;
	}
}

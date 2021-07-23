package com.smp.admin.service.impl.bus;

import com.smp.admin.dao.bus.BusSvcTypeDAO;
import com.smp.admin.model.po.bus.svcinfo.BusSvcSubjectDO;
import com.smp.admin.model.po.bus.svcinfo.BusSvcTypeDO;
import com.smp.admin.service.iface.bus.BusSvcSubjectService;
import com.smp.admin.service.iface.bus.BusSvcTypeService;
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
 * 服务类型 管理Service实现类
 * @author Hongyu Jiang
 * @since  May. 16 2020
 */
@Service
public class BusSvcTypeServiceImpl implements BusSvcTypeService {

	private static final Logger logger = LoggerFactory.getLogger(BusSvcTypeServiceImpl.class);


	/**
	 * 采用构造器方式,注入DAO层&需要的Service层
	 */
	private final BusSvcTypeDAO busSvcTypeDAO;
	private final BusSvcSubjectService busSvcSubjectService;
	@Autowired
	public BusSvcTypeServiceImpl(BusSvcTypeDAO busSvcTypeDAO,
								 BusSvcSubjectService busSvcSubjectService) {
		this.busSvcTypeDAO = busSvcTypeDAO;
		this.busSvcSubjectService = busSvcSubjectService;
	}


	@Override
	public Page<BusSvcTypeDO> listSvcTypes(Integer pageNum, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNum-1, pageSize);
		return busSvcTypeDAO.findAll(pageRequest);
	}

	@Override
	public List<BusSvcTypeDO> listBySubjectId(Long subjectId) {
		try {
			List<BusSvcTypeDO> list = busSvcTypeDAO.listBySvcSubjectId(subjectId);
			return list;
		} catch (Exception e) {
			logger.error("get BusSvcTypeDO list error, {}", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BusSvcTypeDO> listAll() {
		return busSvcTypeDAO.findAll();
	}

	@Override
	public boolean deleteSvcType(Long id) {
		try {
			busSvcTypeDAO.deleteById(id);
			return true;
		} catch (Exception e) {
			logger.error("remove BusSvcTypeDO error, {}", e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<BusSvcTypeDO> listByNameAndSubjectId(String name, Long subjectId) {
		return busSvcTypeDAO.listByNameAndSubjectId(name, subjectId);
	}

	@Override
	public List<BusSvcTypeDO> getList(UniversalRequestVO requestVO) {
		List<BusSvcTypeDO> list = null;
		String name = requestVO.getParamValue("name");
		String subjectName = requestVO.getParamValue("subjectName");

		if (name != null && name.length() != 0 ) {
			if (subjectName != null && subjectName.length() != 0) {
				list = busSvcTypeDAO.listBySvcTypeAndSubjectName(name, subjectName);
			} else {
				list = busSvcTypeDAO.listBySvcTypeName(name);
			}
		} else if (subjectName != null && subjectName.length() != 0) {
			list = busSvcTypeDAO.listBySvcSubjectName(subjectName);
		} else {
			list = busSvcTypeDAO.findAll();
		}

		return list;
	}

	@Override
	public int save(BusSvcTypeDO record) {
		try {
			/*Example<BusSvcTypeDO> example = Example.of(record);
			boolean existed = busSvcTypeDAO.exists(example);*/

			String userName = SecurityContextHolder.getContext().getAuthentication().getName();

			Long curId = record.getTypeId();
			BusSvcTypeDO svcType = this.getById(curId);
			if (svcType == null) {
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

			Long subjectId = record.getBusSvcSubjectDO().getSubjectId();
			if (subjectId != null) {
				BusSvcSubjectDO subject = busSvcSubjectService.getById(subjectId);
				record.setBusSvcSubjectDO(subject);
			}
			busSvcTypeDAO.save(record);
			return 0;
		} catch (Exception e) {
			logger.error("save BusSvcTypeDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(BusSvcTypeDO record) {
		try {
			busSvcTypeDAO.delete(record);
			return 0;
		} catch (Exception e) {
			logger.error("remove BusSvcTypeDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(List<BusSvcTypeDO> records) {
		try {
			busSvcTypeDAO.deleteInBatch(records);
			return 0;
		} catch (Exception e) {
			logger.error("remove BusSvcTypeDO list error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public BusSvcTypeDO getById(Long id) {
		Optional<BusSvcTypeDO> optional = busSvcTypeDAO.findById(id);
		if(optional != null && optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public PageVO<BusSvcTypeDO> getPageList(PageRequestVO pageRequestVO) {
		PageRequest pageRequest = null;

		int pageNum = pageRequestVO.getPageNum();
		int pageSize = pageRequestVO.getPageSize();

		pageRequest = PageRequest.of(pageNum-1, pageSize);

		Page<BusSvcTypeDO> page = null;
		String name = pageRequestVO.getParamValue("name");
		String subjectName = pageRequestVO.getParamValue("subjectName");

		if (name != null && name.length() != 0 ) {
			if (subjectName != null && subjectName.length() != 0) {
				page = busSvcTypeDAO.listBySvcTypeAndSubjectName(name, subjectName, pageRequest);
			} else {
				page = busSvcTypeDAO.listBySvcTypeName(name, pageRequest);
			}
		} else if (subjectName != null && subjectName.length() != 0) {
			page = busSvcTypeDAO.listBySvcSubjectName(subjectName, pageRequest);
		} else {
			page = busSvcTypeDAO.findAll(pageRequest);
		}

		List<BusSvcTypeDO> content = page.getContent();
		PageVO<BusSvcTypeDO> svcTypePageVO = new PageVO<>();
		svcTypePageVO.setList(content);
		svcTypePageVO.setPageNum(page.getNumber()+1);
		svcTypePageVO.setPageSize(page.getSize());
		svcTypePageVO.setTotalPage(page.getTotalPages());
		svcTypePageVO.setTotalRow(page.getTotalElements());

		return svcTypePageVO;
	}
}

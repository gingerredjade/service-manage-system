package com.smp.admin.service.impl.sys;

import com.smp.admin.dao.sys.SysDictDAO;
import com.smp.admin.model.po.sys.SysDictDO;
import com.smp.admin.service.iface.sys.SysDictService;
import com.smp.common.api.PageRequestVO;
import com.smp.common.api.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 字典管理Service实现类
 * @author Hongyu Jiang
 * @since  Apr. 27 2020
 */
@Service
public class SysDictServiceImpl implements SysDictService {

	private static Logger logger = LoggerFactory.getLogger(SysDictServiceImpl.class);

	/**
	 * 采用构造器方式,注入DAO层
	 */
	private final SysDictDAO sysDictDAO;
	@Autowired
	public SysDictServiceImpl(SysDictDAO sysDictDAO) {
		this.sysDictDAO = sysDictDAO;
	}

	@Override
	public List<SysDictDO> findByLabel(String label, Byte delFlag) {
		return sysDictDAO.findByLabelAndDelFlag(label, delFlag);
	}

	@Override
	public boolean repeatByLabel(SysDictDO dictDO) {
		Long id = dictDO.getId() != null ? dictDO.getId() : Long.MIN_VALUE;
		return sysDictDAO.findByLabelAndIdNot(dictDO.getLabel(), id) != null;
	}

	@Override
	public int save(SysDictDO record) {
		try {
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			Long dictId = record.getId();
			SysDictDO dict = this.getById(dictId);
			if (dict == null) {
				// 新增
				Date curDate = new Date();
				record.setCreateTime(curDate);
				record.setCreateBy(userName);
				record.setLastUpdateTime(curDate);
				record.setLastUpdateBy(userName);
			}
			record.setLastUpdateBy(userName);
			record.setLastUpdateTime(new Date());

			sysDictDAO.save(record);
			return 0;
		} catch (Exception e) {
			logger.error("save SysDictDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(SysDictDO record) {
		try {
			sysDictDAO.delete(record);
			return 0;
		} catch (Exception e) {
			logger.error("remove SysDictDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(List<SysDictDO> records) {
		try {
			sysDictDAO.deleteInBatch(records);
			return 0;
		} catch (Exception e) {
			logger.error("remove SysDictDO list error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public SysDictDO getById(Long id) {
		return sysDictDAO.findById(id).orElse(null);

		/*Optional<SysDictDO> optional = sysDictDAO.findById(id);
		if(optional != null && optional.isPresent()) {
			return optional.get();
		}
		return null;*/
	}

	@Override
	public PageVO<SysDictDO> getPageList(PageRequestVO pageRequestVO) {
		PageRequest pageRequest = null;

		int pageNum = pageRequestVO.getPageNum();
		int pageSize = pageRequestVO.getPageSize();

		pageRequest = PageRequest.of(pageNum-1, pageSize);

		Page<SysDictDO> page = null;
		String label = pageRequestVO.getParamValue("label");

		try {
			if (label == null) {
				page = sysDictDAO.findAll(pageRequest);
			} else {
				// JPA复杂查询（单表、多条件）
				page = sysDictDAO.findAll(new Specification<SysDictDO>() {

					@Override
					public Predicate toPredicate(Root<SysDictDO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
						List<Predicate> predicates = new ArrayList<>();
						if (!label.isEmpty()) {
							predicates.add(criteriaBuilder.like(root.get("label").as(String.class), "%"+label+"%"));
						}
						Predicate[] pre = new Predicate[predicates.size()];
						query.where(predicates.toArray(pre));
						return criteriaBuilder.and(predicates.toArray(pre));
					}
				}, pageRequest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<SysDictDO> content = page.getContent();
		PageVO<SysDictDO> dictPageVO = new PageVO<>();
		dictPageVO.setList(content);
		dictPageVO.setPageNum(page.getNumber()+1);
		dictPageVO.setPageSize(page.getSize());
		dictPageVO.setTotalPage(page.getTotalPages());
		dictPageVO.setTotalRow(page.getTotalElements());

		return dictPageVO;
	}
}

package com.smp.admin.service.impl.sys;


import com.smp.admin.dao.sys.SysDeptDAO;
import com.smp.admin.model.po.sys.SysDeptDO;
import com.smp.admin.service.iface.sys.SysDeptService;
import com.smp.common.api.PageRequestVO;
import com.smp.common.api.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
 * 机构管理Service实现类
 * @author Hongyu Jiang
 * @since  Apr. 27 2020
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {

	private static Logger logger = LoggerFactory.getLogger(SysDeptServiceImpl.class);

	/**
	 * 采用构造器方式,注入DAO层
	 */
	private final SysDeptDAO sysDeptDAO;
	@Autowired
	public SysDeptServiceImpl(SysDeptDAO sysDeptDAO) {
		this.sysDeptDAO = sysDeptDAO;
	}


	@Override
	public int save(SysDeptDO record) {
		try {
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			Example<SysDeptDO> example = Example.of(record);

			Long curId = record.getId();
			SysDeptDO dept = this.getById(curId);
			if (dept == null) {
				// 新增
				Date curDate = new Date();
				record.setCreateTime(curDate);
				record.setLastUpdateTime(curDate);
				record.setCreateBy(userName);
				record.setLastUpdateBy(userName);
			} else {
				// 编辑更新
				record.setLastUpdateBy(userName);
				record.setLastUpdateTime(new Date());
			}

			sysDeptDAO.save(record);
			return 0;
		} catch (Exception e) {
			logger.error("save SysDeptDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(SysDeptDO record) {
		try {
			sysDeptDAO.delete(record);
			return 0;
		} catch (Exception e) {
			logger.error("remove SysDeptDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(List<SysDeptDO> records) {
		try {
			sysDeptDAO.deleteInBatch(records);
			return 0;
		} catch (Exception e) {
			logger.error("remove SysDeptDO list error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public SysDeptDO getById(Long id) {
		Optional<SysDeptDO> optional = sysDeptDAO.findById(id);
		if(optional != null && optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public PageVO<SysDeptDO> getPageList(PageRequestVO pageRequestVO) {
		try {
			PageRequest pageRequest = null;

			int pageNum = pageRequestVO.getPageNum();
			int pageSize = pageRequestVO.getPageSize();

			pageRequest = PageRequest.of(pageNum-1, pageSize);

			Page<SysDeptDO> page = null;
			String name = pageRequestVO.getParamValue("name");
			String address = pageRequestVO.getParamValue("address");
			String contact = pageRequestVO.getParamValue("contact");

			try {
				if (name == null &&
					address == null &&
					contact == null){
					page = sysDeptDAO.findAll(pageRequest);
				} else {
					// JPA复杂查询（单表、多条件）
					page = sysDeptDAO.findAll(new Specification<SysDeptDO>() {
						@Override
						public Predicate toPredicate(Root<SysDeptDO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
							List<Predicate> predicates = new ArrayList<>();
							if (name != null && name.length() != 0) {
								predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+name+"%"));
							}
							if (address != null && address.length() != 0) {
								predicates.add(criteriaBuilder.like(root.get("address").as(String.class), "%"+address+"%"));
							}
							if (contact != null && contact.length() != 0) {
								predicates.add(criteriaBuilder.like(root.get("contact").as(String.class), "%"+contact+"%"));
							}
							Predicate[] pre = new Predicate[predicates.size()];
							query.where(predicates.toArray(pre));
							return criteriaBuilder.and(predicates.toArray(pre));
						}
					}, pageRequest);
				}
				sysDeptDAO.findAll(pageRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}

			List<SysDeptDO> content = page.getContent();
			PageVO<SysDeptDO> deptPageVO = new PageVO<>();
			deptPageVO.setList(content);
			deptPageVO.setPageNum(page.getNumber()+1);
			deptPageVO.setPageSize(page.getSize());
			deptPageVO.setTotalPage(page.getTotalPages());
			deptPageVO.setTotalRow(page.getTotalElements());

			return deptPageVO;
		} catch (Exception e) {
			logger.error("get SysDeptDO page list error, {}", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SysDeptDO> findTree() {
		List<SysDeptDO> sysDepts = new ArrayList<>();
		List<SysDeptDO> depts = sysDeptDAO.findAll();
		// 遍历所有根机构
		for (SysDeptDO dept : depts) {
			if (dept.getParentId() == null || dept.getParentId() == 0) {
				dept.setLevel(0);
				sysDepts.add(dept);
			}
		}
		// 查找子机构
		findChildren(sysDepts, depts);
		return sysDepts;
	}

	/**
	 * 递归查询组织子机构
	 * @param sysDepts 所有根机构信息
	 * @param depts 所有机构信息
	 */
	private void findChildren(List<SysDeptDO> sysDepts, List<SysDeptDO> depts) {
		for (SysDeptDO sysDept : sysDepts) {
			List<SysDeptDO> children = new ArrayList<>();
			for (SysDeptDO dept : depts) {
				if (sysDept.getId() != null && sysDept.getId().equals(dept.getParentId())) {
					dept.setParentName(sysDept.getName());
					dept.setLevel(sysDept.getLevel() + 1);
					children.add(dept);
				}
			}
			sysDept.setChildren(children);
			findChildren(children, depts);
		}
	}


	@Override
	public Page<SysDeptDO> listSysDept(Integer pageNum, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNum-1, pageSize);
		return sysDeptDAO.findAll(pageRequest);
	}

	@Override
	public List<SysDeptDO> findTreeByName(String name) {
		List<SysDeptDO> sysDepts = new ArrayList<>();
		List<SysDeptDO> depts = sysDeptDAO.listByDeptName(name);
		// 遍历所有根机构
		for (SysDeptDO dept : depts) {
			if (dept.getParentId() == null || dept.getParentId() == 0) {
				dept.setLevel(0);
				sysDepts.add(dept);
			}
		}
		// 查找子机构
		List<SysDeptDO> wholeDepts = sysDeptDAO.findAll();
		findChildren(sysDepts, wholeDepts);
		return sysDepts;
	}
}

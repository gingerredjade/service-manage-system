package com.smp.admin.service.impl.sys;

import com.smp.admin.constant.SysConstants;
import com.smp.admin.constant.enums.StatusEnum;
import com.smp.admin.dao.sys.SysMenuDAO;
import com.smp.admin.dao.sys.SysRoleDAO;
import com.smp.admin.dao.sys.SysRoleMenuDAO;
import com.smp.admin.model.po.sys.SysMenuDO;
import com.smp.admin.model.po.sys.SysRoleDO;
import com.smp.admin.model.po.sys.SysRoleMenuDO;
import com.smp.admin.service.iface.sys.SysRoleService;
import com.smp.common.api.PageRequestVO;
import com.smp.common.api.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 角色管理Service实现类
 * @author Hongyu Jiang
 * @since  Apr. 27 2020
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

	private static Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);

	/**
	 * 采用构造器方式,注入DAO层&需要的Service层
	 */
	private final SysRoleDAO sysRoleDAO;
	private final SysMenuDAO sysMenuDAO;
	private final SysRoleMenuDAO sysRoleMenuDAO;
	@Autowired
	public SysRoleServiceImpl(SysRoleDAO sysRoleDAO,
							  SysMenuDAO sysMenuDAO,
							  SysRoleMenuDAO sysRoleMenuDAO) {
		this.sysRoleDAO = sysRoleDAO;
		this.sysMenuDAO = sysMenuDAO;
		this.sysRoleMenuDAO = sysRoleMenuDAO;
	}


	@Override
	public int save(SysRoleDO record) {
		try {
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			Long curId = record.getId();
			SysRoleDO role = this.getById(curId);
			if (role == null) {
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
			sysRoleDAO.save(record);
			return 0;
		} catch (Exception e) {
			logger.error("save SysRoleDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(SysRoleDO record) {
		try {
			sysRoleDAO.delete(record);
			return 0;
		} catch (Exception e) {
			logger.error("remove SysRoleDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(List<SysRoleDO> records) {
		try {
			sysRoleDAO.deleteInBatch(records);
			return 0;
		} catch (Exception e) {
			logger.error("remove SysRoleDO list error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public SysRoleDO getById(Long id) {
		Optional<SysRoleDO> optional = sysRoleDAO.findById(id);
		if(optional != null && optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public PageVO<SysRoleDO> getPageList(PageRequestVO pageRequestVO) {
		PageRequest pageRequest = null;

		int pageNum = pageRequestVO.getPageNum();
		int pageSize = pageRequestVO.getPageSize();

		pageRequest = PageRequest.of(pageNum-1, pageSize);

		Page<SysRoleDO> page = null;
		String name = pageRequestVO.getParamValue("name");

		if (name != null && name.length() != 0 ) {
			page = sysRoleDAO.listByName(name, pageRequest);
		} else{
			page = sysRoleDAO.findAll(pageRequest);
		}

		List<SysRoleDO> content = page.getContent();
		PageVO<SysRoleDO> rolePageVO = new PageVO<>();
		rolePageVO.setList(content);
		rolePageVO.setPageNum(page.getNumber()+1);
		rolePageVO.setPageSize(page.getSize());
		rolePageVO.setTotalPage(page.getTotalPages());
		rolePageVO.setTotalRow(page.getTotalElements());

		return rolePageVO;
	}

	@Override
	public List<SysRoleDO> listAll() {
		return sysRoleDAO.findAll();
	}

	@Override
	public List<SysRoleDO> findByName(String name) {
		return sysRoleDAO.findByName(name);
	}

	@Override
	public List<SysMenuDO> findRoleMenus(Long roleId) {
		SysRoleDO sysRoleDO = this.getById(roleId);
		if (SysConstants.ADMIN.equalsIgnoreCase(sysRoleDO.getName())) {
			// 如果是超级管理员，返回全部菜单信息
			return sysMenuDAO.findAll();
		}
		// 返回角色对应的菜单信息
		return sysMenuDAO.findByRoleId(roleId);
	}

	@Transactional
	@Override
	public int saveRoleMenus(List<SysRoleMenuDO> records) {
		if (records == null || records.isEmpty()) {
			return 0;
		}
		Long roleId = records.get(0).getRoleId();
		sysRoleMenuDAO.deleteByRoleId(roleId);
		for (SysRoleMenuDO record : records) {
			sysRoleMenuDAO.save(record);
		}
		return 0;
	}

	@Override
	public Boolean existsUserOk(Long id) {
		Byte status = StatusEnum.OK.getCode();
		return sysRoleDAO.existsByUserIdAndStatus(id, status) != null;
	}

	@Override
	public List<SysRoleDO> getListBySortOk(Sort sort) {
		return sysRoleDAO.findAllByDelFlag(sort, StatusEnum.OK.getCode());
	}

	@Override
	public boolean repeatByName(SysRoleDO role) {
		Long id = role.getId() != null ? role.getId() : Long.MIN_VALUE;
		return sysRoleDAO.findByNameAndIdNot(role.getName(), id) != null;
	}
}

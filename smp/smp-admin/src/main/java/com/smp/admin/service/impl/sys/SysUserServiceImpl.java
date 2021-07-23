package com.smp.admin.service.impl.sys;

import com.smp.admin.constant.enums.StatusEnum;
import com.smp.admin.dao.sys.SysRoleDAO;
import com.smp.admin.dao.sys.SysUserDAO;
import com.smp.admin.dao.sys.SysUserRoleDAO;
import com.smp.admin.model.po.sys.SysMenuDO;
import com.smp.admin.model.po.sys.SysRoleDO;
import com.smp.admin.model.po.sys.SysUserDO;
import com.smp.admin.model.po.sys.SysUserRoleDO;
import com.smp.admin.service.iface.sys.SysDeptService;
import com.smp.admin.service.iface.sys.SysMenuService;
import com.smp.admin.service.iface.sys.SysUserService;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * 用户管理Service实现类
 * @author Hongyu Jiang
 * @since  Apr. 27 2020
 */
@Service
public class SysUserServiceImpl implements SysUserService {

	private static Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

	/**
	 * 采用构造器方式,注入DAO层、需要的Service层
	 */
	private final SysUserDAO sysUserDAO;
	private final SysMenuService sysMenuService;
	private final SysUserRoleDAO sysUserRoleDAO;
	private final SysDeptService sysDeptService;
	private final SysRoleDAO sysRoleDAO;
	@Autowired
	public SysUserServiceImpl(SysUserDAO sysUserDAO,
							  SysMenuService sysMenuService,
							  SysUserRoleDAO sysUserRoleDAO,
							  SysDeptService sysDeptService,
							  SysRoleDAO sysRoleDAO) {
		this.sysUserDAO = sysUserDAO;
		this.sysMenuService = sysMenuService;
		this.sysUserRoleDAO = sysUserRoleDAO;
		this.sysDeptService = sysDeptService;
		this.sysRoleDAO = sysRoleDAO;
	}


	@Override
	public List<SysUserDO> save(List<SysUserDO> userList) {
		return sysUserDAO.saveAll(userList);
	}


	@Override
	public SysUserDO getByName(String userName) {
		SysUserDO userDO = sysUserDAO.findByUsername(userName);

		if (userDO != null) {
			// 填充用户所属机构
			Long deptId = userDO.getDeptId();
			String deptName = sysDeptService.getById(deptId).getName();
			userDO.setDeptName(deptName);

			// 填充用户角色名集合
			List<SysUserRoleDO> urs = sysUserRoleDAO.findByUserId(userDO.getId());
			if (urs != null && urs.size() != 0) {
				// 填充用户对应包含的用户角色信息
				userDO.setUserRoles(urs);

				// 填充用户包含的角色名称字符串
				String roleNames = buildRoleNames(urs);
				if (roleNames != null && !roleNames.equals("")) {
					userDO.setRoleNames(roleNames);
				}
			}
		}

		return userDO;
	}


	@Override
	public Set<String> getPermissions(String userName) {
		Set<String> perms = new HashSet<>();
		List<SysMenuDO> sysMenus = sysMenuService.findByUserName(userName);
		for (SysMenuDO sysMenu : sysMenus) {
			if (sysMenu.getPerms() != null && !"".equals(sysMenu.getPerms())) {
				perms.add(sysMenu.getPerms());
			}
		}
		return perms;
	}


	@Override
	public List<SysUserRoleDO> findUserRoles(Long userId) {
		return sysUserRoleDAO.findByUserId(userId);
	}


	@Override
	public Boolean repeatByUserName(SysUserDO userDO) {
		Long id = userDO.getId() != null ? userDO.getId() : Long.MIN_VALUE;
		return sysUserDAO.findByUsernameAndIdNot(userDO.getUsername(), id) != null;
	}

	@Transactional
	@Override
	public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
		// 联级删除与角色之间的关联
		if (statusEnum == StatusEnum.DELETE) {
			return sysUserDAO.deleteByIdIn(idList) > 0;
		}
		return sysUserDAO.updateStatus(statusEnum.getCode(), idList) > 0;
	}

	@Override
	public List<SysUserDO> listAll() {
		return sysUserDAO.findAll();
	}

	@Override
	public Long countUserGross() {
		return sysUserDAO.countUserGross();
	}


	@Transactional
	@Override
	public int save(SysUserDO record) {
		try {
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();

			Long curId = record.getId();
			SysUserDO user = this.getById(curId);
			if (user == null) {
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
			// 写入用户数据
			sysUserDAO.save(record);

			// 删除用户之前的用户角色数据
			List<SysUserRoleDO> originUrs = sysUserRoleDAO.findByUserId(record.getId());
			if (originUrs != null && originUrs.size() != 0) {
				sysUserRoleDAO.deleteInBatch(originUrs);
			}

			// 写入最新用户角色关联数据
			List<SysUserRoleDO> userRoles = record.getUserRoles();
			if (userRoles != null && userRoles.size() != 0) {
				for (SysUserRoleDO userRole : userRoles) {
					SysUserRoleDO ur = sysUserRoleDAO.
						findByUserIdAndRoleId(userRole.getUserId(), userRole.getRoleId());
					if (ur == null) {
						// 新增-设置初次创建信息
						userRole.setCreateBy(userName);
						userRole.setCreateTime(new Date());

					}
					userRole.setLastUpdateBy(userName);
					userRole.setLastUpdateTime(new Date());
				}
				sysUserRoleDAO.saveAll(userRoles);
			}
			return 0;
		} catch (Exception e) {
			logger.error("save SysUserDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}


	@Transactional
	@Override
	public int delete(SysUserDO record) {
		try {
			// 删除用户角色关联信息
			sysUserRoleDAO.deleteByUserId(record.getId());

			// 删除用户信息
			sysUserDAO.delete(record);
			return 0;
		} catch (Exception e) {
			logger.error("remove SysUserDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}


	@Transactional
	@Override
	public int delete(List<SysUserDO> records) {
		try {
			// 删除用户角色关联信息
			for (SysUserDO record : records) {
				sysUserRoleDAO.deleteByUserId(record.getId());
			}

			// 删除用户信息
			sysUserDAO.deleteInBatch(records);

			return 0;
		} catch (Exception e) {
			logger.error("remove SysUserDO list error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}


	@Override
	public SysUserDO getById(Long id) {
		Optional<SysUserDO> optional = sysUserDAO.findById(id);
		if(optional != null && optional.isPresent()) {
			return optional.get();
		}
		return null;
	}


	@Override
	@Transactional
	public PageVO<SysUserDO> getPageList(PageRequestVO pageRequestVO) {
		PageRequest pageRequest = null;

		int pageNum = pageRequestVO.getPageNum();
		int pageSize = pageRequestVO.getPageSize();

		/*使用方法如此
		Object name = pageRequestVO.getParamValue("name");
		Object email = pageRequestVO.getParamValue("email");
		if (name != null) {
			if (email != null) {}
		} else {

		}*/

		pageRequest = PageRequest.of(pageNum-1, pageSize);

		Page<SysUserDO> page = null;
		String username = pageRequestVO.getParamValue("username");
		String nickName = pageRequestVO.getParamValue("nickName");
		String status = pageRequestVO.getParamValue("status");
		String sex = pageRequestVO.getParamValue("sex");

		try {
			if (username == null &&
				nickName == null &&
				status == null &&
				sex == null) {
				page = sysUserDAO.findAll(pageRequest);
			} else {
				// JPA复杂查询（单表、多条件）
				page = sysUserDAO.findAll(new Specification<SysUserDO>() {
					@Override
					public Predicate toPredicate(Root<SysUserDO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
						List<Predicate> predicates = new ArrayList<>();
						if (username != null && username.length() != 0) {
							predicates.add(criteriaBuilder.like(root.get("username").as(String.class), "%"+username+"%"));
						}
						if (nickName != null && nickName.length() != 0) {
							predicates.add(criteriaBuilder.like(root.get("nickName").as(String.class), "%"+nickName+"%"));
						}
						if (status != null && status.length() != 0) {
							predicates.add(criteriaBuilder.equal(root.get("status").as(Byte.class), status));
						}
						if (sex != null && sex.length() != 0) {
							predicates.add(criteriaBuilder.equal(root.get("sex").as(Byte.class), sex));
						}
						Predicate[] pre = new Predicate[predicates.size()];
						query.where(predicates.toArray(pre));
						return criteriaBuilder.and(predicates.toArray(pre));

						// TODO.. 后续可再增加部门筛选，参Timo
					}
				}, pageRequest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<SysUserDO> content = page.getContent();
		// 用户信息填充
		for (SysUserDO item : content) {
			// 填充用户所属机构
			Long userId = item.getId();
			Long deptId = item.getDeptId();
			String deptName = sysDeptService.getById(deptId).getName();
			item.setDeptName(deptName);

			// 填充用户角色名集合
			List<SysUserRoleDO> urs = sysUserRoleDAO.findByUserId(userId);
			if (urs != null && urs.size() != 0) {
				// 填充用户对应包含的用户角色信息
				item.setUserRoles(urs);

				// 填充用户包含的角色名称字符串
				String roleNames = buildRoleNames(urs);
				if (roleNames != null && !roleNames.equals("")) {
					item.setRoleNames(roleNames);
				}
			}
		}

		PageVO<SysUserDO> userPageVO = new PageVO<>();
		userPageVO.setList(content);
		userPageVO.setPageNum(page.getNumber()+1);
		userPageVO.setPageSize(page.getSize());
		userPageVO.setTotalPage(page.getTotalPages());
		userPageVO.setTotalRow(page.getTotalElements());

		return userPageVO;
	}

	/**
	 * 组织用户对应的角色名信息
	 * @param urs 用户对应的用户角色对象集合
	 * @return 用户对应的角色名信息
	 */
	private String buildRoleNames(List<SysUserRoleDO> urs) {
		StringBuilder sb = new StringBuilder();
		List<Long> roleIds = new ArrayList<>();
		if (urs != null && urs.size() != 0) {
			for (SysUserRoleDO ur : urs) {
				roleIds.add(ur.getRoleId());
			}
		}
		if (roleIds != null && roleIds.size() != 0) {
			List<SysRoleDO> roleList = sysRoleDAO.findByIdIn(roleIds);
			if (roleList != null && roleList.size() != 0) {
				for (SysRoleDO role : roleList) {
					sb.append(role.getRemark());
					sb.append("，");
				}
				sb.deleteCharAt(sb.lastIndexOf("，"));
				return sb.toString();
			}
		}
		return null;
	}
}

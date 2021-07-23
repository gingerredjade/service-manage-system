package com.smp.admin.service.impl.sys;

import com.smp.admin.constant.SysConstants;
import com.smp.admin.dao.sys.SysMenuDAO;
import com.smp.admin.dao.sys.SysRoleMenuDAO;
import com.smp.admin.model.po.sys.SysMenuDO;
import com.smp.admin.model.po.sys.SysRoleMenuDO;
import com.smp.admin.service.iface.sys.SysMenuService;
import com.smp.common.api.PageRequestVO;
import com.smp.common.api.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 菜单管理Service实现类
 * @author Hongyu Jiang
 * @since  Apr. 27 2020
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

	private static Logger logger = LoggerFactory.getLogger(SysMenuServiceImpl.class);

	private final SysMenuDAO sysMenuDAO;
	private final SysRoleMenuDAO sysRoleMenuDAO;
	@Autowired
	public SysMenuServiceImpl(SysMenuDAO sysMenuDAO,
							  SysRoleMenuDAO sysRoleMenuDAO) {
		this.sysMenuDAO = sysMenuDAO;
		this.sysRoleMenuDAO = sysRoleMenuDAO;
	}

	@Override
	public int save(SysMenuDO record) {
		try {
			sysMenuDAO.save(record);
			return 0;
		} catch(Exception e) {
			logger.error("save SysMenuDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(SysMenuDO record) {
		try {
			sysMenuDAO.delete(record);
			return 0;
		} catch (Exception e) {
			logger.error("remove SysMenuDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(List<SysMenuDO> records) {
		try {
			sysMenuDAO.deleteInBatch(records);
			return 0;
		} catch (Exception e) {
			logger.error("remove SysMenuDO list error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public SysMenuDO getById(Long id) {
		Optional<SysMenuDO> optional = sysMenuDAO.findById(id);
		if(optional != null && optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public PageVO<SysMenuDO> getPageList(PageRequestVO pageRequestVO) {
		PageRequest pageRequest = null;

		int pageNum = pageRequestVO.getPageNum();
		int pageSize = pageRequestVO.getPageSize();

		pageRequest = PageRequest.of(pageNum-1, pageSize);

		Page<SysMenuDO> page = null;

		page = sysMenuDAO.findAll(pageRequest);

		List<SysMenuDO> content = page.getContent();
		PageVO<SysMenuDO> menutVO = new PageVO<>();
		menutVO.setList(content);
		menutVO.setPageNum(page.getNumber()+1);
		menutVO.setPageSize(page.getSize());
		menutVO.setTotalPage(page.getTotalPages());
		menutVO.setTotalRow(page.getTotalElements());

		return menutVO;
	}

	@Override
	public List<SysMenuDO> findTree(String userName, int menuType) {
		List<SysMenuDO> sysMenus = new ArrayList<>();
		List<SysMenuDO> menus = findByUserName(userName);
		for (SysMenuDO menu : menus) {
			if (menu.getParentId() == null || menu.getParentId() == 0) {
				menu.setLevel(0);
				if (!exists(sysMenus, menu)) {
					sysMenus.add(menu);
				}
			}
		}
		//sysMenus.sort(((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum())));
		findChildren(sysMenus, menus, menuType);
		return sysMenus;
	}

	@Override
	public List<SysMenuDO> findTreeByName(String name, int menuType) {
		List<SysMenuDO> sysMenus = new ArrayList<>();
		List<SysMenuDO> menus = sysMenuDAO.findByName(name);
		for (SysMenuDO menu : menus) {
			if (menu.getParentId() == null || menu.getParentId() == 0) {
				menu.setLevel(0);
				if (!exists(sysMenus, menu)) {
					sysMenus.add(menu);
				}
			}
		}
		findChildren(sysMenus, menus, menuType);
		return sysMenus;
	}

	@Override
	public List<SysMenuDO> findByName(String name) {
		return sysMenuDAO.findByName(name);
	}

	@Override
	public List<SysMenuDO> findByUserName(String userName) {
		/*
		 * admin超级管理员给全部菜单权限
		 * 其他根据用户名查询对应包含的菜单信息
		 * 超级管理员默认只有admin一个,非admin用户若指定了超级管理员角色，其菜单信息会为空,无权访问。
		 * 	故，一般不应为新建立用户赋予超级管理员权限。
		 */
		if (userName == null || "".equals(userName) || SysConstants.ADMIN.equalsIgnoreCase(userName)) {
			return sysMenuDAO.findAll();
		}
		return sysMenuDAO.findByUserName(userName);
	}

	@Override
	public List<SysMenuDO> findByRoleId(Long roleId) {
		return sysMenuDAO.findByRoleId(roleId);
	}

	@Override
	public List<SysRoleMenuDO> findByMenuId(Long menuId) {
		return sysRoleMenuDAO.findByMenuId(menuId);
	}

	@Override
	public List<SysMenuDO> getListByExample(Example<SysMenuDO> example, Sort sort) {
		return sysMenuDAO.findAll(example, sort);
	}

	@Override
	public SysMenuDO getByMenuToExample(SysMenuDO menu) {
		return sysMenuDAO.findOne(Example.of(menu)).orElse(null);
	}

	@Override
	public SysMenuDO getByUrl(String url) {
		return sysMenuDAO.findByUrl(url);
	}


	/**
	 *
	 * @param sysMenus 父菜单实体
	 * @param menus 所有菜单实体
	 * @param menuType 菜单类型
	 */
	private void findChildren(List<SysMenuDO> sysMenus, List<SysMenuDO> menus, int menuType) {
		for (SysMenuDO sysMenu : sysMenus) {
			List<SysMenuDO> children = new ArrayList<>();
			for (SysMenuDO menu : menus) {
				if (menuType ==1 && menu.getType() == 2) {
					// 如果是获取类型不需要按钮，且菜单类型是按钮的，直接过滤掉
					continue;
				}
				if (sysMenu.getId() != null && sysMenu.getId().equals(menu.getParentId())) {
					menu.setParentName(sysMenu.getName());
					menu.setLevel(sysMenu.getLevel() + 1);
					if (!exists(children, menu)) {
						children.add(menu);
					}
				}
			}
			sysMenu.setChildren(children);
			children.sort(((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum())));
			findChildren(children, menus, menuType);
		}
	}


	/**
	 * 判断列表中是否已经存在指定菜单
	 * @param sysMenus 指定列表
	 * @param sysMenu 被判断是否包含在列表中的菜单实体
	 * @return 是否包含在列表中
	 */
	private boolean exists(List<SysMenuDO> sysMenus, SysMenuDO sysMenu) {
		boolean exist = false;
		for (SysMenuDO menu : sysMenus) {
			if (menu.getId().equals(sysMenu.getId())) {
				exist = true;
			}
		}
		return exist;
	}
}

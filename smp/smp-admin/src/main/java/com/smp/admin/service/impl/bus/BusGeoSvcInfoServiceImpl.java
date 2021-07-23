package com.smp.admin.service.impl.bus;

import com.smp.admin.constant.enums.AuditTypeEnum;
import com.smp.admin.constant.enums.ReleaseTypeEnum;
import com.smp.admin.dao.bus.BusGeoSvcInfoDAO;
import com.smp.admin.model.po.bus.svcinfo.BusGeoSvcInfoDO;
import com.smp.admin.model.po.bus.svcinfo.BusSvcSubjectDO;
import com.smp.admin.model.po.bus.svcinfo.BusSvcTypeDO;
import com.smp.admin.model.vo.ServiceCatalogVO;
import com.smp.admin.model.vo.ServiceTypeVO;
import com.smp.admin.service.iface.bus.BusGeoSvcInfoService;
import com.smp.admin.service.iface.bus.BusSvcSubjectService;
import com.smp.admin.service.iface.bus.BusSvcTypeService;
import com.smp.admin.service.iface.sys.SysDeptService;
import com.smp.common.api.PageRequestVO;
import com.smp.common.api.PageVO;
import com.smp.common.api.UniversalRequestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;


/**
 * 地理信息服务信息 管理Service实现类
 * @author Hongyu Jiang
 * @since  May. 12 2020
 */
@Service
public class BusGeoSvcInfoServiceImpl implements BusGeoSvcInfoService {

	private static final Logger logger = LoggerFactory.getLogger(BusGeoSvcInfoServiceImpl.class);

	/**
	 * 采用构造器方式,注入DAO层&需要的Service层
	 */
	private final BusGeoSvcInfoDAO busGeoSvcInfoDAO;
	private final SysDeptService sysDeptService;
	private final BusSvcTypeService svcTypeService;
	private final BusSvcSubjectService svcSubjectService;
	@Autowired
	public BusGeoSvcInfoServiceImpl(BusGeoSvcInfoDAO busGeoSvcInfoDAO,
									SysDeptService sysDeptService,
									BusSvcTypeService svcTypeService,
									BusSvcSubjectService svcSubjectService) {
		this.busGeoSvcInfoDAO = busGeoSvcInfoDAO;
		this.sysDeptService = sysDeptService;
		this.svcTypeService = svcTypeService;
		this.svcSubjectService = svcSubjectService;
	}


	@Override
	public boolean deleteSvcInfo(Long id) {
		try {
			busGeoSvcInfoDAO.deleteById(id);
			return true;
		} catch (Exception e) {
			logger.error("remove BusGeoSvcInfoDO error, {}", e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Page<BusGeoSvcInfoDO> listSvcInfos(Integer pageNum, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNum-1, pageSize);
		return busGeoSvcInfoDAO.findAll(pageRequest);
	}

	@Override
	public List<BusGeoSvcInfoDO> listAll() {
		return busGeoSvcInfoDAO.findAll();
	}

	@Override
	public List<BusGeoSvcInfoDO> listBySvcTypeId(Long svcTypeId) {
		return busGeoSvcInfoDAO.listBySvcTypeId(svcTypeId);
	}

	@Override
	public List<BusGeoSvcInfoDO> listByDeptId(Long deptId) {
		return busGeoSvcInfoDAO.listByDeptId(deptId);
	}

	@Override
	public Boolean updateAuditStatusAndOpinion(AuditTypeEnum auditTypeEnum, Long id, String auditOpinion) {
		//int num = busGeoSvcInfoDAO.updateAuditStatus(auditTypeEnum.getCode(), id, auditOpinion);
		return busGeoSvcInfoDAO.updateAuditStatus(auditTypeEnum.getCode(), id, auditOpinion) > 0;
	}

	@Override
	public Boolean updateReleaseStatus(ReleaseTypeEnum releaseTypeEnum, List<Long> id) {
		return busGeoSvcInfoDAO.updateReleaseStatus(releaseTypeEnum.getCode(), id) > 0;
	}

	@Override
	public List getCatalogAll() {
		// 全部服务目录信息
		List<ServiceCatalogVO> svcCatalogs = new ArrayList<>();
		int num = 0;
		int afNUm = 0;
		List<BusSvcSubjectDO> subjectList = svcSubjectService.listAll();
		for (BusSvcSubjectDO subject : subjectList) {
			ServiceCatalogVO serviceCatalogVO = new ServiceCatalogVO();
			List<ServiceTypeVO> typeInfoList = new ArrayList<>();

			List<BusSvcTypeDO> typeList = svcTypeService.listBySubjectId(subject.getSubjectId());

			for (BusSvcTypeDO type : typeList) {
				// 只允许查询已发布的服务
				//List<BusGeoSvcInfoDO> svcInfo = busGeoSvcInfoDAO.listBySvcTypeId(type.getTypeId());
				List<BusGeoSvcInfoDO> svcInfo = busGeoSvcInfoDAO.listReleasedBySvcTypeId(type.getTypeId(), ReleaseTypeEnum.RELEASED.getCode());
				ServiceTypeVO typeInfo = new ServiceTypeVO();
				if (svcInfo != null && svcInfo.size() != 0) {
					typeInfo.setType(type);
					typeInfo.setSvcInfo(svcInfo);
					typeInfoList.add(typeInfo);
				}
			}
			if (typeInfoList.size() != 0) {
				serviceCatalogVO.setSubject(subject);
				serviceCatalogVO.setTypes(typeInfoList);
				svcCatalogs.add(serviceCatalogVO);
			}

		}

		return svcCatalogs;
	}

	@Override
	public List<BusGeoSvcInfoDO> listByConditions(Integer terminal, Long deptId,
												  Long svcSubjectId, Long svcTypeId,
												  String svcName, String svcVersion) {
		return busGeoSvcInfoDAO.listByConditions(terminal, deptId, svcSubjectId, svcTypeId, svcName, svcVersion);
	}

	@Override
	public List<BusGeoSvcInfoDO> listByStyleId(Long styleId) {
		try {
			List<BusGeoSvcInfoDO> list = busGeoSvcInfoDAO.listBySvcStyleId(styleId);
			return list;
		} catch (Exception e) {
			logger.error("get BusGeoSvcInfoDO list error, {}", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BusGeoSvcInfoDO> getList(UniversalRequestVO requestVO) {

		List<BusGeoSvcInfoDO> list = null;
		String terminal = requestVO.getParamValue("terminal");
		String deptName = requestVO.getParamValue("deptName");
		String svcSubjectName = requestVO.getParamValue("svcSubjectName");
		String svcTypeName = requestVO.getParamValue("svcTypeName");

		String name = requestVO.getParamValue("name");
		String alias = requestVO.getParamValue("alias");
		String version = requestVO.getParamValue("version");

		String svcKeyword = requestVO.getParamValue("svcKeyword");

		String auditState = requestVO.getParamValue("auditState");
		String releaseState = requestVO.getParamValue("releaseState");

		try {
			if (name == null &&
				alias == null &&
				svcSubjectName == null &&
				svcTypeName == null &&
				svcKeyword == null &&
				deptName == null &&
				version == null &&
				terminal == null &&
				auditState == null &&
				releaseState == null){
				list = busGeoSvcInfoDAO.findAll();
			} else {
				// JPA复杂查询（单表、多条件）
				list = busGeoSvcInfoDAO.findAll(new Specification<BusGeoSvcInfoDO>() {
					@Override
					public Predicate toPredicate(Root<BusGeoSvcInfoDO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
						List<Predicate> predicates = new ArrayList<>();
						if (name != null && name.length() != 0) {
							predicates.add(criteriaBuilder.like(root.get("svcName").as(String.class), "%"+name+"%"));
						}
						if (alias != null && alias.length() != 0) {
							predicates.add(criteriaBuilder.like(root.get("svcAlias").as(String.class), "%"+alias+"%"));
						}
						if (svcSubjectName != null && svcSubjectName.length() != 0) {
							predicates.add(criteriaBuilder.like(root.get("svcTypeDO").get("busSvcSubjectDO").get("name").as(String.class), "%"+svcSubjectName+"%"));
						}
						if (svcTypeName != null && svcTypeName.length() != 0) {
							predicates.add(criteriaBuilder.like(root.get("svcTypeDO").get("name").as(String.class), "%"+svcTypeName+"%"));
						}
						if (svcKeyword != null && svcKeyword.length() != 0) {
							predicates.add(criteriaBuilder.like(root.get("svcKeyword").as(String.class), "%"+svcKeyword+"%"));
						}
						if (deptName != null && deptName.length() != 0) {
							predicates.add(criteriaBuilder.like(root.get("sysDeptDO").get("name").as(String.class), "%"+deptName+"%"));
						}
						if (version != null && version.length() != 0) {
							predicates.add(criteriaBuilder.like(root.get("svcVersion").as(String.class), "%"+version+"%"));
						}
						if (terminal != null && terminal.length() != 0) {
							predicates.add(criteriaBuilder.equal(root.get("terminal").as(Integer.class), terminal));
						}
						if (auditState != null && auditState.length() != 0) {
							predicates.add(criteriaBuilder.equal(root.get("auditState").as(Byte.class), auditState));
						}
						if (releaseState != null && releaseState.length() != 0) {
							predicates.add(criteriaBuilder.equal(root.get("releaseState").as(Byte.class), releaseState));
						}
						Predicate[] pre = new Predicate[predicates.size()];
						query.where(predicates.toArray(pre));
						return criteriaBuilder.and(predicates.toArray(pre));
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public int save(BusGeoSvcInfoDO record) {
		try {
			/*Long deptId = record.getSysDeptDO().getId();
			if (deptId != null) {
				SysDeptDO dept = sysDeptService.getById(deptId);
				record.setSysDeptDO(dept);
			}
			Long typeId = record.getSvcTypeDO().getTypeId();
			if (typeId != null) {
				BusSvcTypeDO type = svcTypeService.getById(typeId);

				Long subjectId = record.getSvcTypeDO().getBusSvcSubjectDO().getSubjectId();
				if (subjectId !=  null ) {
					BusSvcSubjectDO subject = svcSubjectService.getById(subjectId);
					if (subject != null) {
						type.setBusSvcSubjectDO(subject);
					}
				}
				record.setSvcTypeDO(type);
			}*/

			busGeoSvcInfoDAO.save(record);
			return 0;
		} catch (Exception e) {
			logger.error("save BusGeoSvcInfoDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(BusGeoSvcInfoDO record) {
		try {
			busGeoSvcInfoDAO.delete(record);
			return 0;
		} catch (Exception e) {
			logger.error("remove BusGeoSvcInfoDO error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int delete(List<BusGeoSvcInfoDO> records) {
		try {
			busGeoSvcInfoDAO.deleteInBatch(records);
			return 0;
		} catch (Exception e) {
			logger.error("remove BusGeoSvcInfoDO list error, {}", e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public BusGeoSvcInfoDO getById(Long id) {
		Optional<BusGeoSvcInfoDO> optional = busGeoSvcInfoDAO.findById(id);
		if(optional != null && optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public PageVO<BusGeoSvcInfoDO> getPageList(PageRequestVO pageRequestVO) {
		PageRequest pageRequest = null;

		int pageNum = pageRequestVO.getPageNum();
		int pageSize = pageRequestVO.getPageSize();

		pageRequest = PageRequest.of(pageNum-1, pageSize);

		Page<BusGeoSvcInfoDO> page = null;
		String name = pageRequestVO.getParamValue("name");
		String alias = pageRequestVO.getParamValue("alias");
		String svcSubjectName = pageRequestVO.getParamValue("svcSubjectName");
		String svcTypeName = pageRequestVO.getParamValue("svcTypeName");
		String svcKeyword = pageRequestVO.getParamValue("svcKeyword");
		String deptName = pageRequestVO.getParamValue("deptName");
		String version = pageRequestVO.getParamValue("version");
		String terminal = pageRequestVO.getParamValue("terminal");
		String auditState = pageRequestVO.getParamValue("auditState");
		String releaseState = pageRequestVO.getParamValue("releaseState");

		try {
			if (name == null &&
				alias == null &&
				svcSubjectName == null &&
				svcTypeName == null &&
				svcKeyword == null &&
				deptName == null &&
				version == null &&
				terminal == null &&
				auditState == null &&
				releaseState == null){
				page = busGeoSvcInfoDAO.findAll(pageRequest);
			} else {
				// JPA复杂查询（单表、多条件）
				page = busGeoSvcInfoDAO.findAll(new Specification<BusGeoSvcInfoDO>() {
					@Override
					public Predicate toPredicate(Root<BusGeoSvcInfoDO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
						List<Predicate> predicates = new ArrayList<>();
						if (name != null && name.length() != 0) {
							predicates.add(criteriaBuilder.like(root.get("svcName").as(String.class), "%"+name+"%"));
						}
						if (alias != null && alias.length() != 0) {
							predicates.add(criteriaBuilder.like(root.get("svcAlias").as(String.class), "%"+alias+"%"));
						}
						if (svcSubjectName != null && svcSubjectName.length() != 0) {
							predicates.add(criteriaBuilder.like(root.get("svcTypeDO").get("busSvcSubjectDO").get("name").as(String.class), "%"+svcSubjectName+"%"));
						}
						if (svcTypeName != null && svcTypeName.length() != 0) {
							predicates.add(criteriaBuilder.like(root.get("svcTypeDO").get("name").as(String.class), "%"+svcTypeName+"%"));
						}
						if (svcKeyword != null && svcKeyword.length() != 0) {
							predicates.add(criteriaBuilder.like(root.get("svcKeyword").as(String.class), "%"+svcKeyword+"%"));
						}
						if (deptName != null && deptName.length() != 0) {
							predicates.add(criteriaBuilder.like(root.get("sysDeptDO").get("name").as(String.class), "%"+deptName+"%"));
						}
						if (version != null && version.length() != 0) {
							predicates.add(criteriaBuilder.like(root.get("svcVersion").as(String.class), "%"+version+"%"));
						}
						if (terminal != null && terminal.length() != 0) {
							predicates.add(criteriaBuilder.equal(root.get("terminal").as(Integer.class), terminal));
						}
						if (auditState != null && auditState.length() != 0) {
							predicates.add(criteriaBuilder.equal(root.get("auditState").as(Byte.class), auditState));
						}
						if (releaseState != null && releaseState.length() != 0) {
							predicates.add(criteriaBuilder.equal(root.get("releaseState").as(Byte.class), releaseState));
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

		List<BusGeoSvcInfoDO> content = page.getContent();
		PageVO<BusGeoSvcInfoDO> svcInfoPageVO = new PageVO<>();
		svcInfoPageVO.setList(content);
		svcInfoPageVO.setPageNum(page.getNumber()+1);
		svcInfoPageVO.setPageSize(page.getSize());
		svcInfoPageVO.setTotalPage(page.getTotalPages());
		svcInfoPageVO.setTotalRow(page.getTotalElements());

		return svcInfoPageVO;
	}
}

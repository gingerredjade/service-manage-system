package com.smp.admin.service.impl.bus;

import com.smp.admin.constant.enums.AuditTypeEnum;
import com.smp.admin.constant.enums.ReleaseTypeEnum;
import com.smp.admin.constant.enums.TerminalEnum;
import com.smp.admin.dao.bus.BusGeoSvcInfoDAO;
import com.smp.admin.dao.bus.BusSvcSubjectDAO;
import com.smp.admin.dao.bus.BusSvcTypeDAO;
import com.smp.admin.dao.sys.SysDeptDAO;
import com.smp.admin.model.vo.ServiceStatDetail;
import com.smp.admin.model.vo.ServiceStatVO;
import com.smp.admin.service.iface.bus.BusSvcStatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务统计 管理Service实现类
 * @author Hongyu Jiang
 * @since  Sep. 7 2020
 */
@Service
public class BusSvcStatServiceImpl implements BusSvcStatService {

	private static final Logger logger = LoggerFactory.getLogger(BusSvcStatServiceImpl.class);

	/**
	 * 采用构造器方式,注入DAO层&需要的Service层
	 */
	private final BusSvcSubjectDAO busSvcSubjectDAO;
	private final BusSvcTypeDAO busSvcTypeDAO;
	private final SysDeptDAO deptDAO;
	private final BusGeoSvcInfoDAO busGeoSvcInfoDAO;
	@Autowired
	public BusSvcStatServiceImpl(BusSvcSubjectDAO busSvcSubjectDAO,
								 BusSvcTypeDAO busSvcTypeDAO,
								 SysDeptDAO deptDAO,
								 BusGeoSvcInfoDAO busGeoSvcInfoDAO) {
		this.busSvcSubjectDAO = busSvcSubjectDAO;
		this.busSvcTypeDAO = busSvcTypeDAO;
		this.deptDAO = deptDAO;
		this.busGeoSvcInfoDAO = busGeoSvcInfoDAO;
	}

	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public ServiceStatVO statBySubject() {
		List list = busGeoSvcInfoDAO.countBySubject();
		return buildServiceStatVO(list);
	}

	@Override
	public ServiceStatVO statByType() {
		List list = busGeoSvcInfoDAO.countByType();
		return buildServiceStatVO(list);
	}

	@Override
	public ServiceStatVO statByDept() {
		List list = busGeoSvcInfoDAO.countByDept();
		return buildServiceStatVO(list);
	}


	/**
	 * 根据列表数据构建服务统计结果对象
	 * @param data
	 * @return
	 */
	private ServiceStatVO buildServiceStatVO(List data) {
		ServiceStatVO statVO = new ServiceStatVO();
		List<String> keys = new ArrayList<>();
		List<Long> values = new ArrayList<>();
		List<ServiceStatDetail> statDetails = new ArrayList<>();
		if (data.size() != 0) {
			for (int i=0; i < data.size(); i++) {
				ServiceStatDetail detail = new ServiceStatDetail();
				Object[] obj = (Object[]) data.get(i);
				String name = String.valueOf(obj[0]);
				Long count = (Long) obj[1];

				keys.add(name);
				values.add(count);

				detail.setName(name);
				detail.setValue(count);
				statDetails.add(detail);
			}
		}
		statVO.setKeys(keys);
		statVO.setValues(values);
		statVO.setDetail(statDetails);

		return statVO;
	}


	@Override
	public ServiceStatVO statByTerminal() {
		Map<String, Object> params = new HashMap<>();

		StringBuilder sb = new StringBuilder("select svc.terminal, count(svc.terminal) " +
			"from BusGeoSvcInfoDO svc " +
			"group by svc.terminal");

		// 创建JPQL查询(HQL)
		Query query = entityManager.createQuery(sb.toString());

		// 设置查询参数(如果有条件查询参数放入了params里，此处需遍历设置)

		List data = query.getResultList();

		ServiceStatVO statVO = new ServiceStatVO();
		List<String> keys = new ArrayList<>();
		List<Long> values = new ArrayList<>();
		List<ServiceStatDetail> statDetails = new ArrayList<>();
		if (data.size() != 0) {
			for (int i=0; i < data.size(); i++) {
				ServiceStatDetail detail = new ServiceStatDetail();
				Object[] obj = (Object[]) data.get(i);
				Integer key = (Integer) obj[0];
				Long count = (Long) obj[1];

				String name = "";
				if ( key == Integer.valueOf(TerminalEnum.GENERAL.getCode()) ) {
					name = TerminalEnum.GENERAL.getMessage();
				} else if ( key == Integer.valueOf(TerminalEnum.WEB.getCode()) ) {
					name = TerminalEnum.WEB.getMessage();
				} else if ( key == Integer.valueOf(TerminalEnum.DESKTOP.getCode()) ) {
					name = TerminalEnum.DESKTOP.getMessage();
				} else {
					name = "未知";
				}
				keys.add(name);
				values.add(count);

				detail.setName(name);
				detail.setValue(count);
				statDetails.add(detail);
			}
		}
		statVO.setKeys(keys);
		statVO.setValues(values);
		statVO.setDetail(statDetails);

		return statVO;
	}

	@Override
	public ServiceStatVO statByGisOrNo() {
		List data = busGeoSvcInfoDAO.countByGisOrNo();
		ServiceStatVO statVO = new ServiceStatVO();
		List<String> keys = new ArrayList<>();
		List<Long> values = new ArrayList<>();
		List<ServiceStatDetail> statDetails = new ArrayList<>();
		if (data.size() != 0) {
			for (int i=0; i < data.size(); i++) {
				ServiceStatDetail detail = new ServiceStatDetail();
				Object[] obj = (Object[]) data.get(i);
				Integer key = (Integer) obj[0];
				Long count = (Long) obj[1];

				String name = "";
				if (key == 1) {
					name = "地理信息服务";
				} else if (key == 0) {
					name = "非地理信息服务";
				} else {
					name = "未知";
				}

				keys.add(name);
				values.add(count);

				detail.setName(name);
				detail.setValue(count);
				statDetails.add(detail);
			}
		}
		statVO.setKeys(keys);
		statVO.setValues(values);
		statVO.setDetail(statDetails);

		return statVO;
	}

	@Override
	public Long countSvcGross() {
		return busGeoSvcInfoDAO.countSvcGross();
	}

	@Override
	public Long countSvcByUnreleased() {
		//return busGeoSvcInfoDAO.countSvcByReleaseState(ReleaseTypeEnum.UNRELEASED.getCode());
		return busGeoSvcInfoDAO.countSvcByReleaseStateAndAuditState(ReleaseTypeEnum.UNRELEASED.getCode(), AuditTypeEnum.AUDITED_PASS.getCode());
	}

	@Override
	public Long countSvcByReleased() {
		return busGeoSvcInfoDAO.countSvcByReleaseState(ReleaseTypeEnum.RELEASED.getCode());
	}

	@Override
	public Long countSvcByAuditedPass() {
		return busGeoSvcInfoDAO.countSvcByAuditState(AuditTypeEnum.AUDITED_PASS.getCode());
	}

	@Override
	public Long countSvcByUnaudited() {
		return busGeoSvcInfoDAO.countSvcByAuditState(AuditTypeEnum.UNAUDITED.getCode());
	}


}

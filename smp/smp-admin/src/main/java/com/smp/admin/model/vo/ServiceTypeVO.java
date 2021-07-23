package com.smp.admin.model.vo;

import com.smp.admin.model.po.bus.svcinfo.BusGeoSvcInfoDO;
import com.smp.admin.model.po.bus.svcinfo.BusSvcTypeDO;

import java.util.List;

public class ServiceTypeVO {
	BusSvcTypeDO type;
	List<BusGeoSvcInfoDO> svcInfo;

	public BusSvcTypeDO getType() {
		return type;
	}

	public void setType(BusSvcTypeDO type) {
		this.type = type;
	}

	public List<BusGeoSvcInfoDO> getSvcInfo() {
		return svcInfo;
	}

	public void setSvcInfo(List<BusGeoSvcInfoDO> svcInfo) {
		this.svcInfo = svcInfo;
	}
}

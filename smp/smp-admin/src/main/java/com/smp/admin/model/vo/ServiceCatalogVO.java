package com.smp.admin.model.vo;

import com.smp.admin.model.po.bus.svcinfo.BusSvcSubjectDO;

import java.util.List;

public class ServiceCatalogVO {
	BusSvcSubjectDO subject;
	List<ServiceTypeVO> types;

	public BusSvcSubjectDO getSubject() {
		return subject;
	}

	public void setSubject(BusSvcSubjectDO subject) {
		this.subject = subject;
	}

	public List<ServiceTypeVO> getTypes() {
		return types;
	}

	public void setTypes(List<ServiceTypeVO> types) {
		this.types = types;
	}
}

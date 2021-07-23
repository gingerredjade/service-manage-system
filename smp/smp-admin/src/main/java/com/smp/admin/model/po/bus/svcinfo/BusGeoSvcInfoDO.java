package com.smp.admin.model.po.bus.svcinfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smp.admin.constant.enums.AuditTypeEnum;
import com.smp.admin.constant.enums.ReleaseTypeEnum;
import com.smp.admin.model.po.bus.ServiceBase;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 地理信息服务信息模型
 * @author Hongyu Jiang
 * @since Apr. 26 2020
 */
@Entity
@Table(name = "bus_svc_info_geo")
@org.hibernate.annotations.Table(appliesTo = "bus_svc_info_geo", comment = "地理信息服务信息")
@EntityListeners(AuditingEntityListener.class)
public class BusGeoSvcInfoDO extends ServiceBase implements Serializable {

	private static final long serialVersionUID = 1L;


	private String svcCoverage;                	// 覆盖范围( 全球/国家/省/市(县) )
	private String svcServiceArea;              // 服务范围( 经纬度 )
	private String svcCoordinateSystem;        	// 坐标系( CGCS2000、WGS84.... )
	private String svcProjectionType;           // 投影类型( 经纬度/Web墨卡托 )
	private String svcUpdateCycle;              // 更新周期( 大于1年/1年/实时/其他 )


	/*
	 * 图层基本信息
	 */
	private String layerName;                	// 图层名称
	private String layerDesc;                	// 图层简介
	private String layerKeyword;            	// 图层关键字
	private String layerCoverage;            	// 图层覆盖范围
	private String layerServiceArea;        	// 图层数据范围（经纬度）
	private String layerCoordinateSystem;    	// 图层坐标系
	private String layerProjectionType;         // 图层投影类型
	private String layerUpdateCycle;        	// 图层更新周期

//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date layerUpdateTime;            	// 图层最新更新时间


	/*
	 * 发布相关属性
	 */
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date releaseTime;                	// 发布时间
	private Byte releaseState =
		ReleaseTypeEnum.UNRELEASED.getCode();	// 发布状态 (0:未发布, 1:已发布)


	/*
	 * 审核相关属性
	 */
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date auditTime;                    	// 审核时间
	private Byte auditState =
		AuditTypeEnum.UNAUDITED.getCode();		// 审核状态 (0:未审核, 1:审核通过, 2:审核拒绝)
	private String auditOpinion;				// 审核意见

	/*
	 * 服务调用示例（备用）
	 * 		建议使用ServiceBase中的interfaceSiteUrl属性
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String instroRequestParam;			// 请求参数说明

	private String instroDesc;					// 简要描述

	private String instroRequestType;			// 请求方式（GET/POST）


	public String getSvcCoverage() {
		return svcCoverage;
	}

	public void setSvcCoverage(String svcCoverage) {
		this.svcCoverage = svcCoverage;
	}

	public String getSvcServiceArea() {
		return svcServiceArea;
	}

	public void setSvcServiceArea(String svcServiceArea) {
		this.svcServiceArea = svcServiceArea;
	}

	public String getSvcCoordinateSystem() {
		return svcCoordinateSystem;
	}

	public void setSvcCoordinateSystem(String svcCoordinateSystem) {
		this.svcCoordinateSystem = svcCoordinateSystem;
	}

	public String getSvcProjectionType() {
		return svcProjectionType;
	}

	public void setSvcProjectionType(String svcProjectionType) {
		this.svcProjectionType = svcProjectionType;
	}

	public String getSvcUpdateCycle() {
		return svcUpdateCycle;
	}

	public void setSvcUpdateCycle(String svcUpdateCycle) {
		this.svcUpdateCycle = svcUpdateCycle;
	}

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	public String getLayerDesc() {
		return layerDesc;
	}

	public void setLayerDesc(String layerDesc) {
		this.layerDesc = layerDesc;
	}

	public String getLayerKeyword() {
		return layerKeyword;
	}

	public void setLayerKeyword(String layerKeyword) {
		this.layerKeyword = layerKeyword;
	}

	public String getLayerCoverage() {
		return layerCoverage;
	}

	public void setLayerCoverage(String layerCoverage) {
		this.layerCoverage = layerCoverage;
	}

	public String getLayerServiceArea() {
		return layerServiceArea;
	}

	public void setLayerServiceArea(String layerServiceArea) {
		this.layerServiceArea = layerServiceArea;
	}

	public String getLayerCoordinateSystem() {
		return layerCoordinateSystem;
	}

	public void setLayerCoordinateSystem(String layerCoordinateSystem) {
		this.layerCoordinateSystem = layerCoordinateSystem;
	}

	public String getLayerProjectionType() {
		return layerProjectionType;
	}

	public void setLayerProjectionType(String layerProjectionType) {
		this.layerProjectionType = layerProjectionType;
	}

	public String getLayerUpdateCycle() {
		return layerUpdateCycle;
	}

	public void setLayerUpdateCycle(String layerUpdateCycle) {
		this.layerUpdateCycle = layerUpdateCycle;
	}

	public Date getLayerUpdateTime() {
		return layerUpdateTime;
	}

	public void setLayerUpdateTime(Date layerUpdateTime) {
		this.layerUpdateTime = layerUpdateTime;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Byte getReleaseState() {
		return releaseState;
	}

	public void setReleaseState(Byte releaseState) {
		this.releaseState = releaseState;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Byte getAuditState() {
		return auditState;
	}

	public void setAuditState(Byte auditState) {
		this.auditState = auditState;
	}

	public String getInstroRequestParam() {
		return instroRequestParam;
	}

	public void setInstroRequestParam(String instroRequestParam) {
		this.instroRequestParam = instroRequestParam;
	}

	public String getInstroDesc() {
		return instroDesc;
	}

	public void setInstroDesc(String instroDesc) {
		this.instroDesc = instroDesc;
	}

	public String getInstroRequestType() {
		return instroRequestType;
	}

	public void setInstroRequestType(String instroRequestType) {
		this.instroRequestType = instroRequestType;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
}

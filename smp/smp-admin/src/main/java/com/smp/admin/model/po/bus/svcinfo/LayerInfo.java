package com.smp.admin.model.po.bus.svcinfo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class LayerInfo {

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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date layerUpdateTime;            	// 图层最新更新时间


}

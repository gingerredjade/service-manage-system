package com.smp.common.constant.ServiceTypes;

import java.util.HashMap;
import java.util.Map;

public class ServiceTypeDef {

	public static final String MAP_SERVICE_CN = "地图显示服务";
	public static final String MAPTILE_SERVICE_CN = "地图瓦片服务";
	public static final String NETWORKANALYSE_SERVICE_CN = "网络分析服务";
	public static final String PLACENAME_SERVICE_CN = "地名检索服务";
	public static final String TOPOGANALYSE_SERVICE_CN = "地形分析服务";
	public static final String OGC_SERVICE_WMS_CN = "网络地图服务WMS";
	public static final String OGC_SERVICE_WMTS_CN = "网络地图瓦片服务WMTS";
	public static final String GEOMETRY_SERVICE_CN = "几何计算服务";


	public final static Map<String, String> SEVICE_TYPE_MAP = new HashMap<String, String>();

	static {
		// 地图显示服务
		SEVICE_TYPE_MAP.put(MAP_SERVICE.value, MAP_SERVICE_CN);

		// 地图瓦片服务
		SEVICE_TYPE_MAP.put(MAPTILE_SERVICE.value, MAPTILE_SERVICE_CN);

		// 网络分析服务
		SEVICE_TYPE_MAP.put(NETWORKANALYSE_SERVICE.value, NETWORKANALYSE_SERVICE_CN);

		// 地名检索服务
		SEVICE_TYPE_MAP.put(PLACENAME_SERVICE.value, PLACENAME_SERVICE_CN);

		// 地形分析服务
		SEVICE_TYPE_MAP.put(TOPOGANALYSE_SERVICE.value, TOPOGANALYSE_SERVICE_CN);

		// 网络地图服务WMS
		SEVICE_TYPE_MAP.put(OGC_SERVICE_WMS.value, OGC_SERVICE_WMS_CN);

		// 网络地图瓦片服务WMTS
		SEVICE_TYPE_MAP.put(OGC_SERVICE_WMTS.value, OGC_SERVICE_WMTS_CN);

		// 几何计算服务
		SEVICE_TYPE_MAP.put(GEOMETRY_SERVICE.value, GEOMETRY_SERVICE_CN);

	}


	public static String getServiceNameCN(String svcIdentify) {
		String serviceNameCN = SEVICE_TYPE_MAP.get(svcIdentify);
		if (serviceNameCN == null) {
			return null;
		}
		return serviceNameCN;

	}





}

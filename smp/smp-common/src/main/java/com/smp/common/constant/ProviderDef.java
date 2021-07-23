package com.smp.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务供应商名称定义
 */
public class ProviderDef {

	// Myself
	public static final String PROVIDER_CETC15 = "cetc15";
	public static final String PROVIDER_CETC15_NAME = "中国电子科技集团公司第十五研究所";

	// 超图
	public static final String PROVIDER_SUPERMAP = "sm";
	public static final String PROVIDER_SUPERMAP_NAME = "超图";

	// 国遥新天地
	public static final String PROVIDER_EVIMAGE = "ev";
	public static final String PROVIDER_EVIMAGE_NAME = "国遥新天地";

	// 北京星球时空科技有限公司
	public static final String PROVIDER_MAPSCLOUD = "mc";
	public static final String PROVIDER_MAPSCLOUD_NAME = "北京星球时空科技有限公司";

	// 四维图新
	public static final String PROVIDER_NAVINFO = "nav";
	public static final String PROVIDER_NAVINFO_NAME = "四维图新";

	// 庚图
	public static final String PROVIDER_GTGIS = "gt";
	public static final String PROVIDER_GTGIS_NAME = "庚图";



	public final static Map<String, String> PROVIDER_MAP = new HashMap<String, String>();

	static {
		PROVIDER_MAP.put(PROVIDER_CETC15, PROVIDER_CETC15_NAME);
		PROVIDER_MAP.put(PROVIDER_SUPERMAP, PROVIDER_SUPERMAP_NAME);
		PROVIDER_MAP.put(PROVIDER_EVIMAGE, PROVIDER_EVIMAGE_NAME);
		PROVIDER_MAP.put(PROVIDER_MAPSCLOUD, PROVIDER_MAPSCLOUD_NAME);
		PROVIDER_MAP.put(PROVIDER_NAVINFO, PROVIDER_NAVINFO_NAME);
		PROVIDER_MAP.put(PROVIDER_GTGIS, PROVIDER_GTGIS_NAME);
	}

	public static  String getProviderNameByIdentify(String identify) {
		String providerName = PROVIDER_MAP.get(identify);
		if (providerName == null) {
			return null;
		}
		return providerName;
	}



}

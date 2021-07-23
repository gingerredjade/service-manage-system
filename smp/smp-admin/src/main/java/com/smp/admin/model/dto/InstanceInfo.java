package com.smp.admin.model.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.Function;

/**
 * 微服务实例信息
 */
public class InstanceInfo {
	private static final String VERSION_UNKNOWN = "unknown";

	private static final Logger logger = LoggerFactory.getLogger(InstanceInfo.class);

	public static enum ActionType {
		ADDED,
		MODIFIED,
		DELETED;

		private ActionType() {

		}
	}

	public static enum PortType {
		SECURE,
		UNSECURE;

		private PortType() {

		}
	}

	public static enum InstanceStatus {
		UP,
		DOWN,
		STARTING,
		OUT_OF_SERVICE,
		UNKNOWN;

		private InstanceStatus() {

		}

		public static InstanceInfo.InstanceStatus toEnum(String s) {
			if (s != null) {
				try {
					return valueOf(s.toUpperCase());
				} catch (IllegalArgumentException var2) {
					InstanceInfo.logger.debug("illegal argument supplied to InstanceStatus.valueOf: {}, defaulting to {}", s, UNKNOWN);
				}
			}

			return UNKNOWN;
		}


	}



	/*
	 * 应用实例信息
	 */
	private volatile String instanceId;

	private volatile String appName;

	private volatile String serviceIdForDiscovery;

	private volatile String ipAddr;

	private volatile int port;

	private volatile String homePageUrl;

	private volatile String statusPageUrl;

	private volatile String healthPageUrl;

	private volatile InstanceInfo.InstanceStatus status;

	private String version;

	private volatile Map<String, String> metadata;



	public static final class Builder {
		private static final String COLON = ":";
		private static final String HTTPS_PROTOCOL = "https://";
		private static final String HTTP_PROTOCOL = "http://";

		private final Function<String, String> intern;

		private InstanceInfo result;

		public Builder(Function<String, String> intern, InstanceInfo result) {
			this.intern = intern;
			this.result = result;
		}
	}

}

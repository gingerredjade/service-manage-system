package com.smp.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 操作系统信息判断类
 *
 * Created by JHy on 2019/07/26
 */
public class SystemJudge {

	// log
	private static Logger logger = LoggerFactory.getLogger(SystemJudge.class);

	/**
	 * 定义Windows操作系统
	 */
	public static final String WINDOWS_64 = "win64";

	/**
	 * 定义Linux操作系统名称
	 */
	public static final String X86 = "x86";
	public static final String FEITENG = "feiteng1500";
	public static final String LOONGSON = "loongson";
	public static final String LOONGSONDESK = "loongson-desk";
	public static final String SW = "sw";

	/**
	 * 定义Linux操作系统类型
	 */
	public static final String LINUX_ARCHITECTURE_i686 = "i686";
	public static final String LINUX_ARCHITECTURE_3686 = "3686";
	public static final String LINUX_ARCHITECTURE_x86_64 = "x86_64";
	public static final String LINUX_ARCHITECTURE_sparc64 = "sparc64";
	public static final String LINUX_ARCHITECTURE_aarch64 = "aarch64";
	public static final String LINUX_ARCHITECTURE_mips64el = "mips64el";
	public static final String LINUX_ARCHITECTURE_mips64 = "mips64";
	public static final String LINUX_ARCHITECTURE_sw_64 = "sw_64";

	/**
	 * 获取Linux操作系统的类型
	 */
	public static String getOSType() throws Exception {
		String osType = null;
		Runtime run = Runtime.getRuntime();

		try {
			String commandStr = "uname -m";
			Process p = run.exec(commandStr);
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String lineStr;
			while ((lineStr = reader.readLine()) != null) {
				if (lineStr.equals(LINUX_ARCHITECTURE_i686) || lineStr.equals(LINUX_ARCHITECTURE_3686) || lineStr.equals(LINUX_ARCHITECTURE_x86_64)) {
					osType = X86;
					break;
				} else if (lineStr.equals(LINUX_ARCHITECTURE_sparc64)) {
					osType = FEITENG;
					break;
				} else if (lineStr.equals(LINUX_ARCHITECTURE_aarch64)) {
					osType = FEITENG;
					break;
				} else if (lineStr.equals(LINUX_ARCHITECTURE_mips64el)) {
					osType = LOONGSON;
					break;
				} else if (lineStr.equals(LINUX_ARCHITECTURE_mips64)) {
					osType = LOONGSONDESK;
					break;
				} else if (lineStr.equals(LINUX_ARCHITECTURE_sw_64)) {
					osType = SW;
					break;
				}
			}
			if (p.waitFor() != 0) {
				if (p.exitValue() == 1)
					logger.error("Command [{}] excute failed.", commandStr);
			}
			reader.close();
			in.close();
		} catch (Exception e) {
			throw new Exception("get os info failed,error info=" + e.getMessage());
		}
		return osType;
	}


	/**
	 * 判断操作系统是Linux or Windows
	 */
	private static boolean isLinux = true;

	private static void judgeSystemType() {
		String osName = System.getProperty("os.name");
		//if (osName.indexOf("Windows") >= 0) {
		if (osName.contains("Windows")) {
			// Windows
			isLinux = false;
		} else {
			// Linux
			isLinux = true;
		}
	}

	public static boolean isLinux() {
		judgeSystemType();
		return isLinux;
	}

	/**
	 * 获取Linux操作系统位数
	 */
	public static String getOSBit() throws Exception {
		String osBit = null;
		Runtime run = Runtime.getRuntime();
		try {
			String commandStr = "getconf LONG_BIT";
			Process p = run.exec(commandStr);
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String lineStr;
			while ((lineStr = reader.readLine()) != null) {
				osBit = lineStr;
			}
			if (p.waitFor() != 0) {
				if (p.exitValue() == 1)
					logger.error("Command [{}] excute failed.", commandStr);
			}
			reader.close();
			in.close();
		} catch (Exception e) {
			throw new Exception("get os info failed,error info=" + e.getMessage());
		}
		return osBit;
	}


}



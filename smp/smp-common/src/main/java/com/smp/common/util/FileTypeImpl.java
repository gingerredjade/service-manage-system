/*
 *
 */
package com.smp.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一些常用文件类型和MIME的方法封装
 * @author jianghongyu
 *
 */
public class FileTypeImpl {

	public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();
	public final static List<String> FILE_VIDEO_LIST = new ArrayList<String>();
	public final static List<String> FILE_AUDIO_LIST = new ArrayList<String>();
	public final static List<String> FILE_BIGFILE_LIST = new ArrayList<String>();

	static {

		// 音视频类型数据不通过REST方式获取

		/* image */
		FILE_TYPE_MAP.put("jpg", "image/jpeg"); // JPEG (jpg)
		FILE_TYPE_MAP.put("jpe", "image/jpeg");
		FILE_TYPE_MAP.put("jpeg", "image/jpeg");
		FILE_TYPE_MAP.put("png", "image/png"); //PNG (png)
		FILE_TYPE_MAP.put("gif", "image/gif"); //GIF (gif)
		FILE_TYPE_MAP.put("bmp", "image/bmp"); //Windows Bitmap (bmp)
		FILE_TYPE_MAP.put("tif", "image/tiff"); //TIFF (tif)
		FILE_TYPE_MAP.put("tiff", "image/tiff");
		FILE_TYPE_MAP.put("svg", "image/svg+xml");
		FILE_TYPE_MAP.put("ico", "image/x-ico");
		FILE_TYPE_MAP.put("cmx", "image/x-cmx");


		/* application and some files */
		FILE_TYPE_MAP.put("doc", "application/msword"); //MS Word
		FILE_TYPE_MAP.put("dot", "application/msword");
		FILE_TYPE_MAP.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		FILE_TYPE_MAP.put("xls", "application/vnd.ms-excel");
		FILE_TYPE_MAP.put("xlt", "application/vnd.ms-excel");
		FILE_TYPE_MAP.put("xlw", "application/vnd.ms-excel");
		FILE_TYPE_MAP.put("ppt", "application/vnd.ms-powerpoint");
		FILE_TYPE_MAP.put("pot", "application/vnd.ms-powerpoint");
		FILE_TYPE_MAP.put("pps", "application/vnd.ms-powerpoint");
		FILE_TYPE_MAP.put("rtf", "application/rtf");
		FILE_TYPE_MAP.put("pdf", "application/pdf"); //Adobe Acrobat (pdf)

		FILE_TYPE_MAP.put("tgz", "application/x-compressed");
		FILE_TYPE_MAP.put("gtar", "application/x-gtar");
		FILE_TYPE_MAP.put("gz", "application/x-gzip");
		FILE_TYPE_MAP.put("gtar", "application/x-gtar");
		FILE_TYPE_MAP.put("zip", "application/x-zip-compressed");
		FILE_TYPE_MAP.put("rar", "application/octet-stream");

		FILE_TYPE_MAP.put("exe", "application/octet-stream");
		FILE_TYPE_MAP.put("chm", "application/octet-stream");

		FILE_TYPE_MAP.put("js", "application/x-javascript");
		FILE_TYPE_MAP.put("htm", "text/html");
		FILE_TYPE_MAP.put("html", "text/html");
		FILE_TYPE_MAP.put("css", "text/css");
		FILE_TYPE_MAP.put("mht", "message/rfc822");
		FILE_TYPE_MAP.put("mhtml", "message/rfc822");
		FILE_TYPE_MAP.put("xhtml", "text/html");

		FILE_TYPE_MAP.put("c", "text/plain");
		FILE_TYPE_MAP.put("cpp", "text/plain");
		FILE_TYPE_MAP.put("h", "text/plain");
		FILE_TYPE_MAP.put("dll", "application/x-msdownload");
		FILE_TYPE_MAP.put("java", "application/octet-stream");
		FILE_TYPE_MAP.put("class", "application/x-java-applet");
		FILE_TYPE_MAP.put("jar", "application/java-archive");
		FILE_TYPE_MAP.put("txt", "text/plain");
		FILE_TYPE_MAP.put("wsdl", "text/xml");

		FILE_TYPE_MAP.put("map", "text/plain");
		FILE_TYPE_MAP.put("psd", "application/octet-stream");


		/* video */
		FILE_TYPE_MAP.put("mp4", "video/mp4");
		FILE_TYPE_MAP.put("swf", "application/x-shockwave-flash");
		FILE_TYPE_MAP.put("mpe", "video/mpeg");
		FILE_TYPE_MAP.put("mpeg", "video/mpeg");
		FILE_TYPE_MAP.put("mpg", "video/mpeg");
		FILE_TYPE_MAP.put("mov", "video/quicktime");
		FILE_TYPE_MAP.put("flv", "video/x-flv");
		FILE_TYPE_MAP.put("avi", "video/x-msvideo");
		FILE_TYPE_MAP.put("movie", "video/x-sgi-movie");
		FILE_TYPE_MAP.put("wmv", "video/x-ms-wmv");
		FILE_TYPE_MAP.put("rmvb", "application/vnd.rn-realmedia"); // WIN7、IIS7.5
		FILE_TYPE_MAP.put("rm", "application/vnd.rn-realmedia");


		/* audio */
		FILE_TYPE_MAP.put("au", "audio/basic");
		FILE_TYPE_MAP.put("mp3", "audio/mpeg");
		FILE_TYPE_MAP.put("wav", "audio/wav"); // Wave (wav)
		FILE_TYPE_MAP.put("wma", "audio/x-ms-wma");
		FILE_TYPE_MAP.put("ape", "audio/ape"); // 官方暂无...


		/* some particular type */
		FILE_TYPE_MAP.put("*", "application/octet-stream"); // 二进制流，未知文件类型
		FILE_TYPE_MAP.put("cll", "application/octet-stream"); // 未知类型,字节流形式返回供其下载使用

	}

	static {
		FILE_VIDEO_LIST.add("mp4");
		FILE_VIDEO_LIST.add("swf");
		FILE_VIDEO_LIST.add("mpe");
		FILE_VIDEO_LIST.add("mpeg");
		FILE_VIDEO_LIST.add("mpg");
		FILE_VIDEO_LIST.add("mov");
		FILE_VIDEO_LIST.add("flv");
		FILE_VIDEO_LIST.add("avi");
		FILE_VIDEO_LIST.add("movie");
		FILE_VIDEO_LIST.add("rmvb");
		FILE_VIDEO_LIST.add("rm");
	}

	static {
		FILE_AUDIO_LIST.add("au");
		FILE_AUDIO_LIST.add("mp3");
		FILE_AUDIO_LIST.add("wav");
		FILE_AUDIO_LIST.add("wma");
		FILE_AUDIO_LIST.add("ape");
	}

	static {
		FILE_BIGFILE_LIST.add("rar");
	}

	/**
	 * 获取二进制文件输出格式MIME
	 * 		若指定类型的二进制文件无对应的MIME，返回null
	 *
	 * @param filetype 文件类型即文件的后缀名
	 * @return 返回值
	 */
	public final static String getFileMimeType(String filetype){
		String mimetype = FILE_TYPE_MAP.get(filetype.toLowerCase());
		if (mimetype == null) {
			return null;
		}
		return mimetype;
	}

}

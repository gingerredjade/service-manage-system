package com.smp.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by MystFoo on 2019/11/1.
 */
public class UploadFileUtil {

	/**
	 * 上传文件（图片等）
	 *
	 * @param file
	 * @param filePath
	 * @param fileName
	 */
	public static void uploadFile(byte[] file, String filePath, String fileName) {
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(filePath + fileName);
			outputStream.write(file);
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建新的文件名
	 *
	 * @param fileName
	 * @return
	 */
	public static String createUUID(String fileName) {
		return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
	}

}

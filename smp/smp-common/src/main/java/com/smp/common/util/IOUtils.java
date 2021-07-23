package com.smp.common.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * IO相关工具类
 * @author Hongyu Jiang
 * @since Oct 29, 2018
 */
public class IOUtils {

	/**
	 * 关闭对象，连接
	 * @param closeable 待关闭对象
	 */
    public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }
}

package com.ukang.clinic.utils;

import java.io.File;

import android.os.Environment;

public class FileUtils {

	/*
	 * 新建一个以时间为名的jpg图片文件（在Exam_Pics文件下面）
	 */
	public static File NewFile() {
		String path = Environment.getExternalStorageDirectory()
				+ File.separator + "Exam_Pics" + File.separator + "temp";
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		File file = new File(path, System.currentTimeMillis() + ".jpg");
		return file;
	}
}

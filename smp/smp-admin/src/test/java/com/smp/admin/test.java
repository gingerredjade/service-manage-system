package com.smp.admin;

import org.springframework.boot.actuate.autoconfigure.cloudfoundry.Token;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MystFoo on 2019/11/8.
 */
public class test {
	public static void main(String[] args) {
		String dateDir = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/";
//		Token token=new Token();
		System.out.println(dateDir);
	}
}

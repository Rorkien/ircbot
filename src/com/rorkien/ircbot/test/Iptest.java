package com.rorkien.ircbot.test;

import com.rorkien.ircbot.utils.IPUtils;

public class Iptest {

	public static void main(String[] args) {
		long a = IPUtils.getLongFromIP("127.0.0.1");
		String b = IPUtils.getIPFromLong(a);
		System.out.println("R: " + a + " " + b);
	}
}

package com.rorkien.ircbot.utils;

/**
 * Compilation of methods and constants related to IRC String manipulation
 * and verification.
 * 
 * @author Rorkien
 */
public class IPUtils {

	/**
	 * Returns the long ip from a dotted ip. 
	 * 
	 * @param ip the IP in dotted form that should be converted to long form
	 * @return the long form of the IP
	 */
	public static long getLongFromIP(String ip) {
		String[] splitIp = ip.split("\\.");
		long longIp = 0;
		
		if (splitIp.length != 4) throw new IllegalArgumentException("Invalid IP format: " + ip);
		for (int i = 0; i < splitIp.length; i++) longIp += Short.valueOf(splitIp[3 - i]) * (1 << (8 * i));

		return longIp;
	}
	
	/**
	 * Returns the dotted ip from a long ip.
	 * 
	 * @param address the IP in long form that should be converted to dotted form
	 * @return the dotted form of the IP
	 */
	public static String getIPFromLong(long address) {
		String ip = "";
		
		long value = address;
		for (int i = 0; i < 4; i++) {
			ip = "." + (value % 256) + ip;
			value /= 256;
		}
		
		return ip.substring(1);
	}
	
}

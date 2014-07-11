package com.rorkien.ircbot.utils;

/**
 * Compilation of methods and constants related to IRC String manipulation
 * and verification.
 * 
 * @author Rorkien
 */
public class StringUtils {
	public static final char[] INVALID_CHANNEL_CHARACTERS = { 0x07, 0x20, 0x2C };
	public static final char CHANNEL_PREFIX = 0x23;
	public static final int CHANNEL_NAME_LIMIT = 50;

	/**
	 * <p>Returns the fixed channel name from a String.
	 * Channel names can't have <tt>^G</tt>, spaces (<tt> </tt>), or commas(<tt>,</tt>),
	 * or be longer than 50 characters.</p>
	 * 
	 * <p>Also adds a # as prefix, if there isn't any.</p>
	 * 
	 * @param channel the String that should be fixed
	 * @return the fixed channel name
	 */
	public static String getFixedChannelName(String channel) {
		for (char c : INVALID_CHANNEL_CHARACTERS) channel = channel.replace(String.valueOf(c), "");
		
		if (channel.charAt(0) != CHANNEL_PREFIX) channel = CHANNEL_PREFIX + channel;
		if (channel.length() > CHANNEL_NAME_LIMIT) channel = channel.substring(0, CHANNEL_NAME_LIMIT);
		
		return channel;
	}
	
	/**
	 * Returns whether the channel name is valid or not.
	 * 
	 * @param channel the String that should be checked. 
	 * @return <tt>true</tt> if the channel name is valid, else, returns <tt>false</tt>.
	 */
	public static boolean isValidChannelName(String channel) {
		String channelFixed = getFixedChannelName(channel);
		if (channel.equals(channelFixed)) return true;
		else return false;
	}

}

package com.rorkien.ircbot.irc;

import com.rorkien.ircbot.utils.IPUtils;

public class DirectChat {
	private String target;
	private long host;
	
	public DirectChat() {
		
	}
	
	public void setHost(long host) {
		this.host = host;
	}
	
	public void setHost(String host) {
		this.host = IPUtils.getLongFromIP(host);
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	
	public void request() {
		
	}

}

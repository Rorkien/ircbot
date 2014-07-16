package com.rorkien.ircbot.irc;

import java.io.IOException;
import java.net.Socket;

import com.rorkien.ircbot.utils.StringUtils;

public class IRCConnection extends ConnectionHandler {
	private String host;
	private int port;
	private boolean verbose;
	
	private String name, userName, hostName, serverName, realName;
	
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		if (userName == null) return name;
		else return userName;
	}
	
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	public String getHostName() {
		if (hostName == null) return name;
		else return hostName;
	}
	
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	public String getServerName() {
		if (serverName == null) return name;
		else return serverName;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getRealName() {
		if (realName == null) return name;
		else return realName;
	}
	
	public void connect() {
		connect(host, port);
	}
	
	public void connect(String host, int port) {
		try {
			if (getName() == null) throw new IllegalArgumentException("User must have a name.");
			else {				
				setState(State.CONNECTING);
				Socket socket = new Socket(host, port);			
				connection = new BasicConnection(this, socket);
				
				connection.getWriteBuffer().buffer("NICK " + getName());
				connection.getWriteBuffer().buffer("USER " + getUserName() + " " + getHostName() + " " + getServerName() + " :" + getRealName());
				connection.getWriteBuffer().sendBuffers();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void joinChannel(String channel) {
		connection.getWriteBuffer().sendBuffers("JOIN " + StringUtils.getFixedChannelName(channel));
	}
	
	public void fireRawTextEvent(String message) {
		if (verbose) System.out.println("READ: " + message);
		super.fireRawTextEvent(message);
		
		if (message.indexOf("004") >= 0) setState(State.CONNECTED);
		else if (message.startsWith("PING ")) getWriteBuffer().sendBuffers("PONG " + message.substring(5));
	}
}

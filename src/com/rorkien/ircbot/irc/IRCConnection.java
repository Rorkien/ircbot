package com.rorkien.ircbot.irc;

import java.io.IOException;
import java.net.Socket;

import com.rorkien.ircbot.utils.StringUtils;

public class IRCConnection extends ConnectionHandler {
	private String host;
	private int port;
	private boolean verbose;
	
	public IRCConnection(String host, int port) {
		connectionState = State.DISCONNECTED;
	}
	
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
    public synchronized void waitForState(State state) {
    	while (getState() != state);
    }
	
	public void connect() {
		connect(host, port);
	}
	
	public void connect(String host, int port) {
		try {
			connectionState = State.CONNECTING;
			Socket socket = new Socket(host, port);
			connection = new BasicConnection(this, socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void joinChannel(String channel) {
		connection.getWriteBuffer().sendBuffers("JOIN " + StringUtils.getFixedChannelName(channel));
	}
	
	public void read(String message) {
		if (verbose) System.out.println("READ: " + message);
		super.read(message);
		
		if (message.indexOf("004") >= 0) connectionState = State.CONNECTED;
		else if (message.startsWith("PING ")) getWriteBuffer().sendBuffers("PONG " + message.substring(5));
	}

	public void write(String message) {
		if (verbose) System.out.println("WROTE: " + message);		
		super.write(message);
	}
}

package com.rorkien.ircbot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rorkien.ircbot.irc.BasicConnection;
import com.rorkien.ircbot.irc.MessageListener;
import com.rorkien.ircbot.utils.QueueBuffer;
import com.rorkien.ircbot.utils.StringUtils;

public class Client {
	private String name;
	private BasicConnection connection;
	private boolean verbose;
    private List<MessageListener> listeners = new ArrayList<MessageListener>();
	
	public Client() {

	}
	
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public BasicConnection getConnection() {
		return connection;
	}
	
	public QueueBuffer<String> getBuffer() {
		return connection.getWriteBuffer();
	}
	
	public void connect(String host) {
		connect(host, 6667);
	}
	
	public void connect(String host, int port) {
		try {
			connection = new BasicConnection(this, host, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		connection.getWriteBuffer().buffer("NICK " + getName());
		connection.getWriteBuffer().buffer("USER " + getName() + " 8 * : test");
		connection.getWriteBuffer().sendBuffers();
	}
	
	public void joinChannel(String channel) {
		connection.getWriteBuffer().sendBuffers("JOIN " + StringUtils.getFixedChannelName(channel));
	}
	
	public void addMessageListener(MessageListener listener) {
		listeners.add(listener);
	}
	
	public void read(String message) {
		if (verbose) System.out.println("READ: " + message);
		for (MessageListener listener : listeners) listener.onMessage(message);
	}
	
	public void write(String message) {
		if (verbose) System.out.println("WROTE: " + message);
	}
}

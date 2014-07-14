package com.rorkien.ircbot.irc;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import com.rorkien.ircbot.utils.QueueBuffer;

public abstract class ConnectionHandler {
	protected BasicConnection connection;
	protected State connectionState;
    protected List<MessageListener> listeners = new ArrayList<MessageListener>();
    
    public static enum State {
    	DISCONNECTED,
    	CONNECTING,
    	CONNECTED
    }
    
	public synchronized State getState() {
		return connectionState;
	}

	public synchronized QueueBuffer<String> getWriteBuffer() {
		return connection.getWriteBuffer();
	}
	
	public synchronized BufferedReader getReadBuffer() {
		return connection.getReadBuffer();
	}
	
	public String getConnectedAddress() {
		return connection.getConnection().getLocalAddress().getHostAddress();
	}
	
	public void addMessageListener(MessageListener listener) {
		listeners.add(listener);
	}
	
	public void read(String message) {
		for (MessageListener listener : listeners) listener.onMessage(message);
	}
	public void write(String message) {
		//for (MessageListener listener : listeners) listener.onInput(message);	
	}

}

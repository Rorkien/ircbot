package com.rorkien.ircbot.irc;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import com.rorkien.ircbot.utils.QueueBuffer;

public abstract class ConnectionHandler {
	protected BasicConnection connection;
	private State connectionState = State.DISCONNECTED;
	protected List<EventListener> listeners = new ArrayList<EventListener>();

	public static enum State {
		DISCONNECTED,
		CONNECTING,
		CONNECTED
	}

	public synchronized State getState() {
		return connectionState;
	}
	
	public synchronized void setState(State state) {
		connectionState = state;
		if (state == State.CONNECTED) fireConnectEvent();
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

	public void addEventListener(EventListener listener) {
		listeners.add(listener);
	}

	public void fireChatEvent(String message) {
		for (EventListener listener : listeners) listener.onChat(message);
	}
	
	public void fireRawTextEvent(String message) {
		for (EventListener listener : listeners) {
			if (message.indexOf(" PRIVMSG ") >= 0) listener.onText(message);
			listener.onRawText(message);
		}
	}
	
	public void fireConnectEvent() {
		for (EventListener listener : listeners) listener.onConnect();
	}
}

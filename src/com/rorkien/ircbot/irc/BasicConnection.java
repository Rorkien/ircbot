package com.rorkien.ircbot.irc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import com.rorkien.ircbot.Client;
import com.rorkien.ircbot.utils.QueueBuffer;

public class BasicConnection implements Runnable {
	private Client client;
	private Socket connection;
	private State connectionState = State.DISCONNECTED;
	private boolean connectionEnded;
	private QueueBuffer<String> writeBuffer;
    private BufferedReader readBuffer;
    
    public static enum State {
    	DISCONNECTED,
    	CONNECTING,
    	CONNECTED,
    	AUTHENTICATED
    }
    
    public synchronized State getState() {
    	return connectionState;
    }
    
	public BufferedReader getReadBuffer() {
		return readBuffer;
	}
	
	public QueueBuffer<String> getWriteBuffer() {
		return writeBuffer;
	}
    
    public synchronized void waitForState(State state) {
    	while (getState() != state);
    }
	
	public BasicConnection(Client client, String host, int port) throws UnknownHostException, IOException {
		this.client = client;
		connection = new Socket(host, port);
		connectionState = State.CONNECTING;
		
		writeBuffer = new QueueBuffer<String>(client, connection.getOutputStream());
		readBuffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		new Thread(this).start();
	}

	public void run() {
		while (!connectionEnded) {
			try {
				String in = readBuffer.readLine();
				client.read(in);
				
				if (in.indexOf("004") >= 0) connectionState = State.AUTHENTICATED;
				else if (in.startsWith("PING ")) getWriteBuffer().sendBuffers("PONG " + in.substring(5));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Thread.yield();
	}
}

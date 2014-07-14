package com.rorkien.ircbot.irc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.rorkien.ircbot.irc.ConnectionHandler.State;
import com.rorkien.ircbot.utils.QueueBuffer;

public class BasicConnection implements Runnable {
	private ConnectionHandler handler;
	private ServerSocket listener;
	private Socket connection;
	private QueueBuffer<String> writeBuffer;
    private BufferedReader readBuffer;
    private boolean isListener;
    
    public Socket getConnection() {
    	return connection;
    }
    
	public BufferedReader getReadBuffer() {
		return readBuffer;
	}
	
	public QueueBuffer<String> getWriteBuffer() {
		return writeBuffer;
	}
	
	public BasicConnection(ConnectionHandler handler, Socket connection) throws IOException {
		this.handler = handler;
		this.connection = connection;
		
		writeBuffer = new QueueBuffer<String>(handler, connection.getOutputStream());
		readBuffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		new Thread(this).start();
	}
	
	public BasicConnection(ConnectionHandler handler, ServerSocket listener) {
		this.handler = handler;
		this.listener = listener;
		isListener = true;
		
		new Thread(this).start();
	}

	public void run() {
		try {
			if (isListener) {
				connection = listener.accept();
				writeBuffer = new QueueBuffer<String>(handler, connection.getOutputStream());
				readBuffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				
				handler.connectionState = State.CONNECTED;
			}

			while (true) {
				String in = readBuffer.readLine();
				handler.read(in);

				Thread.yield();
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

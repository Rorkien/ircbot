
/*package com.rorkien.ircbot.irc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.rorkien.ircbot.socket.Protocol;
import com.rorkien.ircbot.utils.IPUtils;
import com.rorkien.ircbot.utils.QueueBuffer;

public class DCCServer implements Runnable {
	private String target;
	private long host;
	private int port;
	
	private Client client;	
	private Socket dccConnection;
	private ServerSocket dccServer;
	
	private boolean verbose;
    private List<MessageListener> listeners = new ArrayList<MessageListener>();
	
	private boolean connectionEnded;
	private QueueBuffer<String> writeBuffer;
    private BufferedReader readBuffer;
	
	public DCCServer(Client client, int port) {
		this.client = client;
		this.host = IPUtils.getLongFromIP(client.getConnection().getConnectedAddress());
		this.port = port;
	}
	
	public String getTarget() {
		return target;
	}
	
	public QueueBuffer<String> getWriteBuffer() {
		while (dccConnection == null);
		return writeBuffer;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
		
	public void request() {
		try {
			dccServer = new ServerSocket(port);
			client.getConnection().getWriteBuffer().sendBuffers("PRIVMSG " + target + " :" + Protocol.CTCP_DELIMITER + "DCC CHAT chat " + host + " " + port + Protocol.CTCP_DELIMITER);
			new Thread(this).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			dccConnection = dccServer.accept();
			
			//writeBuffer = new QueueBuffer<String>(client, dccConnection.getOutputStream());
			readBuffer = new BufferedReader(new InputStreamReader(dccConnection.getInputStream()));
			
			while (!connectionEnded) {
				String in = readBuffer.readLine();
				read("(DCC@" + dccConnection.getLocalPort() + ")" + in);
				
				Thread.yield();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void waitForConnection() {

	}
	
	public void read(String message) {
		if (verbose) System.out.println("READ (DCC@" + dccConnection.getLocalPort() + "): " + message);
		for (MessageListener listener : listeners) listener.onMessage(message);
	}
	
	public void write(String message) {
		if (verbose) System.out.println("WROTE (DCC@" + dccConnection.getLocalPort() + "): " + message);
	}
}*/

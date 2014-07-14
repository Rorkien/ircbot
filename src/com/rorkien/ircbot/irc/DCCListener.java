package com.rorkien.ircbot.irc;

import java.io.IOException;
import java.net.ServerSocket;

import com.rorkien.ircbot.socket.Protocol;
import com.rorkien.ircbot.utils.IPUtils;

public class DCCListener extends ConnectionHandler {
	ServerSocket dccListener;
	private String targetName;
	private int port;
	private boolean verbose;
	
	public DCCListener() {
		connectionState = State.DISCONNECTED;
	}
	
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
    public synchronized void waitForState(State state) {
    	while (getState() != state);
    }
	
	public synchronized void request(ConnectionHandler baseConnection, String targetName, int port) {
		try {
			dccListener = new ServerSocket(port);
			baseConnection.getWriteBuffer().sendBuffers("PRIVMSG " + targetName + " :" + Protocol.CTCP_DELIMITER + "DCC CHAT chat " + IPUtils.getLongFromIP(baseConnection.getConnectedAddress()) + " " + port + Protocol.CTCP_DELIMITER);
			connectionState = State.CONNECTING;
			connection = new BasicConnection(this, dccListener);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void read(String message) {
		if (verbose) System.out.println("READ: " + message);
		super.read(message);
	}

	public void write(String message) {
		if (verbose) System.out.println("WROTE: " + message);		
		super.write(message);
	}	
}

package com.rorkien.ircbot.irc;

import java.io.IOException;
import java.net.ServerSocket;

import com.rorkien.ircbot.socket.Protocol;
import com.rorkien.ircbot.utils.IPUtils;

public class DCCConnection extends ConnectionHandler {
	private ServerSocket dccListener;
	private String targetName;
	private int port;
	private boolean verbose;
	
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
    public synchronized void waitForState(State state) {
    	while (getState() != state);
    }
	
	public void request(ConnectionHandler baseConnection, String targetName, int port) {
		this.targetName = targetName;
		this.port = port;
		
		System.out.println(targetName + " / " + this.targetName);
		
		try {
			dccListener = new ServerSocket(port);
			baseConnection.getWriteBuffer().sendBuffers("PRIVMSG " + targetName + " :" + Protocol.CTCP_DELIMITER + "DCC CHAT chat " + IPUtils.getLongFromIP(baseConnection.getConnectedAddress()) + " " + port + Protocol.CTCP_DELIMITER);
			setState(State.CONNECTING);
			connection = new BasicConnection(this, dccListener);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fireReadEvent(String message) {
		if (verbose) System.out.println("READ (" + targetName + "): " + message);
		super.fireChatEvent(message);
	}	
}

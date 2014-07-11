package com.rorkien.ircbot.test;

import java.util.Scanner;

import com.rorkien.ircbot.Client;
import com.rorkien.ircbot.irc.BasicConnection;

public class Application {
	
	public Application() {
		Scanner scanner = new Scanner(System.in);
		
		try {
			Client c = new Client();
			c.setName("Rogor99");
			c.connect("irc.rizon.net");
			c.getConnection().waitForState(BasicConnection.State.AUTHENTICATED);
			c.joinChannel("teste89");
			
			while (true) {
				String input = scanner.nextLine();
				c.getConnection().getWriteBuffer().sendBuffers("PRIVMSG #teste89 :" + input);				
			}


	        /*String line = null;
	        while ((line = c.read()) != null) {
	            if (line.indexOf("004") >= 0) {
	                break;
	            }
	            else if (line.indexOf("433") >= 0) {
	            	c = new Client();
	    			c.setName("Rogor7987");
	    			c.connect("irc.rizon.net");
	            }
	        }
			
			*/
	        
	        /*
			ServerSocket d = new ServerSocket(97);
			String a = d.getInetAddress().getHostAddress();
	        c.getBuffer().buffer("PRIVMSG Rogor :" + Protocol.CTCP_DELIMITER + "DCC CHAT chat " + IPUtils.getLongFromIP("127.0.0.1") + " 97" + Protocol.CTCP_DELIMITER + "\r\n");
			c.getBuffer().sendBuffers();
			Socket f = d.accept();
			
			PrintWriter p = new PrintWriter(f.getOutputStream(), true);
			p.println("alo\r\n");
			
			*/
		} finally {
			scanner.close();
		}
	}
	
	public static void main(String[] args) {
		new Application();
	}

}

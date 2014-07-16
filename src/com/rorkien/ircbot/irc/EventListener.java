package com.rorkien.ircbot.irc;

public interface EventListener {	
	public void onRawText(String message);
	public void onText(String message);
	public void onChat(String message);
	public void onConnect();
}

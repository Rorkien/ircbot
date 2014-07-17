IRCBot
======

Lightweight Java implementation of the IRC protocol.

This project aims to provide a low-level abstraction of the IRC protocol. Although this library abstracts a few features, such as event handling and DCC Chat (Server side only), a fully functional IRC client will need to extend and parse all the raw data transferred.

Getting Started
======

To connect to a IRC network, the code below is all you need:

```java
IRCConnection connection = new IRCConnection();

//Prints all the input/output on the console
connection.setVerbose(true);

//Sets the nickname
connection.setName("Nickname");

//Connects to a server on the port 6667
connection.connect("Server", 6667);
```

Input and Output
======

Once you are successfully connected, you can access the input and output buffers. IRCBot will handle the input buffers automatically, forwarding every message to their respective event listeners.

To access the write buffer, you can use the getter in your connection object.

```java
connection.getWriteBuffer().buffer("This is a message!");
connection.getWriteBuffer().buffer("This is also a message!");
connection.getWriteBuffer().sendBuffers();
```

The *buffer()* method will buffer a message and will only send it when you call the *sendBuffers()* method. You can also directly send the message without buffering, by using the *sendBuffers(message)* method.

Event Listeners
======

IRCBot works with events, like the usual mIRC scripting language. Everytime the input buffer receives a message, it is forwarded to any event listeners you've set up, filter it, and then call the respective method:

```java
//onConnect is called once you are successfully connected and acknowledged
public void onConnect() {
	//Joins a channel
	connection.joinChannel("#somechannel");
}
	
//onText is called when a message directed towards you, via private messaging, ctcp or in a channel you are on.
public void onText(String message) {
	//Be wary that *message* contains the raw data
	connection.getWriteBuffer().sendBuffers("Someone said " + message);
}
```

To use those events, you just need to have a class implement the interface *EventListener* and add it to the connection:

```java
public class Listener implements EventListener {
	IRCConnection connection = new IRCConnection();
	
	public Listener() {
		//Adds an event listener to the connection
		connection.addEventListener(this);
	}

	...
}
```

DCC Chat
======

DCC Chat is a direct connection between **two** points. All the data is transferred directly between them, and as such, ignores all the IRC connection rules (like flood control).

To request a DCC connection, both points **must be connected to the same IRC network**, since the connection request is done via private messaging.

```java
DCCConnection dcc = new DCCConnection();

//Prints all the input/output on the console
dcc.setVerbose(true);

//Requests a connection with "Someone" on the 44080 port, using someConnection as the request point
dcc.request(someConnection, "Someone", 44080);
```

Once the connection is made, you'll have access to the buffers, and can use the event listeners in the same way that you would do in a IRC connection.

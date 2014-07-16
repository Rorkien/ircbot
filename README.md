IRCBot
======

Lightweight Java implementation of the IRC protocol.

This project aims to provide a low-level abstraction of the IRC protocol. While the basic operations are abstracted, all the text and input needs to be parsed in raw form, as specified in the IRC RFC.

As basic as it can go, it supports:
-Event handling (like the usual mIRC events)
-DCC Chat (direct connection chatting)

Getting Started
======

To connect to a IRC network, the code below is all you need:

```java
IRCConnection connection = new IRCConnection(); //Creates a new IRC connection object
connection.setVerbose(true); //If true, all input/output will be displayed on the console
connection.setName("Nickname"); //The desired nickname
connection.addEventListener(this); //Adds an event listener to the connection (see below)
connection.connect("Server", 6667); //Finally connects to the server
```

Input and Output
======

Once you are successfully connected, you have access to the input and output buffers. IRCBot will handle the input buffers automatically, sending each message to their respective events and event listeners. Output, however, must be managed.

To access the write buffer, you can use the getter in your connection object.

```java
connection.getWriteBuffer().buffer("This is a message!");
connection.getWriteBuffer().buffer("This is also a message!");
connection.getWriteBuffer().sendBuffers();
```

The *buffer()* method will store a message and will only send it when you decide to, using the *sendBuffers()* method. You can also directly send the message without buffering, by using the *sendBuffers(message)* method.

Event Listeners
======

Okay, you can connect to networks, send and read messages, but how can i join a channel? And how can i make it react to other people messages?

IRCBot works with events. Everytime a raw message is send, the assigned event listener will filter it, and call the respective method. In short:

```java
//onConnect is called once you are successfully connected
public void onConnect() {
connection.joinChannel("SomeChannel"); //Joins a channel
}
	
//onText is called when a message is send to you, or a channel you are on
public void onText(String message) {
connection.getWriteBuffer().sendBuffers("Someone said " + message); //Be wary that message contains the message in raw form.	
}
```

To use those events, just implement the interface *EventListener* and point it to the connection (like you'd do with an *KeyListener*)

DCC Chat
======

TODO


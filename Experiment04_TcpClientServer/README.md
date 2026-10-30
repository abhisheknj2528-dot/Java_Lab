# Experiment 04 — TCP Client–Server Application

## Question / Problem Statement

Write a Java program to implement a TCP client–server application in which the client sends messages to the server and the server replies.

## Aim

To establish two-way communication between a client and a server using TCP sockets.

## Concepts Used

- `ServerSocket` and `Socket`
- `accept()`, `getInputStream()`, `getOutputStream()`
- `BufferedReader` / `PrintWriter` for line-based messages
- try-with-resources, `IOException` handling
- Default port `5000`, host `localhost`

## Files

| File | Description |
|------|-------------|
| `Experiment04_TcpClientServer.java` | Complete source code |
| `README.md` | This file |
| `output/` | Program output / screenshots |

## How to Compile and Run

Open **two terminals** in this folder. Start the server first.

```bash
javac Experiment04_TcpClientServer.java

# Terminal 1
java Experiment04_TcpClientServer server

# Terminal 2
java Experiment04_TcpClientServer client
```

(Running with no argument shows a menu: `1` = server, `2` = client.)

## Program Logic

**Server:** opens `ServerSocket(5000)`, waits for a client, sends a welcome line, then for every client message
prints it and sends a reply typed by the operator (an empty reply sends `ACK: <MESSAGE>`). `bye` / `exit` ends the session.

**Client:** connects to `localhost:5000`, prints the welcome message, then repeatedly sends a typed line and prints the server's response.

## Output (captured from an actual run)

**Terminal 1 — Server**

```text

[SERVER] Starting TCP Server on port 5000...
[SERVER] Server is listening. Awaiting client connection...
[SERVER] Client connected: /127.0.0.1:45162
[CLIENT]: Hello Server
[SERVER ENTER REPLY]: Reply from server: Hello Client!
[CLIENT]: How are you?
[SERVER ENTER REPLY]: 
[CLIENT]: bye
[SERVER] Client disconnected.
[SERVER] Session closed.
```

**Terminal 2 — Client**

```text

[CLIENT] Connecting to TCP Server at localhost:5000...
[CLIENT] Successfully connected to Server!
[SERVER]: Connected to TCP Server! Type messages, or 'bye' to disconnect.
[CLIENT ENTER MESSAGE]: Hello Server
[SERVER]: Reply from server: Hello Client!
[CLIENT ENTER MESSAGE]: How are you?
[SERVER]: ACK: HOW ARE YOU?
[CLIENT ENTER MESSAGE]: bye
[CLIENT] Disconnecting...
[CLIENT] Connection closed cleanly.
```

## Result

Messages were exchanged between client and server over TCP and the session closed cleanly.

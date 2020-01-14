package controllers;

import models.Connection;
import models.MatchInformation;
import models.Message;
import models.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {
    public static final int CONNECTIONS_COUNT_LIMIT = 4;
    private static final String[] COLORS = {"R", "B", "Y", "G"};
    private Server server = new Server();
    private boolean listening = true;

    public ServerController() { }

    public void startListening() {
        try {
            ServerSocket serverSocket = new ServerSocket(Server.PORT);
            System.out.println("server started");
            while (true) {
                // waiting for a client to connect
                Socket socket = serverSocket.accept();
                if (this.listening == false) {
                    socket.close();
                    continue;
                }
                if (this.server.countConnections() < CONNECTIONS_COUNT_LIMIT) {
                    // only allow maximum 4 connections at a time
                    // bingo! we got a connection
                    Connection newConnection = new Connection(socket, this);
                    // connectionPool is used in order to control
                    // the clients independently in the future
                    this.server.addConnection(newConnection);
                    new Thread(newConnection).start();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("server stopped");
    }

    public void stopListening() {
        this.listening = false;
    }

    public void resumeListening() {
        this.listening = true;
    }

    public void removeConnection(Connection connection) {
        this.server.deleteConnection(connection);
    }

    // broadcast to all the clients in connectionPool except the sender
    public void broadcast(Object object, Connection sender) {
        for (Connection client:this.server.getConnectionPool()) {
            if (client != sender) {
                client.send(object);
            }
        }
    }

    public String getAColor() {
        return COLORS[this.server.countConnections() - 1];
    }

    public boolean isGameReady() {
        // if there is one player, game isn't ready
        if (this.server.countConnections() <= 1) return false;
        // check if every player is ready
        for (Connection player:this.server.getConnectionPool()) {
            if (!player.isReady()) return false;
        }
        return true;
    }

    public void startGame() {
        MatchInformation match = constructNewMatchInformation();
        Message startGameMessage = new Message("startGame", match);
        System.out.printf("Game started with %d players...\n", this.server.countConnections());
        stopListening();
        // broadcast to everyone
        broadcast(startGameMessage, null);
    }

    private MatchInformation constructNewMatchInformation() {
        MatchInformation match = new MatchInformation();
        for (Connection connection:this.server.getConnectionPool()) {
            match.addPlayer(connection.getPlayer());
        }
        return match;
    }

    public void prepareForNewGame() {
        System.out.println("Game over, preparing for new game...");
        changeEveryConnectionToNotReady();
        resumeListening();
    }

    private void changeEveryConnectionToNotReady() {
        for (Connection connection:this.server.getConnectionPool()) {
            connection.setReady(false);
        }
    }

    public void handlePlayerLeaving(Connection connection) {
        try {
            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        removeConnection(connection);
    }
}

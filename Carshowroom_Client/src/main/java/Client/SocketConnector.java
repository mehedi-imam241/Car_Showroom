package Client;

import Utils.ShowAlert;

import java.net.Socket;
import java.io.*;


public class SocketConnector {

    private Socket socket;

    public SocketConnector() {
        try {
            this.socket = new Socket("localhost", 7777);
        } catch (IOException e) {
            System.out.println("Server is disconnected");
            System.exit(0);
        }
    }

    public void close() throws IOException {
        socket.close();
    }

    public boolean isReachable() throws IOException {
        if(socket.getInetAddress().isReachable(500))
        {
            return true;
        }
        return false;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}

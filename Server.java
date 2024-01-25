import java.io.*;
import java.net.*;

class Server {
    public static void main(String[] args) throws IOException {
        //here we have created Socket for our server
        ServerSocket s = new ServerSocket(9876);
        //for server to listen continously we make while(true)
        while (true) {
            //we give the this socket to Echoer so that multiple clients can connect to this socket
            new Echoer(s.accept());
        }
    }
}
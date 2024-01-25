import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Echoer extends Thread {
    private Socket clientSocket;

    public Echoer(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.start();
    }

    // Keep In Mind
    // The Server OutputStream Will Become Client InputStream
    // The Server InputStream will Become Client OutputStream

    public void run() {
        try {
            BufferedReader clientIn = new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));

            // In Java, a PrintStream buffers data before sending it to its destination,
            // like a file or network. Enabling auto-flush (true in constructor) makes it
            // automatically flush the buffer, ensuring data is promptly sent, especially
            // after writing a newline
            PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
            String line;

            // until i got exit from the client i need to print
            while (!"exit".equalsIgnoreCase(line = clientIn.readLine())) {
                System.out.println("Server Got " + line);
                clientOut.println("You Sent me " + line);

            }
            System.out.println("Client Closed");

            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
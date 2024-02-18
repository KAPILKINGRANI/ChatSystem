
/**
 *      The Server OutputStream Will Become Client InputStream
        The Server InputStream will Become Client OutputStream
        (vice versa)

 *      In Java, a PrintStream buffers data before sending it to its destination,
        like a file or network. Enabling auto-flush (true in constructor) makes it
        automatically flush the buffer, ensuring data is promptly sent, especially
        after writing a newline....

 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Echoer extends Thread {
    private Socket clientSocket;
    private static Map<String, Socket> db = new HashMap<>();// db-Static so that values common for all clients
    BufferedReader clientIn;
    PrintWriter clientOut;
    String line;

    public Echoer(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.start();
    }

    public void registerUser(String username, Socket clientSocket) {
        db.put(username, clientSocket);
    }

    public boolean findUser(String username) {
        if (db.containsKey(username)) {
            clientOut.println(username + " is online ");
            return true;
        } else {
            clientOut.println(username + " is offline or Might Be He is not on our Chat Application System");
            return false;
        }
    }

    public void run() {
        try {

            clientIn = new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));
            clientOut = new PrintWriter(clientSocket.getOutputStream(), true);

            String username = clientIn.readLine();
            clientOut.println("Welcome To Chat Application " + username);

            registerUser(username, clientSocket); // adding this to our hashmap

            while (true) {
                String receiverUsername = clientIn.readLine();
                clientOut.println("Hold On Establishing Connection Between " + username +
                        "and" + receiverUsername);
                // boolean result = findUser(receiverUsername); // finding the user in our db
                if (findUser(receiverUsername)) {
                    Socket receiverSocket = db.get(receiverUsername);
                    PrintWriter receiverOut = new PrintWriter(receiverSocket.getOutputStream(), true);

                    while (true) {
                        String line = clientIn.readLine();
                        if (line.equalsIgnoreCase("exit")) {
                            clientOut.println("exit");// this is to break the response thread in client
                            break;
                        } else {
                            receiverOut.println(username + ":-" + line);
                        }
                    }
                }

                String operationResponse = clientIn.readLine();
                if (operationResponse.equalsIgnoreCase("n")) {
                    break;
                }
            }
            System.out.println("Client Closed");
            clientSocket.close();

        } catch (Exception e) {
            // e.printStackTrace();
        }

    }
}

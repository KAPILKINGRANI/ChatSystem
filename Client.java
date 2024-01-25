import java.io.*;
import java.net.*;

class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 9876);
        BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;

        // until i write exit take my input
        while (!"exit".equalsIgnoreCase(line = br.readLine())) {
            serverOut.println(line);
            String response = serverIn.readLine();
            System.out.println("Client Got this response " + response);

        }
        serverOut.println("exit");
        socket.close();

    }
}
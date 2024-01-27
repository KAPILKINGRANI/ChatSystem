import java.io.*;
import java.net.*;

class HandleResponse extends Thread {
    BufferedReader serverIn;
    String line;

    HandleResponse(BufferedReader serverIn, String line) {
        this.serverIn = serverIn;
        this.line = line;
        this.start();
    }

    public void run() {
        try {
            while (!"exit".equalsIgnoreCase(line = serverIn.readLine())) {
                System.out.println();
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 9876);
        BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter UserName:");
        serverOut.println(br.readLine());
        String serverResponse = serverIn.readLine();// greetings from server
        System.out.println(serverResponse);

        System.out.println("wants to chat with ?");
        serverOut.println(br.readLine());
        serverResponse = serverIn.readLine();
        System.out.println(serverResponse);

        serverResponse = serverIn.readLine();
        System.out.println(serverResponse); // user is available or not that response will be printed...

        System.out.println("Write messages and if u want to quit type 'exit' ");
        String line = "";
        new HandleResponse(serverIn, line);// Response Thread

        // sending Thread
        while (!"exit".equalsIgnoreCase(line = br.readLine())) {
            serverOut.println(line);
        }
        serverOut.println("exit");
        socket.close();

    }
}
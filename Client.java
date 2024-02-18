import java.io.*;
import java.net.*;

class HandleResponse extends Thread {
    BufferedReader serverIn;

    HandleResponse(BufferedReader serverIn) {
        this.serverIn = serverIn;
        this.start();
    }

    public void run() {
        String line;
        try {
            while (!"exit".equalsIgnoreCase(line = serverIn.readLine())) {
                System.out.println();
                System.out.println(line);
            }
        } catch (Exception e) {
            // e.printStackTrace();
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

        while (true) {
            System.out.println("wants to chat with ?");
            serverOut.println(br.readLine());
            serverResponse = serverIn.readLine();
            System.out.println(serverResponse);// establishing connection response will be printed

            serverResponse = serverIn.readLine();
            System.out.println(serverResponse); // user is available or not that response will be printed...

            new HandleResponse(serverIn);// Response Thread

            while (true) {
                // Sending Thread
                System.out.println("Write messages and if u want to quit type 'exit' ");
                String line = br.readLine();
                if (line.equalsIgnoreCase("exit")) {
                    break;
                } else {
                    serverOut.println(line);
                }
            }
            serverOut.println("exit");
            System.out.println("Wants to chat with someone else?(y/n)");
            String operation = br.readLine();
            serverOut.println(operation);
            if (operation.equalsIgnoreCase("n")) {
                break;
            }
        }
        socket.close();

    }
}

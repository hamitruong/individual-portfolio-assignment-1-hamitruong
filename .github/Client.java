package TCPclient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


class Reader implements Runnable {
    String botName;
    Socket server;
    BufferedReader in;
    PrintWriter Out;


    public Reader(Socket Server, String name) throws IOException {
        server = Server;
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));                    // Connnects the client and server
        Out = new PrintWriter(server.getOutputStream(), true);
        this.botName = name;

    }

    @Override
    public void run() {

        try {
            do {
                String fromServer = in.readLine();

                if (fromServer != null) {

                    if (!fromServer.contains("/disconnect " + botName)) {                           // If the bot get disconnected
                        if (fromServer.startsWith("Host: ") && !fromServer.contains("/disconnect")) {

                            System.out.println(fromServer);

                            generate(fromServer);

                        } else {
                            System.out.println(fromServer);
                            if (!fromServer.contains("/disconnect")) {                              // If it's getting disconnected, then the bot will respond with "Ah man...."
                                continue;
                            }

                            Out.println(botName + ": " + "Ah man...");

                        }
                    }
                    else {
                        break;
                    }
                }
            }
            while (true);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generate(String fromServer) {
        Bot bot = new Bot(fromServer, botName);
        var botAnswer = bot.answerFrBot();

        System.out.println("Me: " + botAnswer);

        Out.println(botName + ": " + botAnswer);
    }
}


public class Client {

    public Client(String host, int port, String navn) throws IOException {

        Socket socket = new Socket(host, port);
        System.out.println(navn + " has been connected.... waiting for dialog");                // When the server and the bot are connnected, the bot will wait for a dialog

        Reader con = new Reader(socket, navn);
        new Thread(con).start();
    }


    public static void main(String[] args) throws IOException {                                 // It will get the options of bots, and line it up

        List list = new List();

        if (list.getAvailableBots().isEmpty()) {
            list.addBots();
        }

        ArrayList<String> availableBots = list.getAvailableBots();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));   // Next step is to choose a bot from the option list

        System.out.println("Choose your bot");
        System.out.println(availableBots);

        String start = bufferedReader.readLine();

        for (String bot : availableBots) {
            if (bot.equals(start)) {
                Client localhost = new Client("localhost", 2410, start);                // Connecting the server to the right ip
                list.moveBotToUsedList(bot);
                break;
            }
        }

    }
}
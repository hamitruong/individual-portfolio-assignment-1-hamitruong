package TCPserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.System.*;

public class server {
    public static CopyOnWriteArrayList<client> clientList = new CopyOnWriteArrayList<>();

    static final ThreadLocal<ExecutorService> pool = ThreadLocal.withInitial(() ->
            Executors.newFixedThreadPool(4));

    public server(int port) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            var input = new input(clientList);
            new Thread(input).start();

            out.println("WELCOME TO THE SERVER");

            do {

                if (clientList.isEmpty()) {
                    out.println("WAITING FOR A BOT TO JOIN");
                }

                Socket s = serverSocket.accept();

                out.println("a bot joined the room from received ping " + s.getRemoteSocketAddress());


                client serverClient = new client(s, clientList);

                clientList.add(serverClient);

                pool.get().execute(serverClient);

            } while (true);
        }
    }


    static class input implements Runnable {

        final ThreadLocal<BufferedReader> inRead = ThreadLocal.withInitial(() ->
                new BufferedReader(new InputStreamReader(in)));

        CopyOnWriteArrayList<client> arrayList;

        public input(CopyOnWriteArrayList<client> arrayList) {
            this.arrayList = arrayList;
        }

        @Override
        public void run() {

            do {
                String read2Client = null;
                try {
                    read2Client = inRead.get().readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Objects.equals(read2Client, "exit")) {
                    exit(0);
                }

                if (!Objects.requireNonNull(read2Client).isBlank() && !clientList.isEmpty()) {
                    if (!read2Client.startsWith("kick")) {
                        out.println("Message sent: " + read2Client);
                    }

                    CopyOnWriteArrayList<client> arrayListClients = client.getArrayListClients();
                    for (int i = 0; i < arrayListClients.size(); i++) {
                        client client = arrayListClients.get(i);
                        client.out.println(new StringBuilder().append("Host: ").append(read2Client.toLowerCase()).toString());
                    }
                }
            } while (true);
        }
    }


    static class client implements Runnable {
        private final Socket client;
        private final AtomicReference<BufferedReader> clientReader = new AtomicReference<>();
        private final PrintWriter out;

        private static CopyOnWriteArrayList<client> arrayClients;

        public client(Socket Socket, CopyOnWriteArrayList<client> ListClients) throws IOException {

            this.client = Socket;
            server.client.setArrayListClients(ListClients);

            clientReader.set(new BufferedReader(new InputStreamReader(client.getInputStream())));
            out = new PrintWriter(client.getOutputStream(), true);
        }

        public static CopyOnWriteArrayList<client> getArrayListClients() {
            return arrayClients;
        }

        public static void setArrayListClients(CopyOnWriteArrayList<client> arrayListClients) {
            server.client.arrayClients = arrayListClients;
        }

        @Override
        public void run() {
            try {
                do {

                    try {
                        if (!client.isClosed()) {


                            String fromClient = clientReader.get().readLine();

                            if (fromClient == null) {
                                break;
                            } else {


                                System.out.println(fromClient);

                                send(fromClient);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        private void send(String message) {
            CopyOnWriteArrayList<server.client> arrayListClients = getArrayListClients();
            for (server.client client : arrayListClients) {
                if (!(client.client == this.client)) {
                    client.out.println(message);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        server server = new server(2410);
    }
}
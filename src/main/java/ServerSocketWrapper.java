import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Function;

public class ServerSocketWrapper {
    private ServerSocket serverSocket;
    private Socket socket;
    private Function<String, String> router;

    public void start(Function<String, String> router, int port) throws IOException {
        serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String data = in.readLine();

        String response = router.apply(data);
        out.write(response);
        out.flush();
    }

    public void stop() throws IOException {
        serverSocket.close();
    }
}

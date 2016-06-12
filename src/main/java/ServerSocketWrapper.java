import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Function;

public class ServerSocketWrapper {
    private ServerSocket serverSocket;
    private Socket socket;
    private Function<String, String> router;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String data = in.readLine();
        this.router.apply(data);
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public void setRouter(Function<String, String> router) {
        this.router = router;
    }
}

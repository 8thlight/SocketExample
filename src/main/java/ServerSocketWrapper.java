import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Function;

public class ServerSocketWrapper {
    private ServerSocket serverSocket;
    private Function<String, String> router;
    private Socket socket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public void setRouter(Function<String, String> router) {
        this.router = router;
    }
}

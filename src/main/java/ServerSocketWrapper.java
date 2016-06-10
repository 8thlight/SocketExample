import java.io.IOException;
import java.net.ServerSocket;
import java.util.function.Function;

public class ServerSocketWrapper {
    private ServerSocket socket;
    private Function<String, String> router;

    public void start(int port) throws IOException {
        socket = new ServerSocket(port);
    }

    public void stop() throws IOException {
        socket.close();
    }

    public void setRouter(Function<String, String> router) {
        this.router = router;
    }
}

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketWrapper {
    private ServerSocket socket;

    public void start(int port) throws IOException {
        socket = new ServerSocket(port);
    }

    public void stop() throws IOException {
        socket.close();
    }
}

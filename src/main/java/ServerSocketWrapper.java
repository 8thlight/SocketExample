import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketWrapper {
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void stop() throws IOException {
        serverSocket.close();
    }
}

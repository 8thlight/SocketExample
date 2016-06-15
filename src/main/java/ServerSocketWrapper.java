import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Function;

public class ServerSocketWrapper {
    private ServerSocket socketServer;
    private Function<String, String> translator;
    private Socket socket;

    public ServerSocketWrapper(Function<String, String> dummyTranslator) {
        this.translator = dummyTranslator;
    }

    public void start(int port) throws IOException {
        socketServer = new ServerSocket(port);
        socket = socketServer.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String data = in.readLine();
        this.translator.apply(data);
    }

    public void stop() throws IOException {
        socketServer.close();
    }

    public void setTranslator(Function<String, String> translator) {
        this.translator = translator;
    }
}

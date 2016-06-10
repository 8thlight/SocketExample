import org.junit.Test;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import static junit.framework.TestCase.*;

public class ServerSocketWrapperTest {

    @Test(expected = SocketException.class)
    public void itStartsAndStopsAServer() throws IOException {
        ServerSocketWrapper wrapper = new ServerSocketWrapper();
        wrapper.start(5000);

        Socket socket = new Socket("localhost", 5000);
        assertTrue(socket.isConnected());

        wrapper.stop();
        assertEquals(-1, socket.getInputStream().read());
    }

    @Test
    public void itAcceptsDataOverTheConnection() throws IOException {
        ServerSocketWrapper wrapper = new ServerSocketWrapper();
        wrapper.start(5000);

        Socket socket = new Socket("localhost", 5000);

        wrapper.stop();
    }
}

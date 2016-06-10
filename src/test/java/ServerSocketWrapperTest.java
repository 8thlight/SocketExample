import org.junit.Test;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

import static junit.framework.TestCase.*;

public class ServerSocketWrapperTest {

    @Test
    public void itStartsAndStopsAServer() throws IOException {
        ServerSocketWrapper wrapper = new ServerSocketWrapper();
        wrapper.start(5000);

        Socket socketThatWorks = new Socket("localhost", 5000);
        assertTrue(socketThatWorks.isConnected());

        wrapper.stop();
        try {
            new Socket("localhost", 5000);
            fail("Socket should not connect when there is no server running");
        } catch (ConnectException e) {

        }
    }

 /*   @Test
    public void itAcceptsDataOverTheConnection() throws IOException {
        ServerSocketWrapper wrapper = new ServerSocketWrapper();
        wrapper.start(5000);

        Socket socket = new Socket("localhost", 5000);

        wrapper.stop();
    }*/
}

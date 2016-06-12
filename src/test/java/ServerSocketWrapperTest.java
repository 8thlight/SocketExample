import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.function.Function;

import static junit.framework.TestCase.*;

public class ServerSocketWrapperTest {
    ServerSocketWrapper wrapper;

    @Before
    public void setUp() {
        wrapper = new ServerSocketWrapper();
    }

    @After
    public void tearDown() throws IOException {
        wrapper.stop();
    }

    @Test
    public void itStartsAndStopsAServer() throws IOException {

        wrapper.start(5000);

        try(Socket socketThatWorks = new Socket("localhost", 5000)) {
            assertTrue(socketThatWorks.isConnected());
        }

        wrapper.stop();
        try {
            new Socket("localhost", 5000);
            fail("Socket should not connect when there is no server running");
        } catch (ConnectException e) {

        }
    }

    @Test
    public void itAcceptsDataOverTheConnection() throws IOException {
        wrapper.start(5000);

        Socket socket = new Socket("localhost", 5000);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println("data");
        out.flush();

        final String[] sentData = new String[1];
        Function<String, String> router = string -> {
            sentData[0] = string;
            return "";
        };

        wrapper.setRouter(router);

        assertEquals("data", sentData[0]);
    }
}

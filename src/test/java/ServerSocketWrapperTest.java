import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServerSocketWrapperTest {
    public static final String LOCALHOST = "localhost";
    public static final int PORT = 5000;
    private ServerSocketWrapper wrapper;

    @Before
    public void setUp() {
        wrapper = new ServerSocketWrapper();
    }

    @After
    public void tearDown() throws IOException {
        wrapper.stop();
    }

    @Test
    public void itStartsAServer() throws IOException {
        wrapper.start(PORT);

        Socket client = new Socket(LOCALHOST, PORT);
        assertTrue(client.isConnected());
    }

    @Test(expected = ConnectException.class)
    public void itStopsAServer() throws IOException {
        wrapper.start(PORT);
        wrapper.stop();

        new Socket(LOCALHOST, PORT);
    }

    @Test
    public void itCanAcceptData() throws IOException {
        wrapper.start(PORT);

        Socket client = new Socket(LOCALHOST, PORT);
        PrintWriter out = new PrintWriter(client.getOutputStream());
        out.println("sent data");
        out.flush();
    }
}

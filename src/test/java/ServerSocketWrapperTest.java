import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServerSocketWrapperTest {
    public static final String LOCALHOST = "localhost";
    public static final int PORT = 5003;
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
        startServer();

        Socket client = new Socket(LOCALHOST, PORT);
        assertTrue(client.isConnected());
    }

    @Test(expected = ConnectException.class)
    public void itStopsAServer() throws IOException, InterruptedException {
        startServer();
        Thread.sleep(10);
        wrapper.stop();

        new Socket(LOCALHOST, PORT);
    }

  /*  @Test
    public void itCanAcceptData() throws IOException {
        final String[] actualData = new String[1];
        Function<String, String> router = in -> {
            actualData[0] = in;
            return "";
        };
        wrapper.setRouter(router);
        wrapper.start(PORT);

        Socket client = new Socket(LOCALHOST, PORT);
        PrintWriter out = new PrintWriter(client.getOutputStream());
        out.println("sent data");
        out.flush();

        assertEquals(actualData[0], "sent data");
    }*/

    private void startServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wrapper.start(PORT);
                } catch (SocketException e) {

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

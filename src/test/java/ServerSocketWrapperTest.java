import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerSocketWrapperTest {

    public static final int PORT = 9000;
    public static final String LOCALHOST = "localhost";
    private ServerSocketWrapper wrapper;

    @Before
    public void setUp() {
        Function<String, String> dummyTranslator = in -> "";
        wrapper = new ServerSocketWrapper(dummyTranslator);
    }

    @After
    public void tearDown() throws IOException {
        wrapper.stop();
    }

    @Test
    public void itStartsAServer() throws IOException, InterruptedException {
        startWrapper();

        Socket socket = new Socket(LOCALHOST, PORT);
        assertTrue(socket.isConnected());

    }

    private void connectToSocket() throws IOException {
        new Socket(LOCALHOST, PORT);
    }

    @Test(expected = ConnectException.class)
    public void itStopsAServer() throws IOException, InterruptedException {
        startWrapper();
        Thread.sleep(1);
        wrapper.stop();

        connectToSocket();
    }

    @Test
    public void itAcceptsDataFromASocketConnection() throws IOException, InterruptedException {

        final String[] actualData = new String[1];
        Function<String, String> translator = in -> {
            actualData[0] = in;
            return "";
        };
        wrapper.setTranslator(translator);

        startWrapper();
        writeDataToSocket("data");

        int retries = 0;
        while(actualData[0] == null && retries < 5) {
            Thread.sleep(1);
            retries++;
        }
        assertEquals("data", actualData[0]);
    }

    private void writeDataToSocket(String data) throws IOException {
        Socket socket = new Socket(LOCALHOST, PORT);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(data);
        out.close();
    }

    private void startWrapper() throws InterruptedException {
        new Thread(() -> {
            try {
                wrapper.start(PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }
}

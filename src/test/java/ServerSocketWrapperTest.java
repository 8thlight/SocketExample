import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
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
        Function<String, String> dummyRouter = string -> { return ""; };
        startServerSocket(dummyRouter);

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
    public void itAcceptsDataOverTheConnectionAndRoutesIt() throws IOException, InterruptedException {
        final String[] sentData = new String[1];
        Function<String, String> router = string -> {
            sentData[0] = string;
            return "";
        };
        startServerSocket(router);

        Socket socket = new Socket("localhost", 5000);
        sendDataToSocket(socket, "data");

        waitForDataToBePresent(sentData[0]);

        assertEquals("data", sentData[0]);
    }

    @Test
    public void itWritesBackWhatTheRouterReturns() throws IOException {
        Function<String, String> router = string -> {
            return "returned data";
        };
        startServerSocket(router);

        Socket socket = new Socket("localhost", 5000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        sendDataToSocket(socket, "irrelevant");

        socket.setSoTimeout(100);
        String readData = reader.readLine();

        assertEquals("returned data", readData);
    }

    private void waitForDataToBePresent(String s) throws InterruptedException {
        int retries = 0;
        while (s == null && retries < 5) {
            retries++;
            Thread.sleep(10);
        }
    }

    private void sendDataToSocket(Socket socket, String data) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(data);
        out.flush();
    }

    private void startServerSocket(Function<String, String> router) {
        new Thread() {
            public void run() {
                try {
                    wrapper.start(router, 5000);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}

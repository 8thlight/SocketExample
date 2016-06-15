import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServerSocketWrapperTest {

    @Test
    public void itStartsAndStopsAServer() throws IOException {
        ServerSocketWrapper wrapper = new ServerSocketWrapper();
        wrapper.start(5000);

        Socket client = new Socket("localhost", 5000);
        assertTrue(client.isConnected());
    }

    @Test(expected = ConnectException.class)
    public void itStopsAServer() throws IOException {
        ServerSocketWrapper wrapper = new ServerSocketWrapper();
        wrapper.start(5000);
        wrapper.stop();

        new Socket("localhost", 5000);
    }

    @Test
    public void itCanAcceptData() throws IOException {
        ServerSocketWrapper wrapper = new ServerSocketWrapper();
        wrapper.start(5000);

        Socket client = new Socket("localhost", 5000);
        PrintWriter out = new PrintWriter(client.getOutputStream());
        out.println("sent data");
        out.flush();
    }
}

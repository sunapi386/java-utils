import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Jason
 * Date: 31/05/13
 * Time: 10:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionThread implements Runnable {
    static boolean DEBUG = true;
    String address = "";
    long pingtime = 0;

    public ConnectionThread(String addr) {
        address = addr;
    }

    public static long ping(final String address) {
        assert (address.length() > 0);
        try {
            final URL url = new URL("http://" + address);
            final HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(1000 * 10); // mTimeout is in seconds
            final long startTime = System.currentTimeMillis();
            urlConn.connect();
            final long endTime = System.currentTimeMillis();
            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                long pTime = (endTime - startTime);
                if (DEBUG) System.out.println("Time to " + address + " (ms) : " + pTime);
                return pTime;
            }
        } catch (final MalformedURLException e1) {
            e1.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void run() {
        long t = ping(address);
    }

}

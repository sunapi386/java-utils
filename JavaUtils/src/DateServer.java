import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DateServer {
    public static void main(String[] args) {
        try {
            ServerSocket sock = new ServerSocket(6013);
            System.out.println("Socket 6013 open");
            /*
			 * This socket isn't closed. now listen for connections
			 */
            while (true) {
                Socket client = sock.accept();
                System.out.println("Socket accept");
                PrintWriter pout = new PrintWriter(client.getOutputStream(),
                        true);
				/* write the Date to the socket */
                pout.println(new java.util.Date().toString());
				/* close the socket and resume */
				/* listening for connections */
                client.close();
                System.out.println("Socket closed");
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }
}
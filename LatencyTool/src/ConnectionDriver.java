/**
 * Created with IntelliJ IDEA.
 * User: Jason
 * Date: 06/06/13
 * Time: 12:19 AM
 */
public class ConnectionDriver {
    public static void main(String[] args) {
        Thread thread_google = new Thread(new ConnectionThread("google.com"));
        Thread thread_yahoo = new Thread(new ConnectionThread("yahoo.com"));
        thread_google.start();
        thread_yahoo.start();


    }
}

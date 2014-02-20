import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
	private static final int TELNET_PORT = 23;
	private ServerSocket sock;
	private HashMap<Session, Thread> sessions;
	public Server() {
		sessions = new HashMap<Session, Thread>();
		try {
			sock = new ServerSocket(TELNET_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		new Thread(new Runnable() {
			public void run() {
				try {
					while(true) {
						Socket s = sock.accept();
						Session ses = new Session(s);
						Thread t = new Thread((Runnable)ses);
						t.start();
						sessions.put(ses, t);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public static void main(String[] args) {
		new Server().start();
	}
}

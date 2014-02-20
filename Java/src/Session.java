import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class Session implements Runnable {
	private Socket sock;
	private BufferedReader in;
	private DataOutputStream out;
	
	private String id;
	private Date loginTime;
	public Session(Socket sock) throws IOException {
		this.sock = sock;
		in = new BufferedReader(
				new InputStreamReader(sock.getInputStream()));
		out = new DataOutputStream(sock.getOutputStream());
	}
	
	@Override
	public void run() {
		try {
			newConnection();
			writeLog();
			
			String line;
			while ((line = in.readLine()) != null && 
					!(line.trim().equalsIgnoreCase("exit"))) 
				System.out.println(id + ": " + line);
			
			closeShop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeShop() throws IOException {
		in.close();
		out.close();
		sock.close();
		System.out.println(id + ": Session closed");
	}
	
	private void writeLog() throws IOException {
		PrintWriter pw = new PrintWriter(
				new BufferedWriter(
						new FileWriter("log.txt", true)));
		pw.println(id + ",\t\t" + loginTime + "\n");
		pw.close();
	}
	
	private void newConnection() throws IOException {
		out.writeChars("Welcome, please input your login ID.\n");
		out.flush();
		id = in.readLine();
		System.out.println("Login attempt: " + id);
		
		out.writeChars("Welcome, " + id + ", please provide your password\n");
		out.flush();
		in.readLine();
		
		loginTime = new Date();
		out.writeChars("Greetings, " + id + ", time is " + loginTime + "\n");
		out.flush();
		System.out.println("Login for user " + id + " succeeded at " + loginTime);
	}
}

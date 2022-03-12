import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Client {
	
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private String username;
	
	
	public Client(Socket socket, String username) {
		try {
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.username = username;
			
			
		}catch(IOException e) {
			
		}
	}
	
	public void sendMessage() {
		try {
			bufferedWriter.write(username);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			
			Scanner scanner = new Scanner(System.in);
			
			while (socket.isConnected()) {
				String messageToSend = scanner.nextLine();
				bufferedWriter.write(username + ":" + messageToSend);
				bufferedWriter.newLine();
				bufferedWriter.flush();
			}
			
			
		}catch(IOException e) {
			
		}
	}
	
	public void listenForMessage() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				String msgFromGroupChat;
				
				while (socket.isConnected()) {
					try {
						msgFromGroupChat = bufferedReader.readLine();
						System.out.println(msgFromGroupChat);
						
					}catch(IOException e) {
						
					}
				}
				
			}
		}).start();
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Userna,e");
		String username = scanner.nextLine();
		Socket socket = new Socket("localhost",4996);
		Client client = new Client(socket,username);
		client.listenForMessage();
		client.sendMessage();
	}
}

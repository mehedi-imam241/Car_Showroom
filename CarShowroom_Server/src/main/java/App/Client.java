//package App;
//
//import java.io.*;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Client {
//
//	public static void main(String[] args) throws IOException, ClassNotFoundException {
//		// need host and port, we want to connect to the ServerSocket at port 7777
//		Socket socket = new Socket("localhost", 7777);
//		System.out.println("Connected!");
//
//		// get the output stream from the socket.
//		OutputStream outputStream = socket.getOutputStream();
//		// create an object output stream from the output stream so we can send an
//		// object through it
//		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
//
//		// make a bunch of messages to send.
//		List<CarEntity> car = new ArrayList<>();
//		messages.add(new Message("goodbye"));
//		messages.add(new Message("How are you doing?"));
//		messages.add(new Message("What time is it?"));
//		messages.add(new Message("Hi hi hi hi."));
//
//		System.out.println("Sending messages to the ServerSocket");
//		objectOutputStream.writeObject(messages);
//
//		ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
//		messages = (List<Message>) objectInputStream.readObject();
//		messages.forEach((msg) -> System.out.println(msg.getText()));
//
//		System.out.println("Closing socket and terminating program.");
//		socket.close();
//	}
//}
package controller_view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import model.PaintObject;

public class Server
{
	private static List<ObjectOutputStream> outputStreams = new Vector<>();
	private static Vector<PaintObject> allPaintObjects = new Vector<PaintObject>();

	public static void main(String[] args) throws Exception
	{

		// System.out.println("Server started");
		ServerSocket server = new ServerSocket(4000);
		Socket connection;
		// System.out.println("This Server just got a Client at port 4000");
		// Make both connection steams available
		ObjectOutputStream outputToClient;
		ObjectInputStream inputFromClient;
		// Do some IO.

		while (true)
		{
			connection = server.accept();
			outputToClient = new ObjectOutputStream(connection.getOutputStream());
			inputFromClient = new ObjectInputStream(connection.getInputStream());

			outputStreams.add(outputToClient);

			ClientHandler clientHandler = new ClientHandler(inputFromClient);
			Thread thread = new Thread(clientHandler);
			thread.start();
		}
	}

	private static class ClientHandler implements Runnable
	{
		private ObjectInputStream input;

		public ClientHandler(ObjectInputStream input)
		{
			this.input = input;
		}

		@Override
		public void run()
		{
			PaintObject drawing = null;
			
			for (ObjectOutputStream stream : outputStreams)
			{
				try {
					stream.reset();
					stream.writeObject(allPaintObjects);
				} catch (IOException ioe) {
				}
			}

			while (true)
			{
				try {
					drawing = (PaintObject) input.readObject();
					allPaintObjects.add(drawing);
				} catch (IOException ioe) {
				} catch (ClassNotFoundException cnfe) {
				}

				drawToClients(allPaintObjects);
			}
		}

		private void drawToClients(Vector<PaintObject> paintObjects)
		{
			for (ObjectOutputStream stream : outputStreams)
			{
				try {
					stream.reset();
					stream.writeObject(paintObjects);
				} catch (IOException ioe) {
				}
			}
		}
	}
}

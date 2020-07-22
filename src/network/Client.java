package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import game.Player;
import game.Table;

public class Client implements Runnable
{
	
	private Socket socket;
	private int port = 55555;
	ObjectOutputStream out;
	ObjectInputStream in;
	
	private Scanner scanner;
	private Player player;
	
	private boolean connected = false;
	
	public Client(Scanner scanner)
	{
		
		this.scanner = scanner;
		player = createPlayer(this.scanner);
		Thread t1 = new Thread(this);
		t1.start();
		t1.setName(player.getName());
		
	}
	
	
	private static Player createPlayer(Scanner scanner)
	{
		
		System.out.println("please state your name: ");
		String name = scanner.next();
		return new Player(name, scanner);
		
	}
	
	
	@Override
	public void run()
	{
		
		try
		{
			connect();
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		while (connected)
		{
			try
			{
				tableHandling();
			}
			catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	
	private void connect() throws UnknownHostException, IOException, ClassNotFoundException
	{
		
		socket = new Socket("localhost", port);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		out.writeObject(player);
		player.setID(in.readInt());
		connected = true;
		
	}
	
	
	private void tableHandling() throws ClassNotFoundException, IOException
	{
		
		if (player.receiveTable((Table) in.readObject()))
		{
			out.writeObject(player.sendTable());
		}
		
	}
	
}

package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import game.Player;
import game.Table;

public class Server implements Runnable
{
	
	private class Sock
	{
		
		private ObjectOutputStream out;
		private ObjectInputStream in;
		private Player player;
		
		private Sock(ObjectOutputStream out, ObjectInputStream in, Player player)
		{
			
			this.out = out;
			this.in = in;
			this.player = player;
			
		}
		
	}
	
	private ServerSocket server;
	private Vector<Sock> socks = new Vector<Sock>();
	// TODO: changing port from main
	private int port = 55555;
	private int playerID = 0;
	private boolean open = true;
	private int howManyPlayers = 2;
	private Table table;
	
	public Server(int port) throws IOException
	{
		
		server = new ServerSocket(port);
		Thread t1 = new Thread(this);
		t1.start();
		t1.setName("SERVER");
		
	}
	
	
	public Server() throws IOException
	{
		
		server = new ServerSocket(port);
		Thread t1 = new Thread(this);
		t1.start();
		t1.setName("SERVER");
		
	}
	
	
	@Override
	public void run()
	{
		
		table = new Table(howManyPlayers);
		while (open)
		{
			if (socks.size() >= howManyPlayers)
			{
				open = false;
				break;
			}
			
			try
			{
				addPlayer();
				System.out.println("[SERVER] added a player");
			}
			catch (ClassNotFoundException | IOException e)
			{
				System.err.println("[SERVER] Couldn't add player");
				e.printStackTrace();
			}
		}
		
		while (!open)
		{
			try
			{
				sendTable();
				recieveTable();
			}
			catch (ClassNotFoundException | IOException e)
			{
				System.err.println("[SERVER] couldn't sent/receive table");
				e.printStackTrace();
			}
		}
		
	}
	
	
	private void addPlayer() throws IOException, ClassNotFoundException
	{
		
		Socket socket = server.accept();
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		
		Player player = (Player) in.readObject();
		out.writeInt(playerID);
		player.setID(playerID++);
		socks.add(new Sock(out, in, player));
		table.addPlayer(player);
		
	}
	
	
	private void sendTable() throws IOException
	{
		
		for (Sock sock : socks) sock.out.writeObject(table);
		
	}
	
	
	private void recieveTable() throws ClassNotFoundException, IOException
	{
		
		for (Sock sock : socks)
		{
			if (sock.player.equals(table.getCurrentPlayer()))
			{
				table = (Table) sock.in.readObject();
				return;
			}
		}
		
	}
	
}

package game;

import java.io.IOException;
import java.util.Scanner;

import network.Client;
import network.Server;

public class Main
{
	
	public static void main(String[] args)
	{
		
		Scanner scanner = new Scanner(System.in);
		
		try
		{
			new Server();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Client(scanner);
		new Client(scanner);
		
	}
	
}

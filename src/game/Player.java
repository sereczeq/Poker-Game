package game;

import java.io.Serializable;
import java.util.Scanner;

import cards.Deck;
import cards.Hand;
import cards.Tokens;

public class Player implements Serializable
{
	
	/**
	 * TODO: fix raising logistics
	 */
	private static final long serialVersionUID = 1L;
	private Hand hand;
	private Tokens tokens;
	private int ID;
	private String name;
	
	private Table table;
	
	private boolean isInGame = true;
	private boolean isAllIn = false;
	
	transient private Scanner scanner;
	
	public Player(String name, Scanner scanner)
	{
		
		this.name = name;
		this.scanner = scanner;
		hand = new Hand();
		tokens = new Tokens(150);
		
	}
	
	
	public Player(Player player)
	{
		
		hand = new Hand();
		tokens = player.tokens;
		ID = player.ID;
		name = player.name;
		scanner = player.scanner;
		
	}
	
	
	private void updateHand()
	{
		
		Hand temp = hand;
		for (Player player : table.getPlayers())
		{
			if (player.equals(this))
			{
				hand = player.hand;
			}
		}
		if (hand.showNewCard(temp) != null) System.out.println("new card:\n " + hand.showNewCard(temp));
		
	}
	
	
	public boolean receiveTable(Table tableX)
	{
		
		table = tableX;
		System.out.println(table.toString());
		updateHand();
		System.out.println(hand);
		if (table.getCurrentPlayer().equals(this))
		{
			myTurn();
			return true;
		}
		else if (table.getCurrentPlayer().getID() == -1)
		{
			table.finish();
			System.out.println("Do you want to continue? [Y / N]");
			readInput("a");
			return false;
		}
		else
		{
			notMyTurn();
			return false;
		}
		
	}
	
	
	public Table sendTable()
	{
		
		override(table);
		return table;
		
	}
	
	
	public void override(Table table)
	{
		
		table.getPlayers().set(table.getPlayers().indexOf(this), this);
		
	}
	
	
	// Here only if this is the current player
	private void myTurn()
	{
		
		if (canPlay())
		{
			if (isTop())
			{
				raise();
			}
			else
			{
				equal();
			}
		}
		table.nextPlayer();
		
	}
	
	
	private boolean canPlay()
	{
		
		if (!isInGame)
		{
			System.out.println("You are out");
			return false;
		}
		if (isAllIn)
		{
			System.out.println("You are all in");
			return false;
		}
		return true;
		
	}
	
	
	private boolean isTop()
	{
		
		return tokens.getOnTable() == table.getTokensToEnter();
		
	}
	
	
	private void raise()
	{
		
		System.out.println("Do you want to raise? [Y / N]");
		if (readInput("a"))
		{
			System.out.println("How many tokens do you want to raise?");
			int howMany = 0;
			while (!raiseAction(howMany = readInput(1))) System.out.println(howMany + " is not a proper number");
			if (isAllIn) return;
			System.out.println("You raised " + howMany + " tokens");
			table.setLastRaisingPlayer(this);
			table.setLastRaise(howMany);
			
		}
		
	}
	
	
	private void equal()
	{
		
		int difference = table.getTokensToEnter() - tokens.getOnTable();
		System.out.println("Do you want to stay in and raise " + difference + " tokens? [Y / N]");
		if (readInput("a"))
		{
			if (difference < tokens.getTotal())
			{
				System.out.println("Automatically raised " + difference + " tokens.");
				table.equal(difference);
				tokens.raise(difference);
				raise();
				
			}
			else
			{
				goingAllIn();
			}
		}
		else isInGame = false;
		
	}
	
	
	private boolean raiseAction(int howMany)
	{
		
		if (howMany >= tokens.getTotal() && !isAllIn)
		{
			goingAllIn();
			return true;
		}
		else return tokens.raise(howMany) && table.raise(howMany);
		
	}
	
	
	private void goingAllIn()
	{
		
		System.out.println("You don't have enough tokens to enter");
		System.out.println("DO YOU WANT TO GO ALL IN? [Y / N]");
		if (readInput("a"))
		{
			isAllIn = true;
			raiseAction(tokens.getTotal());
		}
		else
		{
			System.out.println("You all OUT");
			isInGame = false;
		}
		
	}
	
	
	private void notMyTurn()
	{
		
		System.out.println("It's " + table.getCurrentPlayer().getName().toUpperCase() + "'s turn");
		
	}
	
	
	public boolean isMyTurn(Table table)
	{
		
		return table.getCurrentPlayer().equals(this);
		
	}
	
	
	public void draw(Deck deck)
	{
		
		hand.take(deck);
		
	}
	
	
	public void draw(Deck deck, int howMany)
	{
		
		hand.take(deck, howMany);
		
	}
	
	
	private int readInput(int integerOrStringInput)
	{
		
		while (scanner.hasNext())
		{
			if (scanner.hasNextInt())
			{
				return scanner.nextInt();
			}
			else System.out.println("please enter a number");
		}
		return 0;
		
	}
	
	
	private boolean readInput(String integerOrStringInput)
	{
		
		while (scanner.hasNext())
		{
			String s = scanner.next();
			if (s.equalsIgnoreCase("y")) return true;
			else if (s.equalsIgnoreCase("n")) return false;
			else System.out.println("enter Y or N");
		}
		return false;
		
	}
	
	
	public int getID()
	{
		
		return ID;
		
	}
	
	
	public void setID(int ID)
	{
		
		this.ID = ID;
		
	}
	
	
	public Hand getHand()
	{
		
		return hand;
		
	}
	
	
	public Tokens getTokens()
	{
		
		return tokens;
		
	}
	
	
	public String getName()
	{
		
		return name;
		
	}
	
	
	public boolean isInGame()
	{
		
		return isInGame;
		
	}
	
	
	public boolean isAllIn()
	{
		
		return isAllIn;
		
	}
	
	
	@Override
	public boolean equals(Object other)
	{
		
		return other instanceof Player && ((Player) other).getID() == ID;
		
	}
	
}

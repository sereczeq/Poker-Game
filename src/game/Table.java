package game;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

import cards.Deck;

public class Table implements Serializable
{
	
	private static final long serialVersionUID = 2L;
	private Player currentPlayer;
	private Player mainPlayer;
	private Player lastRaisingPlayer;
	private Vector<Player> players = new Vector<Player>();
	private int round = 3;
	private Deck deck;
	private int tokensOnTable = 0;
	private int tokensToEnter = 0;
	private int lastRaise = 0;
	
	private final Player finish;
	
	public Table(int howMany)
	{
		
		deck = new Deck(24);
		deck.shuffle();
		Player player = new Player("FINISH", null);
		player.setID(-1);
		finish = player;
		
	}
	
	
	public Player getCurrentPlayer()
	{
		
		return currentPlayer;
		
	}
	
	
	public void nextPlayer()
	{
		
		Iterator<Player> it = players.iterator();
		for (int x = 0; x < 100; x++)
		{
			if (!it.hasNext()) it = players.iterator();
			if (it.next().equals(currentPlayer))
			{
				currentPlayer = it.hasNext() ? it.next() : players.get(0);
				if (currentPlayer.equals(lastRaisingPlayer))
				{
					currentPlayer = mainPlayer;
					nextRound();
					return;
				}
				return;
			}
		}
		
		System.err.println("[TABLE] couldn't set next player");
		
	}
	
	
	public boolean raise(int howMany)
	{
		
		tokensToEnter += howMany;
		tokensOnTable += howMany;
		return true;
		
	}
	
	
	public void equal(int howMany)
	{
		
		tokensOnTable += howMany;
		
	}
	
	
	private void nextRound()
	{
		
		if (round > 5)
		{
			currentPlayer = finish;
			// finish(playingPlayers());
		}
		else if (playingPlayers().size() == 1)
		{
			currentPlayer = finish;
			// finish(playingPlayers());
		}
		else
		{
			for (Player player : playingPlayers())
			{
				player.draw(deck);
			}
			round++;
		}
		
	}
	
	
	private Vector<Player> playingPlayers()
	{
		
		Vector<Player> temp = new Vector<Player>();
		for (Player player : players) if (player.isInGame()) temp.add(player);
		return temp;
		
	}
	
	
	public void finish()
	{
		
		finish(playingPlayers());
		
	}
	
	
	public void finish(Vector<Player> players)
	{
		
		Player max = players.get(0);
		for (Player player : players)
		{
			System.out.println(player);
			if (player.getHand().getHandRank().compareTo(max.getHand().getHandRank()) > 0) max = player;
		}
		System.out.println();
		System.out.println(max.getName() + " IS A WINNER with " + max.getHand().getHandRank() + ", their hand:\n"
				+ max.getHand() + "\nThey won " + getTokensOnTable() + " tokens");
		max.getTokens().add(tokensOnTable);
		
	}
	
	
	public void addPlayer(Player player)
	{
		
		if (players.isEmpty())
		{
			mainPlayer = currentPlayer = lastRaisingPlayer = player;
		}
		player.draw(deck, 2);
		players.add(player);
		
	}
	
	
	public Player getMainPlayer()
	{
		
		return mainPlayer;
		
	}
	
	
	public void setMainPlayer(Player mainPlayer)
	{
		
		this.mainPlayer = mainPlayer;
		
	}
	
	
	public Player getLastRaisingPlayer()
	{
		
		return lastRaisingPlayer;
		
	}
	
	
	public void setLastRaisingPlayer(Player lastRaisingPlayer)
	{
		
		this.lastRaisingPlayer = lastRaisingPlayer;
		
	}
	
	
	public int getRound()
	{
		
		return round;
		
	}
	
	
	public void setRound(int round)
	{
		
		this.round = round;
		
	}
	
	
	public Deck getDeck()
	{
		
		return deck;
		
	}
	
	
	public void setDeck(Deck deck)
	{
		
		this.deck = deck;
		
	}
	
	
	public int getTokensOnTable()
	{
		
		return tokensOnTable;
		
	}
	
	
	public void setTokensOnTable(int tokensOnTable)
	{
		
		this.tokensOnTable = tokensOnTable;
		
	}
	
	
	public int getTokensToEnter()
	{
		
		return tokensToEnter;
		
	}
	
	
	public void setTokensToEnter(int tokensToEnter)
	{
		
		this.tokensToEnter = tokensToEnter;
		
	}
	
	
	public int getLastRaise()
	{
		
		return lastRaise;
		
	}
	
	
	public void setLastRaise(int lastRaise)
	{
		
		this.lastRaise = lastRaise;
		
	}
	
	
	public void setCurrentPlayer(Player currentPlayer)
	{
		
		this.currentPlayer = currentPlayer;
		
	}
	
	
	public void setPlayers(Vector<Player> players)
	{
		
		this.players = players;
		
	}
	
	
	public Vector<Player> getPlayers()
	{
		
		return players;
		
	}
	
	
	@Override
	public String toString()
	{
		
		String s = "[TABLE]:\n";
		s += "Tokens on table: " + tokensOnTable + "\n";
		s += "Tokens to enter " + tokensToEnter + "\n";
		for (Player player : players)
		{
			if (!player.isInGame()) s += "Player " + player.getName() + " is out\n";
		}
		for (Player player : players)
		{
			if (player.isAllIn()) s += "Player " + player.getName() + " is ALL IN!\n";
		}
		s += "Main Player this round is " + mainPlayer.getName() + "\n";
		s += "Last raising player is " + lastRaisingPlayer.getName() + "\n";
		s += "Current Player is " + currentPlayer.getName();
		s += "\n";
		return s;
		
	}
	
}

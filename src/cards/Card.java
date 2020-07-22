package cards;

import java.io.Serializable;

public class Card implements Comparable<Card>, Serializable
{
	
	private Rank rank;
	private Suit suit;
	private int rankIndex;
	private int suitIndex;
	
	public enum Rank
	
	{
		ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO;
	}
	
	public enum Suit
	{
		SPADES, HEARTS, DIAMONDS, CLUBS;
	}
	
	public Card(Rank rank, Suit suit)
	{
		
		this.rank = rank;
		this.suit = suit;
		setIndex();
		
	}
	
	
	public Rank getRank()
	{
		
		return rank;
		
	}
	
	
	public Suit getSuit()
	{
		
		return suit;
		
	}
	
	
	public int getRankIndex()
	{
		
		return rankIndex;
		
	}
	
	
	public int getSuitIndex()
	{
		
		return suitIndex;
		
	}
	
	
	private void setIndex()
	{
		
		switch (this.rank)
		{
			case ACE:
				rankIndex = 13;
				break;
			case KING:
				rankIndex = 12;
				break;
			case QUEEN:
				rankIndex = 11;
				break;
			case JACK:
				rankIndex = 10;
				break;
			case TEN:
				rankIndex = 9;
				break;
			case NINE:
				rankIndex = 8;
				break;
			case EIGHT:
				rankIndex = 7;
				break;
			case SEVEN:
				rankIndex = 6;
				break;
			case SIX:
				rankIndex = 5;
				break;
			case FIVE:
				rankIndex = 4;
				break;
			case FOUR:
				rankIndex = 3;
				break;
			case THREE:
				rankIndex = 2;
				break;
			case TWO:
				rankIndex = 1;
				break;
		}
		
		switch (this.suit)
		{
			case SPADES:
				suitIndex += 4;
				break;
			case HEARTS:
				suitIndex += 3;
				break;
			case DIAMONDS:
				suitIndex += 2;
				break;
			case CLUBS:
				suitIndex += 1;
				break;
		}
		
	}
	
	
	@Override
	public int hashCode()
	{
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + rankIndex;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + suitIndex;
		return result;
		
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Card other = (Card) obj;
		if (rank != other.rank) return false;
		if (rankIndex != other.rankIndex) return false;
		if (suit != other.suit) return false;
		if (suitIndex != other.suitIndex) return false;
		return true;
		
	}
	
	
	@Override
	public String toString()
	{
		
		return "[" + rank + " of " + suit + "]";
		
	}
	
	
	@Override
	public int compareTo(Card other)
	{
		
		if (this.rankIndex == other.rankIndex) return other.suitIndex - this.suitIndex;
		else return other.rankIndex - this.rankIndex;
		
	}
	
}

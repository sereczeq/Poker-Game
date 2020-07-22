package cards;

import java.util.Iterator;
import java.util.Vector;

public class HandRank implements Comparable<HandRank>
{
	
	public Rank rank = Rank.HIGHCARD;
	public Card topCard;
	public Card highCard;
	
	public enum Rank
	{
		ROYALFLUSH, STRAIGHTFLUSH, FOUROFAKIND, FULLHOUSE, FLUSH, STRAIGHT, THREEOFAKIND, TWOPAIR, PAIR, HIGHCARD;
	}
	
	public HandRank(Vector<Card> hand)
	{
		
		pairCheck(hand);
		straightCheck(hand);
		
	}
	
	
	private void straightCheck(Vector<Card> hand)
	{
		
		Iterator<Card> it = hand.iterator();
		Card card = null;
		boolean straight = true;
		if (it.hasNext()) card = it.next();
		while (it.hasNext())
		{
			Card card2 = it.next();
			if (card.getRankIndex() == card2.getRankIndex() + 1)
			{
				card = card2;
			}
			else
			{
				straight = false;
				break;
			}
		}
		if (straight)
		{
			topCard = hand.get(0);
			highCard = topCard;
			rank = Rank.STRAIGHT;
		}
		
		boolean flush = true;
		for (Card card2 : hand)
		{
			if (card.getSuit() != card2.getSuit()) flush = false;
		}
		
		if (flush)
		{
			if (straight)
			{
				if (topCard.getRank() == Card.Rank.ACE) rank = Rank.ROYALFLUSH;
				else rank = Rank.STRAIGHTFLUSH;
			}
			else rank = Rank.FLUSH;
		}
		
	}
	
	
	private void pairCheck(Vector<Card> hand)
	{
		
		highCard = hand.get(0);
		topCard = hand.get(0);
		for (int x = 0; x < hand.size(); x++)
		{
			Card card = hand.get(x);
			int matches = 1;
			for (int y = x + 1; y < hand.size(); y++)
			{
				Card card2 = hand.get(y);
				if (card2 == card) continue;
				if (card2.getRank() == card.getRank()) matches++;
			}
			
			if (matches == 4)
			{
				rank = Rank.FOUROFAKIND;
				topCard = new Card(card.getRank(), Card.Suit.SPADES);
				setHighCard(card, hand);
				break;
			}
			else if (matches == 3)
			{
				if (rank == Rank.PAIR)
				{
					rank = Rank.FULLHOUSE;
					highCard = topCard;
					topCard = card;
					break;
				}
				else
				{
					rank = Rank.THREEOFAKIND;
					topCard = card;
					setHighCard(card, hand);
					x++;
				}
			}
			
			else if (matches == 2)
			{
				if (rank == Rank.THREEOFAKIND)
				{
					rank = Rank.FULLHOUSE;
					highCard = card;
					break;
				}
				if (rank == Rank.PAIR)
				{
					rank = Rank.TWOPAIR;
					highCard = card.getRankIndex() < topCard.getRankIndex() ? card : topCard;
					topCard = card.getRankIndex() > topCard.getRankIndex() ? card : topCard;
					break;
				}
				rank = Rank.PAIR;
				topCard = card;
				setHighCard(card, hand);
				
			}
		}
		
	}
	
	
	public void setHighCard(Card card, Vector<Card> hand)
	{
		
		for (Card card2 : hand)
		{
			if (card2.getRank() != card.getRank())
			{
				highCard = card2;
				break;
			}
		}
		
	}
	
	
	public int getHandRankIndex()
	{
		
		switch (rank)
		{
			case ROYALFLUSH:
				return 10;
			case STRAIGHTFLUSH:
				return 9;
			case FOUROFAKIND:
				return 8;
			case FULLHOUSE:
				return 7;
			case FLUSH:
				return 6;
			case STRAIGHT:
				return 5;
			case THREEOFAKIND:
				return 4;
			case TWOPAIR:
				return 3;
			case PAIR:
				return 2;
			case HIGHCARD:
				return 1;
			default:
				return 0;
		}
		
	}
	
	
	@Override
	public String toString()
	{
		
		return "HandRank [handRankE=" + rank + ", topCard=" + topCard + ", highCard=" + highCard + "]";
		
	}
	
	
	@Override
	public int compareTo(HandRank other)
	{
		
		if (this.getHandRankIndex() == other.getHandRankIndex())
		{
			if (this.topCard.getRank() == other.topCard.getRank())
			{
				return this.highCard.compareTo(other.highCard);
			}
			else return this.topCard.getRankIndex() - other.topCard.getRankIndex();
		}
		else return this.getHandRankIndex() - other.getHandRankIndex();
		
	}
	
}

package cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Deck implements Serializable
{
	
	private List<Card> deck = new ArrayList<Card>();
	
	public Deck(int howMany)
	{
		
		int it = howMany;
		for (Card.Rank type : Card.Rank.values())
		{
			for (Card.Suit suit : Card.Suit.values())
			{
				deck.add(new Card(type, suit));
				it--;
			}
			if (it <= 0) break;
		}
		
	}
	
	
	public Deck()
	{
		
		this(52);
		
	}
	
	
	public void shuffle()
	{
		
		Collections.shuffle(deck);
		
	}
	
	
	public Card takeCard(int index)
	{
		
		if (index <= 0 || index > 52) index = 1;
		Iterator<Card> it = deck.iterator();
		Card card = null;
		for (; index > 0 && it.hasNext(); index--)
		{
			card = it.next();
		}
		deck.remove(card);
		return card;
		
	}
	
	
	public Card takeCard()
	{
		
		return takeCard(1);
		
	}
	
	
	public Card takeCard(Card.Rank rank, Card.Suit suit)
	{
		
		Iterator<Card> it = deck.iterator();
		Card card = null;
		while (it.hasNext())
		{
			card = it.next();
			if (card.getRank() == rank && card.getSuit() == suit) return card;
		}
		return takeCard();
		
	}
	
	
	public Card[] takeCards(int howMany)
	{
		
		if (howMany < 1) howMany = 1;
		Card[] cards = new Card[howMany];
		for (int x = 0; x < howMany; x++)
		{
			cards[x] = takeCard();
		}
		return cards;
		
	}
	
	
	@Override
	public String toString()
	{
		
		if (deck.isEmpty()) return "deck is empty";
		String s = "";
		for (Card card : deck)
		{
			s += card + "\n";
		}
		return s;
		
	}
	
}

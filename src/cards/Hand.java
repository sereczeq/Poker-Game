package cards;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

public class Hand implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	public Vector<Card> cards = new Vector<Card>();
	
	public void take(Deck deck, int howMany)
	{
		
		if (howMany > 5) howMany = 5;
		cards.addAll(Arrays.asList(deck.takeCards(howMany)));
		Collections.sort(cards);
		
	}
	
	
	public void take(Deck deck)
	{
		
		take(deck, 1);
		
	}
	
	
	public void empty()
	{
		
		cards.clear();
		
	}
	
	
	public HandRank getHandRank()
	{
		
		return new HandRank(cards);
		
	}
	
	
	@Override
	public String toString()
	{
		
		String s = "";
		if (cards == null || cards.isEmpty()) return "hand is empty";
		for (Card card : cards)
		{
			s += card + "\n";
		}
		return s;
		
	}
	
	
	public Card showNewCard(Hand hand)
	{
		
		if (hand == null || cards.size() > hand.cards.size() + 1) return null;
		for (Card card : cards) if (!hand.cards.contains(card)) return card;
		return null;
		
	}
	
}

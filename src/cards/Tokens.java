package cards;

import java.io.Serializable;

public class Tokens implements Serializable
{
	
	private int total;
	private int onTable = 0;
	
	public Tokens(int howMany)
	{
		
		total = howMany;
		
	}
	
	
	public boolean raise(int howMany)
	{
		
		if (howMany > total || howMany < 0) return false;
		total -= howMany;
		onTable += howMany;
		return true;
		
	}
	
	
	public void add(int howMany)
	{
		
		total += howMany;
		
	}
	
	
	public void resetTable()
	{
		
		total += onTable;
		onTable = 0;
		
	}
	
	
	@Override
	public String toString()
	{
		
		return "[tokens left=" + total + ", onTable=" + onTable + "]";
		
	}
	
	
	public int getTotal()
	{
		
		return total;
		
	}
	
	
	public int getOnTable()
	{
		
		return onTable;
		
	}
	
}


public class LexException extends Exception
{

	private int lineNum;
	
	private int columnNum;
	
	/**
	 * @param message cannot be null
	 * @param lineNum must be positive
	 * @param columnNum must be positive
	 * @throws IllegalArgumentException if any precondition is not true
	 */
	public LexException(String message, int lineNum, int columnNum)
	{
		super (message);
		if (message == null)
			throw new IllegalArgumentException ("null string argument");
		if (lineNum <= 0)
			throw new IllegalArgumentException ("invalid line number argument");
		if (columnNum <= 0)
			throw new IllegalArgumentException ("invalid column number argument");
		this.lineNum = lineNum;
		this.columnNum = columnNum;
	}

	@Override
	public String toString()
	{
		return getMessage() + " at line number " + lineNum + " column number " + columnNum;
	}

	
}


public class Token
{

	private String lexeme;
	
	private int rowNumber;
	
	private int columnNumber;
	
	private TokenType tok;
	
	public Token(String lexeme, int rowNumber, int columnNumber, TokenType tok)
	{
		if (lexeme == null || lexeme.length() == 0)
			throw new IllegalArgumentException ("invalid lexeme");
		if (rowNumber <= 0)
			throw new IllegalArgumentException ("invalid row number");
		if (columnNumber <= 0)
			throw new IllegalArgumentException ("invalid column number");
		this.lexeme = lexeme;
		this.rowNumber = rowNumber;
		this.columnNumber = columnNumber;
		this.tok = tok;
	}

	public String getLexeme()
	{
		return lexeme;
	}

	public int getRowNum()
	{
		return rowNumber;
	}

	public int getColumnNum()
	{
		return columnNumber;
	}

	public TokenType getTokenType()
	{
		return tok;
	}

}

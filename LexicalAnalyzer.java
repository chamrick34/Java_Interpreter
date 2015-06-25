import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class LexicalAnalyzer
{

	private List<Token> tokenList;
	

	public LexicalAnalyzer(String fileName) throws FileNotFoundException, LexException
	{
		if (fileName == null)
			throw new IllegalArgumentException ("null file name argument");
		tokenList = new LinkedList<Token>();
		int lineNum = 1;
		Scanner input = new Scanner (new File (fileName));
		while (input.hasNext())
		{
			processLine (input.nextLine(), lineNum);
			lineNum++;
		}
		tokenList.add(new Token ("EOS", lineNum, 1, TokenType.EOS_TOK));
		input.close();
	}

	private void processLine(String line, int lineNum) throws LexException
	{
		assert line != null;
		int index = 0;
		index = skip_white_space (line, index);
		while (index < line.length())
		{
			String lexeme = getLexeme (line, index, lineNum);
			TokenType tokType = getTokenType (lexeme, lineNum, index);
			tokenList.add(new Token (lexeme, lineNum, index + 1, tokType));
			index += lexeme.length();
			index = skip_white_space(line, index);
		}
	}

	private TokenType getTokenType(String lexeme, int lineNum, int columnNum) throws LexException
	{
		assert lexeme != null;
		assert lexeme.length() > 0;
		assert lineNum >= 1;
		assert columnNum >= 1;
		TokenType tokType = TokenType.EOS_TOK;
		lexeme = lexeme.toLowerCase();
		if (Character.isLetter(lexeme.charAt(0)))
			if (lexeme.length() == 1)
				tokType = TokenType.ID_TOK;
			else
				switch (lexeme)
				{
					case "program":
						tokType = TokenType.PROGRAM_TOK;
						break;
               case "main()":
                  tokType = TokenType.MAIN_TOK;
                  break;
					case "end":
						tokType = TokenType.END_TOK;
						break;
					case "if":
						tokType = TokenType.IF_TOK;
						break;
					case "else":
						tokType = TokenType.ELSE_TOK;
						break;
               case "elif":
                  tokType = TokenType.ELIF_TOK;
                  break;
               case "while":
                  tokType = TokenType.WHILE_TOK;
					default:
						throw new LexException ("invalid lexeme", lineNum, columnNum);
				}
		else if (Character.isDigit(lexeme.charAt(0)))
		{
			int i = 0;
			while (i < lexeme.length() && Character.isDigit(lexeme.charAt(i)))
				i++;
			if (i == lexeme.length())
				tokType = TokenType.INT_TOK;
			else
				throw new LexException ("invalid integer constant", lineNum, columnNum);
		}
		else
			switch (lexeme)
			{
				case "(":
					tokType = TokenType.LEFT_PAREN_TOK;
					break;
				case ")":
					tokType = TokenType.RIGHT_PAREN_TOK;
					break;
				case "=":
					tokType = TokenType.ASSIGNMENT_TOK;
					break;
				case "<=":
					tokType = TokenType.LE_TOK;
					break;
				case "<":
					tokType = TokenType.LT_TOK;
					break;
				case ">=":
					tokType = TokenType.GE_TOK;
					break;
				case ">":
					tokType = TokenType.GT_TOK;
					break;
				case "==":
					tokType = TokenType.EQ_TOK;
					break;
				case "!=":
					tokType = TokenType.NE_TOK;
					break;
				case "+":
					tokType = TokenType.ADD_TOK;
					break;
				case "-":
					tokType = TokenType.SUB_TOK;
					break;
				case "*":
					tokType = TokenType.MUL_TOK;
					break;
				case "/":
					tokType = TokenType.DIV_TOK;
					break;
            case ":":
               tokType = TokenType.COLON_TOK;
            case "\n":
               tokType = TokenType.EOL_TOK;
				default:
					throw new LexException ("invalid lexeme", lineNum, columnNum);
			}
		return tokType;
	}

	private String getLexeme(String line, int index, int lineNum)
	{
		assert line != null;
		assert index >= 0;
		assert lineNum > 0;
		int i = index;
		while (i < line.length() && !Character.isWhitespace(line.charAt(i)))
			i++;
		return line.substring(index, i);
	}

	private int skip_white_space(String line, int index)
	{
		assert line != null;
		assert index >= 0;
		while (index < line.length() && Character.isWhitespace(line.charAt(index)))
			index++;
		return index;
	}

		public Token getNextToken()
	{
		if (tokenList.isEmpty())
			throw new RuntimeException ("no more tokens");
		return tokenList.remove(0);
	}

		public Token getLookaheadToken()
	{
		if (tokenList.isEmpty())
			throw new RuntimeException ("no more tokens");
		return tokenList.get(0);
	}
   
   
}

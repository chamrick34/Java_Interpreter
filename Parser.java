import java.io.FileNotFoundException;


public class Parser
{

	private LexicalAnalyzer lex;
	
	public Parser (String fileName) throws FileNotFoundException, LexException
	{
		if (fileName == null)
			throw new IllegalArgumentException ("null file name");
		lex = new LexicalAnalyzer (fileName);
	}
	
	public Program parse() throws ParserException
	{
		Token tok = lex.getNextToken();
		match (tok, TokenType.PROGRAM_TOK);
		tok = lex.getNextToken();
		match (tok, TokenType.MAIN_TOK);
		tok = lex.getNextToken();
		match (tok, TokenType.EOL_TOK);
      StatementBlock sBlock = getStatementBlock();
		return new Program (sBlock);
	}


   /**
   mark column number of beginning of the statement block - 
   look at remaining tokens, and as long as they aren't on the same column (aka same indentation),
   we add them to the statement block. So everything between two tokens with the same columnNum are in the same statement block
   **/
   private StatementBlock getStatementBlock() throws ParserException
   {
      StatementBlock sBlock = new StatementBlock();
      StatementList sList = getStatementList();
      sBlock.add(sList);
      Token tok = lex.getLookaheadToken();
      int indent = tok.getColumnNum();
      tok = lex.getLookaheadToken();
      while (tok.getColumnNum() != indent)
      {
         sList = getStatementList();
         sBlock.add(sList);
         tok = lex.getLookaheadToken();
      }
      return sBlock;
   }
   

	private StatementList getStatementList() throws ParserException
	{
		StatementList sList = new StatementList();
		Statement s = getStatement();
		sList.add (s);
		Token tok = lex.getLookaheadToken();
		while (isValidStartOfStatement (tok))
		{
			s = getStatement();
			sList.add(s);
			tok = lex.getLookaheadToken();
		}
		return sList;
	}

	private boolean isValidStartOfStatement(Token tok)
	{
		assert tok != null;
		return tok.getTokenType() == TokenType.ASSIGNMENT_TOK || tok.getTokenType() == TokenType.WHILE_TOK ||
			tok.getTokenType() == TokenType.IF_TOK || tok.getTokenType() == TokenType.PRINT_TOK;
	}

	private Statement getStatement() throws ParserException
	{
		Statement s;
		Token tok = lex.getLookaheadToken();
		if (tok.getTokenType() == TokenType.IF_TOK)
			s = getIfStatement();
		else if (tok.getTokenType() == TokenType.ASSIGNMENT_TOK)
			s = getAssignmentStatement();
		else if (tok.getTokenType() == TokenType.WHILE_TOK)
			s = getWhileStatement();
		else if (tok.getTokenType() == TokenType.PRINT_TOK)
			s = getPrintStatement();
		else
			throw new ParserException ("statement expected", tok.getRowNum(), tok.getColumnNum());
		return s;
	}

	private Statement getPrintStatement() throws ParserException
	{
		Token tok = lex.getNextToken();
		match (tok, TokenType.PRINT_TOK);
		tok = lex.getNextToken();
		match (tok, TokenType.LEFT_PAREN_TOK);
		Expression expr = getExpression();
		tok = lex.getNextToken();
		match (tok, TokenType.RIGHT_PAREN_TOK);
      tok = lex.getNextToken();
      match (tok, TokenType.EOL_TOK);
		return new PrintStatement (expr);
	}

   private Statement getWhileStatement() throws ParserException
   {
      Token tok = lex.getNextToken();
      match (tok, TokenType.WHILE_TOK);
      BooleanExpression expr = getBooleanExpression();
      tok = lex.getNextToken();
      match (tok, TokenType.COLON_TOK);
      tok = lex.getNextToken();
      match (tok, TokenType.EOL_TOK);
      StatementBlock sBlock = getStatementBlock();
      return new WhileStatement(expr, sBlock);
   }

	private Statement getAssignmentStatement() throws ParserException
	{
		Token tok = lex.getNextToken();
		match (tok, TokenType.ID_TOK);
		char ch = tok.getLexeme().charAt(0);
		VariableExpression var = new VariableExpression (ch);
		tok = lex.getNextToken();
		match (tok, TokenType.ASSIGNMENT_TOK);
		Expression expr = getExpression();
      tok = lex.getNextToken();
      match (tok, TokenType.EOL_TOK);
		return new AssignmentStatement (var, expr);
	}

	private Statement getIfStatement() throws ParserException
	{
		Token tok = lex.getNextToken();
		match (tok, TokenType.IF_TOK);
      BooleanExpression expr = getBooleanExpression();
		tok = lex.getNextToken();
		match (tok, TokenType.COLON_TOK);
		tok = lex.getNextToken();
		match (tok, TokenType.EOL_TOK);
		StatementBlock sBlock1 = getStatementBlock();
		tok = lex.getLookaheadToken();
		if (tok.getTokenType() == TokenType.ELSE_TOK)
      {
         tok = lex.getNextToken();
         StatementBlock sBlock2 = getStatementBlock();
		   return new IfStatement(expr, sBlock1, sBlock2);
      }
      if (tok.getTokenType()== TokenType.ELIF_TOK)
      {
         tok = lex.getNextToken();
         BooleanExpression expr1 = getBooleanExpression();
         StatementBlock sBlock2 = getStatementBlock();
         return new IfStatement(expr, sBlock1, expr1, sBlock2);
      }
      else
         return new IfStatement(expr, sBlock1);
	}

	private BooleanExpression getBooleanExpression() throws ParserException
	{
		BooleanExpression.RelativeOperator op = getRelativeOperator();
		Expression expr1 = getExpression();
		Expression expr2 = getExpression();
		return new BooleanExpression (op, expr1, expr2);
	}

	private BooleanExpression.RelativeOperator getRelativeOperator() throws ParserException
	{
		BooleanExpression.RelativeOperator op;
		Token tok = lex.getNextToken();
		switch (tok.getTokenType())
		{
			case LT_TOK:
				op = BooleanExpression.RelativeOperator.LT;
				break;
			case LE_TOK:
				op = BooleanExpression.RelativeOperator.LE;
				break;
			case GT_TOK:
				op = BooleanExpression.RelativeOperator.GT;
				break;
			case GE_TOK:
				op = BooleanExpression.RelativeOperator.GE;
				break;
			case EQ_TOK:
				op = BooleanExpression.RelativeOperator.EQ;
				break;
			case NE_TOK:
				op = BooleanExpression.RelativeOperator.NE;
				break;
			default:
				throw new ParserException ("relational operator expected", tok.getRowNum(),
					tok.getColumnNum());
		}
		return op;
	}

	private Expression getExpression() throws ParserException
	{
		Expression expr;
		Token tok = lex.getLookaheadToken();
		if (tok.getTokenType() == TokenType.ID_TOK)
		{
			tok = lex.getNextToken();		
			expr = new VariableExpression (tok.getLexeme().charAt(0));
		}
		else if (tok.getTokenType() == TokenType.INT_TOK)
		{
			tok = lex.getNextToken();
			expr = new ConstantExpression (Integer.parseInt(tok.getLexeme()));
		}
		else
		{
			ArithmeticExpression.ArithmeticOperator op = getArithmeticOperator();
			Expression expr1 = getExpression();
			Expression expr2 = getExpression();
			expr = new ArithmeticExpression(op, expr1, expr2);
		}
		return expr;
	}

	private ArithmeticExpression.ArithmeticOperator getArithmeticOperator() throws ParserException
	{
		ArithmeticExpression.ArithmeticOperator op;
		Token tok = lex.getNextToken();
		switch (tok.getTokenType())
		{
			case ADD_TOK:
				op = ArithmeticExpression.ArithmeticOperator.ADD_OP;
				break;
			case SUB_TOK:
				op = ArithmeticExpression.ArithmeticOperator.SUB_OP;
				break;
			case MUL_TOK:
				op = ArithmeticExpression.ArithmeticOperator.MUL_OP;
				break;
			case DIV_TOK:
				op = ArithmeticExpression.ArithmeticOperator.DIV_OP;
				break;
			default:
				throw new ParserException ("arithmetic operator expected", tok.getRowNum(),
					tok.getColumnNum());
		}
		return op;
	}

	private void match(Token tok, TokenType expected) throws ParserException
	{
		assert tok != null;
		if (tok.getTokenType() != expected)
			throw new ParserException (expected.toString() + " expected", tok.getRowNum(),
				tok.getColumnNum());		
	}
}

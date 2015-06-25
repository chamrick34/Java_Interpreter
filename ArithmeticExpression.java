
public class ArithmeticExpression implements Expression
{

	enum ArithmeticOperator {ADD_OP, SUB_OP, MUL_OP, DIV_OP}

	private ArithmeticOperator operator;
	
	private Expression expr1, expr2;

	public ArithmeticExpression(ArithmeticOperator operator, Expression expr1,
			Expression expr2)
	{
		if (expr1 == null || expr2 == null)
			throw new IllegalArgumentException ("null expression");
		this.operator = operator;
		this.expr1 = expr1;
		this.expr2 = expr2;
	}

	@Override
	public int evaluate()
	{
		int value = 0;
		switch (operator)
		{
			case ADD_OP:
				value = expr1.evaluate() + expr2.evaluate();
				break;
			case SUB_OP:
				value = expr1.evaluate() - expr2.evaluate();
				break;
			case MUL_OP:
				value = expr1.evaluate() * expr2.evaluate();
				break;
			case DIV_OP:
				value = expr1.evaluate() / expr2.evaluate();
				break;
		}
		return value;
	};
	
}

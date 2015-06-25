
public class BooleanExpression
{

	enum RelativeOperator {LE, LT, GE, GT, EQ, NE}
	
	private RelativeOperator operator;
	
	private Expression expr1, expr2;

	public BooleanExpression(RelativeOperator operator, Expression expr1,
			Expression expr2)
	{
		if (expr1 == null || expr1 == null)
			throw new IllegalArgumentException ("null expression");
		this.operator = operator;
		this.expr1 = expr1;
		this.expr2 = expr2;
	}
	
	public boolean evaluate()
	{
		boolean value = false;
		switch (operator)
		{
			case LE:
				value = expr1.evaluate() <= expr2.evaluate();
				break;
			case LT:
				value = expr1.evaluate() < expr2.evaluate();
				break;
			case GE:
				value = expr1.evaluate() >= expr2.evaluate();
				break;
			case GT:
				value = expr1.evaluate() > expr2.evaluate();
				break;
			case EQ:
				value = expr1.evaluate() == expr2.evaluate();
				break;
			case NE:
				value = expr1.evaluate() != expr2.evaluate();
				break;
		}
		return value;
	}
}

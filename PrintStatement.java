
public class PrintStatement implements Statement
{

	private Expression expr;
	
	public PrintStatement(Expression expr)
	{
		if (expr == null)
			throw new IllegalArgumentException ("null expression");
		this.expr = expr;
	}

	@Override
	public void execute()
	{
		System.out.println(expr.evaluate());
	}

}

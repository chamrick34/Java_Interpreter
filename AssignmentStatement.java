
public class AssignmentStatement implements Statement
{

	private VariableExpression var;
	
	private Expression expr;
	
	public AssignmentStatement(VariableExpression var, Expression expr)
	{
		if (expr == null)
			throw new IllegalArgumentException ("invalid expression");
		this.expr = expr;
		if (var == null)
			throw new IllegalArgumentException ("invalid variable");
		this.var = var;
	}

	@Override
	public void execute()
	{
		var.setValue(expr.evaluate());
	}

}

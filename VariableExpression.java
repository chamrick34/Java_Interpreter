
public class VariableExpression implements Expression
{

	private char var;
	
	public VariableExpression(char var)
	{
		this.var = var;
	}

	@Override
	public int evaluate()
	{
		return Memory.fetch(var);
	}
	
	public void setValue (int value)
	{
		Memory.store(var, value);
	}

}

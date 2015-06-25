
public class Program
{

	private StatementBlock sBlock;
	

	public Program(StatementBlock sBlock)
	{
		if (sBlock == null)
			throw new IllegalArgumentException ("null statement list argument");
		this.sBlock = sBlock;
	}
	
	
	public void execute ()
	{
		sBlock.execute();
	}

}

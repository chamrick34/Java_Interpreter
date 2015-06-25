
public class IfStatement implements Statement
{

	private BooleanExpression expr1, expr2;
	
	private StatementBlock sBlock1, sBlock2;
	
   /**
   Three overloaded constructors: 1st handles only if conditions
   2nd handles if-else conditions
   3rd handles elif conditions
   **/
	public IfStatement(BooleanExpression expr1, StatementBlock sBlock1) 
	{
		if(expr1 == null)
			throw new IllegalArgumentException("Null Expression");
		if(sBlock1 == null)
			throw new IllegalArgumentException("null exception");
		this.expr1 = expr1;
		this.sBlock1 = sBlock1;
	}
   
   public IfStatement(BooleanExpression expr1, StatementBlock sBlock1, StatementBlock sBlock2)
   {
      if(expr1 == null)
         throw new IllegalArgumentException("null expression");
      if(sBlock1 == null)
         throw new IllegalArgumentException("null exception");
      this.expr1 = expr1;
      this.sBlock1 = sBlock1;
      this.sBlock2 = sBlock2;
   }
   
    public IfStatement(BooleanExpression expr1, StatementBlock sBlock1, BooleanExpression expr2, StatementBlock sBlock2)
   {
      if(expr1 == null)
         throw new IllegalArgumentException("null expression");
      if(sBlock1 == null || sBlock2 == null)
         throw new IllegalArgumentException("null exception");
      this.expr1 = expr1;
      this.expr2 = expr2;
      this.sBlock1 = sBlock1;
      this.sBlock2 = sBlock2;
   }

   /**
   if first constructor is used (aka no second statement block specified), evaluate just the 'if' condition with specified statement block.
   if second constructor is used (aka two statement blocks but one boolean expression), evaulate with 'if-else' logic.
   if third constructor is used (aka two statement blocks and two expressions specified), evaluate with 'elif' logic.
   **/
   
   @Override
   public void execute()
   {
      if (sBlock2 == null)
      {
         if (expr1.evaluate())
            sBlock1.execute();
      }
      if (expr2 == null)
      {
         if (expr1.evaluate())
            sBlock1.execute();
         else{
            sBlock2.execute();}
      }
      else
      {
         if (expr1.evaluate())
            sBlock1.execute();
         else
            if (expr2.evaluate())
               sBlock2.execute();
      }
   }


}

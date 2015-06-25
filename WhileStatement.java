public class WhileStatement implements Statement
{
   private BooleanExpression expr;
   
   private StatementBlock sBlock;
   
   public WhileStatement(BooleanExpression expr, StatementBlock sBlock)
   {
      if (expr == null)
         throw new IllegalArgumentException("Null Expression");
      if (sBlock == null) 
         throw new IllegalArgumentException("Null Exception");
      this.expr = expr;
      this.sBlock = sBlock;
   }
   
   @Override
   public void execute()
   {
      while (expr.evaluate() == true)
      {
         sBlock.execute();
      }
   }
}
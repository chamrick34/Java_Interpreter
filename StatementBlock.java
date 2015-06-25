import java.util.ArrayList;
import java.util.List;

public class StatementBlock
{
   
   private List<StatementList> stmtlist;
   
   public StatementBlock()
   {
      stmtlist = new ArrayList<StatementList>();
   }
   
   public void add(StatementList s)
   {
      stmtlist.add(s);
   }
   
   public void execute()
   {
      for (int i = 0; i < stmtlist.size(); i++)
			stmtlist.get(i).execute();
   }
}
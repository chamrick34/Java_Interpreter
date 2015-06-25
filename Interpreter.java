import java.io.FileNotFoundException;


public class Interpreter
{

	public static void main(String[] args)
	{
		try
		{
			Parser parser = new Parser("test1.txt");
			Program prog = parser.parse();
			prog.execute();
		}
		catch (FileNotFoundException e)
		{
			System.out.println ("file not found");
			e.printStackTrace();
		}
      
		catch (ParserException e)
		{
			System.out.println (e);
			e.printStackTrace();
		}
      catch (LexException e)
		{
			System.out.println (e);
			e.printStackTrace();
		}

		catch (Exception e)
		{
			System.out.println ("unknown error occurred");
		}
	}

}


public class Memory
{

	private static int[] mem = new int[26];
	
	public static void store (char var, int value)
	{
		if (!Character.isLetter(var))
			throw new RuntimeException ("invalid memory access");
		if (Character.isUpperCase(var))
			var = Character.toLowerCase(var);
		mem[var - 'a'] = value;
	}
	
	public static int fetch (char var)
	{
		if (!Character.isLetter(var))
			throw new RuntimeException ("invalid memory access");
		if (Character.isUpperCase(var))
			var = Character.toLowerCase(var);
		return mem[var - 'a'];
		
	}
}

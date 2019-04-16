import java.util.*;

public class mod{
		public static void main(String args[])
		{	
			System.out.print(modulo(2,4));
		}
		public static int modulo(int num, int div)
		{
			return num - (num/div)*div;
		}
	}

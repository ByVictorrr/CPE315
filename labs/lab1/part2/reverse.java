import java.util.*;

public class reverse{

	public static void main(String []args){
		//System.out.println(reverseBits(1));
		System.out.println(reverseBits_test(3));
	}
	public static int reverseBits(int num)
	{
			int  revB = 0;
			int pos ; //bit size -1 ;
			for (pos=7; pos>0; pos--)
			{
				revB=revB+(num & 1)<< pos; //first mask num in lsb, then shift that bit to left by pos bits 
				num = num >> 1; // then get the next bit in lsb and repeat at pos= pos-1 
			}
			return revB;
	}

	public static int reverseBits_test(int num)
	{
			int  revB = 0;
			int pos ; //bit size -1 ;
			int mask;
			for (pos=7; pos>0; pos--)
			{
				mask = num & 1;
				for (int i = 0; i< pos; i++)
					mask = mask << 1; //first mask num in lsb, then shift that bit to left by pos bits 

				revB = revB + mask;

				num = num >> 1; // then get the next bit in lsb and repeat at pos= pos-1 

			}
			return revB;
	}




}

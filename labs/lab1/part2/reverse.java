import java.util.*;

public class reverse{

	public static void main(String []args){
		System.out.print(reverseBits(26));
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

}

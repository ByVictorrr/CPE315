import java.util.*; public class exponet{ 
	public static void main(String []args){
		System.out.println("8^0=" + pow(8,0));
		System.out.println("8^1=" + pow(8,1));
		System.out.println("3^8=" + pow(3,8));
		System.out.println("10^10=" + pow(10,10));
		System.out.println(Math.pow(10,10));
	}
	public static int pow (int x,int y ) {
		// x*z = x+x+...+x_ztimes
		// x^y = x*x*......x_ytimes
		int prod=0;
	    int	exp = 0;
	//
		if(y==0) 
			return 1;
		else if (x==0)
			return 0;
		else if (y==1)
			return x;
		else{
			while (y>1){ 
			for(int i = 0; i<x; i++) //this is x*x
			{ 
				prod += x;
			}
				y--;
			}
		}
		return prod;
	}

}

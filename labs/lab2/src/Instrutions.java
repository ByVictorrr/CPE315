import java.util.*;

public class Instruction{

	public Map<String,String> opcode = new HashMap()<>; // this can give infomation about type of insruction
	public Parser p = new Parser();



	public void initalizeMap()
	{
		List <String> opNeumonics = Arrays.asList(
										"and", 
										"or", 
										"add", 
										"addi", 
										"sll", 
										"sub", 
										"slt", 
										"beq", 
										"bne", 
										"lw", 
										"sw", 
										"j", 
										"jr",
										"jal");
		
		List <String> opMachine = Arrays.asList(
												"100100", 
												"100101", 
												"100000", 
												"001000", 
												"000000", 
												"100010", 
												"101010", 
												"000100", 
												"000101", 
												"100011", 
												"101011", 
												"000010", 
												"001000", 
												"000011");
		for (int i  = 0 ; i< opNeumonics.size(); i++)
		{
			opcode.put(opNeumonics.get(i),opMachine.get(i));
		}
	}


/*getFormat:  reads in a string s that instruction text
 * returns 0 - if it decides that string is  register
 * returns 1 - if it decides that string is immediate*
 * returns 2 - if it decides that string is jump 
 */
	public int getFormat(String s)
	{
		//To see if you opcode s is a register type
		if (opcode.get(s) == opcode.get(0) || //add
			opcode.get(s) == opcode.get(1) || //or 
			opcode.get(s) == opcode.get(2) || //and
			opcode.get(s) == opcode.get(5) || //sub
			opcode.get(s) == opcode.get(6) || //slt
			opcode.get(s) == opcode.get(12)) //jr
				return 0;
		//TO see if opcode is immediate type
		else if(opcode.get(s) == opcode.get(7) || //beq
				opcode.get(s) == opcode.get(8) || //bne
				opcode.get(s) == opcode.get(3) || //addi
				opcode.get(s) == opcode.get(9) || //lw
				opcode.get(s) == opcode.get(10)) //sw
				return 1;
		else if(opcode.get(s) == opcode.get(11) || //j
				opcode.get(s) == opcode.get(13)) //jal
				return 2;

		return -1; //if not in thi isa
}


	}

}

public class RInstruction extends Instruction{
}
public class IInstruction extends Instruction{
}
public class JInstruction extends Instruction{

}
public class Format{
	

}

import java.util.*;

public class Instruction{

	public Map<String,String> opcode = new HashMap<>(); // this can give infomation about type of insruction
	public Parser p = new Parser();



	public void initalizeMap()
	{
		List <String> opMnemonic = Arrays.asList(
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
		for (int i  = 0 ; i< opMachine.size(); i++)
		{
			opcode.put(opMnemonic.get(i),opMachine.get(i));
			opcode.forEach((k,v) -> System.out.println(k + ":"+ v));
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
		if (opcode.get(s).equals(opcode.get("add")) || //add
			opcode.get(s).equals(opcode.get("or")) || //or
			opcode.get(s).equals(opcode.get("and")) || //and
			opcode.get(s).equals(opcode.get("sub")) || //sub
			opcode.get(s).equals(opcode.get("slt")) || //slt
			opcode.get(s).equals(opcode.get("jr"))) //jr
				return 0;
		//TO see if opcode is immediate type
		else if(opcode.get(s).equals(opcode.get("beq")) || //beq
				opcode.get(s).equals(opcode.get("bne")) || //bne
				opcode.get(s).equals(opcode.get("addi")) || //addi
				opcode.get(s).equals(opcode.get("lwk")) || //lw
				opcode.get(s).equals(opcode.get("sw"))) //sw
				return 1;
		else if(opcode.get(s).equals(opcode.get("j")) || //j
				opcode.get(s).equals(opcode.get("jal"))) //jal
				return 2;

		return -1; //if not in thi isa
}

}



import java.util.*;
import java.util.stream.Stream;

public class typeInstruction{

	public Parser p = new Parser();

	public static Map<String, String> opMap;
	static {
		opMap= new HashMap<>();
		opMap.put("and", "100100");
		opMap.put("or", "100101");
		opMap.put("add", "100000");
		opMap.put("addi", "001000");
		opMap.put("sll", "000000");
		opMap.put("sub", "100010");
		opMap.put("slt", "101010");
		opMap.put("beq", "000100");
		opMap.put("bne", "000101");
		opMap.put("lw", "100011");
		opMap.put("sw", "101011");
		opMap.put("j", "000010");
		opMap.put("jr", "001000");
		opMap.put("jal", "000011");
	};


/*getFormat:  reads in a string s that instruction text
 * returns 0 - if it decides that string is  register
 * returns 1 - if it decides that string is immediate*
 * returns 2 - if it decides that string is jump 
 */
	public static int getFormat(String opCode)
	{
		//To see if you opMap s is a register type
		if (opMap.get(opCode).equals(opMap.get("add")) || //add
			opMap.get(opCode).equals(opMap.get("or")) || //or
			opMap.get(opCode).equals(opMap.get("and")) || //and
			opMap.get(opCode).equals(opMap.get("sub")) || //sub
			opMap.get(opCode).equals(opMap.get("slt")) || //slt
			opMap.get(opCode).equals(opMap.get("jr"))) //jr
				return 0;
		//TO see if opMap is immediate type
		else if(opMap.get(opCode).equals(opMap.get("beq")) || //beq
				opMap.get(opCode).equals(opMap.get("bne")) || //bne
				opMap.get(opCode).equals(opMap.get("addi")) || //addi
				opMap.get(opCode).equals(opMap.get("lwk")) || //lw
				opMap.get(opCode).equals(opMap.get("sw"))) //sw
				return 1;
		else if(opMap.get(opCode).equals(opMap.get("j")) || //j
				opMap.get(opCode).equals(opMap.get("jal"))) //jal
				return 2;

		return -1; //if not in thi isa
}

}



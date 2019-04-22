import java.util.*;
import java.util.stream.Stream;

public class typeInstruction{


	public Parser p = new Parser();

	public static Map<String, String> opMap = new HashMap() {{
						put("and", "100100");
						put("or",  "100101");
						put("add", "100000");
						put("addi", "001000");
						put("sll", "000000");
						put("sub", "100010");
						put("slt", "101010");
						put("beq", "000100");
						put("bne", "000101");
						put("lw",  "100011");
						put("sw",  "101011");
						put("j",   "000010");
						put("jr",  "001000");
						put("jal", "000011");
	}};


/*getFormat:  reads in a string s that instruction text
 * returns 0 - if it decides that string is  register
 * returns 1 - if it decides that string is immediate*
 * returns 2 - if it decides that string is jump 
 */
	public static int getFormat(String s)
	{
		//To see if you opMap s is a register type
		if (opMap.get(s).equals(opMap.get("add")) || //add
			opMap.get(s).equals(opMap.get("or")) || //or
			opMap.get(s).equals(opMap.get("and")) || //and
			opMap.get(s).equals(opMap.get("sub")) || //sub
			opMap.get(s).equals(opMap.get("slt")) || //slt
			opMap.get(s).equals(opMap.get("jr"))) //jr
				return 0;
		//TO see if opMap is immediate type
		else if(opMap.get(s).equals(opMap.get("beq")) || //beq
				opMap.get(s).equals(opMap.get("bne")) || //bne
				opMap.get(s).equals(opMap.get("addi")) || //addi
				opMap.get(s).equals(opMap.get("lwk")) || //lw
				opMap.get(s).equals(opMap.get("sw"))) //sw
				return 1;
		else if(opMap.get(s).equals(opMap.get("j")) || //j
				opMap.get(s).equals(opMap.get("jal"))) //jal
				return 2;

		return -1; //if not in thi isa
}

}





public class Instruction{
	public Map<Sting,Interger> opcode = new HashMap()<>; // this can give infomation about type of insruction 
	public Parser p = new Parser();



	public void initalizeMap()
	{
		List <String> ops = new ArrayList("and", "or", "add", "addi", "sll", "sub", "slt", "beq", "bne", "lw", "sw", "j", "jr", "jal");
		List <Interger> opsBin = 
	}

	public int getFormat(String s)
	{
		s = p.lines
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

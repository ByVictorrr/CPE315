import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Pattern;


public class driver {

	private static Parser parse = new Parser();

	public static void main(String[] args) {

		parse.filterCommentsWhites(args);
		parse.lines.forEach(System.out::println);

		parse.labelMap = parse.getLabel(100);
		parse.labelMap.forEach((k, v) -> System.out.println(k + ":" + v));

		System.out.println('\n');

		parse.getInst().forEach(s -> System.out.println(s));
//////////////TESST part 2 creating opcode map\\\\\\\\\\\\\


		System.out.println("0 - regType, 1-immediate, 2 - jump type");
		//System.out.println(in.getFormat(parse.lines.get(0)));

		Registers r = new Registers();
		r.regMap.forEach((k, v) -> System.out.println(k + " :" + v));

/*
		System.out.println(getOp(parse.lines.get(0))); //test to see if it gets rs
		System.out.println(getRd(parse.lines.get(0))); //test to see if it gets rs
		System.out.println(getRs(parse.lines.get(0))); //test to see if it gets rs
		System.out.println(getRt(parse.lines.get(0))); //test to see if it gets rs
		System.out.println(parse.lines.get(0)); //test to see if it gets rs
		*/
	}

/*
	public List<Instruction> getInstructions(List<String> unFilteredInst) {

		List<Instruction> instructions = new ArrayList<Instruction>();

		for (int i = 0; i < unFilteredInst.size(); i++) { //Checking the formmaat of each instruction

			String op = unFilteredInst.get(i).split(",")[0]; //get addi or add opcdoe
			System.out.println(op);	 //test if get
			if (typeInstruction.getFormat(typeInstruction.opcode.get(unFilteredInst.get(i))) == 0) //to see what type is the passed in line - list of instructions
			{
			//something like this	instructions[i] =(instructions)RegInstr;
				//Step 1: get rs - getRs(String line)
				//Step 2: get rd - getRd(String line)
				//Step 3: get rt - getRt(String line)
				//Step 4: get shamt - getShamt(String line)
				//Step 5: get funct - getFunct(String line)



				//It would be easier to parse for each field if we alter each time that Field and pass it on to next get func
				instructions.add(new RegInstr(
						op, //opcode
						getRs(), //rs
						getRt(), //rt
						getRd(), //rd
						getShamt(), //sahmt
						getFunct() //funct
						);
				// it is reg type so it has the same fields
			}
			else  if (typeInstruction.getFormat(typeInstruction.opcode.get(unFilteredInst.get(i))) == 1)
			{
			//immediate instruction
			//something like this	instructions[i] =(instructions)ImmedInstr;
				//Step 1: get rs - getRs(String line)
				//Step 2: get rt - getRt(String line)
				//Step 3: get Immed - getImmed(String line);

				instructions.add(new ImmedInstr(
						op, //opcode
						getRs(), //rs
						getRt(), //rt
						getImmed()
						);
			}
			else
			{
				//jump instruction
			//something like this	instructions[i] =(instructions)jumpInstruction
				//Step 1 - get target address either label or actual numbers

				instructions.add(new JumpInstr(
						op, //opcode
						getAddr()
						);

			}
		}
		return instructions;
		}

*/


}


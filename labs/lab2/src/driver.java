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

	}


/*
	public List<Instruction> getInstructions(List<String> unFilteredInst) {

		List<Instruction> instructions = new ArrayList<Instruction>();

		for (int i = 0; i < unFilteredInst.size(); i++) { //Checking the formmaat of each instruction

			if (typeInstruction.getFormat(typeInstruction.opcode.get(unFilteredInst.get(i))) == 0) //to see what type is the passed in line - list of instructions
			{
			//something like this	instructions[i] =(instructions)RegInstr;
				instructions.add(new RegInstr(P));
				// it is reg type so it has the same fields
			}
			else  if (typeInstruction.getFormat(typeInstruction.opcode.get(unFilteredInst.get(i))) == 1)
			{
				instructions.add(new ImmedInstr());
			//immediate instruction

			}
			else
			{
				instructions.add(new JumpInstr());
				//jump instruction
			}
		}
		return instructions;
		}

		*/
}


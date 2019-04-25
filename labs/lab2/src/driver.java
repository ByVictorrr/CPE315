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

		//==============TEST1 - initalize maps ( get two maps - instruction and label map (neumoneic -> address))================\\
		parse.setMaps(args, 100);
		parse.instructMap = parse.getInstrMap();
		parse.labelMap = parse.getLabelMap();

		parse.labelMap.forEach((k, v) -> System.out.println("label map " + k + ":" + v + "\n"));
		parse.instructMap.forEach((k, v) -> System.out.println("instruction map " + k + ":" + v + "\n"));


		//==============TEST2 -  get the format of each instruction ================\\
		//Step 2.1 - pass a list of instructions into a getFormat - which just detemines the type of instruction based on the opcode
		List<Instruction> iBinary;
		//Step 2.2 - et a list of instruction extracted from the map
		List <String> instructions = parse.instructMap.keySet().stream().collect(Collectors.toList());
		//Step 2.3 - iterate throught the list check if each elements type, then get fields and then create object put it into the iBinary fields
			for (String instruction : instructions)
			//2.1.1 - check if its a reg type
			if (typeInstruction.getFormat(instruction))
			{


			}
			//2.1.2 - check if its a immed


		}



	}


}







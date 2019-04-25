
import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toMap;


public class driver {

	private static Parser parse = new Parser();

	public static void main(String[] args) {

		//==============TEST1 - initalize maps ( get two maps - instruction and label map (neumoneic -> address))================\\
		parse.setMaps(args, 0);
		Parser.instructMap = parse.getInstrMap();
		Parser.labelMap = parse.getLabelMap();

		System.out.println(parse.getLabelMap());
		Parser.labelMap.forEach((k, v) -> System.out.println("label map " + k + ":" + v + "\n"));
		Parser.instructMap.forEach((k, v) -> System.out.println("instruction map:" + k + "->" + v + "\n"));



		//====================================END OF TEST1=======================================================================\\

		//Sort the Map
		Map<String,Integer> sortedInstrMap = Parser.instructMap
        .entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue())
        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));



		//==============TEST2 -  get the format of each instruction ===============================================================\\
		//Step 2.1 - pass a list of instructions into a getFormat - which just detemines the type of instruction based on the opcode
		List<Instruction> binaryInstr = new ArrayList<>();
		//Step 2.3 - iterate throught the list check if each elements type, then get fields and then create object put it into the iBinary fields

			for (Map.Entry<String, Integer> instruction : sortedInstrMap.entrySet())
		{

			    System.out.println("instruction currrently"+instruction.getKey());
				//Get the opcode of the given instructtion
				String opCode = Parser.getOp(instruction.getKey());
				//step 2.3.1 - check if its a reg type
				if (typeInstruction.getFormat(opCode) == 0) { //get part of string before first $

					//Case 1 - could be jr - > rd = 0, rt = 0
					if (opCode.equals("jr")) {


					}
					else {
						//Case 2- anyother reg instruction

						//step 2.3.2 - get the fields of each instruction based on the format
						List<String> fields = Parser.getFields(instruction.getKey(), 0);


						System.out.println("in position size = " + fields.size());

						//Step 2,3.3 - format (opcode rs, rt, rd, shamt, funct)
						binaryInstr.add(new RegInstr(fields.get(0), fields.get(1), fields.get(2), fields.get(3), fields.get(4), fields.get(5)));

					}


				}
				//step 2.3.2 - check if its a immed
				else if (typeInstruction.getFormat(opCode) == 1) {

					//step 2.3.2 - get the fields of each instruction based on the format
					List<String> fields = Parser.getFields(instruction.getKey(), 1);

				//FOR Branch instructions get difference between: 16bit immed value just for brancH =  instruction.getValue() - labelMap.get(instruction.getValue())

					//Step 2,3.3 - format (opcode rs, rt, 16-bit immediate value)
					binaryInstr.add(new ImmedInstr(fields.get(0), fields.get(1), fields.get(2), fields.get(3)));

				}
				//step 2.3.2 - check if its a jump
				else if (typeInstruction.getFormat(opCode) == 2) {

					//step 2.3.2 - get the fields of each instruction based on the format
					List<String> fields = Parser.getFields(instruction.getKey(),2);

					//Step 2,3.3 - format (opcode rs, 26 -bit word address)
					binaryInstr.add(new JumpInstr(fields.get(0), fields.get(1)));

				}
				else{

					System.out.println("wrong type of format");
				}

			}
			System.out.println("Print Out starting rn:");
			for (int i =0; i<binaryInstr.size(); i++)
						System.out.println(binaryInstr.get(i).toString());
			//At this point binaryInstr should be filled up with object of differnt types each having it fields converted
		//====================================END OF TEST2==============================================================\\



		}



	}









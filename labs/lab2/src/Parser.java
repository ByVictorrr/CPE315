/* * Instructions.java * Copyright (C) 2019 victor <victor@TheShell> * * Distributed under terms of the MIT license. */
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Pattern;


public class Parser {
	//=================vars=====================\\
	public List<String> lines = new ArrayList<String>(); //this gives lines of instructions unFiltered
	public Map<String,Integer> labelMap = new HashMap<>(); //gives map Label Name (key) => Address(value)
    public Map<String,Integer> instructMap = new HashMap<>();
	//=================Function=====================\\

	public void filterCommentsWhites(String[] args) {

		Pattern noFrontComments = Pattern.compile("^[^#].*");
		Pattern noBlankLines = Pattern.compile("[^\\s][a-z].*$");

		//After passing these patterns its possible for therre to be # after an instruction
		try (Stream<String> stream = Files.lines(Paths.get(args[0]))) {
			//=============filter front of line comments and blank lines=====================\\
			lines = stream
					.filter(noFrontComments.asPredicate()) //get all lines that dont start with
					.filter(noBlankLines.asPredicate()) //get all lines that dont start with
					.collect(Collectors.toList());
			//=================================================================================\\
		} catch (IOException e) {
			e.getSuppressed();
		}

		//================Filters out the comments after instructions\ labels==========\\
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).contains("#")) {
				lines.set(i, lines.get(i).substring(0, lines.get(i).indexOf("#")));
			}
		}
		//====================================================\\


		//=========================Remove white spaces before in the string=========\\
		for (int i = 0; i < lines.size(); i++) {
				lines.set(i,lines.get(i).trim());
		}
		//========================================================================\\\
	}

	public List<String> getInst(List<String>line){

		Pattern noBlankLines = Pattern.compile("[^\\s][a-z].*$");
//===========================Filter out  just instruction============\\
		for (int i = 0; i < line.size(); i++) {
			if (line.get(i).contains(":") || line.get(i).contains(": ") || line.get(i).contains(" :")) {
				line.set(i, line.get(i).substring(line.get(i).indexOf(":"), line.get(i).length()));
				line.set(i,line.get(i).replaceAll("^:\\s?",""));
			}
		}
		line = line.stream().filter(noBlankLines.asPredicate()).collect(Collectors.toList());
		return line;
	}

	public Map<String,Integer> getInstrMap() {return instructMap; }
	public Map<String,Integer> getLabelMap() {return labelMap; }

	public void setMaps(String [] args, Integer BaseAddress) { //get Labels and filter out the
		//1. pass throught filtering out lines
		filterCommentsWhites(args);

		Pattern LabelFormat = Pattern.compile("^[0-9a-zA-Z]+\\s?:");
		//get filtered String that correcponds to labels put them in a list

		//2.Filter out labels
		List<String> labels = lines.stream()
				.filter(LabelFormat.asPredicate())
				.collect(Collectors.toList());


		//this is give a test to see if we can incrment the address (if label is on the same line)
  		  Pattern labelFollowedByInst = Pattern.compile("^[0-9a-zA-z]+\\s?:\\s+?[0-9a-zA-z]+$");
  		  Pattern instrMatch = Pattern.compile("^[0-9a-zA-z]+\\s?[^:]+$");
		//===================get address of corrersponding address of labels===================\\
		int Address = BaseAddress;

		List<Integer> AddrListLabel = new ArrayList<Integer>();
		List<Integer> AddrListInstr = new ArrayList<>();
		//This only test is labels are on a new line after

		//Step 3: get address numbers
		for (int i = 0; i < lines.size(); i++) {

			//Label Followed by a instruction
			if (labelFollowedByInst.matcher(lines.get(i)).find())//count each time a non label is found
			{
				if(i==0)
					AddrListLabel.add(Address);
				else {
					Address++;
					AddrListLabel.add(Address);
					AddrListInstr.add(Address);
					System.out.println(Address);
				}

			}
			//just instruction found
			else if(instrMatch.matcher(lines.get(i)).find()) {
				AddrListInstr.add(Address);
				Address++;
			}
			//else label found
			else { //if it is a label store that address in it
			if(i == lines.size() -1) //if the last run is an incrutio
				Address++;
					AddrListLabel.add(Address);
			}
		}

		//===========================Filter out inline instructions with labels============\\
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).contains("#")) {
				lines.set(i, lines.get(i).substring(0, lines.get(i).indexOf("#")));
			}

		}
		AddrListLabel.stream().forEach(s->System.out.println(s));
		//=======================put label, and corresonding addr in map=================================\\
		for (int i = 0; i < labels.size(); i++) { //get key (label name ) -> value (address)//
			labels.set(i, labels.get(i).substring(0, labels.get(i).indexOf(":")));
			labelMap.put(labels.get(i), AddrListLabel.get(i));

		}
		//=======================put label, and corresonding addr in map=================================\\
		//4. filter out instruction
		lines = getInst(lines);

		for (int i = 0; i < lines.size(); i++) { //get key (label name ) -> value (address)//
			instructMap.put(lines.get(i), AddrListInstr.get(i));
		}//3. filter out instruction
		lines = getInst(lines);


		//5. sort maps by address




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
						new HashMap<String, String>().put(Parser.getRs(Parser.lines),.)

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

	//==================================get Field functions =======================================================\\
	//NEED TO FIX reading parsing up to next $ for regs ALSO PUT each of these function in the instruction Type class
// getOp can go in Instruction class because everyinstruction has a opcode
//When creating a new object of sub instruction pass line -> when passed parse it to each variable
//Format for Reg: op rd, rs, rt


//Returns a list of the fields in binary : corresponding to the type
	public static List<String> getFields(String inst, Type type)
	{
		List<String> binaryFields = new ArrayList<>();
		List<String> nmeumonicFields = new ArrayList<>();

		if (type instanceof RegInstr)
		{
			//step 1: parse into neumonic fields (getRs().....getShamt())
			//Step 1.1 - get opcode
			nmeumonicFields.add(getOp(inst));
			//Step 1.2 - get Rs
			nmeumonicFields.add(getRs(inst));
			//Step 1.3 - get Rt
			nmeumonicFields.add(getRt(inst));
			//Step 1.4 - get rd
			nmeumonicFields.add(getRd(inst));
			//Step 1.5 - get shamt
			nmeumonicFields.add(getShamt(inst));
			//Step 1.5 - get function
			nmeumonicFields.add(getFunct(inst));

			//Step 2: translate nuemonic fields -> binary fields

			//Step 2.1 - map opcode nmeuonic -> binary version
			binaryFields.add(typeInstruction.opMap.get(nmeumonicFields.get(0)));
			//Step 2.2 - map rs nmeuonic -> binary version
			binaryFields.add(Registers.regMap.get(nmeumonicFields.get(1)));
			//Step 2.3 - map rt nmeuonic -> binary version
			binaryFields.add(Registers.regMap.get(nmeumonicFields.get(2)));
			//Step 2.4 - map rd nmeuonic -> binary version
			binaryFields.add(Registers.regMap.get(nmeumonicFields.get(3)));

	 		//Step 2.2 - map shamt nmeuonic -> binary version
			binaryFields.add(decStringToBinary(nmeumonicFields.get(4)));

			//Step 2.3 - map funct nmeuonic -> binary version
			binaryFields.add(decStringToBinary(nmeumonicFields.get(5)));


			//step 3: return binary fields
			return binaryFields;
		}
		else if (type instanceof ImmedInstr)
		{
			///step 1: parse into neumonic fields (getRs().....getShamt())
			//step 2: map nemuics fields into binary fields
			//step 3: return binary fields
		}
		else if (type instanceof JumpInstr)
		{
			//step 1: parse into neumonic fields (getRs().....getShamt())
			//step 2: map nemuics fields into binary fields
			//step 3: return binary fields
		}
		return binaryFields;
	}

	public static String getOp(String line)
	{
		return line.split("\\s")[0];
	}		//==============TEST1 - initalize maps (instruction and label map)================\\

	public static String getRd(String line)
	{
		String Rd = line.split(",")[0];
		return Rd.split("\\s")	[1];
	}


	public static String getRs(String line)
	{
		String Rt = line.split(",")[1];
		Rt = Rt.split(",")[0];

		if (Rt.contains("^\\s+")){
			Rt = Rt.replaceAll("^\\s+","");
		}
		return Rt;

	}
	public static String getRt(String line) {
		String Rt = line.split(",")[2];
		return Rt;
	}


	public static String getShamt(String line)
	{
		String Rt = line.split(",")[2];
		return Rt;
	}
	public static String getFunct(String line) {
		String Rt = line.split(",")[2];
		return Rt;
	}
	/*
	public String getAddrImmed(String line)
	{
	}
	public String getTargetAddr(String line)
	{

	}
	*/
	//Converts a num given in string format to binary string
	public static String decStringToBinary(String dec)
	{
		return Integer.toBinaryString(Integer.parseInt(dec));
	}
}


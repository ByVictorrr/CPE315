/* * Instructions.java * Copyright (C) 2019 victor <victor@TheShell> * * Distributed under terms of the MIT license. */
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Pattern;


public class Parser {
	//=================vars=====================\\
	public List<String> lines = new ArrayList<String>(); //this gives lines of instructions unFiltered
	public Map<String,Integer> labelMap = new HashMap<>(); //gives map Label Name (key) => Address(value)
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

	public List<String> getInst(){

		Pattern noBlankLines = Pattern.compile("[^\\s][a-z].*$");
//===========================Filter out  just instruction============\\
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).contains(":") || lines.get(i).contains(": ") || lines.get(i).contains(" :")) {
				lines.set(i, lines.get(i).substring(lines.get(i).indexOf(":"), lines.get(i).length()));
				lines.set(i,lines.get(i).replaceAll("^:\\s?",""));
			}
		}
		lines = lines.stream().filter(noBlankLines.asPredicate()).collect(Collectors.toList());
		return lines;
	}
	public Map<String, Integer> getLabel(Integer BaseAddress) { //get Labels and filter out the

		Pattern LabelFormat = Pattern.compile("^[0-9a-zA-Z]+\\s?:");
		//get filtered String that correcponds to labels put them in a list
		List<String> labels = lines.stream()
				.filter(LabelFormat.asPredicate())
				.collect(Collectors.toList());

		//this is give a test to see if we can incrment the address (if label is on the same line)
  		  Pattern labelFollowedByInst = Pattern.compile("^[0-9a-zA-z]+\\s?:\\s+?[0-9a-zA-z]+$");
  		  Pattern instrMatch = Pattern.compile("^[0-9a-zA-z]+\\s?[^:]+$");
  		  Pattern labelByItSelf = Pattern.compile("^[0-9a-zA-z]+\\s+?:");
		//===================get address of corrersponding address of labels===================\\
		int Address = BaseAddress;

		List<Integer> AddrList = new ArrayList<Integer>();

		//This only test is labels are on a new line after 
		
		for (int i = 0; i < lines.size(); i++) {

			//Label Followed by a instruction
			if (labelFollowedByInst.matcher(lines.get(i)).find())//count each time a non label is found
			{
				if(i==0)
					AddrList.add(Address);
				else {
					Address++;
					AddrList.add(Address);
					System.out.println(Address);
				}
			}
			//just instruction found
			else if(instrMatch.matcher(lines.get(i)).find()) {
				Address++;
			}
			//else label found
			else { //if it is a label store that address in it
			if(i == lines.size() -1) //if the last run is an incrutio
				Address++;
					AddrList.add(Address);
			}
		}

		//===========================Filter out inline instructions with labels============\\
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).contains("#")) {
				lines.set(i, lines.get(i).substring(0, lines.get(i).indexOf("#")));
			}
		}

		//=======================put label, and corresonding addr in map=================================\\

		for (int i = 0; i < labels.size(); i++) { //get key (label name ) -> value (address)//
			labels.set(i, labels.get(i).substring(0, labels.get(i).indexOf(":")));
			labelMap.put(labels.get(i), AddrList.get(i));
		}
		return labelMap;
	}

	//NEED TO FIX reading parsing up to next $ for regs ALSO PUT each of these function in the instruction Type class
// getOp can go in Instruction class because everyinstruction has a opcode
//When creating a new object of sub instruction pass line -> when passed parse it to each variable
//Format for Reg: op rd, rs, rt


//Returns a list of the fields in binary : corresponding to the type
	public static List<String> getFields(String line, Instruction type)
	{
		List<String> binaryFields;
		List<String> nmeumonicFields;

		if (type instanceof RegInstr)
		{
			//step 1: parse into neumonic fields (getRs().....getShamt())
			//step 2: map nemuics fields into binary fields
			//step 3: return binary fields
		}
		else if (type instanceof ImmedInstr)
		{
			///step 1: parse into neumonic fields (getRs().....getShamt())
			//step 2: map nemuics fields into binary fields
			//step 3: return binary fields
		}
		}
		else if (type instanceof JumpInstr)
		{
			//step 1: parse into neumonic fields (getRs().....getShamt())
			//step 2: map nemuics fields into binary fields
			//step 3: return binary fields
		}
		}

	}
	public static String getOp(String line)
	{
		return line.split("\\s")[0];
	}

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


	public String getShamt(String line)
	{

	}
	public String getFunct(String line) {
	}
	public String getAddrImmed(String line)
	{
	}
	public String getTargetAddr(String line)
	{

	}
	*/
}


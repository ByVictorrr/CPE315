/* * Instructions.java * Copyright (C) 2019 victor <victor@TheShell> * * Distributed under terms of the MIT license. */
import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Pattern;


public class Parser {
	//=================vars=====================\\
	public List<String> lines = new ArrayList<String>();
	public Map<String,Integer> labelMap = new HashMap<>();
	
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

	/*public List<Instructions> getInst(){

		Pattern LabelFormat = Pattern.compile("^[0-9a-zA-Z]+:");
		//get filtered String that correcponds to labels put them in a list
		List<String> labels = lines.stream()
				.filter(LabelFormat.asPredicate())
				.collect(Collectors.toList());


	}





	*/
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
			if (labelByItSelf.matcher(lines.get(i)).find() && i ==0)
			{
				AddrList.add(Address);
			}

			if (labelFollowedByInst.matcher(lines.get(i)).find())//count each time a non label is found
			{

					Address++;
					AddrList.add(Address);
					System.out.println(Address);

			}
			else if(instrMatch.matcher(lines.get(i)).find()) {
				Address++;
			}
			else { //if it is a label store that address in it
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

}


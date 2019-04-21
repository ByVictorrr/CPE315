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
	public Map<String,Long> labelMap = new HashMap<>();

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

	public Map<String, Long> getLabel(Long BaseAddress){ //get Labels and filter out the

		Pattern LabelFormat = Pattern.compile("[0-9a-zA-Z]+\\s+?:");
		//get filtered String that correcponds to labels put them in a list
		List<String> labels = lines.stream()
									.filter(LabelFormat.asPredicate())
									.collect(Collectors.toList());


		//===================get address of corrersponding address of labels===================\\
		Long Address = BaseAddress;
		List<Long> AddrList = new ArrayList<>();

		for (int i = 0; i< lines.size(); i++){

			if(!LabelFormat.matcher(lines.get(i)).find()) //count each time a non label is found
			{
				if (i != 0) { //if not first line
					Address++;
				}
			}
			AddrList
		}
		//=================================================================================\\


		//=======================put label, and corresonding addr in map=================================\\

		for (String label: labels) {
			labelMap.put(label, );
		}
					.filter(noFrontComments.asPredicate()) //get all lines that dont start with
					.filter(noBlankLines.asPredicate()) //get all lines that dont start with
					.collect(Collectors.toList());

		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).contains(":")) {
				lines.set(i, lines.get(i).substring(0, lines.get(i).indexOf(" :")));
			}
			numLines = i; //Use numlines for address number for the labels
		}

	}
}


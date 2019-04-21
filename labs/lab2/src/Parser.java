/* * Instructions.java * Copyright (C) 2019 victor <victor@TheShell> * * Distributed under terms of the MIT license. */
import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Pattern;


public class Parser
{

	//=================vars=====================\\
	public List <String> lines = new ArrayList<String>();
	public int numLines = 0;

	//=================Function=====================\\
	public void filterCommentsWhites(String [] args) {

		Pattern noFrontCommentsOrBlankLines = Pattern.compile("^[^\\s #].*");

		//After passing these patterns its possible for therre to be # after an instruction
		try (Stream<String> stream = Files.lines(Paths.get(args[0]))) {
			//=============filter front of line comments and blank lines=====================\\
			lines = stream
					.filter(noFrontCommentsOrBlankLines.asPredicate()) //get all lines that dont start with
					.collect(Collectors.toList());
			//=================================================================================\\
		} catch (IOException e) {
			e.getSuppressed();
		}

		//================Filters out the comments after instructions\ labels==========\\
		for (int i = 0; i < lines.size(); i++){
			if (lines.get(i).contains("#")) {
				lines.set(i, lines.get(i).substring(0, lines.get(i).indexOf("#")));
			}
			numLines = i; //Use numlines for address number for the labels
			}
			//====================================================\\
	}
	public Map<Integer, Integer Unsigned >

}


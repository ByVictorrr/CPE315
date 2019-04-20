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

	//=================Function=====================\\
	public void filterCommentsWhites(String [] args)
	{

		Pattern whiteSpaceFilter = Pattern.compile("[^\\s].*$");

	    try(Stream<String> stream = Files.lines(Paths.get(args[0]))){
	    	//============Filter white spaces (\t, \s, ..)=========\\
	    	//Case 1: filter out blank spaces
			lines = stream
					.filter(whiteSpaceFilter.asPredicate())  //filter every that is not made fully of white sapce chars
					.collect( Collectors.toList());

			/*
	    	//=============Filter comments out=====================\\
			//case 2 filter comments out
			//case 2.1: filter every line out starting with #
			lines = lines.stream()
					.filter( lines -> lines.startsWith("#"))
					.collect( Collectors.toList());
			//case 2.2: filter substrings after # for (int i = 0; i< lines.size(); i++)
			{
				String filtered =  lines.get(i).substring(0, lines.get(i).indexOf(" #")) ;
				lines.set(i, filtered);  //deletes everything after #
			}
			//====================================================\\
*/

			
		}
		catch (IOException e)
		{
			e.getSuppressed();
		}


	}




}


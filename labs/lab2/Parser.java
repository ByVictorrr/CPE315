/*
 * Instructions.java
 * Copyright (C) 2019 victor <victor@TheShell>
 *
 * Distributed under terms of the MIT license.
 */

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Instructions
{

	public static void main(String [] args) {
		BufferedReader br = null;
	    try{
			br = new BufferedReader(new FileReader(args[1])); //get asm file
			String line;

			//reading reach line if not null
			while ((line = br.readLine()) != null){
				System.out.println(line);
			}
		} catch (IOException e){
			e.printStackTrace(); //not really anything
		} finally{
			try{
				br.close();
			} catch (IOException e){
				e.printStackTrace(); //not really anything

		}	


	}
	




}


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

		//==============TEST1 - initalize maps (instruction and label map)================\\
		parse.setMaps(args, 100);
		parse.instructMap = parse.getInstrMap();
		parse.labelMap = parse.getLabelMap();

		System.out.println(parse.lines.get(0));
		parse.labelMap.forEach((k, v) -> System.out.println("label map " + k + ":" + v + "\n"));
		parse.instructMap.forEach((k, v) -> System.out.println("instruction map " + k + ":" + v + "\n"));


		//==============TEST2 -  maps (instruction and label map)================\\


	}


}







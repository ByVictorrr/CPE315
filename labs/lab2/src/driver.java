import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Pattern;


public class driver
{

	public static void main(String [] args) {
		Parser parse = new Parser();

		parse.filterCommentsWhites(args);
			parse.lines.forEach(System.out::println);

		parse.labelMap = parse.getLabel(100);
		parse.labelMap.forEach((k,v) -> System.out.println(k + ":" + v));

			parse.lines.forEach(System.out::println);

		
	}
}

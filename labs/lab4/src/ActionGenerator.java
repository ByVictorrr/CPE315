import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ActionGenerator {
    private static Prog prog;

    public static void initializeProg(Prog prog){
        ActionGenerator.prog = prog;
    }

    public static Action generateAction(String action, List<String> params) throws Exception{
        if(ActionGenerator.prog == null){
            throw new Exception("Action Generator Error: Prog has not been initialized!!");
        }

        switch(action){
            case "h":
                return new Help();
            case "s":
                if(params != null && params.size() > 0)
                    return new StepCycle(ActionGenerator.prog, Integer.parseInt(params.get(0)));
                else
                    return new StepCycle(ActionGenerator.prog, 1);
            case "m":
                return new MemFetch(Integer.parseInt(params.get(0)), Integer.parseInt(params.get(1)));
            case "d":
                return new Dump(ActionGenerator.prog.getPC());
            case "r":
                return new RunCycle(ActionGenerator.prog);
            case "c":
                return new Clear(ActionGenerator.prog);
            case "q":
                return new Quit();
            case "p":
                return new PipelineDump(ActionGenerator.prog.getPC());
            default:
                throw new Exception("This action does not exist.");
        }
    }

    public static Pair<String, List<String>> parseAction(String line){
        String action;
        List<String> parameters = new ArrayList<String>();

        //Assumes we only pass a line to this function to parse for a single argument
        Scanner scanner = new Scanner(line);
        action = scanner.next();
        while(scanner.hasNext()){
            parameters.add(scanner.next());
        }//while

        return new Pair<String, List<String>>(action, parameters);
    }
}

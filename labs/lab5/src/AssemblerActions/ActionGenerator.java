package AssemblerActions;

import AssemblerActions.Steppers.Clear;
import AssemblerActions.Steppers.Run;
import AssemblerActions.Steppers.Step;
import AssemblerActions.Utility.Pair;
import Modules.BranchPredictor;
import Modules.Prog;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ActionGenerator {
    private final Prog prog;

    public ActionGenerator(Prog prog){ this.prog = prog; }

    public Action generateAction(String action, List<String> params) throws Exception{
        switch(action){
            case "h":
                return new Help();
            case "s":
                if(params != null && params.size() > 0)
                    return new Step(this.prog, Integer.parseInt(params.get(0)));
                else
                    return new Step(this.prog, 1);
            case "b":
                    return new BranchPrinter();
            case "m":
                return new MemFetch(Integer.parseInt(params.get(0)), Integer.parseInt(params.get(1)));
            case "d":
                return new Dump(prog.getPC());
            case "r":
                return new Run(prog);
            case "c":
                return new Clear(prog);
            case "q":
                return new Quit();
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

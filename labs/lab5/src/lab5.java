/* Name: Eeron Grant and Victor Delaplaine
 * Assignment: Lab 3
 * Course: CPE 315
 * Professor: John Seng
 */

import AssemblerActions.Action;
import AssemblerActions.ActionGenerator;
import AssemblerActions.Utility.Pair;
import Instructions.Instruction;
import Modules.BranchPredictor;
import Modules.Prog;
import Passes.FirstPass;
import Passes.SecondPass;
import ReferenceTables.LabelTable;

import java.io.File;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

public class lab5 {
    public static String INTERMEDIATE_FILE = "intermediateFile.txt";

    public static void main(String[] args) {
        String filePath = args[0];
        LabelTable labeltable = null;
        FirstPass firstPass = new FirstPass();
        List<Instruction> instructionsList = null;
        try{
            labeltable = firstPass.passFile(filePath);
        }catch (Exception e){
            System.out.println(e.getMessage());
            deleteIntermediateFile();
            exit(-1);
        }//catch

        //If label table is null, this should never be reached!!!
        SecondPass secondPass = new SecondPass(labeltable);
        try {
            Deque<Instruction> instructions = secondPass.passFile(INTERMEDIATE_FILE);
            instructionsList = new ArrayList<Instruction>(instructions);
        }catch (Exception e){
            System.out.println(e.getMessage());
            exit(-1);
        }finally{
            deleteIntermediateFile();
        }//finally


        /*This is where new code for Lab 3 Begins!!! */
        Prog prog = new Prog(instructionsList);
        ActionGenerator actionGenerator = new ActionGenerator(prog);

        /*======================BRANCH PREDICTOR shit================================*/

        try {
            //If the user didn't enter GHR size assume its 8
            if(args.length != 3) {
                BranchPredictor.setGHR(8);
            }else{
                BranchPredictor.setGHR(Integer.parseInt(args[2]));
            }
        }catch(Exception e)
        {
            System.out.println("GHR size isnt 2,4 or 8");
        }
       /*============================================================= */


        if(args.length == 2){
            runActionScript(args[1], actionGenerator);
        }
        Scanner scanner;
        for(;;){
            System.out.print("mips> ");
            scanner = new Scanner(System.in);
            Pair<String, List<String>> actionParsed = ActionGenerator.parseAction(scanner.nextLine());
            try{
                Action action = actionGenerator.generateAction(actionParsed.getKey(),
                        actionParsed.getValue());
                action.executeAction();
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public static void deleteIntermediateFile(){
        File intermediateFile= new File(INTERMEDIATE_FILE);
        intermediateFile.delete();
    }

    public static void runActionScript(String script, ActionGenerator actionGenerator){
        try {
            Scanner input;
            File file = new File(script);
            input = new Scanner(file);

            while (input.hasNextLine()) {
                Pair<String, List<String>> actionParsed = ActionGenerator.parseAction(input.nextLine());
                String printString = "mips> " + actionParsed.getKey();
                for(String param : actionParsed.getValue()){
                    printString = printString + " " + param;
                }//for
                System.out.println(printString);
                Action action = actionGenerator.generateAction(actionParsed.getKey(), actionParsed.getValue());
                action.executeAction();
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

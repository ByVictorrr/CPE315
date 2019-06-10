import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public final class SecondPass extends Pass {
    private final InstructionFactory instructionFactory;

    public SecondPass(LabelTable labelAddress) {
        this.instructionFactory = new InstructionFactory(labelAddress);
    }

    public Deque<Instruction> passFile(String filename) throws Exception{
        Deque<Instruction> translatedFile = new ArrayDeque<>();
        File file = new File(filename);
        Scanner inputFile = new Scanner(file);
        String inputLine;

        //*This while loop take the line and will pass the first token (which is the command)
        while(inputFile.hasNextLine()){
            inputLine = inputFile.nextLine();
            Instruction generatedInstruction = instructionFactory.generateInstruction(inputLine);
            translatedFile.add(generatedInstruction);
            if(generatedInstruction instanceof ErrorInstruction){
                break;
            }//if
        }//while

        return translatedFile;
    }

}

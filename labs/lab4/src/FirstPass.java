import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import static java.lang.System.exit;

//This is the first pass the finds the address locations of each new label.
//This class prevents the duplicate labels from being introduced and prevents commands from being used
//as labels.

//First, create an instance of the class.
public class FirstPass extends Pass {
    public FirstPass() { }
    private String line = "";

    //Interprets the arguments based on the format defined!!
    public LabelTable passFile(String filename) throws Exception {
        //First open the file
        File file = new File(filename);
        Scanner inputFile = new Scanner(file);
        Scanner inputLineReader;
        String inputLine;
        LabelTable labelTable = new LabelTable();

        /*Create an intermediate file that allows us to parse the file without extra lables and comments. This makes
        * parsing much easier for the second pass*/
        BufferedWriter out = null;
        FileWriter fstream = new FileWriter("intermediateFile.txt", false); //true tells to append data.
        out = new BufferedWriter(fstream);

        Integer commandNumber = 0;//Keeps track of the instruction that we are at.
        boolean instructionFlag, priorLineLable = false; //These two flags help keep us incrementing only when needed.

        while(inputFile.hasNextLine()){
            instructionFlag = false;
            inputLine = inputFile.nextLine();
            line = inputLine;
            /*
            Process the line until one of two scenarios are encountered
                1. The # has been encountered, therefore there continue through the rest of the line
                processing nothing for the rest of the line.
                2. A statement with : (i.e. Loop1:) has been encountered and store that value as a label.
            */
            inputLineReader = new Scanner(inputLine);

            while(inputLineReader.hasNext()){
                String token = inputLineReader.next();
                if(token.charAt(0) == '#'){//Checks to see if the token is a comment,
                    break;
                } else if(token.contains(":")){//The : indicates that we have encountered a lable
                    String label = getLabel(token);
                    String remain = token.replaceFirst(label + ":", "");//May also have a command like Loop1:jr$ra
                    try{
                        labelTable.addLabel(label, commandNumber);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                        exit(-1);
                    }
                    if(!remain.isEmpty()){//Checks if anything occurs after the : (eg. Loop1:jr$ra -> jr$ra)
                        commandNumber++;
                        instructionFlag = true;//This flag is neccessary, we do not want to increment commandNumbers more than once per line
                    }else if(inputLine.replaceFirst(label + ":", "").trim().isEmpty()){ //checks to see if instruction is on same line as label
                        priorLineLable = true;
                    }
                }else{//This means we have an instruction. Set neccessary flags to false
                    priorLineLable = false;
                }//else

                if(!instructionFlag && !priorLineLable){//Again, this acts for the case that the lable had not commands next to :
                    commandNumber++;
                    instructionFlag = true;
                }//if
            }//while

            //This prints out an intermediate file that can be used for the second pass.
            if(!inputLine.isEmpty()){
                String filtered = removeLable(removeComment(inputLine)).trim();
                if(!filtered.isEmpty()){
                    out.write(filtered + "\n");
                }//if
            }//if
        }//while
        if(out != null) {
            out.close();
        }//if
        return labelTable;
    }

    private String getLabel(String token){
        String label = "";
        for(int i = 0; i < token.length(); i++){
            if(token.charAt(i) == ':'){
                label = token.substring(0, i);
                break;
            }//if
        }//for
        return label;
    }

    private String removeLable(String token){
        String label = getLabel(token);
        String remain = token.replaceFirst(label + ":", "");
        return remain;
    }

    private String removeComment(String token){
        String content = token;
        for(int i = 0; i < token.length(); i++){
            if(token.charAt(i) == '#'){
                content = token.substring(0, i);
                break;
            }//if
        }//for
        return content;
    }

}

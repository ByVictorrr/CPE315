import java.util.ArrayList;
import java.util.List;

/*This class generates an instruction based on the user input*/
public class InstructionFactory {
    private final LabelTable labelTable;
    private final String J_TYPE = "JT", R_TYPE = "RT", I_TYPE = "IT";
    private static Integer commandNumber = 1;

    public InstructionFactory(LabelTable labelAddress){
        this.labelTable = labelAddress;
    }

    public Instruction generateInstruction(String input){
        String trimmedInput = input.trim();
        String command = null;
        String remaining = null;
        Instruction instruction = null;
        List<String> parameters = new ArrayList<String>();

        for(int i = 0; i < trimmedInput.length(); i++){
            if(trimmedInput.charAt(i) == '$' || Character.isWhitespace(trimmedInput.charAt(i))){
                command = trimmedInput.substring(0,i).trim();
                remaining = trimmedInput.substring(i, trimmedInput.length());
                break;
            }//if
        }//for

        int parameterCounter = 0;
        for(int j = 0; j < remaining.length(); j++){
            if(remaining.charAt(j) == ',' || remaining.charAt(j) == '('){ //Case we run into the next parameter or we have reached the end of line
                String parameter = remaining.substring(parameterCounter, j).trim();
                parameters.add(parameter);
                parameterCounter = j + 1;
            }else if(j == remaining.length()-1){
                String parameter = remaining.substring(parameterCounter, j+1).trim();
                parameter = parameter.replace(")", "");
                parameters.add(parameter);
            }//else if
        }//for

        try{
            instruction = createInstruction(command, parameters);
        }catch(Exception e){
            try {
                instruction = new ErrorInstruction(e.getMessage());
            }catch (Exception e2){
                System.out.println("Somehow you screwed this up!! Check your exception op code!!!");
            }//catch
        }//catch
        commandNumber++;
        return instruction;
    }

    private  Instruction createInstruction(String command, List<String> parameters) throws Exception {
        OPTable_Item operation = OPTable.getOperation(command);
        Integer rs = 0, rt = 0, rd = 0, imm = 0, shamt = 0;

        //Returns error instruction to print!!! This means that code syntax is correct but an invalid command has been inputted.
        if(operation == null){
            return new ErrorInstruction("invalid instruction: " + command);
        }//if

        switch(operation.getCls()){
            case J_TYPE:
                return new JInstruction(operation.getM_code(), labelTable.getLabelRef(parameters.get(0)));
            case I_TYPE:
                //Checks to see if we have branch operation
                if(checkBranch(operation)){//Compute command difference in label if it is a branch
                    rs = RegisterTable.getRegisterOpCode(parameters.get(0));
                    rt = RegisterTable.getRegisterOpCode(parameters.get(1));
                    Integer labelRef = labelTable.getLabelRef(parameters.get(2));
                    imm = labelRef - commandNumber;
                }else if (checkMemInstruction(operation)){ //Check to see if this is a memory instruction. The parameters will be in a different order
                    rs = RegisterTable.getRegisterOpCode(parameters.get(2));
                    rt = RegisterTable.getRegisterOpCode(parameters.get(0));
                    imm = Integer.parseInt(parameters.get(1));
                } else{ //Otherwise the immediate value will be given as a numeric value!!!
                    rs = RegisterTable.getRegisterOpCode(parameters.get(1));
                    rt = RegisterTable.getRegisterOpCode(parameters.get(0));
                    imm = Integer.parseInt(parameters.get(2));
                }//else
                return new I_Instruction(operation.getM_code(), rs, rt, imm);
            case R_TYPE:
                if(operation.getMnc().equals("jr")){//if jump
                    rs = RegisterTable.getRegisterOpCode(parameters.get(0));
                }else if (checkShift(operation)){//if a shift operation use shamt
                    rt = RegisterTable.getRegisterOpCode(parameters.get(1));
                    rd = RegisterTable.getRegisterOpCode(parameters.get(0));
                    shamt = Integer.parseInt(parameters.get(2));
                }else{ // all other instructions
                    rd = RegisterTable.getRegisterOpCode(parameters.get(0));
                    rs = RegisterTable.getRegisterOpCode(parameters.get(1));
                    rt = RegisterTable.getRegisterOpCode(parameters.get(2));
                }//else
                return new RInstruction(rs, rt, rd, shamt, operation.getM_code());
            default://This means that there was an issue creating the instruction.
                return new ErrorInstruction( operation.getMnc() + ": There was an error assembling this instruction.");
        }
    }

    private boolean checkMemInstruction(OPTable_Item operation){
        switch (operation.getMnc()){
            default:
                return false;
            case "lw":
                return true;
            case "sw":
                return true;
        }
    }

    //This table checks to see if the logical instruction is a branch instruction. This means you need to compute the command location difference!!
    private boolean checkBranch(OPTable_Item operation){
        switch(operation.getMnc()){
            default:
                return false;
            case "beq":
                return true;
            case "bne":
                return true;
        }
    }

    //This table checks to see if the logical instruction is a shift instruction. USE SHAMT when this is true!!
    private boolean checkShift(OPTable_Item operation){
        switch(operation.getMnc()){
            default:
                return false;
            case "sll":
                return true;
        }
    }
}

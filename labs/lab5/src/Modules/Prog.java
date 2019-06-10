package Modules;

import Instructions.Instruction;
import Instructions.JInstruction;
import Instructions.RegisterInstructions.I_Instruction;
import Instructions.RegisterInstructions.RInstruction;
import ReferenceTables.RegisterTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.exit;

public class Prog {
    private static Integer PC = 0;
    private final List<Instruction> instructions;
    private final Integer MAX_COMMAND; //This will be total amount of commands in asm file.
    private final String RA_REGISTER = "$ra";
    public static Map<Integer, Boolean> mapOpToRF_WE;
    static {
        mapOpToRF_WE = new HashMap<>();
        mapOpToRF_WE.put(0b100100, true); //and
        mapOpToRF_WE.put(0b100101, true); //or
        mapOpToRF_WE.put(0b100000, true); //add
        mapOpToRF_WE.put(0b000000, true); //sll
        mapOpToRF_WE.put(0b100010, true); //sub
        mapOpToRF_WE.put(0b101010, true); //slt
        mapOpToRF_WE.put(0b001000, false); //jr
        mapOpToRF_WE.put(0b001000, true); //addi
        mapOpToRF_WE.put(0b000100, false); //beq
        mapOpToRF_WE.put(0b000101, false); //bne
        mapOpToRF_WE.put(0b100011, true); //lw
        mapOpToRF_WE.put(0b101011, false); //sw
        mapOpToRF_WE.put(0b000010, false); //j
        mapOpToRF_WE.put(0b000011, false); // jal
    };

    public Prog(List<Instruction> instructions){
        this.instructions = instructions;
        MAX_COMMAND = instructions.size();
    }

    public int getPC(){ return PC; }

    //This function increments the to the next instruction and returns a flag upon success. If failure, the end has been reached.
    public int stepNextInstruction(){
        if(PC >= MAX_COMMAND) {//Return -1;
            return -1;
        }//if
        PC++;
        return PC;
    }//stepNextInstruction

    //This function jumps to the given instruction from the current point of reference. Assumes jump distance never over counts instructions
    public int branchToInstruction(Integer amountOfInstructionsToJumpFrom){
        PC = PC + amountOfInstructionsToJumpFrom;
        return PC;
    }//jumpToInstruction

    private void jumpToLabel(Integer labelLocation){ PC = labelLocation; }

    //This function gets the current instruction from the current PC. It does not increment the PC Counter
    public Instruction fetchInstruction(){
        return instructions.get(PC-1);
    }//fetchInstruction

    public int clearPC(){
        PC = 0;
        return PC;
    }

    //This function executes the current Instruction at the current PC. It will continue the amount of instructions
    //pass through the parameters. Default should be 1!!!
    public void executeInstruction() throws Exception{
        Instruction instruction = fetchInstruction();
        int op_Func_Code;

        /*Case one, we do not know if the instruction is Register Write Enabled. Must check if it is. However,
        * we must first check to see if it is a Register Instruction since the main instruction code will be in
        * func instead of op*/
        if(instruction instanceof RInstruction)
            op_Func_Code = ((RInstruction) instruction).getFunc();
        else
            op_Func_Code = instruction.getOpCode();

        //Now check to see if code is register write enabled
        if(mapOpToRF_WE.get(op_Func_Code) == true)//If this is a register write enabled instruction
            executeRegisterWriteEnabledInstruction(instruction);
        else //All other cases
            executeNonRegisterWriteEnabledInstruction(instruction);

    }//executeInstruction

    private void executeRegisterWriteEnabledInstruction(Instruction instruction) throws Exception{
        //Case 1 - reg type pass r2 (as rt)
        if (instruction instanceof RInstruction) {
            RInstruction rI = (RInstruction) instruction;
            if(rI.getFunc() == 0b001000){ // Edge case if we encounter a jump register.
                PC = RF.readData(RegisterTable.getRegisterOpCode(RA_REGISTER));
            }else {//Cases that it is not a jump register
                try {
                    RF.writeData(
                            rI.getRd(),  //write reg
                            ALU.getResult(rI) //value for write reg
                    );
                } catch (Exception e) {//if exe
                    System.out.println(e);
                    exit(-1);
                }
            }
        }//Case 2 - immediate type pass r2 (as immediate)
        else if (instruction instanceof I_Instruction) {
            I_Instruction i_I = (I_Instruction) instruction;
            //lw is an exception to the written registers
            if (i_I.getOpCode() == 0b100011){ //check if lw
                try {
                    int aluResult = ALU.getResult(instruction);
                    int loadedMemory = Memory.loadWordFromMemory(aluResult);
                    RF.writeData(
                            i_I.getRt(),  //write reg
                            loadedMemory //data to be written
                            //RECALL: since we are using whole number integers, we must not forget to divide by 4
                    );
                }catch(Exception e){
                    throw e;
                }
            }else { //this is our addi for the scope of this project
                try {
                    int aluResult = ALU.getResult(i_I);
                    RF.writeData(
                            i_I.getRt(),  //write reg
                            aluResult //value for write reg
                    );
                }catch(Exception e){
                    throw e;
                }
            }
        } else {
            throw new Exception("Error Executing Register Write Enabled Instruction: " +
                    "This instruction type is not a valid R or I type");
        }

    }//executeRegisterWriteEnabledInstruction

    private void executeNonRegisterWriteEnabledInstruction(Instruction instruction) throws Exception{
        if (instruction instanceof  RInstruction){//Then we have a jr instruction
            int returnRegisterValue = RF.readData(RegisterTable.getRegisterOpCode(RA_REGISTER));
            //The value in jr should contain the return address of the label. This means we should be able to
            //assign the PC to that particular position without worry
            PC = returnRegisterValue;
        } else if (instruction instanceof I_Instruction) {
            I_Instruction i_I = (I_Instruction) instruction;
            switch (i_I.getOpCode()) {
                case 0b000100: //beq
                    //check to see if zero flag is set if so change i to the offset
                    if (ALU.getResult(i_I) == 1) //Our ALU returns 1 if beq is true and 0 if false
                        this.branchToInstruction(i_I.getImm());
                    break;
                case 0b000101: //bne
                    if (ALU.getResult(i_I) == 1) //Our ALU returns 1 if bne is true and 0 if false
                        this.branchToInstruction(i_I.getImm());
                    break;
                case 0b101011: //sw
                    int indexOffset = ALU.getResult(instruction);
                    int dataFromRt = RF.readData(i_I.getRt());
                    Memory.storeWordIntoMemory(indexOffset, dataFromRt); //write To M
                    break;
                default:
                    throw new Exception("Error Executing Non-Register Write Enabled Instruction: Immediate Instruction not Found");
            }
        } else if (instruction instanceof JInstruction) {
            JInstruction jI = (JInstruction)instruction;
            switch(jI.getOpCode()){
                case 0b000010: //j
                    this.jumpToLabel(jI.getAddr());
                    break;
                case 0b000011: // jal
                    RF.writeData(RegisterTable.getRegisterOpCode(RA_REGISTER), PC); //write to ra = pc
                    this.jumpToLabel(jI.getAddr());
                    break;
                default:
                    throw new Exception("Error Executing Non-Register Write Enabled Instruction: Jump Instruction not found !");
            }
        }else {
            throw new Exception("Error Executing Non-Register Write Enabled Instruction: " +
                    "No such instruction found in this assembler!");
        }
    }

}

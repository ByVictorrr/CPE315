/*This is a public static class where the pipeline registers reside
* All members and methods in this class will be class and not based
* on the object.*/
public class PipelineRegisters {
    //Registers
    private static Instruction If_Id;
    private static Instruction Id_Ex;
    private static Instruction Ex_Mem;
    private static Instruction Mem_Wb;

    public static EmptyInstruction empty; //Empty Instruction must be initialized before being used!!!

    public static boolean executionBegin = false;
    private static Integer clockCycles = 0;
    public static final Integer EMPTY_OPCODE = -5;
    private static PipelineStall stall;
    private static Integer pcAtPipe = 0;
    private static Boolean continueToIncrement = true;
    //Steps through exactly 1 clock cycle and assigns the registers in sequential order.
    //Pass in the Empty Instruction provided by this class.
    public static void setPipeLineRegisters(Instruction If_Id){
        pcAtPipe = Prog.getPC();

        Mem_Wb = Ex_Mem;
        Ex_Mem = Id_Ex;
        Id_Ex = PipelineRegisters.If_Id;
        PipelineRegisters.If_Id = If_Id;
        if(getContinueToIncrement()){
            incrementClockCycle();
        }

        if(stall.getBranchStall() && stall.getStallCount() >= 1){ // Offset the pc for the simulator on NT branches
            pcAtPipe = pcAtPipe - stall.getStallCount();
        }

        if(Id_Ex.getOpCode() == 0b100011 && (If_Id instanceof RegisterInstruction
                && (Id_Ex instanceof I_Instruction)) ){ //check to see if instruction uses a lw registers right after
            Integer loadRegister = ((I_Instruction) Id_Ex).getRt();
            Integer rs = ((RegisterInstruction) If_Id).getRs();
            if(loadRegister == rs && rs != 0){ //Check to see if register used if the one loaded (with exception to zero)
                stall = new PipelineStall(Id_Ex, true);
                return;
            }
            if(If_Id instanceof RInstruction) {//Case we have an r class
                Integer rt = ((RegisterInstruction) If_Id).getRt();
                if(loadRegister == rt && rt != 0){//Check to see if register used if the one loaded (with exception to z
                    stall = new PipelineStall(Id_Ex, true);
                    return;
                }
            }
        } else if(If_Id.getOpCode() != 0b100011 && !(If_Id instanceof StallInstruction)) { //Case instruction is in not lw. Then we do not need to check for immediate after instruction usage
            stall = new PipelineStall(If_Id, false);
        }


    }

    //This function assigns the registers a stall value. This does not actually check for stalls
    //This function is called assuming a stall exists! It is meant to be a helper for branch stall conditions
    public static void setPipeLineRegistersLoadStall(Instruction Id_Ex){
        Mem_Wb = Ex_Mem;
        Ex_Mem = PipelineRegisters.Id_Ex;
        PipelineRegisters.Id_Ex = Id_Ex ;
        incrementClockCycle();
    }


    //This utility function checks to see if all the registers have been emptied and at least 1 instruction was executed.
    //This typically means the program has run and it is time to reset/terminate
    public static boolean registersEmptyAfterExecution(){
        if(executionBegin && Mem_Wb instanceof EmptyInstruction && If_Id instanceof EmptyInstruction
        && Ex_Mem instanceof EmptyInstruction && Id_Ex instanceof  EmptyInstruction){
            return true;
        }
        return false;
    }

    //This function sets all the pipelines to the EmptyInstruction instruction. This instruction
    //will be passed through to represent registers that have no instructions/have squashed instructions
    public static void initializePipeLine(){
        try {
            empty = new EmptyInstruction();
        } catch (Exception e){
            System.out.println("Error Initializing Pipeline:" +
                    "Someone was bad and edited the Instruction Code. You should never get here!!");
        }
        If_Id = empty;
        Ex_Mem = empty;
        Id_Ex = empty;
        Mem_Wb = empty;
        clockCycles = 0;
        executionBegin = false;
        pcAtPipe = 0;
        continueToIncrement = true;
        stall = new PipelineStall(empty, false);
    }

    //Wrapper function to increment the clockCycles.
    private static void incrementClockCycle(){ PipelineRegisters.clockCycles++; }

    //Wrapper function for the stalls stallComplete function.
    public static boolean checkStall(){ return stall.stallComplete(); }

    //This function assigns the first three registers a StallInstruction with "squashed" as the message.
    //This is intended to be used when a non taken branch has reached the last instruction.
    public static void squashRegisters(){
        pcAtPipe = Prog.getPC();
        incrementClockCycle();
        try{
            Ex_Mem = new StallInstruction("squash");
            Id_Ex = Ex_Mem;
            If_Id = Ex_Mem;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //This function returns the op/func code of the instruction
    public static Integer retrieveInstructionCode(Instruction instruction){
        if(instruction instanceof RInstruction) {
            return ((RInstruction) instruction).getFunc();
        } else if (instruction instanceof EmptyInstruction){
            return EMPTY_OPCODE;
        }
        return instruction.getOpCode();
    }

    /*Accessor methods for instructions!!*/
    public static Instruction getIf_Id() { return If_Id; }
    public static Instruction getId_Ex() { return Id_Ex; }
    public static Instruction getEx_Mem() { return Ex_Mem; }
    public static Instruction getMem_Wb() { return Mem_Wb; }
    public static Integer getClockCycles(){ return clockCycles; }
    public static PipelineStall getStall() { return stall; }
    public static Integer getPcAtPipe() { return pcAtPipe; }
    public static EmptyInstruction getEmpty() { return empty; }
    public static void setExecutionBegin(){ executionBegin = true; }
    public static void disableContinueToIncrement() { continueToIncrement = false;}
    public static Boolean getContinueToIncrement(){return continueToIncrement;}
}

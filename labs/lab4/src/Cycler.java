import static java.lang.System.exit;

//This is an AssemblerAction class that controls the behavior of any class that iterates through clock cycles.
// It is a stepper by default.
public abstract class Cycler extends Stepper {
    public static int totalStalls = 0;
    public static int totalInstructions = 0;
    public Cycler(Prog prog) {
        super(prog);
    }

    public static void clearCycle(){
        totalStalls = 0;
        totalInstructions = 0;
    }

    protected void executeCycle(){

        if(PipelineRegisters.checkStall()){//Most complicated task, this branches to the condition a stall has occured
            try {
                if(PipelineRegisters.getStall().getLoadStall()){//delay after a load and usage of its dest register
                    PipelineRegisters.setPipeLineRegistersLoadStall(new StallInstruction("stall"));
                }else if(PipelineRegisters.getStall().getBranchStall()){//delay after a branch
                    if(PipelineRegisters.getStall().getStallCount() > 0){
                        Instruction inst = prog.getInstructionAfterBranch();
                        String herringInstruction;//This "instruction" will print out the name of said instruction but not actually be an instance of that instruction
                        if(inst instanceof RInstruction){
                            herringInstruction = OPTable.getOperation(((RInstruction)inst).getFunc()).getMnc();
                        }else if (inst instanceof EmptyInstruction){
                            herringInstruction = "empty";
                        }
                        else{
                            herringInstruction = OPTable.getOperation((inst.getOpCode())).getMnc();
                        }
                        PipelineRegisters.setPipeLineRegisters(new StallInstruction(herringInstruction));
                    }else{//Squash all the registers
                        PipelineRegisters.squashRegisters();
                    }

                } else{ // delay after a jump
                    PipelineRegisters.setPipeLineRegisters(new StallInstruction("squash"));
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
                exit(-1);
            }
            totalStalls++;
            return;
        }

        if(prog.stepNextInstruction() == -1){
            try {
                PipelineRegisters.setPipeLineRegisters(new EmptyInstruction());
            }catch (Exception e){
                System.out.println(e.getMessage());
                exit(-1);
            }
        }
        else {
            Instruction nextInstruction = prog.fetchInstruction();
            PipelineRegisters.setPipeLineRegisters(nextInstruction);
            try{
                prog.executeInstruction();
                totalInstructions++;
            }catch(Exception e){
                System.out.println(e.getMessage());
                exit(-1);
            }
        }
    }
}

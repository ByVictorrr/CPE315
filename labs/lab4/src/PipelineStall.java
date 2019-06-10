public class PipelineStall {
    private Integer stallCount = 0;
    private Boolean loadStall = false;
    private Boolean branchStall = false;

    //This constructor takes care of assigning stall lengths. It also helps indicate if
    //a branchStall has occurred. To pass in a loadStall, it is neccessary to pass a boolean if the condition for a load stall
    //has been met.
    public PipelineStall(Instruction instruction, Boolean loadStall){
        if(instruction instanceof RInstruction){
            if(((RInstruction) instruction).getFunc() == 0b001000){
                stallCount = 1;
            }
        } else if(instruction instanceof JInstruction){
            stallCount = 1;
        } else {
            if(instruction.getOpCode() == 0b100011){
                stallCount = 1;
                this.loadStall = loadStall;
            }
            else if(instruction.getOpCode() == 0b000101 || instruction.getOpCode() == 0b000100){
                Integer branchTaken = 0;
                try{
                    branchTaken = ALU.getResult(instruction); // Check if the branch was taken!!!
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
                if(branchTaken == 1) {
                    stallCount = 3;
                    branchStall = true;
                }
            }
        }
    }

    public boolean stallComplete(){
        if(stallCount > 0){
            stallCount--;
            return true;
        }
        return false;
    }


    public Integer getStallCount() { return stallCount; }
    public Boolean getLoadStall() { return loadStall; }
    public Boolean getBranchStall() { return branchStall; }
}

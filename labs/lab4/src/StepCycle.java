public class StepCycle extends Cycler {
    private final int steps;
    public static int totalStalls = 0;

    public StepCycle(Prog prog, int steps) {
        super(prog);
        this.steps = steps;
    }

    @Override
    public void executeAction() {
        if(Prog.getPC() == 0){
            PipelineRegisters.setExecutionBegin();
            executeCycle();
        }

        for(int i = 0; i < steps; i++){
            new PipelineDump(Prog.getPC()).executeAction();
            executeCycle();
            if(PipelineRegisters.registersEmptyAfterExecution()){
                PipelineRegisters.disableContinueToIncrement();
            }
        }//for
    }

    @Override
    public String toString() {
        return "\t"+ steps + " cycle(s) executed";

    }
}

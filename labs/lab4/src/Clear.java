public class Clear extends Stepper {

    public Clear(Prog prog) {
        super(prog);
    }

    @Override
    public void executeAction() {
        prog.clearPC();
        Memory.clearMemory();
        RF.clearRegisters();
        PipelineRegisters.initializePipeLine();
        Cycler.clearCycle();
        System.out.println("\tSimulator reset\n");
    }
}

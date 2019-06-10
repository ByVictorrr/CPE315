import java.math.BigDecimal;


public class RunCycle extends Cycler {
    public RunCycle(Prog prog) {
        super(prog);
    }

    @Override
    public void executeAction() {
        if(Prog.getPC() == 0){
            PipelineRegisters.setExecutionBegin();
            executeCycle();
        }

        while(!PipelineRegisters.registersEmptyAfterExecution()) {
            executeCycle();
        }
        PipelineRegisters.disableContinueToIncrement();

        BigDecimal clockCycles = new BigDecimal(PipelineRegisters.getClockCycles());
        BigDecimal instructions = new BigDecimal(totalInstructions);
        BigDecimal cpi = clockCycles.divide(instructions, 3, BigDecimal.ROUND_DOWN);

        System.out.print( "CPI: " );
        System.out.printf("%.3f", cpi);
        System.out.println("\tCycles: " + PipelineRegisters.getClockCycles() +
                "\tInstructions: " + totalInstructions);
    }
}

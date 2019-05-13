package AssemblerActions.Steppers;

import Simulator.pipeLineRegs;
import Emulator.Modules.Prog;
import static java.lang.System.exit;

public class Run extends Stepper {
    public Run(Prog prog) {
        super(prog);
    }

    @Override
    public void executeAction() {
        while(prog.stepNextInstruction() != -1){
            try{
                //Added this function - it just shift the pipeline regs down one instruction

                prog.executeInstruction();
                pipeLineRegs.nonBlockingShift(prog);
            }catch (Exception e){
                System.out.println(e);
                exit(-1);
            }
        }
    }
}

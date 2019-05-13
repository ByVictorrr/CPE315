package AssemblerActions.Steppers;

import Emulator.Modules.Prog;
import  Simulator.pipeLineRegs;

import static java.lang.System.exit;

public class Step extends Stepper {
    private final int steps;
    public Step(Prog prog, int steps) {
        super(prog);
        this.steps = steps;
    }

    @Override
    public void executeAction() {
        for(int i = 0; i < steps; i++){
            if(prog.stepNextInstruction() != -1){
                try{

                    prog.executeInstruction();
                    /*This function just shifts the pl regs over once*/
                   pipeLineRegs.nonBlockingShift(prog);

                }catch (Exception e){
                    System.out.println(e);
                    exit(-1);
                }//try
            }//if
        }//for
        System.out.println("\t"+ steps + " instruction(s) executed");
    }//executeAction
}

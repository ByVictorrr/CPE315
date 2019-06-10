package AssemblerActions.Steppers;

import Modules.BranchPredictor;
import Modules.Prog;

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
                    BranchPredictor.checkPredictor(prog.fetchInstruction());
                    prog.executeInstruction();
                }catch (Exception e){
                    System.out.println(e);
                    exit(-1);
                }//try
            }//if
        }//for
        System.out.println("\t"+ steps + " instruction(s) executed");
    }//executeAction
}

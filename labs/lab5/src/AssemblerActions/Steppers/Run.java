package AssemblerActions.Steppers;

import Modules.BranchPredictor;
import Modules.Prog;
import static java.lang.System.exit;

public class Run extends Stepper {
    public Run(Prog prog) {
        super(prog);
    }

    @Override
    public void executeAction() {
        while(prog.stepNextInstruction() != -1){
            try{
                BranchPredictor.checkPredictor(prog.fetchInstruction());
                prog.executeInstruction();

            }catch (Exception e){
                System.out.println(e);
                exit(-1);
            }
        }
    }
}

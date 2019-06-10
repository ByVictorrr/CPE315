package AssemblerActions.Steppers;

import Modules.Memory;
import Modules.Prog;
import Modules.RF;

public class Clear extends Stepper {

    public Clear(Prog prog) {
        super(prog);
    }

    @Override
    public void executeAction() {
        prog.clearPC();
        Memory.clearMemory();
        RF.clearRegisters();
        System.out.println("\tSimulator reset\n");
    }
}

package AssemblerActions.Steppers;

import Simulator.pipeLineRegs;
import Emulator.Modules.Memory;
import Emulator.Modules.Prog;
import Emulator.Modules.RF;

public class Clear extends Stepper {

    public Clear(Prog prog) {
        super(prog);
    }

    @Override
    public void executeAction() {
        prog.clearPC();
        Memory.clearMemory();
        RF.clearRegisters();
        pipeLineRegs.clearPLRegs();

        System.out.println("\tSimulator reset\n");
    }
}

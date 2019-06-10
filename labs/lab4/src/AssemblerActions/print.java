package AssemblerActions;

import Emulator.Modules.Prog;
import Simulator.Simulation.pipeLineRegs;

public class print implements Action{
        private Prog prog;
        public print(Prog prog){this.prog = prog;}

        @Override
        public void executeAction() {
            pipeLineRegs.printPipeLineRegs(prog);
        }

}

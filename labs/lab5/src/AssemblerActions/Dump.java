package AssemblerActions;

import Modules.RF;

public class Dump implements Action {
    private final Integer pc;

    public Dump(Integer pc) { this.pc = pc;}

    @Override
    public void executeAction() {
        System.out.println();
        System.out.println("pc = " + pc);
        RF.printRegisters();
        System.out.println();
    }
}

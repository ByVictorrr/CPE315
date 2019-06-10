package AssemblerActions;

import Modules.BranchPredictor;

public class BranchPrinter implements Action{


        @Override
        public void executeAction() {
            System.out.println();
            BranchPredictor.printStats();
            System.out.println();
        }
    }

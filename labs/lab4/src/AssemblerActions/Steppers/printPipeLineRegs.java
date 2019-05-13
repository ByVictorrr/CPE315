package AssemblerActions.Steppers;

import Emulator.Instructions.Instruction;
import Emulator.Instructions.RegisterInstructions.RInstruction;
import Emulator.Modules.Prog;
import Simulator.SimulatorTables.BinaryToMnemonicTable;
import Simulator.pipeLineRegs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class printPipeLineRegs extends Stepper {

    public printPipeLineRegs(Prog prog)
    {
        super(prog);
    }

    @Override
    public void executeAction() {

        final Integer if_id  = 0;
        final Integer id_exe  = 1;
        final Integer exe_mem = 2;
        final Integer mem_wb = 3;
        final Integer NUMBER_OF_PIPELINE_REGS = 4;

        System.out.println("pc" + "     " + "if/id" + "     " + "id/exe" + "        " + "exe/mem" + "       "+ "mem/wb");

        List<Instruction> pl_Inst = Arrays.asList(
                                        pipeLineRegs.getPipeLineRegs().get("if/id"),
                                        pipeLineRegs.getPipeLineRegs().get("id/exe"),
                                        pipeLineRegs.getPipeLineRegs().get("exe/mem"),
                                        pipeLineRegs.getPipeLineRegs().get("mem/wb"));

       //Contents to be written out to the screen, based on R type or I/J type

        String if_idReg = pl_Inst.get(if_id) instanceof RInstruction ?
               BinaryToMnemonicTable.getfunctMap().get(((RInstruction) pl_Inst.get(if_id)).getFunc()) :  //use the function map if it is a reg type
               BinaryToMnemonicTable.getOpMap().get(pl_Inst.get(id_exe).getOpCode());

          String id_exeReg  = pl_Inst.get(id_exe) instanceof RInstruction ?
               BinaryToMnemonicTable.getfunctMap().get(((RInstruction)pl_Inst.get(id_exe)).getFunc()) :  //use the function map if it is a reg type
               BinaryToMnemonicTable.getOpMap().get(pl_Inst.get(id_exe).getOpCode());

        String exe_memReg = pl_Inst.get(exe_mem) instanceof RInstruction ?
               BinaryToMnemonicTable.getfunctMap().get(((RInstruction)pl_Inst.get(exe_mem)).getFunc()) :  //use the function map if it is a reg type
               BinaryToMnemonicTable.getOpMap().get(pl_Inst.get(exe_mem).getOpCode());//else use the op map if its j/i type

        String mem_wbReg = pl_Inst.get(mem_wb) instanceof RInstruction ?
               BinaryToMnemonicTable.getfunctMap().get(((RInstruction)pl_Inst.get(mem_wb)).getFunc()) :  //use the function map if it is a reg type
               BinaryToMnemonicTable.getOpMap().get(pl_Inst.get(mem_wb).getOpCode());




                        System.out.println(
                                prog.getPC()+ "       "  +
                                if_idReg  + "      " +
                                        id_exeReg + "      " +
                                exe_memReg + "     " +
                                mem_wbReg + "     "
        );
    }
}
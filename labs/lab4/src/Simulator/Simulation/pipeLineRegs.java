package Simulator.Simulation;
import Emulator.Instructions.*;
import Emulator.Instructions.RegisterInstructions.I_Instruction;
import Emulator.Instructions.RegisterInstructions.RInstruction;
import Emulator.Modules.ALU;
import Emulator.Modules.Prog;
import Simulator.Stall_Instructions.EmptyInstruction;
import Simulator.SimulatorTables.BinaryToMnemonicTable;
import Simulator.Stall_Instructions.SquashInstruction;
import Simulator.Stall_Instructions.Stall_Instruction;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

import java.util.*;

public class pipeLineRegs{

    //Stores non - delayed instructions
    private static Map <String,Instruction> pipeLineRegs;
    //Stores delayed instructions(sim instruction)
    private static Map<String, Instruction> shadowPipeLineRegs;

    static{
        pipeLineRegs = new HashMap<>();
        shadowPipeLineRegs = new HashMap<>();
        try {
            pipeLineRegs.put("if/id", new EmptyInstruction());
            pipeLineRegs.put("id/exe", new EmptyInstruction());
            pipeLineRegs.put("exe/mem", new EmptyInstruction());
            pipeLineRegs.put("mem/wb", new EmptyInstruction());

            shadowPipeLineRegs.put("if/id", new EmptyInstruction());
            shadowPipeLineRegs.put("id/exe", new EmptyInstruction());
            shadowPipeLineRegs.put("exe/mem", new EmptyInstruction());
            shadowPipeLineRegs.put("mem/wb", new EmptyInstruction());

        }catch (Exception e)
        {
            System.out.print("No operation error");
        }

    }

    private static final List<String> keys = Arrays.asList("if/id", "id/exe", "exe/mem", "mem/wb");
    private static final Integer if_id  = 0;
    private static final Integer id_exe  = 1;
    private static final Integer exe_mem = 2;
    private static final Integer mem_wb = 3;
    private static final Integer NUMBER_OF_PIPELINE_REGS = 4;
    /*nonBlockingShift: shift the contents of
	    mem_wb = exe_mem
	    exe_mem = id_exe
	    id_exe = if_id
	    if_id = pc
	    nonblocking in computer engineering means - everything executes in parallel
     */

    public static void nonBlockingShift(Prog prog, Prog sim)
    {
            /*Step 1 - set all mem/wb - id/ex*/
            for (int i = NUMBER_OF_PIPELINE_REGS - 1; i > 0; i--) {
                /*Replaces - ith value with ith - 1 value, up until i-1 = 0*/
               pipeLineRegs.replace(keys.get(i), pipeLineRegs.get(keys.get(i)) , pipeLineRegs.get(keys.get(i-1)));
               shadowPipeLineRegs.replace(keys.get(i), shadowPipeLineRegs.get(keys.get(i)) , shadowPipeLineRegs.get(keys.get(i-1)));
            }
            /*Step 2 - set if/id with the value of the pc*/
                if(prog.getPC() > 0) {
                    pipeLineRegs.replace(keys.get(if_id), prog.fetchInstruction());
                    shadowPipeLineRegs.replace(keys.get(if_id), sim.fetchInstruction());
                }
    }

        public static void clearPLRegs(){
            for(int i = 0; i < NUMBER_OF_PIPELINE_REGS;  i++)
                try {
                    pipeLineRegs.replace(keys.get(i), new EmptyInstruction());
                }catch (Exception e)
                {
                    System.out.println("No Operation instruction error");
                }
        }

    public static Map<String, Instruction> getPipeLineRegs(){return pipeLineRegs;}




    //Print shadow or reg pipeline regs conditionally
    public static void printPipeLineRegs(Prog prog)
    {
         /*Cases:
               branch-taken: inserts squash after
               use-after-lw: inserts stall after lw gets to exe/mem
                */

        System.out.println("pc" + "     " + "if/id" + "     " + "id/exe" + "        " + "exe/mem" + "       "+ "mem/wb");

        List<Instruction> pl_Inst = Arrays.asList(
                pipeLineRegs.get("if/id"),
                pipeLineRegs.get("id/exe"),
                pipeLineRegs.get("exe/mem"),
                pipeLineRegs.get("mem/wb")
        );
        List<Instruction> shdw_pl_Inst = Arrays.asList(
                shadowPipeLineRegs.get("if/id"),
                shadowPipeLineRegs.get("id/exe"),
                shadowPipeLineRegs.get("exe/mem"),
                shadowPipeLineRegs.get("mem/wb")
        );

        List<String> pl_opMnemonic = new ArrayList<>();

        //In this loop want to get the string equivalent of each pipeline instruction

       //Add to the pl_opMnemonic conditionally
        List<Instruction> choose_pl_Inst;
        for(int i = NUMBER_OF_PIPELINE_REGS -1 ; i > 0; i--) {

            //Condition 1 - Check if I_instruction to check for use after load word or branch taken
            if ( pl_Inst.get(mem_wb) instanceof I_Instruction || pl_Inst.get(exe_mem) instanceof I_Instruction)
            {
                //Branch taken - check the shadow pipeline regs to see if we should switch to show them
                if(
                        (BinaryToMnemonicTable.getOpMap().get(shdw_pl_Inst.get(mem_wb).getOpCode()).equals("beq")  ||
                         BinaryToMnemonicTable.getfunctMap().get(shdw_pl_Inst.get(mem_wb).getOpCode()).equals("bne"))
                                &&
                                (shdw_pl_Inst.get(exe_mem) instanceof SquashInstruction &&
                                shdw_pl_Inst.get(id_exe) instanceof SquashInstruction &&
                                shdw_pl_Inst.get(if_id) instanceof SquashInstruction)
                        )
                {
                    //print out shadow regs

                }//if branch taken

                //use after load - check the shadow pipeline regs to see if we should switch to them
                else if (BinaryToMnemonicTable.getOpMap().get(shdw_pl_Inst.get(exe_mem).getOpCode()).equals("lw")
                         && shdw_pl_Inst.get(id_exe) instanceof Stall_Instruction)
                {
                   //print out shadow flags

                }//if use after load


            }//if I_instruction
            //Condition 2 - check if J_jump condition
            else if(pl_Inst.get(id_exe) instanceof JInstruction)
            {
               //print out shadow reg
            }



        }
        for(Instruction pipeLine: choose_pl)
        {
            if(pipeLine instanceof RInstruction)
            {
                System.out.print(((RInstruction) pipeLine).getFunc());
                pl_opMnemonic.add(BinaryToMnemonicTable.getfunctMap().get(((RInstruction) pipeLine).getFunc()));
            }else{
                 pl_opMnemonic.add(BinaryToMnemonicTable.getOpMap().get(pipeLine.getOpCode()));
            }
        }
        System.out.println(
                        prog.getPC()+ "       "  +
                        pl_opMnemonic.get(if_id) +  "     " +
                        pl_opMnemonic.get(id_exe) + "     " +
                        pl_opMnemonic.get(exe_mem)+ "     " +
                        pl_opMnemonic.get(mem_wb) + "     "
        );
    }






    //Branch wont be known if needed to be taken until mem stage
    public void branchDetector(Instruction mem_stage) {
        try {
            if (pipeLineRegs.get(keys.get(exe_mem)).equals("j") ||
                    pipeLineRegs.get(keys.get(exe_mem)).equals("jr") ||
                    (pipeLineRegs.get(keys.get(exe_mem)).equals("bne") && ALU.getResult(pipeLineRegs.get(keys.get(exe_mem))) != 0) ||
                    pipeLineRegs.get(keys.get(exe_mem)).equals("beq") && ALU.getResult(pipeLineRegs.get(keys.get(exe_mem))) == 0) {
                //check now if the zero flag is is set

            }
        }catch (Exception e)
        {
            System.out.println("Error trying to use ALU");
        }

    }

}
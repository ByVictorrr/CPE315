package Simulator;
import Emulator.Instructions.*;
import Emulator.Modules.ALU;
import Emulator.Modules.Prog;
import Simulator.Delays.nopInstruction;

import java.util.*;

public class pipeLineRegs{

    private static Map <String,Instruction> pipeLineRegs ;
    static{
        pipeLineRegs = new HashMap<>();
        try {
            pipeLineRegs.put("if/id", new nopInstruction());
            pipeLineRegs.put("id/exe", new nopInstruction());
            pipeLineRegs.put("exe/mem", new nopInstruction());
            pipeLineRegs.put("mem/wb", new nopInstruction());
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

    public static void nonBlockingShift(Prog prog)
    {
            /*Step 1 - set all mem/wb - id/ex*/
            for (int i = NUMBER_OF_PIPELINE_REGS - 1; i > 0; i--) {
                /*Replaces - ith value with ith - 1 value, up until i-1 = 0*/
                pipeLineRegs.replace(keys.get(i), pipeLineRegs.get(keys.get(i)) , pipeLineRegs.get(keys.get(i-1)));
            }
            /*Step 2 - set if/id with the value of the pc*/
                if(prog.getPC() > 0)
                    pipeLineRegs.replace(keys.get(if_id),prog.fetchInstruction());
    }

        //opcode = -1
        public static void clearPLRegs(){
            for(int i = 0; i < NUMBER_OF_PIPELINE_REGS;  i++)
                try {
                    pipeLineRegs.replace(keys.get(i), new nopInstruction());
                }catch (Exception e)
                {
                    System.out.println("No Operation instruction error");
                }
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

    public static Map<String, Instruction> getPipeLineRegs(){return pipeLineRegs;}

}
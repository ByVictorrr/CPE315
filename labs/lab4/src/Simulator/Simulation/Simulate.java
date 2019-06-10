package Simulator.Simulation;

import Emulator.Instructions.Instruction;
import Emulator.Instructions.JInstruction;
import Emulator.Instructions.RegisterInstructions.I_Instruction;
import Emulator.Instructions.RegisterInstructions.RInstruction;
import Emulator.Modules.ALU;
import Emulator.Modules.Memory;
import Emulator.Modules.Prog;
import Emulator.Modules.RF;
import Emulator.ReferenceTables.OPTable;
import Simulator.Stall_Instructions.SquashInstruction;
import Simulator.Stall_Instructions.Stall_Instruction;
import Utility.Pair;

import java.util.*;

import static java.lang.System.exit;

public class Simulate {
    private List<Instruction> instructionListWithDelays;
    private Prog prog;

    public Simulate(Prog prog)
    {
        this.prog = prog;
       instructionListWithDelays = new ArrayList<>();
    }


    //getIndexInstructionBranchTaken - returns the indexes at which a branch taken occurs
   private PriorityQueue<Pair<Integer,String>> getIndexInstructionBranchTaken()
    {
        PriorityQueue<Pair<Integer,String>> listBranchTaken = new PriorityQueue<>(Comparator.comparingInt(Pair::getKey));

        //Step 1 - find all the branches in the prog
            for (int i = 0; i < prog.getInstructionList().size() && prog.stepNextInstruction() != -1; i++ ) {
                try {
                    if (prog.getInstructionList().get(i) instanceof I_Instruction
                            &&
                            (prog.getInstructionList().get(i).getOpCode() == OPTable.getOperation("beq").getM_code() &&
                                    ALU.getResult(prog.getInstructionList().get(i)) == 0)
                            ||
                            (prog.getInstructionList().get(i).getOpCode() == OPTable.getOperation("bne").getM_code() &&
                                    ALU.getResult(prog.getInstructionList().get(i)) == 1)) {
                        //Check if branch taken
                        listBranchTaken.add(new Pair<>(i,"branchTaken"));
                    }//if
                    prog.executeInstruction();
                    System.out.println(i);
                } catch (Exception e) {
                    System.out.println(e);
                    exit(-1);
                }//catch
            }//for loop
                //Step 2 - reset everything
                RF.clearRegisters();
                Memory.clearMemory();
                prog.clearPC();
                return listBranchTaken;
            }

            //getIndexInstructionUseAfterLoad - returns the indexes at which a use after load occurs
    private PriorityQueue<Pair<Integer, String>> getIndexInstructionUseAfterLoad(){

        PriorityQueue<Pair<Integer,String>> useAfterLoad = new PriorityQueue<>(Comparator.comparingInt(Pair::getKey));

        //Find all the load instruction - where i+1th instruction uses rt in load in the prog
            for (int i = 0; i < prog.getInstructionList().size()-1 && prog.stepNextInstruction() != -1; i++ ) {
                try {
                    //Step 1 - check if current instruction (ith) is a load
                    if (prog.getInstructionList().get(i) instanceof I_Instruction &&
                            prog.getInstructionList().get(i).getOpCode() == OPTable.getOperation("lw").getM_code()) {

                        I_Instruction loadInstruction = (I_Instruction) prog.getInstructionList().get(i);

                        //Step 2 - check if the next instruction (i+1) is a R - type
                        if (prog.getInstructionList().get(i + 1) instanceof RInstruction) {
                            //If the destination of the load is either operand of the R type instruction
                            if (loadInstruction.getRt() == ((RInstruction) prog.getInstructionList().get(i + 1)).getRs() ||
                                    loadInstruction.getRt() == ((RInstruction) prog.getInstructionList().get(i + 1)).getRt()) {
                                //Add index of where this happens
                                useAfterLoad.add(new Pair<>(i, "useAfterLoad"));
                            }
                        }//end of step 2
                        //Step 3 - check if the next instruction (i+1) is a I - type
                        else if (prog.getInstructionList().get(i + 1) instanceof I_Instruction) {
                            //Only one place in the immediate instruction where load destination reg could be dep on immediate
                            if (loadInstruction.getRt() == ((I_Instruction) prog.getInstructionList().get(i + 1)).getRs()) {
                                //Add index of where it happens
                            }
                            useAfterLoad.add(new Pair<>(i,"useAfterLoad"));
                        }//step 3
                    }//step 1
                    prog.executeInstruction();
                } catch (Exception e) {
                    System.out.println(e);
                    exit(-1);
                }//catch
            }//for loop

                //Step 69 - reset everything
                RF.clearRegisters();
                Memory.clearMemory();
                prog.clearPC();

                return useAfterLoad;
            }


 //getIndexInstructionJump - returns a list of indexs for where the jumps are
   private PriorityQueue<Pair<Integer,String>> getIndexInstructionJump()
    {
        PriorityQueue<Pair<Integer,String>>  listJumps = new PriorityQueue<>(Comparator.comparingInt(Pair::getKey));

        //Step 1 - find all the jumps
            for (int i = 0; i < prog.getInstructionList().size() && prog.stepNextInstruction() != -1; i++ ) {
                try {
                    if (prog.getInstructionList().get(i) instanceof JInstruction)
                    {
                        //Check if branch taken
                        listJumps.add(new Pair<>(i, "jump"));
                    }//if
                    prog.executeInstruction();
                } catch (Exception e) {
                    System.out.println(e);
                    exit(-1);
                }//catch
            }//for loop
                //Step 2 - reset everything
                RF.clearRegisters();
                Memory.clearMemory();
                prog.clearPC();

                return listJumps;
            }


            /*getDelayInstructionList - using the utility function above, inserts squash's needed*/
        public List<Instruction> getDelayInstructionList()
        {
            List<Instruction> instructionsWithDelays = new ArrayList<>();

            PriorityQueue<Pair<Integer,String>> listBranchTaken = getIndexInstructionBranchTaken();
            PriorityQueue<Pair<Integer,String>> useAfterLoad = getIndexInstructionUseAfterLoad();
            PriorityQueue<Pair<Integer,String>>  listJumps = getIndexInstructionJump();

            PriorityQueue<Pair<Integer,String>> allDelayIndexs  = new PriorityQueue<>(Comparator.comparing(Pair::getKey));

            //Step 1 - Integrate the three queues into a bigger list
            allDelayIndexs.addAll(listBranchTaken);
            allDelayIndexs.addAll(useAfterLoad);
            allDelayIndexs.addAll(listJumps);

            //Step 2 - iterate through each one and then insert needed delays based on Value() of the pair
            for(int i = 0; i< prog.getInstructionList().size(); i++)
            {
                //Add current instruction to the delayed list
                instructionsWithDelays.add(prog.getInstructionList().get(i));

                //Condition : if the list is not empty and if the indexed instruction should is a delay index
                if(allDelayIndexs.peek() != null && (allDelayIndexs.peek().getKey() == i))
                {
                    try{
                    //Insert delay to List<instruction> and remove that index from the list of indexes
                    switch (allDelayIndexs.peek().getValue())
                    {
                            case "branchTaken": //Delay 3 cycles after that instruction
                                //Insert 3 squash
                                instructionListWithDelays.add(new SquashInstruction());
                                instructionListWithDelays.add(new SquashInstruction());
                                instructionListWithDelays.add(new SquashInstruction());
                                break;
                            case "useAfterLoad": //Delay 1 cycle after
                                //insert 1 stall
                                instructionsWithDelays.add(new Stall_Instruction());
                                break;
                            case "jump": //Delay 1 cycle after
                                //insert 1 stall
                                instructionsWithDelays.add(new SquashInstruction());
                                break;
                        }//switch
                    }//try
                    catch (Exception e) {
                            System.out.print(e);
                    }//catch
                    allDelayIndexs.poll();
                }//if
            }//for
            return instructionsWithDelays;
        }



}

import static java.lang.System.exit;

public class PipelineDump implements Action {
    private final Integer pc;

    public PipelineDump(Integer pc){ this.pc = pc; }

    @Override
    public void executeAction() {
        String ifIDString = "";
        String idExString = "";
        String exMemString = "";
        String memWbString = "";

        Instruction ifId = PipelineRegisters.getIf_Id();
        Instruction idEx = PipelineRegisters.getId_Ex();
        Instruction exMem = PipelineRegisters.getEx_Mem();
        Instruction memWb = PipelineRegisters.getMem_Wb();


        Integer ifIDInstruction = PipelineRegisters.retrieveInstructionCode(PipelineRegisters.getIf_Id());
        Integer idExInstruction = PipelineRegisters.retrieveInstructionCode(PipelineRegisters.getId_Ex());
        Integer exMemInstruction = PipelineRegisters.retrieveInstructionCode(PipelineRegisters.getEx_Mem());
        Integer memWbInstruction = PipelineRegisters.retrieveInstructionCode(PipelineRegisters.getMem_Wb());


        try {//Checks to see if the instructions passed exist.
            ifIDString = OpcodeToString(ifIDInstruction);
            idExString = OpcodeToString(idExInstruction);
            exMemString = OpcodeToString(exMemInstruction);
            memWbString = OpcodeToString(memWbInstruction);
        }catch (Exception e){
            System.out.println(e);
            exit(-1);
        }

        ifIDString = (ifId instanceof StallInstruction) ? ifId.toString() : ifIDString;
        idExString = (idEx instanceof StallInstruction) ? idEx.toString() : idExString;
        exMemString = (exMem instanceof StallInstruction) ? exMem.toString() : exMemString;
        memWbString = (memWb instanceof StallInstruction) ? memWb.toString() : memWbString;


        System.out.printf("%-10s %-10s %-10s %-10s %-10s\n",
                "pc", "if/id", "id/exe", "exe/mem", "mem/wb");
        System.out.printf("%-10s %-10s %-10s %-10s %-10s\n",
                PipelineRegisters.getPcAtPipe(), ifIDString, idExString, exMemString, memWbString);

    }

    private String OpcodeToString(Integer opCode) throws Exception{
        OPTable_Item op = OPTable.getOperation(opCode);
        if(op == null){
            throw new Exception("Error PipeLineDump at PC = " + pc + " : Operation " + opCode + " does not exist.");
        }
        return op.getMnc();
    }
}

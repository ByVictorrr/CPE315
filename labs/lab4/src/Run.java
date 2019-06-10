import static java.lang.System.exit;

public class Run extends Stepper {
    public Run(Prog prog) {
        super(prog);
    }

    @Override
    public void executeAction() {
        while(prog.stepNextInstruction() != -1){
            try{
                prog.executeInstruction();
            }catch (Exception e){
                System.out.println(e);
                exit(-1);
            }
        }
    }
}

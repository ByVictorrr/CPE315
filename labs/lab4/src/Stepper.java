//A class is considered a stepper if it analyzes or executed any change to
//prog in any way or format!
public abstract class Stepper implements Action {
    protected final Prog prog;
    public Stepper(Prog prog){ this.prog = prog; }
}

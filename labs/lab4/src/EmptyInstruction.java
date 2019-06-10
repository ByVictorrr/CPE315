public class EmptyInstruction extends Instruction {
    public EmptyInstruction() throws Exception {
        super(0);
    }

    @Override
    public String toString() {
        return "empty";
    }
}

public class StallInstruction extends Instruction {
    private final String message;
    public StallInstruction(String message) throws Exception {
        super(0);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}

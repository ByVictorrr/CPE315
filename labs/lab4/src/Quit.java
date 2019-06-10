import static java.lang.System.exit;

public class Quit implements Action {
    @Override
    public void executeAction() {
        exit(0);
    }
}

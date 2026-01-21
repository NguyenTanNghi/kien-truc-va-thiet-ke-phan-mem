package state;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:09 PM
 */
public class Context {
    private State state;

    public Context(State state) { this.state = state; }
    public void setState(State state) { this.state = state; }

    public void pressButton() {
        state.pressButton(this);
    }
}
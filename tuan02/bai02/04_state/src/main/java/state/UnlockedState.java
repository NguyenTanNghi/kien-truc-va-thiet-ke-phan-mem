package state;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:10 PM
 */
public class UnlockedState implements State {
    public void pressButton(Context ctx) {
        System.out.println("Locking...");
        ctx.setState(new LockedState());
    }
}
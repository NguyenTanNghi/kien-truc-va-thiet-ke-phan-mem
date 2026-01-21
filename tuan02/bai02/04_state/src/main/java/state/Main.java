package state;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:08 PM
 */
public class Main {
    public static void main(String[] args) {
        Context door = new Context(new LockedState());
        door.pressButton(); // unlock
        door.pressButton(); // lock
        door.pressButton(); // unlock
    }
}
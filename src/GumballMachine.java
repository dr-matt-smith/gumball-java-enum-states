/**
 * The CONTEXT for the State pattern.
 *
 * Note what is NOT here:
 *   - no fields holding each state object (noQuarterState, soldState, ...)
 *   - no getters for each state
 *   - no "new XxxState(this)" calls when changing state
 *
 * The enum constants ARE the state objects, so the machine just remembers
 * which one is current, and delegates every event to it - passing "this"
 * so the (field-less) state knows which machine to act on.
 */
public class GumballMachine {

    private GumballState state;
    private int count;

    public GumballMachine(int count) {
        this.count = count;
        this.state = (count > 0) ? GumballState.NO_QUARTER : GumballState.SOLD_OUT;
    }

    // ---- data the states read and change ----
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    // ---- state change: set the new state, then run its entry action ----
    public void setState(GumballState state) {
        this.state = state;
        this.state.entry(this);
    }

    public GumballState getState() {
        return state;
    }

    // ---- EVENTS: delegate to the current state ----
    public void insertQuarter() {
        state.insertQuarter(this);
    }

    public void ejectQuarter() {
        state.ejectQuarter(this);
    }

    public void turnCrank() {
        state.turnCrank(this);
    }

    public void refill() {
        state.refill(this);
    }
}

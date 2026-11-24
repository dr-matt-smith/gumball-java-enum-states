/**
 * The STATE pattern, implemented as a Java enum.
 *
 * Each enum constant (NO_QUARTER, HAS_QUARTER, SOLD, SOLD_OUT) is a real object
 * with its OWN method bodies - Java calls these "constant-specific class bodies".
 * So each constant is really an anonymous subclass of GumballState: this is still
 * polymorphism, and still the State pattern - NOT a switch statement.
 *
 * Because the JVM creates exactly ONE instance of each constant, the states are
 * shared singletons. That only works because they hold NO fields of their own:
 * the machine they act on is passed in as a parameter to every event method.
 *
 * The methods declared at the bottom of the enum are the DEFAULTS - a constant
 * only overrides the events that are valid in that state. Everything else falls
 * through to "invalid event".
 */
public enum GumballState {

    NO_QUARTER("NO QUARTER") {
        @Override
        public void insertQuarter(GumballMachine machine) {
            // System.out.println("You inserted a quarter");
            // changeState(machine, HAS_QUARTER);
            machine.setState(HAS_QUARTER);
        }
    },

    HAS_QUARTER("HAS QUARTER") {
        @Override
        public void ejectQuarter(GumballMachine machine) {
            System.out.println("Quarter returned");
            changeState(machine, NO_QUARTER);
        }

        @Override
        public void turnCrank(GumballMachine machine) {
            System.out.println("You turned the crank...");
            changeState(machine, SOLD);
        }
    },

    SOLD("SOLD") {
        // SOLD has no valid external events - all its work is done in its
        // ENTRY action, after which it immediately decides the next state
        @Override
        public void entry(GumballMachine machine) {
            machine.setCount(machine.getCount() - 1);
            System.out.println("a gumball rolls out the machine !");

            if (machine.getCount() > 0) {
                changeState(machine, NO_QUARTER);
            } else {
                System.out.println("Oops, out of gumballs!");
                changeState(machine, SOLD_OUT);
            }
        }
    },

    SOLD_OUT("SOLD OUT") {
        @Override
        public void refill(GumballMachine machine) {
            machine.setCount(machine.getCount() + 5);
            System.out.println("added 5 gumballs !");
            changeState(machine, NO_QUARTER);
        }
    };

    // ------------------------------------------------------------------
    // shared by ALL constants
    // ------------------------------------------------------------------

    // the only field is a constant label - set once, never changes,
    // so sharing one instance between many machines is still safe
    private final String label;

    GumballState(String label) {
        this.label = label;
    }

    // DEFAULT behaviour for every event = invalid in this state
    public void insertQuarter(GumballMachine machine) { invalid("insert a quarter"); }
    public void ejectQuarter(GumballMachine machine)  { invalid("eject the quarter"); }
    public void turnCrank(GumballMachine machine)     { invalid("turn the crank"); }
    public void refill(GumballMachine machine)        { invalid("refill"); }

    // DEFAULT entry action = nothing to do
    public void entry(GumballMachine machine) { }

    protected void invalid(String event) {
        System.out.println("invalid event: can't " + event + " when in state " + this);
    }

    protected void changeState(GumballMachine machine, GumballState next) {
        System.out.println("Changing from " + this + " to " + next);
        machine.setState(next);
    }

    @Override
    public String toString() {
        return label;
    }
}

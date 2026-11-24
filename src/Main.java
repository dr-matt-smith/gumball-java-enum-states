import java.util.Scanner;

/**
 * A simple text menu for the gumball machine, run in the terminal:
 *
 *   javac -d out src/*.java && java -cp out Main
 */
public class Main {

    public static void main(String[] args) {
        GumballMachine machine = new GumballMachine(2);
        Scanner in = new Scanner(System.in);

        while (true) {
            showStatus(machine);
            showMenu();
            System.out.print("> ");

            if (!in.hasNextLine()) {
                break;
            }
            String choice = in.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1" -> machine.insertQuarter();
                case "2" -> machine.ejectQuarter();
                case "3" -> machine.turnCrank();
                case "4" -> machine.refill();
                case "q" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice \"" + choice + "\" - please enter 1, 2, 3, 4 or q");
            }
        }
        System.out.println("Goodbye!");
    }

    private static void showStatus(GumballMachine machine) {
        System.out.println("---------------------------");
        System.out.println("State: " + machine.getState());
        System.out.println("Gumballs: " + machine.getCount());
    }

    private static void showMenu() {
        System.out.println();
        System.out.println("1 - insert a quarter");
        System.out.println("2 - eject the quarter");
        System.out.println("3 - turn the crank");
        System.out.println("4 - refill");
        System.out.println("q - quit");
    }
}

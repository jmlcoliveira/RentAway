import commands.Command;
import database.*;
import outputmessages.*;

import java.util.Scanner;

/**
 * Main class of the program which read commands from console and communicates with the database.
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class Main {

    /**
     * Main method where Scanner and Database are initialized
     *
     * @param args contains the supplied command-line arguments as an array of String objects
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Database db = new DatabaseClass();
        commands(in, db);
        in.close();
    }

    /**
     * Reads a command from console and executes the method associated with that command
     *
     * @param in input where the data will be read from
     * @param db Database where this action will be performed
     */
    private static void commands(Scanner in, Database db) {
        Command command = getCommand(in);
        while (command != Command.EXIT) {
            switch (command) {
                case REGISTER:
                    break;

                case USERS:
                    break;

                case PROPERTY:
                    break;

                case PROPERTIES:
                    break;

                case BOOK:
                    break;

                case CONFIRM:
                    break;

                case REJECT:
                    break;

                case REJECTIONS:
                    break;

                case PAY:
                    break;

                case REVIEW:
                    break;

                case HOST:
                    break;

                case GUEST:
                    break;

                case STAYS:
                    break;

                case SEARCH:
                    break;

                case BEST:
                    break;

                case GLOBALTROTTER:
                    break;

                case HELP:
                    processHelpCommand(in);
                    break;
                case UNKNOWN:
                    break;

            }
            command = getCommand(in);
        }
        processExitCommand();
    }

    /**
     * Command 2.2
     * Outputs in the console all available commands except UNKNOWN command
     *
     * @param in input where the data will be read from
     */
    private static void processHelpCommand(Scanner in) {
        in.nextLine();
        for (Command c : Command.values()) {
            if (!c.name().equals("UNKNOWN"))
                System.out.printf(Success.HELP_IND, c.name().toLowerCase(), c.getDescription());
        }
    }

    /**
     * Command 2.1
     * Outputs in the console a goodbye message
     */
    private static void processExitCommand() {
        System.out.println(Success.EXIT);
    }

    /**
     * Returns Command which corresponds to the command given by the user
     *
     * @param in input where the data will be read from
     * @return Command which corresponds to the command given by the user
     */
    private static Command getCommand(Scanner in) {
        try {
            String comm = in.next().toUpperCase();
            return Command.valueOf(comm);
        } catch (Exception e) {
            return Command.UNKNOWN;
        }
    }
}

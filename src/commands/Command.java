package commands;

public enum Command {
    REGISTER("register", "adds a new user"),
    USERS("users", "lists all the registered users"),
    PROPERTY("property", "adds a new a property"),
    PROPERTIES("properties", "lists all properties of a host"),
    BOOK("book", "guest adds a new booking"),
    CONFIRM("confirm", "host confirms a booking"),
    REJECT("reject", "host rejects a booking"),
    REJECTIONS("rejections", "lists all rejected bookings by a host"),
    PAY("pay", "lists all rejected bookings by a host"),
    REVIEW("review", "adds a review to a stay"),
    GUEST("guest", "guest pays for a booking"),
    STAYS("stays", "list all stays of a property"),
    SEARCH("search", "lists all properties in a location over a given capacity"),
    BEST("best", "lists the best properties in a location"),
    GLOBALTROTTER("globaltrotter", "lists the guest who has visited more locations"),
    HELP("help", "shows the available commands"),
    EXIT("exit", "terminates the execution of the program"),
    UNKNOWN("unknown", "Unknown command. Type help to see available commands.");

    private final String description;
    private final String command;

    Command(String command, String description){
        this.command = command;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public String getCommand() {
        return command;
    }
}

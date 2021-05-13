package commands;

public enum Command {
    REGISTER("adds a new user"),
    USERS("lists all the registered users"),
    PROPERTY("adds a new a property"),
    PROPERTIES("lists all properties of a host"),
    BOOK("guest adds a new booking"),
    CONFIRM("host confirms a booking"),
    REJECT("host rejects a booking"),
    REJECTIONS("lists all rejected bookings by a host"),
    PAY("lists all rejected bookings by a host"),
    REVIEW("adds a review to a stay"),
    HOST("list statistical information about a host"),
    GUEST("list statistical information about a guest"),
    STAYS("list all stays of a property"),
    SEARCH("lists all properties in a location over a given capacity"),
    BEST("lists the best properties in a location"),
    GLOBALTROTTER("lists the guest who has visited more locations"),
    HELP("shows the available commands"),
    EXIT("terminates the execution of the program"),
    UNKNOWN("Unknown command. Type help to see available commands.");

    private final String description;

    Command(String description){
        this.description=description;
    }

    public String getDescription() {
        return description;
    }
}

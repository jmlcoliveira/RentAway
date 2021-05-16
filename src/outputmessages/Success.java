package outputmessages;

public abstract class Success {

    //Help
    public static final String HELP_IND = "%s - %s\n";

    //Users
    public static final String USER_ADDED = "User %s was registered as %s";
    public static final String USERS_LIST = "All registered users:";
    public static final String USER_LISTED = "%s: %s from %s with email %s [%s has %d proprieties]";

    //Properties
    public static final String PROPERTY_ADDED = "Property %s was added to %s %s";
    public static final String PROPERTIES_LIST = "Properties of %s %s";
    public static final String PROPERTY_LISTED = "%s: %s [%s %d %d]";

    //Bookings
    public static final String BOOK = "Booking %s-%d was confirmed";
    public static final String BOOKING_CONFIRM = "Booking %s-%d was confirmed";
    public static final String BOOKING_REJECT = "Booking %s-%d was rejected";
    public static final String BOOKING_NO_REJECTIONS = "%s %s has not rejected any bookings.";
    public static final String BOOKINGS_REJECTED_LIST = "Bookings rejected by %s %s";
    public static final String BOOKING_REJECTED_LISTED = "%s-%d: %s; %s; %s; %d";
    public static final String BOOKING_PAID = "Booking %s-%d was paid: %f";
    public static final String BOOKING_CANCELLED = "Booking %s-%d was cancelled.";
    public static final String BOOKING_REJECTED = "Booking %s-%d was rejected";

    //Reviews
    public static final String REVIEW_REGISTER = "Review for %s-%d was registered.";

    //Hosts
    public static final String HOST_NO_PROPERTIES = "Host %s has no registered properties";
    public static final String HOST_PROPERTIES_LIST = "Properties of host %s: %d; %d, %f; %f";
    public static final String HOST_PROPERTIES_LISTED = "%s: %d; %d, %f; %f";

    //Guests


    //Exit
    public static final String EXIT = "Bye!";
}

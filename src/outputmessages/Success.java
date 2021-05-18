package outputmessages;

public abstract class Success {

    //Help
    public static final String HELP_IND = "%s - %s\n";

    //Users
    public static final String USER_ADDED = "User %s was registered as %s.\n";
    public static final String USERS_LIST = "All registered users:";
    public static final String USER_LISTED_HOST = "%s: %s from %s with email %s [host user has " +
            "%d proprieties]\n";
    public static final String USER_LISTED_GUEST ="%s: %s from %s with email %s [guest user has " +
            "%d bookings]\n";

    //Properties
    public static final String PROPERTY_ADDED = "Property %s was added to host %s.\n";
    public static final String PROPERTY_HOST_LIST = "Properties of host %s:\n";
    public static final String PROPERTY_HOST_LISTED = "%s: %s %d %d [%s %d %d]\n";
    public static final String PROPERTY_LIST_STAY = "Property %s stays:";
    public static final String PROPERTY_LISTED_STAY = "%s: %s; %s; %s; %s; %d; %.2f\n";
    public static final String PROPERTY_IN_LOCATION_LIST = "Properties in %s:";
    public static final String PROPERTY_IN_LOCATION_LISTED = "%s: %f; %.2f; %d; %s";
    public static final String PROPERTY_BEST_IN_LOCATION_LIST = "The best properties in %s";
    public static final String PROPERTY_BEST_IN_LOCATION_LISTED = "%s: %.1f; %d; %f.2; %s";

    //Book
    public static final String BOOKING_REGISTER = "Booking %s was registered.\n";
    public static final String BOOKING_CONFIRM = "Booking %s was confirmed.\n";
    public static final String BOOKING_REJECT = "Booking %s was rejected.\n";
    public static final String BOOKING_NO_REJECTIONS = "%s %s has not rejected any bookings.\n";
    public static final String BOOKINGS_REJECTED_LIST = "Bookings rejected by host %s:\n";
    public static final String BOOKING_REJECTED_LISTED = "%s: %s; %s; %s; %d\n";
    public static final String BOOKING_PAID = "Booking %s was paid: %f\n";
    public static final String BOOKING_PAID_CANCEL = "Booking %s was %s.\n";

    //Reviews
    public static final String REVIEW_REGISTER = "Review for %s was registered.\n";

    //Hosts
    public static final String HOST_PROPERTIES_LIST = "Properties of host %s: %d; %d, %.1f; %.2f\n";
    public static final String HOST_PROPERTIES_LISTED = "%s: %d; %d, %.1f; %.2f\n";

    //Guests
    public static final String GUEST_BOOKING_LIST = "Guest %s bookings: %d; %d; %d; %d; %d; " +
            "%d; %.2f\n";
    public static final String GUEST_BOOKINGS_LISTED = "%s: %s; %s; %s; %s; %s, %d; %s; %.2f\n";

    //Exit
    public static final String EXIT = "Bye!";
}

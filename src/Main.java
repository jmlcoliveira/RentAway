import booking.Booking;
import booking.exceptions.*;
import commands.Command;
import database.Database;
import database.DatabaseClass;
import outputmessages.Empty;
import outputmessages.Success;
import property.Property;
import property.PropertyType;
import property.exceptions.NumGuestsExceedsCapacityException;
import property.exceptions.PropertyAlreadyExistException;
import property.exceptions.PropertyDoesNotExistException;
import property.exceptions.UnknownPropertyTypeException;
import users.Guest;
import users.Host;
import users.User;
import users.UserType;
import users.exceptions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Main class of the program which read commands from console and communicates with the database.
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class Main {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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
                    processRegister(in, db);
                    break;
                case USERS:
                    processUsers(in, db);
                    break;
                case PROPERTY:
                    processProperty(in, db);
                    break;
                case PROPERTIES:
                    processProperties(in, db);
                    break;
                case BOOK:
                    processBook(in, db);
                    break;
                case CONFIRM:
                    processConfirm(in, db);
                    break;
                case REJECT:
                    processReject(in, db);
                    break;
                case REJECTIONS:
                    processRejections(in, db);
                    break;
                case PAY:
                    processPay(in, db);
                    break;
                case REVIEW:
                    processReview(in, db);
                    break;
                case GUEST:
                    processGuest(in, db);
                    break;
                case STAYS:
                    processStays(in, db);
                    break;
                case SEARCH:
                    processSearch(in, db);
                    break;
                case BEST:
                    processBest(in, db);
                    break;
                case GLOBETROTTER:
                    processGlobeTrotter(in, db);
                    break;
                case HELP:
                    processHelpCommand(in);
                    break;
                case UNKNOWN:
                    processUnknown();
                    break;
            }
            command = getCommand(in);
        }
        processExitCommand();
    }

    /**
     * Command 2.17
     * Shows the guest that has visited more distinct locations.
     *
     * @param in input where the data will be read from
     * @param db Database where this action will be performed
     */
    private static void processGlobeTrotter(Scanner in, Database db) {
        in.nextLine();
        try {
            Guest guest = db.getGlobeTrotter();
            System.out.printf(Success.GLOBE_TROTTER_INFO, guest.getIdentifier(), guest.getVisitedLocations());
        } catch (NoGlobeTrotterException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Command 2.16
     * Lists all properties of a given location sorted by rating.
     * Read from console the location and presents all the properties in that location sorted by their average rating.
     *
     * @param in input where the data will be read from
     * @param db Database where this action will be performed
     */
    private static void processBest(Scanner in, Database db) {
        String location = in.next();

        if (db.hasProperty(location)) {
            Iterator<Property> it = db.iteratorPropertiesByAverage(location);

            System.out.printf(Success.PROPERTY_BEST_IN_LOCATION_LIST, location);
            while (it.hasNext()) {
                Property next = it.next();
                System.out.printf(Success.PROPERTY_BEST_IN_LOCATION_LISTED, next.getIdentifier(),
                        next.getAverageRating(), next.getGuestsCapacity(), next.getPrice(),
                        next.getType().getTypeValue());
            }
        } else
            System.out.printf(Empty.PROPERTY_NOT_FOUND, location);
    }

    /**
     * Command 2.15
     * Lists all properties for a given location and number of guests.
     * Reads from console the location and the number of guests, and presents all the properties in that location
     * with capacity equal or higher than the number of guests.
     *
     * @param in input where the data will be read from
     * @param db Database where this action will be performed
     */
    private static void processSearch(Scanner in, Database db) {
        String location = in.nextLine().trim();
        int numGuests = in.nextInt();

        if (db.hasProperty(location, numGuests)) {
            Iterator<Property> it = db.iteratorPropertiesByCapacity(location, numGuests);
            if (it.hasNext()) {
                System.out.printf(Success.PROPERTY_IN_LOCATION_LIST, location);

                while (it.hasNext()) {
                    Property next = it.next();
                    System.out.printf(Success.PROPERTY_IN_LOCATION_LISTED, next.getIdentifier(),
                            next.getAverageRating(), next.getPrice(), next.getGuestsCapacity(),
                            next.getType().getTypeValue());
                }
            } else
                System.out.printf(Empty.PROPERTY_NOT_FOUND, location);
        } else
            System.out.printf(Empty.PROPERTY_NOT_FOUND, location);
    }

    /**
     * Command 2.14
     * Lists all stays at a property.
     * Reads from console the property id and presents all the stays at that property.
     *
     * @param in input where the data will be read from
     * @param db Database where this action will be performed
     */
    private static void processStays(Scanner in, Database db) {
        String propertyID = in.next().trim();

        try {
            if (db.propertyHasStays(propertyID)) {
                Iterator<Booking> it = db.iteratorStaysAtProperty(propertyID);
                System.out.printf(Success.PROPERTY_LIST_STAY, propertyID);
                while (it.hasNext()) {
                    Booking next = it.next();
                    System.out.printf(Success.PROPERTY_LISTED_STAY, next.getIdentifier(),
                            next.getArrivalDate().format(formatter), next.getDepartureDate().format(formatter),
                            next.getGuest().getIdentifier(), next.getGuest().getNationality(),
                            next.getNumberOfGuests(), next.getPaidAmount());
                }
            } else {
                System.out.printf(Empty.PROPERTY_HAS_NO_STAYS, propertyID);
            }

        } catch (PropertyDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Command 2.13
     * Presents information about the bookings of a given guest.
     * Reads from console the guest user id and presents their detailed booking information.
     *
     * @param in input where the data will be read from
     * @param db Database where this action will be performed
     */
    private static void processGuest(Scanner in, Database db) {
        String guestID = in.nextLine().trim();
        try {
            if (db.guestHasBookings(guestID)) {
                Guest g = db.getGuest(guestID);
                System.out.printf(Success.GUEST_BOOKING_LIST,
                        guestID,
                        g.getTotalAmountPaid()
                );

                Iterator<Booking> it = g.iteratorBookings();
                while (it.hasNext()) {
                    Booking b = it.next();
                    Property p = b.getProperty();
                    System.out.printf(Success.GUEST_BOOKINGS_LISTED,
                            b.getIdentifier(),
                            p.getIdentifier(),
                            p.getType().getTypeValue(),
                            p.getLocation(),
                            b.getArrivalDate().format(formatter),
                            b.getDepartureDate().format(formatter),
                            b.getNumberOfGuests(),
                            b.getState().name().toLowerCase(),
                            b.getPaidAmount()
                    );
                }
            } else
                System.out.printf(Empty.GUEST_HAS_NO_BOOKINGS, guestID);
        } catch (InvalidUserTypeException | UserDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Command 2.12
     * Adds a review to an already paid booking.
     * Reads from console the booking id, the guest user id , the comment, and the classification.
     * In case of success, the review is registered into the system.
     *
     * @param in input where the data will be read from
     * @param db Database where this action will be performed
     */
    private static void processReview(Scanner in, Database db) {
        String bookingID = in.next().trim();
        String userID = in.nextLine().trim();
        String review = in.nextLine();
        String classification = in.nextLine();

        try {
            db.addReview(bookingID, userID, review, classification);
            System.out.printf(Success.REVIEW_REGISTER, bookingID);
        } catch (InvalidUserTypeForBookingException | BookingAlreadyReviewedException | CannotExecuteActionInBookingException | UserDoesNotExistException | BookingDoesNotExistException | InvalidUserTypeException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Command 2.11
     * Guest pays for a booking.
     * Reads from console the booking id, and the guest user id.
     * In case of success, the booking paid is shown along with all the bookings which had to be rejected or canceled.
     *
     * @param in input where the data will be read from
     * @param db Database where this action will be performed
     */
    private static void processPay(Scanner in, Database db) {
        String bookingID = in.next().trim();
        String userID = in.next().trim();
        in.nextLine();

        try {
            Iterator<Booking> it = db.pay(bookingID, userID);

            Booking paidBooking = it.next();
            System.out.printf(Success.BOOKING_PAID, paidBooking.getIdentifier(),
                    paidBooking.getPaidAmount());

            while (it.hasNext()) {
                Booking next = it.next();
                System.out.printf(Success.BOOKING_WAS, next.getIdentifier(),
                        next.getState().name().toLowerCase());
            }
        } catch (BookingDoesNotExistException | InvalidUserTypeException | UserDoesNotExistException | UserNotAllowedToPayBookingException
                | CannotExecuteActionInBookingException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Command 2.10
     * Lists all rejected bookings of a host.
     * Reads from console the host user id and  presents all the bookings rejected by the host,
     * even bookings automatically rejected.
     *
     * @param in input where the data will be read from
     * @param db Database where this action will be performed
     */
    private static void processRejections(Scanner in, Database db) {
        String userID = in.next().trim();
        in.nextLine();

        try {
            if (db.hostHasRejectedBookings(userID)) {
                Iterator<booking.Booking> it = db.iteratorRejections(userID);
                System.out.printf(Success.BOOKINGS_REJECTED_LIST, userID);
                while (it.hasNext()) {
                    booking.Booking b = it.next();
                    User guest = b.getGuest();
                    System.out.printf(Success.BOOKING_REJECTED_LISTED, b.getIdentifier(),
                            b.getProperty().getIdentifier(), guest.getIdentifier(), guest.getNationality(),
                            b.getNumberOfGuests());
                }
            } else
                System.out.printf(Empty.NO_REJECTED_BOOKINGS, userID);
        } catch (UserHasNoBookingsException | InvalidUserTypeException | UserDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Command 2.9
     * Host rejects a guest booking.
     * Reads from console the booking id, and the host user id.
     * In case of success, the booking is rejected.
     *
     * @param in input where the data will be read from
     * @param db Database where this action will be performed
     */
    private static void processReject(Scanner in, Database db) {
        String bookingID = in.next().trim();
        String userID = in.next().trim();

        try {
            Booking book = db.rejectBooking(bookingID, userID);
            System.out.printf(Success.BOOKING_WAS, book.getIdentifier(), book.getState().name().toLowerCase());
        } catch (BookingDoesNotExistException | UserDoesNotExistException |
                InvalidUserTypeException | InvalidUserTypeForBookingException | CannotExecuteActionInBookingException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Command 2.8
     * Host confirms a guest booking.
     * Reads from console the booking id, and the host user id.
     * In case of success lists the confirmed booking and all bookings which had to be rejected.
     *
     * @param in input where the data will be read from
     * @param db Database where this action will be performed
     */
    private static void processConfirm(Scanner in, Database db) {
        String bookingID = in.next().trim();
        String userID = in.next().trim();
        try {
            Iterator<Booking> it = db.confirmBooking(bookingID, userID);
            while (it.hasNext()) {
                Booking b = it.next();
                System.out.printf(Success.BOOKING_WAS, b.getIdentifier(), b.getState().name().toLowerCase());
            }
        } catch (CannotExecuteActionInBookingException | InvalidUserTypeException | UserDoesNotExistException |
                BookingDoesNotExistException | InvalidUserTypeForBookingException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Command 2.7
     * Adds a guest booking to the system.
     * Reads from console the guest user id, the property id, the arrival and departure date, and number of guests.
     *
     * @param in input where the data will be read from
     * @param db Database where this action will be performed
     */
    private static void processBook(Scanner in, Database db) {
        String userID = in.next().trim();
        String propertyID = in.next().trim();
        String arrival = in.next().trim();
        LocalDate arrivalDate = LocalDate.parse(arrival, formatter);
        String departure = in.next().trim();
        LocalDate departureDate = LocalDate.parse(departure, formatter);
        int numGuests = in.nextInt();
        in.nextLine();

        try {
            Booking book = db.addBooking(userID, propertyID, arrivalDate, departureDate, numGuests);
            System.out.printf(Success.BOOKING_WAS, book.getIdentifier(), book.getState().name().toLowerCase());
        } catch (UserDoesNotExistException | InvalidUserTypeException | PropertyDoesNotExistException |
                NumGuestsExceedsCapacityException | InvalidBookingDatesException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Command 2.6
     * Lists all properties of a host.
     * Reads from console the host user id and presents all their properties.
     *
     * @param in input where the data will be read from
     * @param db Database where this action will be performed
     */
    private static void processProperties(Scanner in, Database db) {
        String hostID = in.next().trim();

        try {

            if (db.hostHasProperties(hostID)) {
                Iterator<Property> it = db.iteratorPropertiesByHost(hostID);
                System.out.printf(Success.PROPERTY_HOST_LIST, hostID);
                while (it.hasNext()) {
                    Property next = it.next();
                    System.out.printf(Success.PROPERTY_HOST_LISTED,
                            next.getIdentifier(),
                            next.getLocation(),
                            next.getGuestsCapacity(),
                            next.getPrice(),
                            next.getType().getTypeValue(),
                            next.getBookingCount(),
                            next.getReviewCount()
                    );
                }
            } else {
                System.out.printf(Empty.HOST_HAS_NO_PROPERTIES, hostID);
            }

        } catch (UserDoesNotExistException | InvalidUserTypeException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Command 2.5
     * Uploads a new rental property.
     * Reads from console the rental property type and executes a method depending on the type
     *
     * @param in input where the data will be read from
     * @param db Database where this action will be performed
     */
    private static void processProperty(Scanner in, Database db) {
        PropertyType propertyType = getPropertyType(in);
        try {
            String propertyID = in.next().trim();
            String userID = in.next().trim();
            in.nextLine();
            String location = in.nextLine();
            int capacity = in.nextInt();
            int price = in.nextInt();

            switch (propertyType) {
                case ENTIRE_PLACE:
                    addEntirePlace(in, db, propertyID, userID, location, capacity, price);
                    break;
                case PRIVATE_ROOM:
                    addPrivateRoom(in, db, propertyID, userID, location, capacity, price);
                    break;
                case UNKNOWN:
                    throw new UnknownPropertyTypeException();
            }
        } catch (UnknownPropertyTypeException e) {
            in.nextLine();
            System.out.println(e.getMessage());
        } catch (UserDoesNotExistException | InvalidUserTypeException | PropertyAlreadyExistException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Registers a private room in the database
     * Receives as arguments the property id, the (host) owner user id, the location, capacity, and price per night
     * Reads from console the number of amenities and reads their value
     *
     * @param in         input where the data will be read from
     * @param db         Database where this action will be performed
     * @param propertyID ID of a property
     * @param userID     ID of a user
     * @param location   name of a location
     * @param capacity   max number of guests of a property
     * @param price      cost of a property
     * @throws UserDoesNotExistException if no user was found
     * @throws InvalidUserTypeException  if the user with that userID is not a host
     */
    private static void addPrivateRoom(Scanner in, Database db, String propertyID, String userID, String location, int capacity, int price)
            throws UserDoesNotExistException, InvalidUserTypeException {
        in.nextLine();
        int amenitiesCount = in.nextInt();
        in.nextLine();
        int count = 0;
        try {
            db.addPrivateRoom(propertyID, userID, location, capacity, price, amenitiesCount);
            System.out.printf(Success.PROPERTY_ADDED, propertyID, userID);
            while (count < amenitiesCount) {
                String amenity = in.nextLine();
                db.addAmenity(propertyID, amenity);
                count++;
            }
        } catch (PropertyAlreadyExistException e) {
            while (count < amenitiesCount) {
                in.nextLine();
                count++;
            }
            System.out.println(e.getMessage());
        }
    }

    /**
     * Registers an entire place property in the database
     * Receives as arguments the property id, the (host) owner user id, the location, capacity, and price per night
     * Reads from console the number of rooms and the place type.
     *
     * @param in         input where the data will be read from
     * @param db         Database where this action will be performed
     * @param propertyID ID of a property
     * @param userID     ID of a user
     * @param location   name of a location
     * @param capacity   max number of guests of a property
     * @param price      cost of a property
     * @throws UserDoesNotExistException     if no user was found
     * @throws InvalidUserTypeException      if the user with that userID is not a host
     * @throws PropertyAlreadyExistException if there is already a property with that propertyID
     */
    private static void addEntirePlace(Scanner in, Database db, String propertyID, String userID, String location, int capacity, int price)
            throws UserDoesNotExistException,
            PropertyAlreadyExistException, InvalidUserTypeException {
        int numberOfRooms = in.nextInt();
        String placeType = in.next().trim();
        in.nextLine();
        db.addEntirePlace(propertyID, userID, location, capacity, price, numberOfRooms, placeType);

        System.out.printf(Success.PROPERTY_ADDED, propertyID, userID);
    }

    /**
     * Command 2.4
     * Lists all registered users.
     * If there are no registered users, outputs an empty message.
     * Otherwise, it will print a header message in the first line and then print all users.
     *
     * @param in input where the data will be read from
     * @param db Database where this action will be performed
     */
    private static void processUsers(Scanner in, Database db) {
        in.nextLine();
        if (db.hasUsers()) {
            Iterator<User> it = db.iteratorUsers();

            System.out.println(Success.USERS_LIST);
            while (it.hasNext()) {
                User next = it.next();

                if (next instanceof Guest)
                    System.out.printf(Success.USER_LISTED_GUEST, next.getIdentifier(),
                            next.getName(),
                            next.getNationality(), next.getEmail(), ((Guest) next).getBookingsCount());

                if (next instanceof Host)
                    System.out.printf(Success.USER_LISTED_HOST, next.getIdentifier(),
                            next.getName(),
                            next.getNationality(), next.getEmail(), ((Host) next).numOfProperties());
            }
        } else
            System.out.println(Empty.NO_USERS);
    }

    /**
     * Command 2.3
     * Registers a user in the system.
     * The command receives as arguments the user type, which can be
     * either host or guest, followed by the user identifier, their name, nationality and email.
     *
     * @param in input where the data will be read from
     * @param db Database where this action will be performed
     */
    private static void processRegister(Scanner in, Database db) {
        UserType userType = getUserType(in);
        String identifier = in.next().trim();
        in.nextLine();
        String name = in.nextLine();
        String nationality = in.nextLine();
        String email = in.nextLine();
        try {
            switch (userType) {
                case GUEST:
                    db.addGuest(identifier, name, nationality, email);
                    break;
                case HOST:
                    db.addHost(identifier, name, nationality, email);
                    break;
                case UNKNOWN:
                    throw new UnknownUserTypeException();
            }
            System.out.printf(Success.USER_ADDED, identifier, userType.toString().toLowerCase());
        } catch (UnknownUserTypeException | UserAlreadyExistException e) {
            System.out.println(e.getMessage());
        }
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
            if (c != Command.UNKNOWN)
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

    private static void processUnknown() {
        System.out.println(Command.UNKNOWN.getDescription());
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

    /**
     * Returns UserType which corresponds to the userType given by the user
     *
     * @param in input where the data will be read from
     * @return UserType which corresponds to the userType given by the user
     */
    private static UserType getUserType(Scanner in) {
        try {
            String userType = in.next().toUpperCase();
            return UserType.valueOf(userType);
        } catch (Exception e) {
            return UserType.UNKNOWN;
        }
    }

    /**
     * Returns PropertyType which corresponds to the property given by the user
     *
     * @param in input where the data will be read from
     * @return PropertyType which corresponds to the property given by the user
     */
    private static PropertyType getPropertyType(Scanner in) {
        String propertyType = in.nextLine().trim();
        for (PropertyType p : PropertyType.values())
            if (p.getTypeValue().equalsIgnoreCase(propertyType))
                return p;
        return PropertyType.UNKNOWN;
    }
}

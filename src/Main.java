import commands.Command;
import database.*;
import exceptions.*;
import exceptions.booking.*;
import exceptions.property.*;
import exceptions.user.*;
import outputmessages.*;
import property.*;
import users.*;
import booking.Booking;

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

    private static final String DATE_PATTERN = "dd-MM-yyyy";

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
                    processUnknown(in);
                    break;
            }
            command = getCommand(in);
        }
        processExitCommand();
    }

    /**
     * 2.17
     *
     * @param in
     * @param db
     */
    private static void processGlobeTrotter(Scanner in, Database db) {
        in.nextLine();
        try {
            Guest guest = db.getGlobeTrotter();
            System.out.printf(Success.GLOBE_TROTTER_INFO, guest.getIdentifier(), guest.getVisitedLocations());
        } catch (NoGlobalTrotterException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 2.16
     *
     * @param in
     * @param db
     */
    private static void processBest(Scanner in, Database db) {
        String location = in.next();

        try {
            Iterator<Property> it = db.iteratorPropertiesByAverage(location);

            System.out.printf(Success.PROPERTY_BEST_IN_LOCATION_LIST, location);
            while (it.hasNext()) {
                Property next = it.next();
                System.out.printf(Success.PROPERTY_BEST_IN_LOCATION_LISTED, next.getIdentifier(),
                        next.getAverageRating(), next.getGuestsCapacity(), next.getPrice(),
                        next.getType().getTypeValue());
            }

        } catch (NoPropertyInLocationException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 2.15
     *
     * @param in
     * @param db
     */
    private static void processSearch(Scanner in, Database db) {
        String location = in.nextLine().trim();
        int numGuests = in.nextInt();

        try {
            Iterator<Property> it = db.iteratorPropertiesByCapacity(location, numGuests);
            System.out.printf(Success.PROPERTY_IN_LOCATION_LIST, location);

            while (it.hasNext()) {
                Property next = it.next();
                System.out.printf(Success.PROPERTY_IN_LOCATION_LISTED, next.getIdentifier(),
                        next.getAverageRating(), next.getPrice(), next.getGuestsCapacity(),
                        next.getType().getTypeValue());
            }
        } catch (NoPropertyInLocationException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 2.14
     *
     * @param in
     * @param db
     */
    private static void processStays(Scanner in, Database db) {
        String propertyID = in.next().trim();

        try {
            Iterator<Booking> it = db.iteratorStaysAtProperty(propertyID);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
            System.out.printf(Success.PROPERTY_LIST_STAY, propertyID);
            while (it.hasNext()) {
                Booking next = it.next();
                System.out.printf(Success.PROPERTY_LISTED_STAY, next.getIdentifier(),
                        next.getArrivalDate().format(formatter), next.getDepartureDate().format(formatter),
                        next.getGuest().getIdentifier(), next.getGuest().getNationality(),
                        next.getNumberOfGuests(), next.getPaidAmount());
            }
        } catch (NoPropertyInLocationException | PropertyDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 2.13
     *
     * @param in
     * @param db
     */
    private static void processGuest(Scanner in, Database db) {
        String guestID = in.nextLine().trim();
        try {
            Guest g = db.getGuest(guestID);
            System.out.printf(Success.GUEST_BOOKING_LIST,
                    guestID,
                    g.getTotalAmountPaid()
            );

            Iterator<Booking> it = g.iteratorBookings();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
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
                        b.getState().getStateValue(),
                        b.getPaidAmount()
                );
            }
        } catch (GuestHasNoBookingsException | InvalidUserTypeException | UserDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 2.12
     *
     * @param in
     * @param db
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
     * 2.11
     *
     * @param in
     * @param db
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
                        next.getState().getStateValue());
            }
        } catch (BookingDoesNotExistException | InvalidUserTypeException | UserDoesNotExistException | UserNotAllowedToPayBookingException
                | CannotExecuteActionInBookingException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * 2.10
     *
     * @param in
     * @param db
     */
    private static void processRejections(Scanner in, Database db) {
        String userID = in.next().trim();
        in.nextLine();
        try {
            Iterator<booking.Booking> it = db.iteratorRejections(userID);
            System.out.printf(Success.BOOKINGS_REJECTED_LIST, userID);
            while (it.hasNext()) {
                booking.Booking b = it.next();
                User guest = b.getGuest();
                System.out.printf(Success.BOOKING_REJECTED_LISTED, b.getIdentifier(),
                        b.getPropertyID(), guest.getIdentifier(), guest.getNationality(), b.getNumberOfGuests());
            }
        } catch (UserHasNoBookingsException | InvalidUserTypeException | UserDoesNotExistException | HostHasNotRejectedBookingsException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * 2.9
     *
     * @param in
     * @param db
     */
    private static void processReject(Scanner in, Database db) {
        String bookingID = in.next().trim();
        String userID = in.next().trim();

        try {
            Booking book = db.rejectBooking(bookingID, userID);
            System.out.printf(Success.BOOKING_WAS, book.getIdentifier(), book.getState().getStateValue());
        } catch (BookingDoesNotExistException | UserDoesNotExistException |
                InvalidUserTypeException | InvalidUserTypeForBookingException | CannotExecuteActionInBookingException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 2.8
     *
     * @param in
     * @param db
     */
    private static void processConfirm(Scanner in, Database db) {
        String bookingID = in.next().trim();
        String userID = in.next().trim();
        try {
            Iterator<Booking> it = db.confirmBooking(bookingID, userID);
            while (it.hasNext()) {
                Booking b = it.next();
                System.out.printf(Success.BOOKING_WAS, b.getIdentifier(), b.getState().getStateValue());
            }
        } catch (CannotExecuteActionInBookingException | InvalidUserTypeException | UserDoesNotExistException | BookingDoesNotExistException | InvalidUserTypeForBookingException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 2.7
     *
     * @param in
     * @param db
     */
    private static void processBook(Scanner in, Database db) {
        String userID = in.next().trim();
        String propertyID = in.next().trim();
        String arrival = in.next().trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate arrivalDate = LocalDate.parse(arrival, formatter);
        String departure = in.next().trim();
        LocalDate departureDate = LocalDate.parse(departure, formatter);
        int numGuests = in.nextInt();
        in.nextLine();

        try {
            Booking book = db.addBooking(userID, propertyID, arrivalDate, departureDate, numGuests);
            System.out.printf(Success.BOOKING_WAS, book.getIdentifier(), book.getState().getStateValue());

        } catch (UserDoesNotExistException | InvalidUserTypeException | PropertyDoesNotExistException |
                NumGuestsExceedsCapacityException | InvalidBookingDatesException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 2.6
     *
     * @param in
     * @param db
     */
    private static void processProperties(Scanner in, Database db) {
        String hostID = in.next().trim();

        try {
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

        } catch (UserDoesNotExistException | InvalidUserTypeException | NoPropertiesRegisteredException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 2.5
     *
     * @param in
     * @param db
     */
    private static void processProperty(Scanner in, Database db) {
        PropertyType propertyType = getPropertyType(in);
        try {
            switch (propertyType) {
                case ENTIRE_PLACE:
                    addEntirePlace(in, db);
                    break;
                case PRIVATE_ROOM:
                    addPrivateRoom(in, db);
                    break;
                case UNKNOWN:
                    throw new UnknownPropertyTypeException();
            }
        } catch (UnknownPropertyTypeException e) {
            String a = in.nextLine();
            a = in.nextLine();
            a = in.nextLine();
            System.out.println(e.getMessage());
        } catch (UserDoesNotExistException | InvalidUserTypeException | PropertyAlreadyExistException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addPrivateRoom(Scanner in, Database db) throws UserDoesNotExistException, InvalidUserTypeException {
        String propertyID = in.next().trim();
        String userID = in.next().trim();
        in.nextLine();
        String location = in.nextLine();
        int capacity = in.nextInt();
        int price = in.nextInt();
        in.nextLine();
        int amenities = in.nextInt();
        in.nextLine();
        int count = 0;
        try {
            db.addPrivateRoom(propertyID, userID, location, capacity, price, amenities);
            System.out.printf(Success.PROPERTY_ADDED, propertyID, userID);
            while (count < amenities) {
                String amenity = in.nextLine();
                db.addAmenity(propertyID, amenity);
                count++;
            }
        } catch (PropertyAlreadyExistException e) {
            while (count < amenities) {
                in.nextLine();
                count++;
            }
            System.out.println(e.getMessage());
        }
    }

    private static void addEntirePlace(Scanner in, Database db) throws UserDoesNotExistException,
            PropertyAlreadyExistException, InvalidUserTypeException {
        String propertyID = in.next().trim();
        String userID = in.next().trim();
        in.nextLine();
        String location = in.nextLine();
        int capacity = in.nextInt();
        int price = in.nextInt();
        int numberOfRooms = in.nextInt();
        String placeType = in.next().trim();
        in.nextLine();
        db.addEntirePlace(propertyID, userID, location, capacity, price, numberOfRooms, placeType);

        System.out.printf(Success.PROPERTY_ADDED, propertyID, userID);
    }

    /**
     * 2.4
     *
     * @param in
     * @param db
     */
    private static void processUsers(Scanner in, Database db) {
        try {
            in.nextLine();
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
        } catch (NoUsersRegisteredException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 2.3
     *
     * @param in
     * @param db
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

    private static void processUnknown(Scanner in) {
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

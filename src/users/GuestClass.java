package users;

import booking.Booking;

import java.util.List;

public class GuestClass extends UserClassAbs implements Guest{
    private List<Booking> bookings;

    public GuestClass(String identifier, String name, String nationality, String email) {
        super(identifier, name, nationality, email);
    }
}

package booking;

public enum BookingState {
    REQUESTED("requested"),
    CONFIRMED("confirmed"),
    REJECTED("rejected"),
    PAID("paid"),
    REVIEWED("reviewed"),
    CANCELLED("cancelled");

    private final String state;

    BookingState(String state) {
        this.state = state;
    }

    public String getStateValue() {
        return state;
    }
}

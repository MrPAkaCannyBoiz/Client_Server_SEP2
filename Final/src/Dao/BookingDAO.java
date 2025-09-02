package Dao;

import Model.Booking;

import java.util.List;

public interface BookingDAO {
    void addBooking(Booking booking);
    Booking getBookingById(int id);
    List<Booking> getAllBookings();
    void updateBooking(Booking booking);
    void deleteBooking(int id);
}

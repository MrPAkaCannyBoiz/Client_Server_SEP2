package Server.Command;

import Model.Booking;
import Model.Customer;
import Shared.RemoteModel;

public class CancelBookingCommand implements RemoteCommand<Void> {
  private Booking booking;
  private Customer customer;

  public CancelBookingCommand(Booking booking, Customer customer) {
    this.booking = booking;
    this.customer = customer;
  }

  @Override public Void execute(RemoteModel model)
  {
    model.cancelBooking(booking, customer);
    return null;
  }
}
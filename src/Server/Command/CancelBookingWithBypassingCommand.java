package Server.Command;

import Model.Booking;
import Shared.RemoteModel;

public class CancelBookingWithBypassingCommand implements RemoteCommand<Void>
{
  private Booking booking;

  public CancelBookingWithBypassingCommand(Booking booking) {
    this.booking = booking;
  }

  @Override public Void execute(RemoteModel model)
  {
    model.cancelBookingWithByPassing(booking);
    return null;
  }
}

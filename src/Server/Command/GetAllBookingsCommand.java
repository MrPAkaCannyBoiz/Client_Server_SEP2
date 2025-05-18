package Server.Command;

import Model.Booking;
import Shared.RemoteModel;

import java.util.List;

public class GetAllBookingsCommand implements RemoteCommand<List<Booking>>
{
  @Override public List<Booking> execute(RemoteModel model)
  {
    return model.getAllBookings();
  }
}

package Server.Command;

import Model.Customer;
import Model.Vehicle;
import Shared.RemoteModel;

import java.time.LocalDate;

public class MakeBookingByCustomerCommand implements RemoteCommand<Void>
{
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final Customer customer;
  private final Vehicle vehicle;

  public MakeBookingByCustomerCommand(LocalDate startDate, LocalDate endDate,
      Customer customer, Vehicle vehicle) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.customer = customer;
    this.vehicle = vehicle;
  }

  @Override
  public Void execute(RemoteModel model) {
    model.makeBookingByCustomer(startDate, endDate, customer, vehicle);
    System.out.println("booked vehicle");
    return null;
  }
}

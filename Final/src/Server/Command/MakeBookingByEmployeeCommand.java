package Server.Command;

import Model.Customer;
import Model.Employee;
import Model.Vehicle;
import Shared.RemoteModel;

import java.io.IOException;
import java.time.LocalDate;

public class MakeBookingByEmployeeCommand implements RemoteCommand<Void>
{
  private LocalDate startDate, endDate;
  private Employee employee;
  private Customer customer;
  private Vehicle vehicle;

  public MakeBookingByEmployeeCommand(LocalDate startDate, LocalDate endDate,
      Employee employee, Customer customer, Vehicle vehicle) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.employee = employee;
    this.customer = customer;
    this.vehicle = vehicle;
  }
  @Override public Void execute(RemoteModel model)
  {
    try {
      model.makeBookingByEmployee(startDate, endDate, employee, customer, vehicle);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return null;
  }
}

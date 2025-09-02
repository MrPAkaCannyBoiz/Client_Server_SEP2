package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Booking implements Serializable
{
  private int bookingId;
  private int customerId;
  private int employeeId;
  private LocalDate startDate;
  private LocalDate endDate;
  private Vehicle vehicle;
  private boolean isActive;
  private String vehiclePlateNumber;
  private static int bookingIdCounter;
  private double finalPayment;
  // when sending over objects Java will look for the UID, if there is none java creates one but may cause errors
  private static final long serialVersionUID = 2L;

  // Used by DAO to load bookings from database — skips validation
  public Booking(int bookingId, int customerId, int employeeId,
                 LocalDate startDate, LocalDate endDate,
                 Vehicle vehicle, boolean isActive, double finalPayment)
  {
    this.bookingId = bookingId;
    this.customerId = customerId;
    this.employeeId = employeeId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.vehicle = vehicle;
    this.isActive = isActive;
    this.finalPayment = finalPayment;
  }

  //Customer book by their own version + auto increment version
  public Booking(int customerId, LocalDate startDate,
      LocalDate endDate, Vehicle vehicle)
  {
    //I could do it with try & Catch but this way is more understandable to me.
    if (startDate.isBefore(LocalDate.now()))
    {
      throw new IllegalArgumentException("Start Date must be in today or the future.");
    }
    if (endDate.isBefore(startDate))
    {
      throw new IllegalArgumentException("End Date cannot be before start date.");
    }

    this.bookingId = bookingIdCounter++;
    this.customerId = customerId;
    this.employeeId = -1;
    this.startDate = startDate;
    this.endDate = endDate;
    this.vehicle = vehicle;
    this.isActive = true;
    this.vehiclePlateNumber= vehicle.getPlateNo();
    this.finalPayment = 0;

    Thread t0 = new Thread(this::setToInActive);
    t0.start();
  }

  //Employee book to customer version + auto increment version
  public Booking(int customerId, int employeeId, LocalDate startDate,
      LocalDate endDate, Vehicle vehicle)
  {
    //I could do it with try & Catch but this way is more understandable to me.
    if (startDate.isBefore(LocalDate.now()))
    {
      throw new IllegalArgumentException("Start Date must be in today or the future.");
    }
    if (endDate.isBefore(startDate))
    {
      throw new IllegalArgumentException("End Date cannot be before start date.");
    }

    this.bookingId = bookingIdCounter++;
    this.customerId = customerId;
    this.employeeId = employeeId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.vehicle = vehicle;
    this.vehiclePlateNumber= vehicle.getPlateNo();
    this.isActive = true;
    this.finalPayment = 0;

    Thread t0 = new Thread(this::setToInActive);
    t0.start();
  }

  public void setBookingId(int i) {
    this.bookingId = i;
  }

  public String getVehiclePlateNumber() {
    return vehicle != null ? vehicle.getPlateNo() : "-";
  }


  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public int getBookingId() {
    return bookingId;
  }

  public int getCustomerId() {
    return customerId;
  }

  public int getEmployeeId()
  {
    return employeeId;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }

  public double getFinalPayment()
  {
    return finalPayment;
  }

  public boolean getIsActive(){
    return isActive;
  }

  public String getVehiclePlateNo()
  {
    return vehicle.getPlateNo();
  }

  public void setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
  }

  public void calculatePayment()
  {
    double totalPayment = vehicle.getPricePerDay() *
        ChronoUnit.DAYS.between(startDate, LocalDate.now());
    LocalDateTime endDateAtMidnight = endDate.atStartOfDay().plusDays(1);
    if (LocalDateTime.now().isAfter(endDateAtMidnight))
    {
      double totalLateFee = ChronoUnit.HOURS.between
          (LocalDateTime.now(),endDateAtMidnight) * vehicle.getLateFee();
      double sum = totalLateFee + totalPayment;
      setFinalPayment(sum);
    }
    else if (ChronoUnit.DAYS.between(startDate,LocalDate.now()) <= 0)
    {
      double total = vehicle.getPricePerDay();
      setFinalPayment(total);
    }
    else
    {
      setFinalPayment(totalPayment);
    }
  }


  public String toString() {
    return "Booking ID:" + bookingId +
        "startDate=" + startDate +
        ", endDate=" + endDate +
        ", vehicle=" + vehicle
        ;
  }

  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass())
      return false;

    Booking other = (Booking) obj;
    return startDate.equals(other.startDate) &&
        endDate.equals(other.endDate) &&
        vehicle.equals(other.vehicle);
  }

  public boolean isActive()
  {
    return isActive;
  }

  public void setToActive()
  {
    isActive = true;
  }

  public void setToInActiveInstantly()
  {
    isActive = false;
  }

  public static void setBookingCounter(int nextId) {
    bookingIdCounter = nextId;
  }

  public void setFinalPayment(double finalPayment)
  {
    this.finalPayment = finalPayment;
  }

  public void setToInActive()
  {
    while (LocalDate.now().isBefore(endDate))
    {
      try
      {
        Thread.sleep(24 * 60 * 60 * 1000);
      }
      catch (InterruptedException e)
      {
        throw new RuntimeException(e);
      }
    }
    synchronized (this)
    {
      isActive = false;
      notifyAll();
      System.out.println("this booking is now InActive");
    }
  }
}

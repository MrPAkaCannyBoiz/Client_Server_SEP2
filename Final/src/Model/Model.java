package Model;

import Shared.RemoteModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Model implements RemoteModel,PropertyChangeSubject
{
  //TODO create ArrayList of employees
  private PropertyChangeSupport support;
  private ArrayList<Customer> customers;
  private ArrayList<Vehicle> vehicles;
  private ArrayList<Booking> allCustomerBookings;
  private ArrayList<Employee> employees;
  private static int NEXT_CUSTOMER_ID = 0;
  public Model()
  {
    this.support = new PropertyChangeSupport(this);
    this.vehicles = new ArrayList<>();
    this.customers = new ArrayList<>();
    this.allCustomerBookings = new ArrayList<>();
    this.employees = new ArrayList<>();
  }

  public void firePropertyForVehicle()
  {
    support.firePropertyChange("VehicleEvent",null,new ArrayList<>(vehicles));
  }

  public void firePropertyForCustomer()
  {
    support.firePropertyChange("CustomerEvent", null,new ArrayList<>(customers));
  }

  public void firePropertyForAllCustomerBooking()
  {
    support.firePropertyChange("AllCustomerBookingEvent",null,new ArrayList<>(allCustomerBookings));
  }

  @Override
  public void updateCustomer(Customer oldCustomer, Customer edited) {

    // 1) locate the authoritative Customer object ----------------------
    Customer target = customers.stream()
            .filter(c -> c.equals(oldCustomer))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                    "Original customer not found on server"));

    // 2) uniqueness test that skips the record we are editing ----------
    for (Customer c : customers) {

      if (c.equals(target)) continue;

      if (Objects.equals(c.getEmail(), edited.getEmail()))
        throw new IllegalArgumentException("This email is already used");

      if (Objects.equals(c.getCpr(), edited.getCpr()))
        throw new IllegalArgumentException("This CPR is already used");

      if (Objects.equals(c.getPassNo(), edited.getPassNo()))
        throw new IllegalArgumentException("This passport number is already used");

      if (Objects.equals(
              c.getDriverLicense().getLicenseNumber(),
              edited.getDriverLicense().getLicenseNumber()))
        throw new IllegalArgumentException("This licence number is already used");
    }

    // 3) copy the edited fields onto the target object -----------------
    target.setName(edited.getName());
    target.setPhoneNo(edited.getPhoneNo());
    target.setEmail(edited.getEmail());
    target.setCprAndPassNo(edited.getCpr(), edited.getPassNo());
    target.setDob(edited.getDob().toString());
    target.setNationality(edited.getNationality());
    target.setDriverLicense(edited.getDriverLicense());

    // 4) notify every client -------------------------------------------
    firePropertyForCustomer();
  }


  public void firePropertyForOneCustomerBooking(Object newValue)
  {
    support.firePropertyChange("BookingEvent",null,newValue);
  }

  public void firePropertyForEmployee()
  {
    support.firePropertyChange("EmployeeEvent",null,employees);
  }

  public void firePropertyForCurrentCustomer(Customer newValue)
  {
    support.firePropertyChange("CurrentCustomerEvent",null,newValue);
  }

  public void firePropertyForSelectedVehicle(Vehicle newValue)
  {
    support.firePropertyChange("SelectedVehicleEvent",null, newValue);
  }

  public void firePropertyForCurrentEmployee(Employee newValue)
  {
    support.firePropertyChange("CurrentEmployeeEvent",null, newValue);

  }

  ///////////////////////
  //Logics for Vehicle //
  ///////////////////////

  public void addVehicle(Vehicle vehicle)
  {
    boolean isPlateNumberUnique = true;
    for (Vehicle value : vehicles)
    {
      if (vehicle.getPlateNo().equals(value.getPlateNo()))
      {
        isPlateNumberUnique = false;
        break;
      }
    }
    if (!isPlateNumberUnique)
    {
      throw new IllegalArgumentException("The given plate number is already in the vehicle list");
    }
    vehicles.add(vehicle);
    System.out.println("\n(From Model): " + vehicle.getVehicleType() + " " + vehicle.getPlateNo() + " is added");
    System.out.println(vehicle);
    firePropertyForVehicle();
  }

  public void removeVehicle(Vehicle vehicle)
  {
    //TODO add more condition where it can't be removed unless it's available/broken state
    if (vehicle.getCurrentState() instanceof RentedState)
    {
      throw new IllegalArgumentException("Cannot remove vehicle plate no. "
          + vehicle.getPlateNo() + " as it's not available");
    }
    else
    {
      boolean removed = vehicles.remove(vehicle);
      if (removed) {
        firePropertyForVehicle();      // broadcast updated list
      } else {
        System.out.println("[Model] removeVehicle: not found -> no broadcast");
      }
    }
  }

  public void rentTheVehicle(Vehicle vehicleToRent, Customer customerWhoRents)
  {
    //find if the customerWhoRent is in the list
    for (Customer customer : customers)
    {
      boolean matchedCustomerFound = customer.equals(customerWhoRents);
      if (matchedCustomerFound)
      {
        // find if vehicleToRent are in the list
        for (Vehicle vehicle : vehicles)
        {
          boolean matchedVehicleFound = vehicle.equals(vehicleToRent);
          if (matchedVehicleFound)
          {
            vehicle.rentVehicle(customerWhoRents);
            firePropertyForVehicle();
            System.out.println("Vehicle" + vehicleToRent.getPlateNo() + " matched"
                + " with customer " + vehicle.getPlateNo() + "in the list");
            return;
          }
        }
        System.out.println("customer" + customerWhoRents.getVIAId() + " matched"
            + " with customer " + customer.getVIAId() + "in the list");
        return;
      }
      else
      {
        System.out.println(
            "customer" + customerWhoRents.getVIAId() + " does not matched"
                + " with customer " + customer.getVIAId() + "in the list");
      }
    }
  }

  public void returnTheVehicle(Vehicle vehicleToReturn, Customer customerWhoReturns) {
    // find same customer first (kept)
    for (Customer customer : customers) {
      if (!customer.equals(customerWhoReturns)) continue;

      // find the *authoritative* Vehicle object
      for (Vehicle vehicle : vehicles) {
        if (!vehicle.equals(vehicleToReturn)) continue;

        /* >>> use ‘vehicle’, NOT ‘vehicleToReturn’ <<< */
        vehicle.returnVehicle(customerWhoReturns);
        firePropertyForVehicle();
        System.out.println("Vehicle " + vehicle.getPlateNo() +
                " returned by customer " + customer.getVIAId());
        return;
      }
      // …
    }
  }

  public void returnTheVehicle(Vehicle vehicleToReturn) {
    for (Vehicle vehicle : vehicles) {
      if (!vehicle.equals(vehicleToReturn)) continue;

      vehicle.returnVehicleWithBypassing();   // use correct instance
      firePropertyForVehicle();
      return;
    }
  }


  ///////////////////////
  //Logics for customer//
  ///////////////////////

  public void addCustomer(Customer customer)
  {
    customer.setVIAId(NEXT_CUSTOMER_ID++);
    checkIfCustomerInfoIsUnique(customer);
    customers.add(customer);
    System.out.println("(From model) customer is added" + "\n" + customer);
    firePropertyForCustomer();
  }

  public void checkIfCustomerInfoIsUnique(Customer customer)
  {
    for (Customer eachCustomer : customers)
    {
      boolean sameCPR = Objects.equals(customer.getCpr(), eachCustomer.getCpr());
      boolean sameEmail = Objects.equals(customer.getEmail(), eachCustomer.getEmail());
      boolean samePassNo = Objects.equals(customer.getPassNo(), eachCustomer.getPassNo());
      boolean sameLicence = Objects.equals(customer.getDriverLicense().getLicenseNumber()
          , eachCustomer.getDriverLicense().getLicenseNumber());
      if (sameEmail)
      {
        throw new IllegalArgumentException("This email is already used");
      }
      else if (sameCPR)
      {
        throw new IllegalArgumentException("This CPR is already used");
      }
      else if (samePassNo)
      {
        throw new IllegalArgumentException("This passNo is already used");
      }
      else if (sameLicence)
      {
        throw new IllegalArgumentException("This licence number is already used");
      }
    }
  }

  public void removeCustomer(Customer customer)
  {
    customers.remove(customer);
    System.out.println("(From model) customer is removed");
    firePropertyForCustomer();
  }

  ///////////////////////
  //Logics for booking //
  ///////////////////////

  public void addTheBooking(Booking bookingToAdd, Customer customerWhoBooks)
  {
    customerWhoBooks.addBooking(bookingToAdd);
    allCustomerBookings.add(bookingToAdd);
    firePropertyForOneCustomerBooking(customerWhoBooks.getBookingList());
    System.out.println("Booking ID" + bookingToAdd.getBookingId() + " is added");
    firePropertyForAllCustomerBooking();
  }

  public void removeTheBooking(Booking bookingToRemove, Customer customerWhoBooked)
  {
    //no need condition (each customer has their own bookings)
    customerWhoBooked.removeBooking(bookingToRemove);
    allCustomerBookings.remove(bookingToRemove);
    firePropertyForCustomer();
    firePropertyForAllCustomerBooking();
    firePropertyForOneCustomerBooking(customerWhoBooked.getBookingList());
  }

  //very important method
  public void makeBookingByCustomer(LocalDate startDate, LocalDate endDate,
      Customer customerWhoBooked, Vehicle vehicleToBook)
  {
    if (customerWhoBooked.getDriverLicense().canDrive(
        vehicleToBook.getRequiredLicenseType()))
    {
      Booking booking = new Booking(customerWhoBooked.getVIAId(), startDate, endDate, vehicleToBook);
      addTheBooking(booking, customerWhoBooked);
      rentTheVehicle(vehicleToBook, customerWhoBooked);
      firePropertyForAllCustomerBooking();
      firePropertyForOneCustomerBooking(customerWhoBooked.getBookingList());
    }
    else
    {
      throw new IllegalArgumentException("You can't rent this vehicle as you don't have "
          + vehicleToBook.getRequiredLicenseType() + " category permit");
    }
  }

  public void makeBookingByEmployee(LocalDate startDate, LocalDate endDate,
      Employee employeeWhoBook, Customer customerWhoBooked, Vehicle selectedVehicle)
  {
    if (customerWhoBooked.getDriverLicense().canDrive(
        selectedVehicle.getRequiredLicenseType()))
    {
      Booking booking = new Booking(customerWhoBooked.getVIAId(), employeeWhoBook.getId(),
          startDate, endDate, selectedVehicle);
      addTheBooking(booking, customerWhoBooked);
      rentTheVehicle(selectedVehicle, customerWhoBooked);
      firePropertyForAllCustomerBooking();
      firePropertyForOneCustomerBooking(customerWhoBooked.getBookingList());
    }
    else
    {
      throw new IllegalArgumentException("You can't rent this vehicle as you don't have "
          + selectedVehicle.getRequiredLicenseType() + " category permit");
    }
  }

  public void cancelBooking(Booking bookingToCancel, Customer customerWhoBooked) {
    System.out.println("customerWhoBooked ");
    // -------- fallback #1 : viaId just like before --------------------
    if (customerWhoBooked == null) {
      int viaId = bookingToCancel.getCustomerId();
      for (Customer c : customers) {
        if (c.getVIAId() == viaId) {
          customerWhoBooked = c;
          break;
        }
      }
    }

    // -------- fallback #2 : search every customer's booking list ------
    if (customerWhoBooked == null) {
      for (Customer c : customers) {
        if (c.getBookingList().contains(bookingToCancel)) {
          customerWhoBooked = c;
          break;
        }
      }
    }

    if (customerWhoBooked == null) {
      throw new IllegalArgumentException(
              "Cannot identify customer for booking " + bookingToCancel.getBookingId());
    }

    if (bookingToCancel.getStartDate().isAfter(LocalDate.now())) {
      bookingToCancel.setToInActiveInstantly();
      returnTheVehicle(bookingToCancel.getVehicle(), customerWhoBooked);
      removeTheBooking(bookingToCancel, customerWhoBooked);
      firePropertyForVehicle();
      firePropertyForAllCustomerBooking();
      firePropertyForOneCustomerBooking(customerWhoBooked.getBookingList());
    } else {
      throw new IllegalArgumentException("Cannot cancel booking after it has started …");
    }
  }


  public void cancelBookingWithByPassing(Booking bookingToCancel) {

    boolean beforeStart = bookingToCancel.getStartDate().isAfter(LocalDate.now());
    if (!beforeStart)
      throw new IllegalArgumentException("Cannot cancel after start date");

    Vehicle v = bookingToCancel.getVehicle();

    Customer c = v.getRentedByCustomer();
    if (c == null) {                       // fallback if embedded copy is stale
      int viaId = bookingToCancel.getCustomerId();
      for (Customer cust : customers) {
        if (cust.getVIAId() == viaId) { c = cust; break; }
      }
      if (c == null)
        throw new IllegalArgumentException("Cannot identify customer for booking "
                + bookingToCancel.getBookingId());
    }

    // use the authoritative Vehicle instance
    Vehicle master = vehicles.stream()
            .filter(v::equals)
            .findFirst()
            .orElse(v);

    removeTheBooking(bookingToCancel, c);
    bookingToCancel.setToInActiveInstantly();
    master.returnVehicleWithBypassing();

    firePropertyForVehicle();
    firePropertyForAllCustomerBooking();
    firePropertyForOneCustomerBooking(c.getBookingList());
  }


  /* ------------------------------------------------------------------ */
  /* Return + pay (customer)                                            */
  /* ------------------------------------------------------------------ */
  /* -------------------------------------------------------------- */
  /* Return + pay (called from CUSTOMER page)                       */
  /* -------------------------------------------------------------- */
  public void returnBookedVehicleAndPay(Vehicle vehicleFromGui,
                                        Customer ignored) {

    String plate = vehicleFromGui.getPlateNo();

    for (Booking booking : allCustomerBookings) {

      Vehicle vCopy   = booking.getVehicle();                     // the stale copy
      Vehicle v       = vehicles.stream()                         // the master obj
              .filter(vCopy::equals)
              .findFirst()
              .orElse(vCopy);

      boolean active  = booking.isActive();
      boolean sameCar = v.getPlateNo().equals(plate);
      boolean started = !LocalDate.now().isBefore(booking.getStartDate());

      if (active && sameCar && started) {

        booking.setToInActiveInstantly();
        booking.calculatePayment();

        Customer renter = v.getRentedByCustomer();   // always non-null now

        if (renter == null) {                    // <- add this fallback block
          int id = booking.getCustomerId();
          for (Customer c : customers) {
            if (c.getVIAId() == id) { renter = c; break; }
          }
          v.setRentedByCustomer(renter);       // so returnVehicle() is happy
        }

        v.returnVehicle(renter);
        //v.returnVehicleWithBypassing();

        firePropertyForAllCustomerBooking();
        firePropertyForVehicle();
        firePropertyForOneCustomerBooking(renter.getBookingList());

        System.out.println("Returned " + plate +
                " | payment = " + booking.getFinalPayment());
        return;
      }
    }
    throw new IllegalArgumentException(
            "No active booking found for that vehicle, or return date not reached.");
  }


  /* -------------------------------------------------------------- */
  /* Return + pay (processed by EMPLOYEE)                           */
  /* -------------------------------------------------------------- */
  public void returnBookedVehicleAndPayForEmployee(Vehicle vehicleFromGui) {

    String plate = vehicleFromGui.getPlateNo();

    for (Booking booking : allCustomerBookings) {

      Vehicle vCopy   = booking.getVehicle();                     // the stale copy
      Vehicle v       = vehicles.stream()                         // the master obj
              .filter(vCopy::equals)
              .findFirst()
              .orElse(vCopy);

      boolean active  = booking.isActive();
      boolean sameCar = v.getPlateNo().equals(plate);
      boolean started = !LocalDate.now().isBefore(booking.getStartDate());

      if (active && sameCar && started) {

        booking.setToInActiveInstantly();
        booking.calculatePayment();

        Customer renter = v.getRentedByCustomer();   // always non-null now

        if (renter == null) {                    // <- add this fallback block
          int id = booking.getCustomerId();
          for (Customer c : customers) {
            if (c.getVIAId() == id) { renter = c; break; }
          }
          v.setRentedByCustomer(renter);       // so returnVehicle() is happy
        }

        v.returnVehicle(renter);
        //v.returnVehicleWithBypassing();

        firePropertyForAllCustomerBooking();
        firePropertyForVehicle();
        firePropertyForOneCustomerBooking(renter.getBookingList());

        System.out.println("(Server) Vehicle " + plate +
                " returned | payment = " + booking.getFinalPayment());
        return;
      }
    }
    throw new IllegalArgumentException(
            "No active booking found for that vehicle, or return date not reached.");
  }


  ///////////////////////
  //Logics for employee//
  ///////////////////////

  public void addEmployees(Employee employee)
  {
    employees.add(employee);
    firePropertyForEmployee();
    System.out.println("New employee added");
  }

  public void removeEmployees(Employee employee)
  {
    employees.remove(employee);
    firePropertyForEmployee();
  }


  public ArrayList<Customer> getCustomers()
  {
    return customers;
  }


  public ArrayList<Vehicle> getVehicles()
  {
    return vehicles;
  }
  @Override
  public ArrayList<Booking> getAllBookings()
  {
    return allCustomerBookings;
  }

  public ArrayList<Employee> getEmployees()
  {
    return employees;
  }

  @Override public void addPropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
  }

  @Override public void addPropertyChangeListener(String name,
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(name,listener);
  }

  @Override public void removePropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(listener);
  }

  @Override public void removePropertyChangeListener(String name,
      PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(name,listener);
  }
}

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
    support.firePropertyChange("VehicleEvent",null,vehicles);
  }

  public void firePropertyForCustomer()
  {
    support.firePropertyChange("CustomerEvent",null,customers);
  }

  public void firePropertyForAllCustomerBooking()
  {
    support.firePropertyChange("AllCustomerBookingEvent",null,allCustomerBookings);
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
      vehicles.remove(vehicle);
      firePropertyForVehicle();
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
            vehicleToRent.rentVehicle(customerWhoRents);
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

  public void returnTheVehicle(Vehicle vehicleToReturn, Customer customerWhoReturns)
  {
    // find if customerWhoReturn are in the list
    for (Customer customer : customers)
    {
      boolean matchedCustomerFound = customer.equals(customerWhoReturns);
      if (matchedCustomerFound)
      {
        // find if vehicleToReturn are in the list
        for (Vehicle vehicle : vehicles)
        {
          boolean matchedVehicleFound = vehicle.equals(vehicleToReturn);
          if (matchedVehicleFound)
          {
            vehicleToReturn.returnVehicle(customerWhoReturns);
            firePropertyForVehicle();
            System.out.println("Vehicle" + vehicleToReturn.getPlateNo() + " matched"
                + " with customer " + vehicle.getPlateNo() + "in the list");
            return;
          }
        }
        System.out.println("customer" + customerWhoReturns.getVIAId() + " matched"
            + " with customer " + customer.getVIAId() + "in the list");
        return;
      }
      else
      {
        System.out.println(
            "customer" + customerWhoReturns.getVIAId() + " does not matched"
                + " with customer " + customer.getVIAId() + "in the list");
      }
    }
  }

  public void returnTheVehicle(Vehicle vehicleToReturn)
  {
    for (Vehicle vehicle : vehicles)
    {
      boolean matchedVehicleFound = vehicle.equals(vehicleToReturn);
      if (matchedVehicleFound)
      {
        vehicleToReturn.returnVehicleWithBypassing();
        firePropertyForVehicle();
        System.out.println("Vehicle" + vehicleToReturn.getPlateNo() + " matched"
            + " with customer " + vehicle.getPlateNo() + "in the list");
        return;
      }
    }
  }

  ///////////////////////
  //Logics for customer//
  ///////////////////////

  public void addCustomer(Customer customer)
  {
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

  public void cancelBooking(Booking bookingToCancel, Customer customerWhoBooked)
  {
    boolean condition1 = bookingToCancel.getStartDate().isAfter(LocalDate.now());
    if (condition1)
    {
      bookingToCancel.setToInActiveInstantly();
      returnTheVehicle(bookingToCancel.getVehicle(),customerWhoBooked);
      removeTheBooking(bookingToCancel,customerWhoBooked);
      firePropertyForVehicle();
      firePropertyForAllCustomerBooking();
      firePropertyForOneCustomerBooking(customerWhoBooked.getBookingList());
    }
    else
    {
      throw new IllegalArgumentException("Cannot cancel booking after start rent date: "
          + bookingToCancel.getStartDate() + " (Today is " + LocalDate.now() + " )"
          + "\nUse 'Return Vehicle' to return vehicle)");
    }
  }

  public void cancelBookingWithByPassing(Booking bookingToCancel)
  {
    boolean condition1 = bookingToCancel.getStartDate().isAfter(LocalDate.now());
    if (condition1)
    {
      Customer customerGivenByRentedVehicle = bookingToCancel.getVehicle().getRentedByCustomer();
      removeTheBooking(bookingToCancel,customerGivenByRentedVehicle);
      bookingToCancel.setToInActiveInstantly();
      bookingToCancel.getVehicle().returnVehicleWithBypassing();
      firePropertyForVehicle();
      firePropertyForAllCustomerBooking();
      firePropertyForOneCustomerBooking(customerGivenByRentedVehicle.getBookingList());
    }
    else
    {
      throw new IllegalArgumentException("Cannot cancel booking after start rent date: "
          + bookingToCancel.getStartDate() + " (Today is " + LocalDate.now() + " )"
          + "\nUse 'Return Vehicle' to return vehicle)");
    }
  }

  public void returnBookedVehicleAndPay(Vehicle vehicleToReturn, Customer customerWhoBooked)
  {
    for (Booking booking : allCustomerBookings)
    {
      boolean condition1 = LocalDate.now().isAfter(booking.getStartDate());
      boolean condition2 = LocalDate.now().isEqual(booking.getStartDate());
      if (booking.isActive() && booking.getVehicle().equals(vehicleToReturn) && (condition1 || condition2))
      {
        booking.setToInActiveInstantly();
        booking.calculatePayment();
        vehicleToReturn.returnVehicle(customerWhoBooked);
        vehicleToReturn.returnVehicleWithBypassing();
        firePropertyForAllCustomerBooking();
        firePropertyForVehicle();
        firePropertyForOneCustomerBooking(customerWhoBooked.getBookingList());
        System.out.println("Returned Vehicle, this booking will be archived"
            + "\nPayment: " + booking.getFinalPayment());
        break;
      }
      else if (booking.isActive() && booking.getVehicle().equals(vehicleToReturn) && (!condition1 || !condition2))
      {
        throw new IllegalArgumentException("Your vehicle renting period is not start yet, cannot return"
            + "\nUse 'Cancel Booking' to return vehicle");
      }
    }
  }

  public void returnBookedVehicleAndPayForEmployee(Vehicle vehicleToReturn)
  {
    for (Booking booking : allCustomerBookings)
    {
      boolean condition1 = LocalDate.now().isAfter(booking.getStartDate());
      boolean condition2 = LocalDate.now().isEqual(booking.getStartDate());
      if (booking.isActive() && booking.getVehicle().equals(vehicleToReturn) && (condition1 || condition2))
      {
        booking.setToInActiveInstantly();
        booking.calculatePayment();
        Customer customerWhoRented = vehicleToReturn.getRentedByCustomer();
        vehicleToReturn.returnVehicle(customerWhoRented);
        firePropertyForAllCustomerBooking();
        firePropertyForVehicle();
        firePropertyForOneCustomerBooking(customerWhoRented.getBookingList());
        System.out.println("(From Model) returned Vehicle, this booking will be archived"
            + "\nPayment: " + booking.getFinalPayment());
        break;
      }
      else if (booking.isActive() && booking.getVehicle().equals(vehicleToReturn) && (!condition1 || !condition2))
      {
        throw new IllegalArgumentException("Your vehicle renting period is not start yet, cannot return"
            + "\nUse 'Cancel Booking' to return vehicle");
      }
    }
  }

  @Override public List<Booking> getAllBookings()
  {
    return List.of();
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

  ///////////////////////////////
  //Logics for current customer//
  ///////////////////////////////

  //  public void setCurrentCustomer(Customer currentCustomer)
  //  {
  //    boolean found = false;
  //    for (Customer customer : customers)
  //    {
  //      if (customer.equals(currentCustomer))
  //      {
  //        found = true;
  //        this.currentCustomer = currentCustomer;
  //        support.firePropertyChange("CurrentCustomerEvent", null,
  //            currentCustomer);
  //        System.out.println("(From Model): customer ID " + customer.getVIAId() +
  //            " found in the list and now it's set to current customer");
  //        break;
  //      }
  //    }
  //    if (!found)
  //    {
  //      System.out.println("Cannot find " + currentCustomer.getName() + " (VIAID: "
  //          + currentCustomer.getVIAId() + ")" + " out from customer list");
  //    }
  //  }
  //
  //  public Customer getCurrentCustomer()
  //  {
  //    return currentCustomer;
  //  }

  ///////////////////////////////
  //Logics for current employee//
  ///////////////////////////////

  //  public void setCurrentEmployee(Employee currentEmployee)
  //  {
  //    boolean found = false;
  //    for(Employee eachEmployee : employees)
  //    {
  //      if (eachEmployee.equals(currentEmployee))
  //      {
  //        found = true;
  //        this.currentEmployee = currentEmployee;
  //        support.firePropertyChange("CurrentEmployeeEvent",
  //            null,currentEmployee);
  //        System.out.println("(From Model): employee ID " + currentEmployee.getId()
  //            + "found in the list and now it's set to current employee");
  //        break;
  //      }
  //    }
  //    if (!found)
  //    {
  //      System.out.println("Cannot find " + currentEmployee.getName() + " (ID: "
  //          + currentEmployee.getId() + ")" + " out from employee list");
  //    }
  //  }
  //
  //  public Employee getCurrentEmployee()
  //  {
  //    return currentEmployee;
  //  }

  public ArrayList<Customer> getCustomers()
  {
    return customers;
  }

  @Override public void addEmployee(Employee employee)
  {
    addEmployees(employee);
  }

  @Override public void removeEmployee(Employee employee)
      throws IOException
  {

  }


  public ArrayList<Vehicle> getVehicles()
  {
    return vehicles;
  }

  public ArrayList<Booking> getAllCustomerBookings()
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

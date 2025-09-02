  package Model;

  import java.io.Serializable;

  public class Vehicle implements Serializable
  {
    private String model, color, engine, plateNo, brand;
    private String vehicleType;
    private double pricePerDay, deposit, lateFee;
    private int noOfTyre, noOfSeats;
    private String requiredLicenseType;
    private VehicleState currentState;
    private String currentStatus;
    private String VIAVehicleId;
    private Customer rentedByCustomer;
    // when sending over objects Java will look for the UID, if there is none java creates one but may cause errors
    private static final long serialVersionUID = 5L;
    public Vehicle(String brand, String model, String color, String engine, String plateNo,
        double pricePerDay, double deposit, double lateFee, int noOfTyre,
        int noSeats, String requiredLicenseType, String vehicleType)
    {
      setBrand(brand);
      setModel(model);
      setColor(color);
      setEngine(engine);
      setPlateNo(plateNo);
      setPricePerDay(pricePerDay);
      setDeposit(deposit);
      setLateFee(lateFee);
      setNoOfTyre(noOfTyre);
      setNoOfSeats(noSeats);
      this.requiredLicenseType = requiredLicenseType;
      setVehicleType(vehicleType);
      this.currentState = new AvailableState();
      this.currentStatus = currentState.getStatus();
      this.VIAVehicleId = ""; //set it on Model later (CK)
      this.rentedByCustomer = null; //null may not worked, if it's, we could use empty String instead
    }


    public String getModel()
    {
      return model;
    }

    public void setModel(String model)
    {
      if (model.isEmpty())
      {
        throw new IllegalArgumentException("Model is empty");
      }
      this.model = model;
    }

    public String getColor()
    {
      return color;
    }

    public void setColor(String color)
    {
      if (color.isEmpty())
      {
        throw new IllegalArgumentException("Color is empty/not selected");
      }
      this.color = color;
    }

    public String getEngine()
    {
      return engine;
    }

    public void setEngine(String engine)
    {
      if (engine.isEmpty())
      {
        throw new IllegalArgumentException("Engine is empty/not selected");
      }
      this.engine = engine;
    }

    public String getPlateNo()
    {
      return plateNo;
    }

    public void setPlateNo(String plateNo)
    {
      if (plateNo == null || !plateNo.matches("[A-Z]{2}\\d{5}"))
      {
        throw new IllegalArgumentException(
            "Invalid plate number. Format: 2 uppercase letters followed by 5 digits (e.g., AB12345)"
        );
      }
      this.plateNo = plateNo;
    }

    public double getPricePerDay()
    {
      return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay)
    {
      if (pricePerDay <= 0)
      {
        throw new IllegalArgumentException("Price can't be free or negative amount");
      }
      this.pricePerDay = pricePerDay;
    }

    public double getDeposit()
    {
      return deposit;
    }

    public void setDeposit(double deposit)
    {
      if (deposit <= 0)
      {
        throw new IllegalArgumentException("Price can't be free or negative amount");
      }
      this.deposit = deposit;
    }

    public double getLateFee()
    {
      return lateFee;
    }

    public void setLateFee(double lateFee)
    {
      if (lateFee <= 0)
      {
        throw new IllegalArgumentException("Price can't be free or negative amount");
      }
      this.lateFee = lateFee;
    }

    public int getNoOfTyre()
    {
      return noOfTyre;
    }

    public void setNoOfTyre(int noOfTyre)
    {
      this.noOfTyre = noOfTyre;
    }

    public int getNoOfSeats()
    {
      return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats)
    {
      if (noOfSeats <= 0)
      {
        throw new IllegalArgumentException("Vehicle should have at least 1 seat");
      }
      this.noOfSeats = noOfSeats;
    }

    public String getRequiredLicenseType()
    {
      return requiredLicenseType;
    }

    public void setReDriverLice(String requiredLicenseType)
    {
      this.requiredLicenseType = requiredLicenseType;
    }

    public VehicleState getCurrentState()
    {
      return currentState;
    }

    public String getVIAVehicleId()
    {
      return VIAVehicleId;
    }

    public String getStatus()
    {
      return currentState.getStatus();
    }

    public void setStatus(String status) {
      this.currentStatus = status;

      if (status.equalsIgnoreCase("Available")) {
        this.currentState = new AvailableState();
      } else if (status.equalsIgnoreCase("Rented")) {
        this.currentState = new RentedState();
      } else {
        throw new IllegalArgumentException("Unknown status: " + status);
      }
    }


    public void setVIAVehicleId(String VIAVehicleId)
    {
      this.VIAVehicleId = VIAVehicleId;
    }

    public void setRentedByCustomer(Customer rentedByCustomer)
    {
      this.rentedByCustomer = rentedByCustomer;
    }

    public Customer getRentedByCustomer()
    {
      return rentedByCustomer;
    }

    public void rentVehicle(Customer rentedByCustomer)
    {
      boolean allowed = rentedByCustomer.getDriverLicense().canDrive(requiredLicenseType);
      if (allowed)
      {
        getCurrentState().onRentVehicle(this);
        setRentedByCustomer(rentedByCustomer);
      }
      else
      {
        throw new IllegalArgumentException((rentedByCustomer.getName() + " doesn't have permission to drive"
            + requiredLicenseType + " category. Can't rent" + plateNo));
      }
    }

    public void returnVehicle(Customer customerWhoRentedThis) {

      /* 1 ── protect against stale copies  ------------------------------ */
      if (rentedByCustomer == null) {               // ← was the NPE
        // -- Either the copy inside Booking never got the field set,
        //    or the vehicle was already force-returned.
        currentState.onReturnVehicle(this);
        return;                                   // simply treat as ok
      }

      /* 2 ── normal consistency check  --------------------------------- */
      if (!rentedByCustomer.equals(customerWhoRentedThis))
        throw new IllegalArgumentException(customerWhoRentedThis.getName()
                + " did not rent vehicle " + plateNo);

      /* 3 ── state change  --------------------------------------------- */
      currentState.onReturnVehicle(this);
      rentedByCustomer = null;
    }



    public void returnVehicleWithBypassing()
    {
      getCurrentState().onReturnVehicle(this);
      setRentedByCustomer(null);
    }

    public void setCurrentState(VehicleState currentState)
    {
      this.currentState = currentState;
    }


    public String toString(){
  return "Brand: " + brand + "\nModel: " + this.model + "\nColor: " +  this.color + "\nEngine: " + this.engine + "\nPlateNo: " + this.plateNo + "\nDeposit : " + this.deposit+" DKK" + "\nPrice/Day: " + this.pricePerDay+" DKK"
  + "\nNumber of tires: "+ this.noOfTyre + "\nLate fee: " + this.lateFee + " DKK"+ "\nRequired driver license: "+ this.requiredLicenseType + "\nVehicle state: "+ this.getStatus()+ "\nNumber of seats: "+ this.noOfSeats;
    }

  //  public boolean equals(Object obj)
  //  {
  //    if (obj == null || getClass() != obj.getClass())
  //      return false;
  //    Vehicle vehicleV2 = (Vehicle) obj;
  //
  //      return model.equals(vehicleV2.model) && color.equals(vehicleV2.color)
  //          && engine.equals(vehicleV2.engine) && plateNo.equals(
  //          vehicleV2.plateNo) && this.pricePerDay == vehicleV2.pricePerDay
  //          && this.deposit == vehicleV2.deposit
  //          && this.noOfTyre == vehicleV2.noOfTyre
  //          && this.lateFee == vehicleV2.lateFee
  //          && requiredLicenseType.equals(vehicleV2.requiredLicenseType)
  //          && this.currentState == vehicleV2.currentState
  //          && this.noOfSeats == vehicleV2.noOfSeats;
  //    }

    public String getCurrentStatus()
    {
      return currentStatus;
    }

    public String getBrand()
    {
      return brand;
    }

    public void setBrand(String brand)
    {
      if (brand.isEmpty())
      {
        throw new IllegalArgumentException("Brand is not select");
      }
      this.brand = brand;
    }

    public String getVehicleType()
    {
      return vehicleType;
    }

    public void setVehicleType(String vehicleType)
    {
      if (vehicleType.isEmpty())
      {
        throw new IllegalArgumentException("Vehicle type is not select");
      }
      this.vehicleType = vehicleType;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Vehicle v)) return false;
      return plateNo.equals(v.plateNo);   // plate number is unique
    }

    @Override
    public int hashCode() {
      return plateNo.hashCode();
    }

  }


package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer implements Serializable
{
  private List<Booking> bookingList;
  private String name;
  private String phoneNo;
  private String email;
  private DriverLicense driverLicense;
  private String nationality;
  private LocalDate dob;
  private String cpr;
  private String passNo;
  private int VIAId;
  private static int VIAIdCounter = 0;
  // when sending over objects Java will look for the UID, if there is none ja creates one but may cause errors
  private static final long serialVersionUID = 1L;

  public Customer(int VIAId, String cpr, String passNo, String name, String phoneNo, String email, DriverLicense driverLicense, String nationality, String dob)
  {
    this.VIAId = VIAId; //set it on Model later
    this.bookingList = new ArrayList<>();
    setName(name);
    setPhoneNo(phoneNo);
    setEmail(email);
    this.driverLicense = Objects.requireNonNull(driverLicense, "Driver License cannot be null");
    setNationality(nationality);
    this.dob = parseValidDate(dob);
    setCprAndPassNo(cpr, passNo);
  }

  public Customer(String cpr, String passNo, String name, String phoneNo, String email, DriverLicense driverLicense, String nationality, String dob)
  {
    this.VIAId = VIAIdCounter++;
    this.bookingList = new ArrayList<>();
    setName(name);
    setPhoneNo(phoneNo);
    setEmail(email);
    this.driverLicense = Objects.requireNonNull(driverLicense, "Driver License cannot be null");
    setNationality(nationality);
    this.dob = parseValidDate(dob);
    setCprAndPassNo(cpr, passNo);
  }

  private LocalDate parseValidDate(String dateStr)
  {
    try
    {
      return LocalDate.parse(dateStr); // this return in ISO format yyyy-MM-dd
    }
    catch (DateTimeParseException e)
    {
      throw new IllegalArgumentException("Date of Birth must be in format yyyy-MM-dd");
    }
  }

  public String getCpr() {
    return cpr;
  }

  public int getVIAIdCounter()
  {
    return VIAIdCounter;
  }

  public String getPassNo() {
    return passNo;
  }

  public String getName() {
    return name;
  }

  public String getPhoneNo() {
    return phoneNo;
  }

  public String getEmail() {
    return email;
  }

  public DriverLicense getDriverLicense() {
    return driverLicense;
  }

  public String getNationality() {
    return nationality;
  }

  public LocalDate getDob() {
    return dob;
  }

  public List<Booking> getBookingList()
  {
    return bookingList;
  }

  public int getVIAId()
  {
    return VIAId;
  }

  public void setVIAId(int VIAId)
  {
    this.VIAId = VIAId;
  }

  public void setCpr(String cpr)
  {
    if ((cpr == null || cpr.isEmpty()) && (passNo == null || passNo.isEmpty()))
    {
      throw new IllegalArgumentException("Both CPR and Passport number cannot be empty.");
    }
    if (cpr != null && !cpr.isEmpty() && cpr.length() != 10)
    {
      throw new IllegalArgumentException("CPR number must be exactly 10 characters.\nCurrent CPR length: " + cpr.length());
    }
    if (passNo != null && !passNo.isEmpty() && passNo.length() > 9)
    {
      throw new IllegalArgumentException("Passport number can't be more than 9 characters.\nCurrent Passport length: " + passNo.length());
    }
    this.cpr = cpr;
  }

  public void setPassNo(String passNo)
  {
    if ((cpr == null || cpr.isEmpty()) && (passNo == null || passNo.isEmpty()))
    {
      throw new IllegalArgumentException("Both CPR and Passport number cannot be empty.");
    }
    if (cpr != null && !cpr.isEmpty() && cpr.length() != 10)
    {
      throw new IllegalArgumentException("CPR number must be exactly 10 characters.\nCurrent CPR length: " + cpr.length());
    }
    if (passNo != null && passNo.length() > 9)
    {
      throw new IllegalArgumentException("Passport number can't be more than 9 characters.\nCurrent Passport length: " + passNo.length());
    }
    this.passNo = passNo;
  }

  public void setCprAndPassNo(String cpr, String passNo)
  {
    if ((cpr == null || cpr.isEmpty()) && (passNo == null || passNo.isEmpty()))
    {
      throw new IllegalArgumentException("Both CPR and Passport number cannot be empty.");
    }
    if (cpr != null && !cpr.isEmpty() && cpr.length() != 10)
    {
      throw new IllegalArgumentException("CPR number must be exactly 10 characters.\nCurrent CPR length: " + cpr.length());
    }
    if (passNo != null && passNo.length() > 9)
    {
      throw new IllegalArgumentException("Passport number can't be more than 9 characters.\nCurrent Passport length: " + passNo.length());
    }
    this.cpr = cpr;
    this.passNo = passNo;
  }

  public void setName(String name)
  {
    if (name == null || name.isEmpty())
    {
      throw new IllegalArgumentException("Name can't be null or empty");
    }
   this.name = name;
  }

  public void setPhoneNo(String phoneNo)
  {
    if (phoneNo == null || phoneNo.isEmpty())
    {
      throw new IllegalArgumentException("Phone number can't be null or empty");
    }
    this.phoneNo = phoneNo;
  }

  public void setEmail(String email)
  {
    if (email == null || email.isEmpty())
    {
      throw new IllegalArgumentException("Email can't be null or empty");
    }
    this.email = email;
  }

  public void setDriverLicense(DriverLicense driverLicense)
  {
    this.driverLicense = driverLicense;
  }

  public void setNationality(String nationality)
  {
    if (nationality == null || nationality.isEmpty())
    {
      throw new IllegalArgumentException("nationality can't be null or empty");
    }
    this.nationality = nationality;
  }

  //method for CPR number
  public boolean hasCPR(){
    return cpr != null && !cpr.isEmpty();
  }

  //method for the passport
  public boolean hasPassport(){
    return passNo != null && !passNo.isEmpty();
  }

  public void setDob(String dob) {
    this.dob = parseValidDate(dob);
  }

  public void addBooking(Booking booking)
  {
    bookingList.add(booking);
  }

  public void removeBooking(Booking booking)
  {
    bookingList.remove(booking);
  }

  @Override
  public String toString()
  {
    return
            "  Name: '" + name + '\'' +
            ", PhoneNo: '" + phoneNo + '\'' +
            ", Email='" + email + '\'' +
            ", DriverLicense: " + driverLicense +
            ", Nationality: '" + nationality + '\'' +
            ", DOB: '" + dob + '\'' +
            ", CPR: '" + cpr + '\'' +
            ", Passport No: '" + passNo + '\'';
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Customer other = (Customer) obj;
    return this.getVIAId() == other.getVIAId();
  }

  @Override
  public int hashCode() {
    return Integer.hashCode(getVIAId());
  }


//  @Override public boolean equals(Object object)
//  {
//    if (this == object)
//      return true;
//    if (object == null || getClass() != object.getClass())
//      return false;
//    Customer customer = (Customer) object;
//    return getVIAId() == customer.getVIAId() && Objects.equals(getBookingList(),
//        customer.getBookingList()) && Objects.equals(getName(),
//        customer.getName()) && Objects.equals(getPhoneNo(),
//        customer.getPhoneNo()) && Objects.equals(getEmail(),
//        customer.getEmail()) && Objects.equals(getDriverLicense(),
//        customer.getDriverLicense()) && Objects.equals(getNationality(),
//        customer.getNationality()) && Objects.equals(getDob(),
//        customer.getDob()) && Objects.equals(getCpr(), customer.getCpr())
//        && Objects.equals(getPassNo(), customer.getPassNo());
//  }
//
//  @Override public int hashCode()
//  {
//    return Objects.hash(getBookingList(), getName(), getPhoneNo(), getEmail(),
//        getDriverLicense(), getNationality(), getDob(), getCpr(), getPassNo(),
//        getVIAId());
//  }
}

package ViewModel;


import Model.Customer;
import Model.DriverLicense;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Shared.RemoteModel;
import java.time.LocalDate;

public class NewCustomerViewModel
{
  private RemoteModel model;
  private Customer selectedCustomer;
  //for customer
  private StringProperty name, phoneNo, email, cpr, passNo;
  private ObjectProperty<LocalDate> dob;
  //list for nationality
  private StringProperty selectedNationality;
  private ObservableList<String> nationality;

  //for driving licence
  private StringProperty licenceNumber;
  private BooleanProperty isCategoryA, isCategoryB, isCategoryC, isCategoryD, isCategoryX;

  public NewCustomerViewModel(RemoteModel model)
  {
    this.model = model;
//    DriverLicense emptyLicense =
//        new DriverLicense("EMPTY" ,false,false
//            ,false,false,false);
//    this.selectedCustomer =
//        new Customer(0, "1111111111","unknown","dupa",
//            "321321","dupa1",emptyLicense,"idk1","1000-12-11");
    this.name = new SimpleStringProperty("");
    this.phoneNo = new SimpleStringProperty("");
    this.email = new SimpleStringProperty("");
    this.dob = new SimpleObjectProperty<>();
    this.cpr = new SimpleStringProperty("");
    this.passNo = new SimpleStringProperty("");
    this.licenceNumber = new SimpleStringProperty("");
    this.isCategoryA = new SimpleBooleanProperty(false);
    this.isCategoryB = new SimpleBooleanProperty(false);
    this.isCategoryC = new SimpleBooleanProperty(false);
    this.isCategoryD = new SimpleBooleanProperty(false);
    this.isCategoryX = new SimpleBooleanProperty(false);

    this.selectedNationality = new SimpleStringProperty();
    this.nationality = FXCollections.observableArrayList(
        "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina",
        "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados",
        "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina",
        "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia",
        "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros",
        "Congo (Congo-Brazzaville)", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic",
        "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic",
        "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini",
        "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana",
        "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras",
        "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy",
        "Ivory Coast", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait",
        "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein",
        "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta",
        "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco",
        "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal",
        "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "North Macedonia",
        "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay",
        "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda",
        "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa",
        "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles",
        "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia",
        "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname",
        "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste",
        "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu",
        "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay",
        "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"
    );
  }

  public void addCustomer()
  {
    if (dob.get() != null)
    {
      DriverLicense newLicense = new DriverLicense(licenceNumber.get(),
          isCategoryA.get(), isCategoryB.get(), isCategoryC.get(),
          isCategoryD.get(), isCategoryX.get()
      );
      Customer newCustomer = new Customer(
          cpr.get(), passNo.get(), name.get(), phoneNo.get(), email.get(), newLicense,
          selectedNationality.get(), dob.get().toString()
      );
      model.addCustomer(newCustomer);
    }
    else
    {
      throw new NullPointerException("Date of birth have to filled");
    }
  }

  public void addCustomerAndSetToCurrentCustomer()
  {
    if (dob.get() != null)
    {
      DriverLicense newLicense = new DriverLicense(licenceNumber.get(),
          isCategoryA.get(), isCategoryB.get(), isCategoryC.get(),
          isCategoryD.get(), isCategoryX.get()
      );
      Customer newCustomer = new Customer(
          cpr.get(), passNo.get(), name.get(), phoneNo.get(), email.get(), newLicense,
          selectedNationality.get(), dob.get().toString()
      );
      model.addCustomer(newCustomer);
      setSelectedCustomer(newCustomer);
    }
    else
    {
      throw new NullPointerException("Date of birth have to filled");
    }
  }

  public void checkIfCustomerInfoIsUnique(Customer customer)
  {
    model.checkIfCustomerInfoIsUnique(customer);
  }

  public StringProperty nameProperty()
  {
    return name;
  }

  public StringProperty phoneNoProperty()
  {
    return phoneNo;
  }

  public StringProperty emailProperty()
  {
    return email;
  }

  public StringProperty cprProperty()
  {
    return cpr;
  }

  public StringProperty passNoProperty()
  {
    return passNo;
  }

  public ObjectProperty<LocalDate> dobProperty()
  {
    return dob;
  }

  public StringProperty selectedNationalityProperty()
  {
    return selectedNationality;
  }

  public ObservableList<String> getNationalityList()
  {
    return nationality;
  }

  public StringProperty licenceNumberProperty()
  {
    return licenceNumber;
  }

  public BooleanProperty isCategoryAProperty()
  {
    return isCategoryA;
  }

  public BooleanProperty isCategoryBProperty()
  {
    return isCategoryB;
  }

  public BooleanProperty isCategoryCProperty()
  {
    return isCategoryC;
  }

  public BooleanProperty isCategoryDProperty()
  {
    return isCategoryD;
  }

  public BooleanProperty isCategoryXProperty()
  {
    return isCategoryX;
  }

  public Customer getSelectedCustomer()
  {
    return selectedCustomer;
  }

  public void setSelectedCustomer(Customer selectedCustomer)
  {
    this.selectedCustomer = selectedCustomer;
  }
}

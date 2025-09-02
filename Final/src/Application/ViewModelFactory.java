package Application;

import Shared.RemoteModel;
import ViewModel.*;

public class ViewModelFactory
{
  private AddVehicleViewModel addVehicleViewModel;
  private VehicleBookingViewModel vehicleBookingViewModel;
  private NewCustomerViewModel newCustomerViewModel;
  private ChooseCustomerViewModel chooseCustomerViewModel;
  private NewBookingViewModel newBookingViewModel;
  private ChooseEmployeeViewModel chooseEmployeeViewModel;
  private NewEmployeeViewModel newEmployeeViewModel;

  private RemoteModel model;

  public ViewModelFactory(RemoteModel model)
  {
    this.model = model;
  }

  public AddVehicleViewModel getAddVehicleViewModel()
  {
    if (addVehicleViewModel == null)
    {
      addVehicleViewModel = new AddVehicleViewModel(model);
    }
    return addVehicleViewModel;
  }

  public VehicleBookingViewModel getVehicleBookingViewModel()
  {
    if (vehicleBookingViewModel == null)
    {
      vehicleBookingViewModel = new VehicleBookingViewModel
          (model);
    }
    return vehicleBookingViewModel;
  }

  public NewCustomerViewModel getNewCustomerViewModel()
  {
    if (newCustomerViewModel == null)
    {
      newCustomerViewModel = new NewCustomerViewModel(model);
    }
    return newCustomerViewModel;
  }

  public ChooseCustomerViewModel getChooseCustomerViewModel()
  {
    if (chooseCustomerViewModel == null)
    {
      chooseCustomerViewModel = new ChooseCustomerViewModel(model);
    }
    return chooseCustomerViewModel;
  }

  public NewBookingViewModel getNewBookingViewModel()
  {
    if (newBookingViewModel == null)
    {
      newBookingViewModel = new NewBookingViewModel(model);
    }
    return newBookingViewModel;
  }

  public ChooseEmployeeViewModel getChooseEmployeeViewModel()
  {
    if (chooseEmployeeViewModel == null)
    {
      chooseEmployeeViewModel = new ChooseEmployeeViewModel(model);
    }
    return chooseEmployeeViewModel;
  }

  public NewEmployeeViewModel getNewEmployeeViewModel()
  {
    if (newEmployeeViewModel == null)
    {
      newEmployeeViewModel = new NewEmployeeViewModel(model);
    }
    return newEmployeeViewModel;
  }

}

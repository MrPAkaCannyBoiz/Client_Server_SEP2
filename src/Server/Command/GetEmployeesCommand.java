package Server.Command;

import Model.Employee;
import Shared.RemoteModel;

import java.util.List;

public class GetEmployeesCommand implements RemoteCommand<List<Employee>> {

  @Override public List<Employee> execute(RemoteModel model)
  {
    return model.getEmployees();
  }
}


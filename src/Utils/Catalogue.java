package Utils;

import Model.Customer;
import Model.Vehicle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Catalogue
{
  private static Catalogue instance;
  private List<String> usageLog;

  private Catalogue(){
    usageLog= new ArrayList<>();
  }
public static synchronized Catalogue getInstance(){
    if(instance==null){
      instance = new Catalogue();
    }
return instance;
  }
public void logUsage(Customer customer, Vehicle vehicle, String event){
  // if there is a simple way look into it
    String logEntry = String.format("[%s] Customer '%s' (ID: %d) %s vehicle '%s' (%s)",
      LocalDateTime.now(),
      customer.getName(),
      customer.getVIAId(),
      event,
      vehicle.getModel(),
      vehicle.getPlateNo()
  );
  usageLog.add(logEntry);
  System.out.println(logEntry);
}
public List<String> getUsageLog(){
    return new ArrayList<>(usageLog);
}
public void printLogs(){
    usageLog.forEach(System.out::println);
}
}

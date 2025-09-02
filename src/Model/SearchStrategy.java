package Model;

import javafx.collections.ObservableList;

public interface SearchStrategy
{
  ObservableList<Vehicle> search(ObservableList<Vehicle> vehicles);
}



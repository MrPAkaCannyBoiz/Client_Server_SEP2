package Command;

import Shared.RemoteModel;

import java.io.Serializable;

public interface Command extends Serializable
{
  void execute(RemoteModel model) throws Exception; // Executes the action on the server-side Model
  Object getResult(); // For returning data (e.g., query results)
}

package Application;

import Client.ClientSocketHandler;
import Model.*;
import Server.RemoteModelManager;
import Shared.RemoteModel;

import java.io.IOException;

public class ModelFactory
{
  private RemoteModel model;

  public RemoteModel getModel() throws IOException
  {
    if (model == null)
    {
      model = new ClientSocketHandler("localhost", 12345);
    }
    return model;
  }
}

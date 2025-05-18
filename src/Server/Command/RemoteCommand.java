package Server.Command;

import Shared.RemoteModel;

import java.io.IOException;
import java.io.Serializable;

public interface RemoteCommand<T> extends Serializable
{
  T execute(RemoteModel model) throws IOException;
}

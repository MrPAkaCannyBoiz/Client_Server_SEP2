package Server;

import Command.Command;
import Server.Command.RemoteCommand;
import Shared.RemoteModel;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerHandler implements Runnable {
  private Socket socket;
  private RemoteModel model;
  private ObjectInputStream in;
  private ObjectOutputStream out;

  public ServerHandler(Socket socket, RemoteModel model, ObjectInputStream in, ObjectOutputStream out) {
    this.socket = socket;
    this.model = model;
    this.in = in;
    this.out = out;
  }

  @Override
  public void run() {
    try {
      while (true) {
        Object obj = in.readObject();
        if (obj instanceof RemoteCommand<?> command) {
          Object result = command.execute(model);
          out.writeObject(result);
          out.flush();
        }
      }
    } catch (Exception e) {
      System.err.println("[Server] Connection error: " + e.getMessage());
    }
  }
}
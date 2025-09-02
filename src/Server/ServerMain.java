package Server;

import Shared.RemoteModel;
import Model.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServerMain
{
  private static final int PORT = 12345;
  private static List<ObjectOutputStream> clientOutputs = new ArrayList<>();


  public static void main(String[] args)
  {
    Model rawModel = new Model();
    RemoteModelManager modelWithBroadcast = new RemoteModelManager(rawModel);

    rawModel.addPropertyChangeListener(evt ->
    {
      synchronized (clientOutputs) {
        for (ObjectOutputStream out : clientOutputs) {
          try {
            out.reset();
            out.writeObject(evt);
            out.flush();
          } catch (IOException e) {
            System.err.println("[Server] Failed to send event: " + e.getMessage());
          }
        }
      }
    });

    try (ServerSocket serverSocket = new ServerSocket(PORT))
    {
      System.out.println("Server is running on port " + PORT);
      while (true)
      {
        Socket clientSocket = serverSocket.accept();
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

        synchronized (clientOutputs) {
          clientOutputs.add(out);
        }

        new Thread(new ServerHandler(clientSocket, modelWithBroadcast, in, out)).start();
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}













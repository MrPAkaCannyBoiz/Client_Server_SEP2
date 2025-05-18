package Client;

import java.io.Serializable;

public class NetworkMessage implements Serializable
{
  private Object payload;
  public NetworkMessage(Object payload) {
    this.payload = payload;
  }
  public Object getPayload() {
    return payload;
  }
}
package Model;

import java.io.Serializable;
import java.util.Objects;

public class Employee implements Serializable
{
    private int id;
    private String name;
    private String role;
    private static int idCounter;
    private static final long serialVersionUID = 4L;

    public Employee(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    //auto increment version
    public Employee(String name, String role)
    {
        this.id = idCounter++;
        this.name = name;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public void setName(String name)
    {
      if (!name.isEmpty())
      {
        this.name = name;
      }
      else
      {
          throw new IllegalArgumentException("Name can't be empty");
      }
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String toString()
    {
        return "Employee ID: " + id + ", name: (" + name + "), " + "Role: " + role;
    }

    @Override public boolean equals(Object object)
    {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        Employee employee = (Employee) object;
        return getId() == employee.getId() && Objects.equals(getName(),
            employee.getName()) && Objects.equals(getRole(),
            employee.getRole());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId(), getName(), getRole());
    }
}

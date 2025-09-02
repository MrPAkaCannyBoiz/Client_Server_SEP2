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

    public Employee(String name, String role, int id) {
        this.name = name;
        this.role = role;
        this.id = id;

    }

    public Employee(String name, String role) {
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

    public void setId(int i) {
        this.id=i;
    }

    public static void setIdCounter(int value) {
        idCounter = value;
    }

    public void setRole(String role)
    {
        if (!role.isEmpty())
        {
            this.role = role;
        }
        else
        {
            throw new IllegalArgumentException("Role can't be empty");
        }
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

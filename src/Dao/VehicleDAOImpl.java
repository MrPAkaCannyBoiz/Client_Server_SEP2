package Dao;

import Application.DatabaseConnection;
import Model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAOImpl implements VehicleDAO {

    private Connection connection;

    public VehicleDAOImpl() throws SQLException {
        this.connection = new DatabaseConnection().getConnection();
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicle (model, color, enginetype, platenumber, priceperday, deposit, requiredlicensecategory, vehiclestatus, numberofseats, brand, type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (platenumber) DO NOTHING";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, vehicle.getModel());
            stmt.setString(2, vehicle.getColor());
            stmt.setString(3, vehicle.getEngine());
            stmt.setString(4, vehicle.getPlateNo());
            stmt.setDouble(5, vehicle.getPricePerDay());
            stmt.setDouble(6, vehicle.getDeposit());
            stmt.setString(7, vehicle.getRequiredLicenseType());
            stmt.setString(8, vehicle.getStatus());
            stmt.setInt(9, vehicle.getNoOfSeats());
            stmt.setString(10, vehicle.getBrand());
            stmt.setString(11, vehicle.getVehicleType());

            stmt.executeUpdate();

            // === SUBTYPES ===
            if (vehicle instanceof Truck truck) {
                String subSql = "INSERT INTO truck (platenumber, loadcapacity) VALUES (?, ?)";
                try (PreparedStatement subStmt = connection.prepareStatement(subSql)) {
                    subStmt.setString(1, truck.getPlateNo());
                    subStmt.setDouble(2, truck.getLoadCapacity());
                    subStmt.executeUpdate();
                }
            } else if (vehicle instanceof Van van) {
                String subSql = "INSERT INTO van (platenumber, cargovolume) VALUES (?, ?)";
                try (PreparedStatement subStmt = connection.prepareStatement(subSql)) {
                    subStmt.setString(1, van.getPlateNo());
                    subStmt.setDouble(2, van.getCargoVolume());
                    subStmt.executeUpdate();
                }
            } else if (vehicle instanceof MotorCycle bike) {
                String subSql = "INSERT INTO motorcycle (platenumber, sidecar) VALUES (?, ?)";
                try (PreparedStatement subStmt = connection.prepareStatement(subSql)) {
                    subStmt.setString(1, bike.getPlateNo());
                    subStmt.setBoolean(2, bike.hasSidecar());
                    subStmt.executeUpdate();
                }
            } else if (vehicle instanceof SportsCar car) {
                String subSql = "INSERT INTO sportscar (platenumber, topspeed, turboengine) VALUES (?, ?, ?)";
                try (PreparedStatement subStmt = connection.prepareStatement(subSql)) {
                    subStmt.setString(1, car.getPlateNo());
                    subStmt.setInt(2, car.getTopSpeed());
                    subStmt.setBoolean(3, car.getHasTurbo());
                    subStmt.executeUpdate();
                }
            } else if (vehicle instanceof Ufo ufo) {
                String subSql = "INSERT INTO ufo (platenumber, antigravitylevel) VALUES (?, ?)";
                try (PreparedStatement subStmt = connection.prepareStatement(subSql)) {
                    subStmt.setString(1, ufo.getPlateNo());
                    subStmt.setInt(2, ufo.getAntiGravityLevel());
                    subStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error adding vehicle", e);
        }
    }

    @Override
    public Vehicle getVehicleByPlateNo(String plateNo) {
        String sql = "SELECT * FROM vehicle WHERE platenumber = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, plateNo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String model = rs.getString("model");
                String color = rs.getString("color");
                String engine = rs.getString("enginetype");
                double price = rs.getDouble("priceperday");
                double deposit = rs.getDouble("deposit");
                double lateFee = rs.getDouble("latefee");
                int seats = rs.getInt("numberofseats");
                String brand = rs.getString("brand");

                // 👇 Default tyres (lub można dodać do bazy)
                int tyres = 4;

                if (existsInTable("truck", plateNo)) {
                    PreparedStatement sub = connection.prepareStatement("SELECT * FROM truck WHERE platenumber = ?");
                    sub.setString(1, plateNo);
                    ResultSet subRs = sub.executeQuery();
                    if (subRs.next()) {
                        return new Truck(brand, model, color, engine, plateNo, price, deposit, lateFee,
                                tyres, seats, subRs.getInt("loadcapacity"),
                                subRs.getBoolean("trailerattached"));
                    }
                } else if (existsInTable("van", plateNo)) {
                    PreparedStatement sub = connection.prepareStatement("SELECT * FROM van WHERE platenumber = ?");
                    sub.setString(1, plateNo);
                    ResultSet subRs = sub.executeQuery();
                    if (subRs.next()) {
                        return new Van(brand, model, color, engine, plateNo, price, deposit, lateFee,
                                tyres, seats, subRs.getInt("cargovolume"),
                                subRs.getBoolean("hasslidingdoor"));
                    }
                } else if (existsInTable("motorcycle", plateNo)) {
                    PreparedStatement sub = connection.prepareStatement("SELECT * FROM motorcycle WHERE platenumber = ?");
                    sub.setString(1, plateNo);
                    ResultSet subRs = sub.executeQuery();
                    if (subRs.next()) {
                        return new MotorCycle(brand, model, color, engine, plateNo, price, deposit, lateFee,
                                tyres, seats, subRs.getBoolean("hassidecar"));
                    }
                } else if (existsInTable("sportscar", plateNo)) {
                    PreparedStatement sub = connection.prepareStatement("SELECT * FROM sportscar WHERE platenumber = ?");
                    sub.setString(1, plateNo);
                    ResultSet subRs = sub.executeQuery();
                    if (subRs.next()) {
                        return new SportsCar(brand, model, color, engine, plateNo, price, deposit, lateFee,
                                tyres, seats, subRs.getInt("topspeed"),
                                subRs.getBoolean("turboengine"));
                    }
                } else if (existsInTable("ufo", plateNo)) {
                    PreparedStatement sub = connection.prepareStatement("SELECT * FROM ufo WHERE platenumber = ?");
                    sub.setString(1, plateNo);
                    ResultSet subRs = sub.executeQuery();
                    if (subRs.next()) {
                        return new Ufo(brand, model, color, engine, plateNo, price, deposit, lateFee,
                                seats, subRs.getInt("antigravitylevel"),
                                subRs.getBoolean("iscloakingdevice"));
                    }
                }

                // Default — zwykły Car
                return new Car(brand, model, color, engine, plateNo, price, deposit, lateFee, tyres, seats);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving vehicle", e);
        }

        return null;
    }



    @Override
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT platenumber FROM vehicle";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String plate = rs.getString("platenumber");
                Vehicle v = getVehicleByPlateNo(plate);
                if (v != null) vehicles.add(v);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all vehicles", e);
        }
        return vehicles;
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        String sql = "UPDATE vehicle SET model=?, color=?, enginetype=?, priceperhour=?, deposit=?, requiredlicensecategory=?, vehiclestatus=?, numberofseats=?, brand=?, type=? WHERE platenumber=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vehicle.getModel());
            stmt.setString(2, vehicle.getColor());
            stmt.setString(3, vehicle.getEngine());
            stmt.setDouble(4, vehicle.getPricePerDay());
            stmt.setDouble(5, vehicle.getDeposit());
            stmt.setString(7, vehicle.getRequiredLicenseType());
            stmt.setString(7, vehicle.getStatus());
            stmt.setInt(8, vehicle.getNoOfSeats());
            stmt.setString(9, vehicle.getBrand());
            stmt.setString(10, vehicle.getVehicleType());
            stmt.setString(11, vehicle.getPlateNo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating vehicle", e);
        }
    }

    @Override
    public void deleteVehicle(String plateNo) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM vehicle WHERE platenumber = ?")) {
            stmt.setString(1, plateNo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting vehicle", e);
        }
    }

    private boolean existsInTable(String table, String plateNo) throws SQLException {
        String sql = "SELECT 1 FROM " + table + " WHERE platenumber = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, plateNo);
            return stmt.executeQuery().next();
        }
    }
}

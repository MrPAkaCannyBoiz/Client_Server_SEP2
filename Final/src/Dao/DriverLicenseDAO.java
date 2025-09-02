package Dao;

import Model.DriverLicense;

import java.util.List;

public interface DriverLicenseDAO {
    void addDriverLicense(DriverLicense license);
    DriverLicense getDriverLicense(String licenseNumber);
    List<DriverLicense> getAllDriverLicenses();
    void updateDriverLicense(DriverLicense license);
    void deleteDriverLicense(String licenseNumber);
}

//package Model;
//
//public class DriverLicense
//{
//  private String licenseType;
//
//  public DriverLicense(String licenseType){
//    this.licenseType=licenseType;
//  }
//public String getLicenseType(){
//    return licenseType;
//}
//public void  setLicenseType(String licenseType){
//    this.licenseType=licenseType;
//}
//  public boolean hasLicenseFor(String required)
//  {
//    return licenseType.contains(required);
//  }
//
//public String toString(){
//    return "Driver license: "+ licenseType;
//}
//public boolean equals(Object obj){
//    if(obj==null || getClass() != obj.getClass()){
//      return false;
//    }
//DriverLicense driverLicenseV2 = (DriverLicense) obj;
//    return licenseType.equals(driverLicenseV2.licenseType);
//  }
//}

package Model;

import java.io.Serializable;

public class DriverLicense implements Serializable
{
    private String licenseNumber;
    private boolean isCategoryA;
    private boolean isCategoryB;
    private boolean isCategoryC;
    private boolean isCategoryD;
    private boolean isCategoryX;
    // when sending over objects Java will look for the UID, if there is none ja creates one but may cause errors
    private static final long serialVersionUID = 3L;

    public DriverLicense(String licenseNumber, boolean isCategoryA, boolean isCategoryB,
                         boolean isCategoryC, boolean isCategoryD, boolean isCategoryX)
    {
        setLicenseNumber(licenseNumber);
        this.isCategoryA = isCategoryA;
        this.isCategoryB = isCategoryB;
        this.isCategoryC = isCategoryC;
        this.isCategoryD = isCategoryD;
        this.isCategoryX = isCategoryX;
    }

    public boolean canDrive(String requiredLicenseType) {
        switch (requiredLicenseType.toUpperCase()) {
            case "A": return isCategoryA;
            case "B": return isCategoryB;
            case "C": return isCategoryC;
            case "D": return isCategoryD;
            case "X": return isCategoryX;
            default: return false;
        }
    }
    public String getLicenseNumber() {
        return licenseNumber;
    }

    public boolean isCategoryA() {
        return isCategoryA;
    }

    public boolean isCategoryB() {
        return isCategoryB;
    }

    public boolean isCategoryC() {
        return isCategoryC;
    }

    public boolean isCategoryD() {
        return isCategoryD;
    }

    public boolean isCategoryX() {
        return isCategoryX;
    }
    public void setCategoryX(boolean categoryX)
    {
        isCategoryX = categoryX;
    }

    public void setCategoryD(boolean categoryD)
    {
        isCategoryD = categoryD;
    }

    public void setCategoryC(boolean categoryC)
    {
        isCategoryC = categoryC;
    }

    public void setCategoryB(boolean categoryB)
    {
        isCategoryB = categoryB;
    }

    public void setCategoryA(boolean categoryA)
    {
        isCategoryA = categoryA;
    }

    public void setLicenseNumber(String licenseNumber)
    {
        if (licenseNumber == null || licenseNumber.isEmpty())
        {
            throw new IllegalArgumentException("License number cannot be empty or null");
        }
        this.licenseNumber = licenseNumber;
    }


    public String toString() {
        return "DriverLicense{" +
                "licenseNumber='" + licenseNumber + '\'' +
                ", A=" + isCategoryA +
                ", B=" + isCategoryB +
                ", C=" + isCategoryC +
                ", D=" + isCategoryD +
                ", X=" + isCategoryX +
                '}';
    }

    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        DriverLicense other = (DriverLicense) obj;
        return licenseNumber.equals(other.licenseNumber) &&
                isCategoryA == other.isCategoryA &&
                isCategoryB == other.isCategoryB &&
                isCategoryC == other.isCategoryC &&
                isCategoryD == other.isCategoryD &&
                isCategoryX == other.isCategoryX;
    }
}
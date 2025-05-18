package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingTest {
    public static void main(String[] args) {
        //LocalDate today = LocalDate.now();

        /*
        // Test 1: Valid license for sports car (Category A)
        DriverLicense licenseA = new DriverLicense(true, false, false, false, false);
        Customer joe = new Customer("Speedy Joe", "123456789", "joe@fast.com", licenseA, "USA", "1990-01-01");
        Vehicle ferrari = new SportsCar(joe, "Ferrari", "Red", "V8", "AB1234", 100, 500, 20, 4, 2, "A", 320, true);

        Booking booking1 = new Booking(today.plusDays(1), today.plusDays(3), joe, ferrari, today);
        System.out.println("Test 1 - Valid License: " + (booking1.isLicenseValid() ? "PASS" : "FAIL"));


        // Test 2: Invalid license (Category B trying to book Category A car)
        DriverLicense licenseB = new DriverLicense(false, true, false, false, false);
        Customer carla = new Customer("Careful Carla", "987654321", "carla@safe.com", licenseB, "UK", "1985-12-12");
        Vehicle lambo = new SportsCar(carla, "Lamborghini", "Yellow", "V12", "CD5678", 150, 600, 25, 4, 2, "A", 330, true);

        Booking booking2 = new Booking(today.plusDays(2), today.plusDays(4), carla, lambo, today);
        System.out.println("Test 2 - Invalid License: " + (!booking2.isLicenseValid() ? "PASS" : "FAIL"));


        // Test 3: Late fee calculation (return 4 hours late)
        DriverLicense licenseLate = new DriverLicense(true, false, false, false, false);
        Customer larry = new Customer("Late Larry", "111222333", "larry@late.com", licenseLate, "Mars", "1999-07-07");
        Vehicle bugatti = new SportsCar(larry, "Bugatti", "Black", "Quad-Turbo", "XYZ999", 200, 1000, 50, 4, 2, "A", 400, true);

        Booking booking3 = new Booking(today.plusDays(1), today.plusDays(2), larry, bugatti, today);
        LocalDateTime returnTime = booking3.getEndDate().atStartOfDay().plusHours(4);
        double lateFee = booking3.calculateLateFee(returnTime);
        System.out.println("Test 3 - Late Fee Calculation (expecting 4 * 50 = 200): " +
                (lateFee == 200 ? "PASS" : "FAIL (got " + lateFee + ")"));


        // Test 4: Start date in the past
        try {
            new Booking(today.minusDays(1), today.plusDays(1), joe, ferrari, today);
            System.out.println("Test 4 - Past Start Date: FAIL");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 4 - Past Start Date: PASS");
        }

         */
    }
}

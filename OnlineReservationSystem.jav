import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Reservation {
    private String reservationId;
    private String name;
    private String date;
    private int seats;

    public Reservation(String reservationId, String name, String date, int seats) {
        this.reservationId = reservationId;
        this.name = name;
        this.date = date;
        this.seats = seats;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getSeats() {
        return seats;
    }
}

class ReservationSystem {
    private ArrayList<Reservation> reservations;
    private HashMap<String, Integer> availableSeats;

    public ReservationSystem() {
        reservations = new ArrayList<>();
        availableSeats = new HashMap<>();
    }

    public void addAvailableDate(String date, int seats) {
        availableSeats.put(date, seats);
    }

    public boolean createReservation(String reservationId, String name, String date, int seats) {
        if (availableSeats.containsKey(date) && availableSeats.get(date) >= seats) {
            reservations.add(new Reservation(reservationId, name, date, seats));
            availableSeats.put(date, availableSeats.get(date) - seats);
            return true;
        }
        return false;
    }

    public Reservation viewReservation(String reservationId) {
        for (Reservation res : reservations) {
            if (res.getReservationId().equals(reservationId)) {
                return res;
            }
        }
        return null;
    }

    public boolean cancelReservation(String reservationId) {
        for (Reservation res : reservations) {
            if (res.getReservationId().equals(reservationId)) {
                availableSeats.put(res.getDate(), availableSeats.get(res.getDate()) + res.getSeats());
                reservations.remove(res);
                return true;
            }
        }
        return false;
    }

    public void showAvailableDates() {
        for (String date : availableSeats.keySet()) {
            System.out.println("Date: " + date + ", Available Seats: " + availableSeats.get(date));
        }
    }
}

public class OnlineReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReservationSystem system = new ReservationSystem();
        system.addAvailableDate("2025-02-17", 50);
        system.addAvailableDate("2025-02-18", 30);
        system.addAvailableDate("2025-02-19", 20);

        while (true) {
            System.out.println("1. Create Reservation");
            System.out.println("2. View Reservation");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. Show Available Dates");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter Reservation ID: ");
                String id = scanner.nextLine();
                System.out.print("Enter Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter Date (YYYY-MM-DD): ");
                String date = scanner.nextLine();
                System.out.print("Enter Number of Seats: ");
                int seats = scanner.nextInt();
                scanner.nextLine();

                if (system.createReservation(id, name, date, seats)) {
                    System.out.println("Reservation Created Successfully.");
                } else {
                    System.out.println("Failed to Create Reservation. Check Availability.");
                }

            } else if (choice == 2) {
                System.out.print("Enter Reservation ID: ");
                String id = scanner.nextLine();
                Reservation res = system.viewReservation(id);
                if (res != null) {
                    System.out.println("Reservation ID: " + res.getReservationId());
                    System.out.println("Name: " + res.getName());
                    System.out.println("Date: " + res.getDate());
                    System.out.println("Seats: " + res.getSeats());
                } else {
                    System.out.println("Reservation Not Found.");
                }

            } else if (choice == 3) {
                System.out.print("Enter Reservation ID: ");
                String id = scanner.nextLine();
                if (system.cancelReservation(id)) {
                    System.out.println("Reservation Cancelled Successfully.");
                } else {
                    System.out.println("Failed to Cancel Reservation.");
                }

            } else if (choice == 4) {
                system.showAvailableDates();

            } else if (choice == 5) {
                break;
            }
        }
        scanner.close();
    }
}

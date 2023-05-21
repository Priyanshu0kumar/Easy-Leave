import java.util.Scanner;

public class login {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Leave Management System!");
        System.out.println("Please enter your username and password to login.");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (authenticate(username, password)) {
            System.out.println("Login successful!");
            // Redirect the user to the dashboard page
        } else {
            System.out.println("Invalid username or password. Please try again.");
            // Display an error message and ask the user to try again
        }
    }

    public static boolean authenticate(String username, String password) {
        // TODO: Implement authentication logic here
        // This could involve checking the user's credentials against a database of user accounts
        return true; // Change this to return true or false based on the authentication result
    }

}

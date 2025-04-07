import javax.swing.JOptionPane;

public class clientORserver {
    public static void main() {
        // Display a single dialog to let the user choose and optionally input the port
        String input = (String) JOptionPane.showInputDialog(
            null,
            "Choose your role and, if Client, enter the port (e.g., Client:3031 or Server):",
            "Choose Role",
            JOptionPane.QUESTION_MESSAGE,
            null,
            null,
            "Client:3031" // Default input
        );

        if (input != null && input.startsWith("Client:")) {
            try {
                int port = Integer.parseInt(input.split(":")[1]); // Extract the port
                System.out.println("Starting Client on port: " + port);
                // Pass the port to the Client class (replace with actual Client initialization)
                new Client(port);
            } catch (Exception e) {
                System.err.println("Invalid input. Please enter in the format Client:<port>.");
            }
        } else if ("Server".equalsIgnoreCase(input)) {
            System.out.println("Starting Server...");
            // Initialize the Server (replace with actual Server initialization)
            new Server();
        } else {
            System.out.println("No valid option selected.");
        }
    }
}
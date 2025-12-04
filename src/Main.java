import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        
        // Multithreading: Start the auto-save thread
        AutoSaveThread autoSave = new AutoSaveThread(manager);
        Thread autoSaveThread = new Thread(autoSave, "Auto-Save-Thread");
        autoSaveThread.start();

        System.out.println("✅ Welcome to the Simple Task Manager!");
        
        boolean running = true;
        while (running) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task Complete");
            System.out.println("4. Save and Exit");
            System.out.print("Enter choice: ");

            // Exception Handling for non-integer input
            try {
                if (scanner.hasNextInt()) {
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    
                    switch (choice) {
                        case 1:
                            System.out.print("Enter task description: ");
                            String description = scanner.nextLine();
                            System.out.print("Enter priority (1-5, 5 is highest): ");
                            int priority = scanner.nextInt();
                            scanner.nextLine();
                            
                            // Exception Handling for adding task
                            manager.addTask(description, priority); 
                            break;
                            
                        case 2:
                            manager.viewTasks();
                            break;
                            
                        case 3:
                            System.out.print("Enter task number to mark complete: ");
                            int index = scanner.nextInt();
                            scanner.nextLine();
                            manager.markTaskCompleted(index);
                            break;

                        case 4:
                            running = false;
                            break;
                            
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } else {
                    scanner.nextLine(); // Consume bad input
                    throw new InputMismatchException(); // Trigger the catch block
                }
            } catch (InputMismatchException e) {
                System.err.println("❌ Invalid input. Please enter a number for the menu option.");
            } catch (InvalidInputException e) {
                // Catch custom exception
                System.err.println("❌ Error: " + e.getMessage());
            } catch (Exception e) {
                // Catch any other unexpected exceptions
                System.err.println("An unexpected error occurred: " + e.getMessage());
            }
        }
        
        // Shut down sequence
        System.out.println("\n🛑 Shutting down...");
        autoSave.stop(); // Stop the thread gracefully
        autoSaveThread.interrupt(); // Interrupt to wake it up from sleep
        manager.saveTasks(); // Final manual save
        scanner.close();
        System.out.println("👋 Thank you for using the Task Manager. Goodbye!");
    }
}

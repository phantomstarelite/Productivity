// Implementing Runnable for Multithreading
public class AutoSaveThread implements Runnable {
    private TaskManager taskManager;
    private boolean running = true;
    private final long SLEEP_TIME = 30000; // 30 seconds

    public AutoSaveThread(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void stop() {
        running = false;
    }

    @Override
    // Method that runs in the new thread
    public void run() {
        while (running) {
            try {
                // Multithreading concept: sleep the thread
                Thread.sleep(SLEEP_TIME); 
                System.out.println("\n[Auto-Save Initiated]");
                taskManager.saveTasks(); // Perform the background save
                System.out.println("[Auto-Save Complete]");
                
            } catch (InterruptedException e) {
                // Exception Handling for thread interruption
                System.out.println("Auto-Save thread interrupted.");
                Thread.currentThread().interrupt(); // Restore interrupted status
                running = false;
            }
        }
    }
}
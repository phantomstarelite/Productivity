import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskManager {
    // Collections: ArrayList to store Task objects
    private List<Task> taskList;
    private static final String FILE_NAME = "tasks.ser";

    public TaskManager() {
        // Attempt to load tasks from file on startup
        this.taskList = loadTasks(); 
    }

    public void addTask(String description, int priority) throws InvalidInputException {
        if (description == null || description.trim().isEmpty() || priority < 1 || priority > 5) {
            // Throw custom exception for validation
            throw new InvalidInputException("Task description cannot be empty and priority must be between 1 and 5.");
        }
        Task newTask = new Task(description, priority);
        taskList.add(newTask);
        System.out.println("Task added successfully.");
    }

    public void viewTasks() {
        if (taskList.isEmpty()) {
            System.out.println("The task list is empty.");
            return;
        }
        
        // Use Collections.sort with a custom Comparator for sorting by priority (Polymorphism)
        Collections.sort(taskList, Comparator.comparingInt(Task::getPriority).reversed());

        System.out.println("\n--- Task List (Highest Priority First) ---");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.printf("%d. %s\n", (i + 1), taskList.get(i));
        }
        System.out.println("-----------------------------------------\n");
    }

    // File Handling: Saving the task list using Serialization
    public void saveTasks() {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            
            objectOut.writeObject(taskList);
            System.out.println("Tasks saved successfully to " + FILE_NAME);
            
        } catch (IOException e) {
            // Exception Handling for File I/O
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    // File Handling: Loading the task list using Deserialization
    private List<Task> loadTasks() {
        try (FileInputStream fileIn = new FileInputStream(FILE_NAME);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            
            // Unchecked cast is suppressed/handled here.
            @SuppressWarnings("unchecked") 
            List<Task> loadedList = (List<Task>) objectIn.readObject();
            System.out.println("Tasks loaded successfully from " + FILE_NAME);
            return loadedList;
            
        } catch (FileNotFoundException e) {
            System.out.println("No existing task file found. Starting with an empty list.");
            return new ArrayList<>();
            
        } catch (IOException | ClassNotFoundException e) {
            // Exception Handling for I/O errors or if the class definition is missing
            System.err.println("Error loading tasks: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    // Additional method for completion (optional, for a complete app)
    public void markTaskCompleted(int index) throws InvalidInputException {
        if (index > 0 && index <= taskList.size()) {
            taskList.get(index - 1).setCompleted(true);
            System.out.println("Task " + index + " marked as completed.");
        } else {
            throw new InvalidInputException("Invalid task number.");
        }
    }
}
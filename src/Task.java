import java.io.Serializable;

// Task implements Serializable for file handling (saving/loading objects)
public class Task implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes

    private String description;
    private int priority;
    private boolean isCompleted;

    // Constructor
    public Task(String description, int priority) {
        this.description = description;
        this.priority = priority;
        this.isCompleted = false;
    }

    // Getters and Setters (Encapsulation)
    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    // Overriding toString() for easy display (Polymorphism)
    @Override
    public String toString() {
        String status = isCompleted ? "[COMPLETED]" : "[PENDING]";
        return String.format("%s (Priority: %d) - %s", status, priority, description);
    }
}
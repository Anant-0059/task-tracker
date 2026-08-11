public class Task {
    private int id;
    private String description;
    private String status;
    private String createdAt;
    private String updatedAt;

    public Task(int id, String description, String status, String createdAt, String updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setDescription() {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // public static void main(String[] args) {

    //     Task task = new Task(
    //         1,
    //         "Buy Groceries",
    //         "todo",
    //         "2026-08-09T10:30:00",
    //         "2026-08-09T10:30:00"
    //     );
    // }
}


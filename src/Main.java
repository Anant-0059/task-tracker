import java.time.LocalDateTime;
import java.util.List;
import java.nio.file.*;

public class Main {

    private static void printHelp() {
        System.out.println("Task Tracker CLI");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("  java Main add \"task description\"");
        System.out.println("  java Main update <id> \"new description\"");
        System.out.println("  java Main delete <id>");
        System.out.println("  java Main mark-in-progress <id>");
        System.out.println("  java Main mark-done <id>");
        System.out.println("  java Main list");
        System.out.println("  java Main list done");
        System.out.println("  java Main list todo");
        System.out.println("  java Main list in-progress");
    }

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        if (args.length == 0) {
            printHelp();
            return;
        }

        String command = args[0];

        boolean commandFound = false;

        if (command.equals("help")) {
            printHelp();
            return;
        }

        if (command.equals("add")) {

            if (args.length < 2) {
                System.out.println("Please provide a task description!");
                return;
            }

            String description = args[1];

            int id = manager.getNextId();

            LocalDateTime now = LocalDateTime.now();

            Task task = new Task(
                id,
                description,
                "todo",
                now.toString(),
                now.toString()
            );

            manager.addTask(task);

            System.out.println(
                "Task added successfully (ID: " + id + ")"
            );
        }

        if (command.equals("update")) {

            if (args.length < 3) {
                System.out.println(
                    "Usage: update <id> <description>"
                );
                return;
            }

            int id;

            try {
                id = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("ID must be a number.");
                return;
            }

            String newDescription = args[2];

            boolean updated = manager.updateTask(
                id,
                newDescription
            );

            if (updated) {
                System.out.println(
                    "Task updated successfully!"
                );
            } else {
                System.out.println(
                    "Task with ID " + id + " not found."
                );
            }
        }

        if (command.equals("delete")) {

            if (args.length < 2) {
                System.out.println("Usage: delete <id>");
                return;
            }

            int id;

            try {
                id = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("ID must be a number.");
                return;
            }

            boolean deleted = manager.deleteTask(id);

            if (deleted) {
                System.out.println("Task deleted successfully!");
            } else {
                System.out.println("Task with ID " + id + " not found.");
            }
        }

        if (command.equals("mark-in-progress")) {

            if (args.length < 2) {
                System.out.println("Usage: mark-in-progress <id>");
                return;
            }

            int id;

            try {
                id = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("ID must be a number.");
                return;
            }

            boolean updated = manager.updateStatus(
                id,
                "in-progress"
            );

            if (updated) {
                System.out.println(
                    "Task marked as in-progress!"
                );
            } else {
                System.out.println(
                    "Task with ID " + id + " not found."
                );
            }
        }

        if (command.equals("mark-done")) {

            if (args.length < 2) {
                System.out.println("Usage: mark-done <id>");
                return;
            }

            int id;

            try {
                id = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("ID must be a number.");
                return;
            }

            boolean updated = manager.updateStatus(
                id,
                "done"
            );

            if (updated) {
                System.out.println(
                    "Task marked as done!"
                );
            } else {
                System.out.println(
                    "Task with ID " + id + " not found."
                );
            }
        }

        if (command.equals("list")) {

            if (args.length == 1) {

                List<Task> taskList = manager.getAllTasks();

                if (taskList.isEmpty()) {
                    System.out.println("No tasks found.");
                    return;
                }

                for (Task task : taskList) {

                    System.out.println(
                        task.getId() + " | " +
                        task.getDescription() + " | " +
                        task.getStatus()
                    );
                }

                return;
            }

            String status = args[1];

            if (!status.equals("done")
                    && !status.equals("todo")
                    && !status.equals("in-progress")) {

                System.out.println(
                    "Invalid status. Use: done, todo, or in-progress."
                );

                return;
            }

            List<Task> filteredTasks =
                manager.getTasksByStatus(status);

            if (filteredTasks.isEmpty()) {
                System.out.println(
                    "No tasks found with status: " + status
                );
                return;
            }

            for (Task task : filteredTasks) {

                System.out.println(
                    task.getId() + " | " +
                    task.getDescription() + " | " +
                    task.getStatus()
                );
            }
        }
    
        if (!commandFound) {
            System.out.println("Unknown command: " + command);
            System.out.println("Use 'java Main help' to see available commands.");
        }
    }
}


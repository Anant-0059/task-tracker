import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TaskManager {

    private static final Path FILE_PATH = Path.of("tasks.json");

    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
        loadTasks();
    }

    public void addTask(Task task) {
        tasks.add(task);
        saveTasks();
    }

    public int getNextId() {
        int maxId = 0;

        for (Task task: tasks) {
            if (task.getId() > maxId) {
                maxId = task.getId();
            }
        }

        return maxId + 1;
    }

    public int getTaskCount() {
        return tasks.size();
    }

    private void loadTasks() {

        if (!Files.exists(FILE_PATH)) {
            return;
        }

        try {

            String json = Files.readString(FILE_PATH).trim();

            if (json.equals("[]")) {
                return;
            }

            List<String> taskJsons = splitTasks(json);

            for (String taskJson : taskJsons) {

                Task task = parseTask(taskJson);

                tasks.add(task);
            }

        } catch (Exception e) {

            System.out.println(
                "Error reading tasks !!" + e.getMessage()
            );
        }
    }

    private Task parseTask(String json) {
        int id = Integer.parseInt(
            getValue(json, "id")
        );

        String description = getValue(json, "description");
        String status = getValue(json, "status");
        String createdAt = getValue(json, "createdAt");
        String updatedAt = getValue(json, "updatedAt");

        return new Task(
            id,
            description,
            status,
            createdAt,
            updatedAt
        );
    }

    private String getValue(String json, String key) {

        String searchKey = "\"" + key + "\"";

        int keyIndex = json.indexOf(searchKey);

        if (keyIndex == -1) {
            return "";
        }

        int colonIndex = json.indexOf(":", keyIndex);

        int valueStart = colonIndex + 1;

        // Skip spaces
        while (valueStart < json.length()
                && Character.isWhitespace(json.charAt(valueStart))) {
            valueStart++;
        }

        // String value
        if (json.charAt(valueStart) == '"') {

            int firstQuote = valueStart + 1;

            int secondQuote = json.indexOf('"', firstQuote);

            return json.substring(firstQuote, secondQuote);
        }

        // Number value
        int valueEnd = json.indexOf(",", valueStart);

        if (valueEnd == -1) {
            valueEnd = json.indexOf("}", valueStart);
        }

        return json.substring(valueStart, valueEnd).trim();
    }

    private List<String> splitTasks(String json) {
        List<String> taskJsons = new ArrayList<>();

        int depth = 0, start = -1;

        for (int i = 0; i < json.length(); i++) {
            char currChar = json.charAt(i);

            if (currChar == '{') {
                if (depth == 0) {
                    start = i;
                }
                depth++;
            } else if (currChar == '}') {
                depth--;

                if (depth == 0) {
                    String taskJson = json.substring(start, i+1);
                    taskJsons.add(taskJson);
                }
            }
        }

        return taskJsons;
    }

    private void saveTasks() {

        try {

            StringBuilder json = new StringBuilder();

            json.append("[\n");

            for (int i = 0; i < tasks.size(); i++) {

                Task task = tasks.get(i);

                json.append("  {\n");
                json.append("    \"id\": ").append(task.getId()).append(",\n");
                json.append("    \"description\": \"")
                    .append(task.getDescription()).append("\",\n");
                json.append("    \"status\": \"")
                    .append(task.getStatus()).append("\",\n");
                json.append("    \"createdAt\": \"")
                    .append(task.getCreatedAt()).append("\",\n");
                json.append("    \"updatedAt\": \"")
                    .append(task.getUpdatedAt()).append("\"\n");
                json.append("  }");

                if (i < tasks.size() - 1) {
                    json.append(",");
                }

                json.append("\n");
            }

            json.append("]");

            Files.writeString(FILE_PATH, json.toString());

        } catch (Exception e) {

            System.out.println(
                "Error saving tasks: " + e.getMessage()
            );
        }
    }

    public boolean updateTask(int id, String newDescription) {

        for (Task task : tasks) {

            if (task.getId() == id) {

                task.setDescription(newDescription);

                task.setUpdatedAt(
                    java.time.LocalDateTime.now().toString()
                );

                saveTasks();

                return true;
            }
        }

        return false;
    }

    public boolean deleteTask(int id) {

        for (int i = 0; i < tasks.size(); i++) {

            if (tasks.get(i).getId() == id) {

                tasks.remove(i);

                saveTasks();

                return true;
            }
        }

        return false;
    }

    public boolean updateStatus(int id, String newStatus) {

        for (Task task : tasks) {

            if (task.getId() == id) {

                task.setStatus(newStatus);

                task.setUpdatedAt(
                    java.time.LocalDateTime.now().toString()
                );

                saveTasks();

                return true;
            }
        }

        return false;
    }

    public List<Task> getTasksByStatus(String status) {

        List<Task> filteredTasks = new ArrayList<>();

        for (Task task : tasks) {

            if (task.getStatus().equals(status)) {
                filteredTasks.add(task);
            }
        }

        return filteredTasks;
    }

    public List<Task> getAllTasks() {
        return tasks;
    }
    
}

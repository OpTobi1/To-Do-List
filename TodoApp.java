import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TodoApp {
    private static final String FILE_NAME = "tasks.txt";
    private static ArrayList<String> tasks = new ArrayList<>();

    public static void main(String[] args) {
        loadTasksFromFile();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        System.out.println("=== Personal To-Do List ===");

        while (choice != 4) {
            System.out.println("\n1. View Tasks\n2. Add Task\n3. Remove Task\n4. Exit");
            System.out.print("Choose an option: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1 -> showTasks();
                case 2 -> addTask(scanner);
                case 3 -> removeTask(scanner);
                case 4 -> {
                    saveTasksToFile();
                    System.out.println("Tasks saved. Goodbye!");
                }
                default -> System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }

    private static void showTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Your list is empty.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    private static void addTask(Scanner scanner) {
        System.out.print("Enter task description: ");
        String task = scanner.nextLine();
        tasks.add(task);
        System.out.println("Task added!");
    }

    private static void removeTask(Scanner scanner) {
        showTasks();
        if (!tasks.isEmpty()) {
            System.out.print("Enter task number to remove: ");
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < tasks.size()) {
                tasks.remove(index);
                System.out.println("Task removed.");
            } else {
                System.out.println("Invalid number.");
            }
        }
    }

    private static void saveTasksToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String task : tasks) {
                writer.println(task);
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private static void loadTasksFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                tasks.add(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("No saved tasks found.");
        }
    }
}

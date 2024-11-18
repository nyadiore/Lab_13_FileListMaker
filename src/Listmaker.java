import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Listmaker {
    private static List<String> list = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static String currentFileName = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            String choice = scanner.nextLine().toUpperCase();

            try {
                switch (choice) {
                    case "A":
                        addItem(scanner);
                        break;
                    case "D":
                        deleteItem(scanner);
                        break;
                    case "I":
                        insertItem(scanner);
                        break;
                    case "V":
                        viewList();
                        break;
                    case "M":
                        moveItem(scanner);
                        break;
                    case "O":
                        openList(scanner);
                        break;
                    case "S":
                        saveList();
                        break;
                    case "C":
                        clearList();
                        break;
                    case "Q":
                        if (needsToBeSaved) {
                            System.out.print("You have unsaved changes. Do you want to save? (Y/N): ");
                            String saveChoice = scanner.nextLine().toUpperCase();
                            if (saveChoice.equals("Y")) {
                                saveList();
                            }
                        }
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option, try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Displays the main menu
    private static void printMenu() {
        System.out.println("\n Listmaker Menu ");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("I - Insert an item into the list");
        System.out.println("V - View the list");
        System.out.println("M - Move an item in the list");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list to disk");
        System.out.println("C - Clear all items from the list");
        System.out.println("Q - Quit the program");
        System.out.print("Enter your choice: ");
    }

    // Adds an item to the list
    private static void addItem(Scanner scanner) {
        System.out.print("Enter the item to add: ");
        String item = scanner.nextLine();
        list.add(item);
        needsToBeSaved = true;
        System.out.println("Item added.");
    }

    // Deletes an item from the list
    private static void deleteItem(Scanner scanner) {
        System.out.print("Enter the index of the item to delete: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index >= 0 && index < list.size()) {
            list.remove(index);
            needsToBeSaved = true;
            System.out.println("Item deleted.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    // Inserts an item into the list at a specific index
    private static void insertItem(Scanner scanner) {
        System.out.print("Enter the index to insert the item: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index >= 0 && index <= list.size()) {
            System.out.print("Enter the item to insert: ");
            String item = scanner.nextLine();
            list.add(index, item);
            needsToBeSaved = true;
            System.out.println("Item inserted.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    // Views (prints) the list
    private static void viewList() {
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + ": " + list.get(i));
            }
        }
    }

    // Moves an item from one index to another
    private static void moveItem(Scanner scanner) {
        System.out.print("Enter the index of the item to move: ");
        int fromIndex = Integer.parseInt(scanner.nextLine());
        if (fromIndex >= 0 && fromIndex < list.size()) {
            System.out.print("Enter the new index for the item: ");
            int toIndex = Integer.parseInt(scanner.nextLine());
            if (toIndex >= 0 && toIndex <= list.size()) {
                String item = list.remove(fromIndex);
                list.add(toIndex, item);
                needsToBeSaved = true;
                System.out.println("Item moved.");
            } else {
                System.out.println("Invalid destination index.");
            }
        } else {
            System.out.println("Invalid source index.");
        }
    }

    // Opens a list from a file
    private static void openList(Scanner scanner) throws IOException {
        if (needsToBeSaved) {
            System.out.print("You have unsaved changes. Do you want to save before opening a new file? (Y/N): ");
            String saveChoice = scanner.nextLine().toUpperCase();
            if (saveChoice.equals("Y")) {
                saveList();
            }
        }

        System.out.print("Enter the filename to open: ");
        String filename = scanner.nextLine();
        Path path = Paths.get(filename);
        if (Files.exists(path)) {
            list.clear();
            list.addAll(Files.readAllLines(path));
            currentFileName = filename;
            needsToBeSaved = false;
            System.out.println("List loaded from " + filename);
        } else {
            System.out.println("File not found.");
        }
    }

    // Saves the current list to a file
    private static void saveList() throws IOException {
        if (currentFileName == null) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a filename to save the list: ");
            currentFileName = scanner.nextLine();
        }
        Files.write(Paths.get(currentFileName), list);
        needsToBeSaved = false;
        System.out.println("List saved to " + currentFileName);
    }

    // Clears all items from the list
    private static void clearList() {
        list.clear();
        needsToBeSaved = true;
        System.out.println("List cleared.");
    }
}

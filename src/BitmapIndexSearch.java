import java.sql.SQLException;
import java.util.*;

public class BitmapIndexSearch {

    private BitmapIndexGenerator bitmapIndexGenerator;

    public BitmapIndexSearch() {
        bitmapIndexGenerator = new BitmapIndexGenerator();
        try {
            bitmapIndexGenerator.generateBitmapIndexes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BitSet search(String field, Object value) {
        Map<Object, BitSet> fieldIndex = bitmapIndexGenerator.getBitmapIndexCache().get(field);
        if (fieldIndex == null) {
            throw new IllegalArgumentException("Invalid field: " + field);
        }
        return fieldIndex.get(value);
    }

    public void startConsole() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter field to search (restroom, parking, category or exit to quit):");
            String field = scanner.nextLine();
            if ("exit".equalsIgnoreCase(field)) {
                break;
            }

            System.out.println("Enter value to search:");
            String value = scanner.nextLine();

            BitSet result = search(field, value);
            if (result == null) {
                System.out.println("No result found.");
            } else {
                System.out.println("Found at rows: " + result);
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        BitmapIndexSearch search = new BitmapIndexSearch();
        search.startConsole();
    }
}

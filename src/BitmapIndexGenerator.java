import java.sql.*;
import java.util.*;
// TODO : 비트맵 인덱스를 텍스트 파일로 저장하도록 변경하기
public class BitmapIndexGenerator {

    private Map<String, Map<Object, BitSet>> bitmapIndexCache = new HashMap<>();
    public Map<String, Map<Object, BitSet>> getBitmapIndexCache() {
        return bitmapIndexCache;
    }

    public void generateBitmapIndexes() throws SQLException {
        try (Connection connection = DriverManager.getConnection(Config.URL, Config.USERNAME, Config.PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM places")) {

            int rowCount = 0;

            while (resultSet.next()) {
                String restroom = resultSet.getString("화장실_보유여부");
                String parking = resultSet.getString("주차장_보유여부");
                String category = resultSet.getString("카테고리");

                updateBitmapIndex("restroom", restroom, rowCount);
                updateBitmapIndex("parking", parking, rowCount);
                updateBitmapIndex("category", category, rowCount);

                rowCount++;
            }
        }
    }

    public void queryByBitmapIndex() throws SQLException {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter restroom availability (Y/N):");
            String restroom = scanner.nextLine();
            System.out.println("Enter parking availability (Y/N):");
            String parking = scanner.nextLine();
            System.out.println("Enter category:");
            String category = scanner.nextLine();

            BitSet restroomBitSet = bitmapIndexCache.get("restroom").get(restroom);
            BitSet parkingBitSet = bitmapIndexCache.get("parking").get(parking);
            BitSet categoryBitSet = bitmapIndexCache.get("category").get(category);

            BitSet resultBitSet = (BitSet) restroomBitSet.clone();
            resultBitSet.and(parkingBitSet);
            resultBitSet.and(categoryBitSet);

            try (Connection connection = DriverManager.getConnection(Config.URL, Config.USERNAME, Config.PASSWORD);
                 Statement statement = connection.createStatement()) {

                for (int i = resultBitSet.nextSetBit(0); i >= 0; i = resultBitSet.nextSetBit(i+1)) {
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM places WHERE id=" + (i + 1));
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString("장소명"));
                        // Print other fields if necessary
                    }
                }
            }
        }
    }
    private void updateBitmapIndex(String field, Object value, int row) {
        Map<Object, BitSet> fieldIndex = bitmapIndexCache.computeIfAbsent(field, k -> new HashMap<>());
        BitSet bitSet = fieldIndex.computeIfAbsent(value, k -> new BitSet());
        bitSet.set(row);
    }

    public static void main(String[] args) {
        BitmapIndexGenerator generator = new BitmapIndexGenerator();
        try {
            generator.generateBitmapIndexes();
            System.out.println("Bitmap indexes generated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

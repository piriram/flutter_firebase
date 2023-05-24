import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RangeSearch {

    public static void main(String[] args) {
        try {
            searchRecordsByColumnAndRange("점포수", 10, 20);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void searchRecordsByColumnAndRange(String columnName, int min, int max) throws SQLException {
        int count = 0;

        try (Connection connection = DriverManager.getConnection(Config.URL, Config.USERNAME, Config.PASSWORD);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM Market WHERE " + columnName + " BETWEEN " + min + " AND " + max;
            count = getCount(count, statement, query);
        }

        System.out.println("조회된 레코드 개수: " + count);
    }

    static int getCount(int count, Statement statement, String query) throws SQLException {
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            System.out.println("시장명: " + resultSet.getString("시장명") +
                    ", 시장유형: " + resultSet.getString("시장유형") +
                    ", 시장도로명주소: " + resultSet.getString("시장도로명주소") +
                    ", 시장개설주기: " + resultSet.getString("시장개설주기") +
                    ", 점포수: " + resultSet.getInt("점포수") +
                    ", 공중화장실보유여부: " + resultSet.getBoolean("공중화장실보유여부") +
                    ", 주차장보유여부: " + resultSet.getBoolean("주차장보유여부") +
                    ", 개설연도: " + resultSet.getString("개설연도"));
            count++;
        }
        return count;
    }
}

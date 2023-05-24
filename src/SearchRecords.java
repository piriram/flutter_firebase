import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SearchRecords {
    public static void main(String[] args) {
        searchRecordsByColumn("시장명", "흑석");
        try {
            searchRecordsByColumn(3, "흑석");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void searchRecordsByColumn(String columnName, String keyword) {
        String url = "jdbc:mysql://localhost:3306/TEST1?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
        int count=0;


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, Config.USERNAME, Config.PASSWORD);
            System.out.println("MySQL 데이터베이스에 연결되었습니다!");

            Statement statement = connection.createStatement();

            String selectQuery = String.format("SELECT * FROM Market WHERE %s LIKE '%%%s%%'", columnName, keyword);

            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                // 결과 출력
                System.out.println("시장명: " + resultSet.getString("시장명") + ", 시장유형: " + resultSet.getString("시장유형") + ", 시장도로명주소: " + resultSet.getString("시장도로명주소") + ", 시장개설주기: " + resultSet.getString("시장개설주기") + ", 점포수: " + resultSet.getInt("점포수") + ", 공중화장실보유여부: " + resultSet.getBoolean("공중화장실보유여부") + ", 주차장보유여부: " + resultSet.getBoolean("주차장보유여부") + ", 개설연도: " + resultSet.getString("개설연도"));
                count++;
            }
            System.out.println("조회된 레코드 개수: " + count);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("데이터베이스 연결 또는 쿼리 실행에 실패했습니다.");
            e.printStackTrace();
        }
    }
    private static final String URL = "jdbc:mysql://localhost:3306/TEST1?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";



    public static void searchRecordsByColumn(int index, String keyword) throws SQLException {
        String columnName = getColumnByIndex(index);
        int count = 0;

        try (Connection connection = DriverManager.getConnection(URL, Config.USERNAME, Config.PASSWORD);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM Market WHERE " + columnName + " LIKE '%" + keyword + "%'";
            count = RangeSearch.getCount(count, statement, query);
        }
        System.out.println("조회된 레코드 개수: " + count);
    }

    public static String getColumnByIndex(int index) {
        switch (index) {
            case 1:
                return "시장명";
            case 2:
                return "시장유형";
            case 3:
                return "시장도로명주소";
            case 4:
                return "시장개설주기";
            case 5:
                return "점포수";
            case 6:
                return "공중화장실보유여부";
            case 7:
                return "주차장보유여부";
            case 8:
                return "개설연도";
            default:
                throw new IllegalArgumentException("Invalid column index: " + index);
        }
    }
}

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/TEST1?serverTimezone=UTC";
        String username = "root";
        String password = "rhsid312";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("연결 성공!");
            Statement statement = connection.createStatement();

            String createTableQuery = "CREATE TABLE Market2 (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                    "시장명 VARCHAR(50) NULL," +
                    "시장유형 VARCHAR(20) NULL," +
                    "시장도로명주소 VARCHAR(100) NULL," +
                    "시장개설주기 VARCHAR(20) NULL," +
                    "점포수 INT NULL," +
                    "공중화장실보유여부 BOOLEAN NULL," +
                    "주차장보유여부 BOOLEAN NULL," +
                    "개설연도 VARCHAR(10) NULL" +
                    ");";

            statement.executeUpdate(createTableQuery);
            System.out.println("Market 테이블이 생성되었습니다.");

            statement.close();

            // 연결 해제
            connection.close();
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("데이터베이스 연결에 실패했습니다.");
            e.printStackTrace();
        }
    }
}

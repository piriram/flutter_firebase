import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteAllDataFromMarketTable {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/TEST1?serverTimezone=UTC";
        String username = "root";
        String password = "rhsid312";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("MySQL 데이터베이스에 연결되었습니다!");

            String deleteQuery = "DELETE FROM Market";
            Statement statement = connection.createStatement();

            int deletedRows = statement.executeUpdate(deleteQuery);

            System.out.println("Market 테이블에서 " + deletedRows + "개의 행이 삭제되었습니다.");

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
}

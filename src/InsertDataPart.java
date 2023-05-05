import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertDataPart {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/TEST1?serverTimezone=UTC";
        String username = "root";
        String password = "rhsid312";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("MySQL 데이터베이스에 연결되었습니다!");

            String insertQuery = "INSERT INTO Market (시장명, 시장유형, 시장도로명주소, 시장개설주기, 점포수, 공중화장실보유여부, 주차장보유여부, 개설연도) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            Object[][] data = {
                    {"화천재래시장", "상설장", "강원도 화천군 화천읍 중앙로 4길 21-7외", "매일", 100, true, true, "1953"},
                    {"용인중앙시장", "상설+오일장", "경기도 용인시 처인구 금령로99번길 9", "매일, 5일+10일", 535, true, true, "1960"},
                    {"죽전로데오상점가시장", "상설", "경기도 용인시 수지구 용구대로 2725-3", "매일", 100, true, true, "1996"},
                    {"금촌전통시장", "상설장", "경기도 파주시 금정24길 18-6(금촌동)", "매일", 96, true, true, "1942"},
                    {"문산자유시장", "상설장", "경기도 파주시 문산읍 문향로 57", "매일", 145, true, true, "1964"},
                    {"광탄시장", "상설장", "경기도 파주시 광탄면 혜음로 1120번길 22", "매일", 169, true, true, "1956"}
            };

            for (Object[] row : data) {
                preparedStatement.setString(1, (String) row[0]);
                preparedStatement.setString(2, (String) row[1]);
                preparedStatement.setString(3, (String) row[2]);
                preparedStatement.setString(4, (String) row[3]);
                preparedStatement.setInt(5, (int) row[4]);
                preparedStatement.setBoolean(6, (boolean) row[5]);
                preparedStatement.setBoolean(7, (boolean) row[6]);
                preparedStatement.setString(8, (String) row[7]);

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            System.out.println("데이터가 Market 테이블에 삽입되었습니다.");

            preparedStatement.close();


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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertData {
    public static void main(String[] args) {
        String dbName="TEST1";
        String url = "jdbc:mysql://localhost:3306/TEST1?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
        String textFilePath = "/Users/ram/시장데이터.txt";
        String tableName="places";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, Config.USERNAME, Config.PASSWORD);
            System.out.println("MySQL 데이터베이스에 연결되었습니다!");

            String insertQuery = "INSERT INTO "+ tableName +"(시장명, 시장유형, 시장도로명주소, 시장개설주기, 점포수, 공중화장실보유여부, 주차장보유여부, 개설연도) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
//            InputStreamReader reader=new InputStreamReader(input,"UTF-8");
//            BufferedReader in=new BufferedReader(reader);
            // 한글 깨짐 현상 해결
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(textFilePath),"euc-kr"));

            String line;

            // 헤더 건너뛰기
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                String[] fields = line.split("\t");


                preparedStatement.setString(1, fields[0]);
                preparedStatement.setString(2, fields[1]);
                preparedStatement.setString(3, fields[2]);
                preparedStatement.setString(4, fields[3]);
                preparedStatement.setInt(5, Integer.parseInt(fields[4]));
                preparedStatement.setBoolean(6, "Y".equals(fields[5]));
                preparedStatement.setBoolean(7, "Y".equals(fields[6]));

                // 누락된 필드에 대한 기본값 설정
                if (fields.length > 7) {
                    preparedStatement.setString(8, fields[7]);
                } else {
                    preparedStatement.setString(8, null);
                }

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            System.out.println("데이터가 Market 테이블에 삽입되었습니다.");

            preparedStatement.close();
            bufferedReader.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("데이터베이스 연결 또는 쿼리 실행에 실패했습니다.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("텍스트 파일을 읽는 데 실패했습니다.");
            e.printStackTrace();
        }
    }
}

package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//클래스에서 인스턴스를 만들었을떄 자동으로 초기화 해주는 생성자
	public UserDAO() {
		try {
			String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
			String dbID = "phj98";
			String dbPassword = "java";
			Class.forName("oracle.jdbc.driver.OracleDriver"); //드라이버를 검색하게 해줌
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		}catch(Exception e) { 	//오류발생시 실행
			e.printStackTrace();//에러의 발생근원지를 찾아서 단계별로 에러를 출력합니다.
		}
	}
	
	//데이터베이스 안에 저장되어 있는 사용자 정보를 가져와 list에 담기
	public ArrayList<UserVO> search(String userName){
		String SQL = "SELECT * FROM USER1 WHERE USER_NAME LIKE ?"; //userName에 포함된 값이 같으면 보여줌
		ArrayList<UserVO> userList = new ArrayList<UserVO>();
		try {
			pstmt = conn.prepareStatement(SQL); 	//연결된 db에 sql문 넣기
			pstmt.setString(1, "%"+ userName + "%" ); 	//?로 설정해둔 영역에 넣을 변수(파라미터), %로 하나라도 같으면 보여줌
			rs = pstmt.executeQuery();  			//쿼리실행 결과를 rs에 담아줌
			while(rs.next()) {  					//rs의 값을 하나씩 읽기
				UserVO user = new UserVO();			//값을 담을 객체 선언
				user.setUserName(rs.getString(1));	//rs에 담은 name을 가져와 userName에 넣어줌
				user.setUserAge(rs.getInt(2));		//rs에 담은 age을 가져와 userAge에 넣어줌
				user.setUserGender(rs.getString(3));//rs에 담은 Gender을 가져와 userGender에 넣어줌
				user.setUserEmail(rs.getString(4));	//rs에 담은 Email을 가져와 userEmail에 넣어줌
				userList.add(user);					//값을 넣은 user객체를 리스트에 넣기
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return userList;	//정보 보내기
	}
}














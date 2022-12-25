package user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserSearchServlet")
public class UserSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");	//넘어온 값들을 UTF-8로 처리
		response.setContentType("text/html;charset=UTF-8");
		String userName = request.getParameter("userName"); //요청에 담긴 파라미터 값 userName을 가져옴
		response.getWriter().write(getJSON(userName));		//검색한 회원정보를 getJSON함수로 json형태로 출력하기
	}
	
	//특정 회원 검색시 정보가 json형태로 출력되는데 그것을 파싱해서 분석한다음 우리에게 보여줌
	public String getJSON(String userName) {
		if(userName == null) {								//userName 입력이 안되면 공백
			userName = "";
		}
		StringBuffer result = new StringBuffer("");	
		result.append("{\result\":[");						//buffer에 문자열 추가
		UserDAO userDAO = new UserDAO();					//회원 리스트를 가져오기 위해 생성
		ArrayList<User> userList = userDAO.search(userName);//매개변수로 넘어온 값을 넣어 메소드 실행
		for(int i=0; i<userList.size(); i++) {				//전체정보를 json형태로 출력
			//하나의 회원정보를 배열의 원소형태로 보여주기
			result.append("[{\"value\":\""+userList.get(i).getUserName()+"\"},");
			result.append("{\"value\":\""+userList.get(i).getUserAge()+"\"},");
			result.append("{\"value\":\""+userList.get(i).getUserGender()+"\"},");
			result.append("{\"value\":\""+userList.get(i).getUserEmail()+"\"}],");
		}
		result.append("]}");
		return result.toString();//StringBuffer는 toString 써서 출력하거나 리턴함
	}

}

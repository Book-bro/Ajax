<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width", initial-scale="1">
	<link rel="stylesheet" href="css/bootstrap.css">
	<title>JSP AJAX</title>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<script type="text/javascript">
		var request = new XMLHttpRequest(); //웹사이트에 요청을 보냄
		function searchFunction(){
			//입력한 userName을 한글 인코딩해서 post 방식으로 서블릿에 보냄, 서블릿은 요청을 받아처리후 json으로 내보냄
			request.open("Post","./UserSearchServlet?userName="+ encodeURIComponent(document.getElementById("userName").value), true);
			request.onreadystatechange = searchProcess; //요청이 성공시 실행
			request.send(null);
		};
		function searchProcess(){
			var table = document.getElementById("ajaxTable"); //테이블 값 가져오기
			table.innerHTML = "";
			if(request.readyState == 4 && request.status == 200){//요청처리완료 응답할 준비됨, 문서가 존재함
				var object = eval('('+ request.responseText + ')');//처리된 데이터를 받아 요청
				var result = object.result;//서블릿에서 result란 이름으로 변수를 담기때문에 result를 가져옴
				for(var i=0; i<result.length; i++){ //넘어온 결과의 갯수만큼
					var row = table.insertRow(0); //테이블 행 추가
					for(var j=0;j<result[i].length;j++){ //하나의 배열의 값이 끝날때까지
						var cell = row.insertCell(j);	//회원의 정보 갯수(이름,나이,주소,성별)를 탐색해서 해당 행에 갯수만큼 셀 추가
						cell.innerHTML = result[i][j].value; //셀에 i번쨰의 회원정보 넣기
					}
				}
			}
		}
		//처음부터 실행되게 해서 리스트 나오게 만듬
		window.onload = function(){
			searchFunction();
		}
	</script>
</head>
<body>
	<br>
	<div class="container">
		<div class="form-group row pull-right">
			<div class="col-xs-8">
				<!-- 입력한 값을 userName이란 id로 관리함
					onkeyup:입력하는 순간마다 searchFunction이 실행됨
				 -->
				<input class="form-control" id="userName" onkeyup="searchFunction()" type="text" size="20">
			</div>
			<div class="col-xs-2">
				<button class="btn btn-primary" onclick="searchFunction();" type="button">검색</button>
			</div>
		</div>
		<!-- 멤버 목록 테이블 -->
		<table class="table" style="text-align: center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<th style="background-color: #fafafa; text-align: center;">이름</th>
					<th style="background-color: #fafafa; text-align: center;">나이</th>
					<th style="background-color: #fafafa; text-align: center;">성별</th>
					<th style="background-color: #fafafa; text-align: center;">이메일</th>
				</tr>
			</thead>
			<!-- searchFunction을 할때마다 출력이 됨 -->
			<tbody id="ajaxTable">
				
			</tbody>
		</table>
	</div>
</body>
</html>
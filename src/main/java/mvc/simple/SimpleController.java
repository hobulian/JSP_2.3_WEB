package mvc.simple;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleController extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 2단계, 요청 파악
        // request 객체로 부터 사용자의 요청을 파악
        String type = request.getParameter("type");

        // 3단계, 요청한 기능 수행
        // 사용자 요쳉에 따라 알맞은 코드
        Object resultObj = null;
        if (type == null || type.equals("greeting")) {
            resultObj = "안녕하세요";
        } else if (type.equals("date")) {
            resultObj = new java.util.Date();
        } else {
            resultObj = "Invalid type";
        }

        // 4단계, request나 session에 처리 결과 저장
        request.setAttribute("result", resultObj);

        // 5단계, RequestDispatcher를 사용하여 알맞은 뷰로 포워딩
        RequestDispatcher dispatcher = request.getRequestDispatcher("/chap18/simpleView.jsp");
        dispatcher.forward(request, response);

    }
}

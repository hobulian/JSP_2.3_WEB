package mvc.hello;

import mvc.command.CommandHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) {
        req.setAttribute("hello", "안녕하세요!");
        return "/chap18/hello.jsp";
    }
}

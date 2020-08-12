package controllers.auth;

import com.google.gson.Gson;
import controllers.ControllerConstants;
import models.Response;
import models.User;
import services.ResponseService;
import services.UserService;
import utils.RequestParseUtil;;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = ControllerConstants.currentURL + "/register")
public class LocalRegisterController extends HttpServlet{

    private UserService userService ;
    private ResponseService responseService;

    public LocalRegisterController() throws IOException {
        userService = UserService.getInstance();
        responseService  = new ResponseService();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        super.service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Response response = null;
        try{
            User user = RequestParseUtil.parseBodyToObj(req, User.class);
            int id = userService.createUser(user);
            response = Response.builder()
                    .status(200)
                    .success(true)
                    .body(userService.getUserById(id))
                    .build();
            PrintWriter out = resp.getWriter();
            out.print(responseService.getResponseJSON(response));
            out.flush();
        } catch (Exception ex){
            ex.printStackTrace();
            response = Response.builder()
                    .body(ex.getMessage())
                    .status(500)
                    .success(false).build();
            resp.getWriter().print(responseService.getResponseJSON(response));
        }
    }
}

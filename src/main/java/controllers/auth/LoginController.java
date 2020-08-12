package controllers.auth;

import com.google.gson.Gson;
import controllers.ControllerConstants;
import models.Response;
import models.User;
import services.ResponseService;
import services.UserService;
import utils.JwtUtil;
import utils.RequestParseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = ControllerConstants.currentURL + "/login")
public class LoginController extends HttpServlet {

    private UserService userService ;
    private ResponseService responseService;
    private JwtUtil jwtUtil;

    public LoginController() throws IOException {
        userService = UserService.getInstance();
        responseService  = new ResponseService();
        jwtUtil = JwtUtil.getInstance();
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
            Map reqBody = RequestParseUtil.parseBodyToMap(req);
            User user = userService.loginUser(reqBody.get("email").toString(),
                    reqBody.get("password").toString());
            if(user != null){
                user.setToken(jwtUtil.createJWT("tinder", String.valueOf(user.getId())));
                response = Response.builder()
                        .status(200)
                        .success(true)
                        .body(user)
                        .build();
            }
            else {
                response = Response.builder()
                        .status(401)
                        .success(false)
                        .body("Invalid email or password")
                        .build();
            }
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

package controllers.User;

import com.google.gson.Gson;
import controllers.ControllerConstants;
import models.Preference;
import models.Response;
import services.PreferenceService;
import services.ResponseService;
import utils.RequestParseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = ControllerConstants.currentURL + "/preference")
public class PreferenceController extends HttpServlet {
    private PreferenceService preferenceService ;
    private ResponseService responseService;

    public PreferenceController() throws IOException {
        preferenceService = PreferenceService.getInstance();
        responseService  = new ResponseService();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        super.service(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        Response response = null;
        Gson gson = new Gson();
        try {
            //Preference preference = RequestParseUtil.parseBodyToObj(req, Preference.class);
            Preference preference = gson.fromJson(req.getReader(), Preference.class);
            int id = preferenceService.createPreference(preference);
            response = Response.builder()
                    .status(200)
                    .success(true)
                    .body(id)
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

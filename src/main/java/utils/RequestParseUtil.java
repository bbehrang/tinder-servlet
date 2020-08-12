package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.User;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class RequestParseUtil<T> {
    private static Gson gson = new Gson();

    public static <T> T parseBodyToObj(HttpServletRequest req, Type type) throws IOException {
        return gson.fromJson(req.getReader(), (Type) type.getClass());
    }
    public static Map parseBodyToMap(HttpServletRequest req) throws IOException {
        Map<String, Object> map = gson.fromJson(req.getReader(), new TypeToken<Map<String, Object>>() {}.getType());
        return map;
    }
}

package services;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import models.Response;

public class ResponseService{
    private Gson gson;

    public ResponseService(){
        this.gson = new Gson();
    }
    public String getResponseJSON(Response response){
        return gson.toJson(response);
    }
}

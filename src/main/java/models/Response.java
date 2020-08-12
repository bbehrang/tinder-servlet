package models;

import com.google.gson.Gson;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response<T> {
    private T body;
    private int status;
    private boolean success;
}

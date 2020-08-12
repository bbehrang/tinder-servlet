package models;

import lombok.Builder;
import lombok.Data;

import java.net.URL;
import java.util.Base64;
import java.util.List;

@Data
@Builder
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String avatar;
    private int age;
    private String bio;
    private List<URL> images;
    private Preference preference;
    private String token;
}

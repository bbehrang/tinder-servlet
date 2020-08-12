package models;

import lombok.Data;

import java.net.URL;

@Data
public class Preference {
    private int age_min;
    private int age_max;
    private String gender;
    private int user_id;
}

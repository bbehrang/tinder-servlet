package repositories;

import dao.PreferenceDAO;
import models.Preference;
import org.apache.commons.dbcp2.BasicDataSource;
import utils.DataBaseUtility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreferenceRepository implements PreferenceDAO {
    private static PreferenceRepository preferenceRepository;
    private final BasicDataSource dataSource;

    private PreferenceRepository() throws IOException {
        dataSource = DataBaseUtility.getDataSource();
    }
    public static PreferenceRepository getInstance() throws IOException {
        if (preferenceRepository == null) {
            preferenceRepository = new PreferenceRepository();
        }
        return preferenceRepository;
    }
    @Override
    public int createPreference(Preference preference) throws SQLException {
        int id;
        String query =
                "insert into prefrences (age_min, age_max, gender, user_id) " +
                        "values (?, ?, ?, ?) returning id";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
        ){
            stmt.setInt(1, preference.getAge_min());
            stmt.setInt(2, preference.getAge_max());
            stmt.setString(3, preference.getGender());
            stmt.setInt(4, preference.getUser_id());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            id = rs.getInt("id");
        }
        return id;
    }
}

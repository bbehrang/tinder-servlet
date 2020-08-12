package dao;

import models.Preference;

import java.sql.SQLException;

public interface PreferenceDAO {
    int createPreference(Preference preference) throws SQLException;
}

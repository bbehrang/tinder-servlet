package services;

import models.Preference;
import repositories.PreferenceRepository;

import java.io.IOException;
import java.sql.SQLException;

public class PreferenceService {
    private static PreferenceService preferenceService;
    private static PreferenceRepository preferenceRepository;

    private PreferenceService() throws IOException {
        preferenceRepository = PreferenceRepository.getInstance();
    }
    public static PreferenceService getInstance() throws IOException {
        if (preferenceService == null) {
            preferenceService = new PreferenceService();
        }
        return preferenceService;
    }

    public int createPreference(Preference preference) throws SQLException {
        return preferenceRepository.createPreference(preference);
    }

}

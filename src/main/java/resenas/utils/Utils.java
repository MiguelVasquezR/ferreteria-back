package resenas.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class Utils {

    private static final Dotenv dotenv = Dotenv.load();

    public static final int PASSWORD_TOKEN_EXPIRATION;
    public static final int PASSWORD_TOKEN_EXPIRATION_REFRESH;
    public static final String PASSWORD_TOKEN;
    public static final String PASSWORD_TOKEN_REFRESH;
    public static final String MYSQL_PORT;
    public static final String MYSQL_USERNAME;
    public static final String MYSQL_PASSWORD;
    public static final String MYSQL_DATABASE_NAME;
    public static final String MYSQL_HOST;

    static {
        PASSWORD_TOKEN = dotenv.get("PASSWORD_TOKEN");
        PASSWORD_TOKEN_REFRESH = dotenv.get("PASSWORD_TOKEN_REFRESH");
        PASSWORD_TOKEN_EXPIRATION = Integer.parseInt(dotenv.get("PASSWORD_TOKEN_EXPIRATION"));
        PASSWORD_TOKEN_EXPIRATION_REFRESH = Integer.parseInt(dotenv.get("PASSWORD_TOKEN_EXPIRATION_REFRESH"));

        MYSQL_PORT = dotenv.get("MYSQL_PORT");
        MYSQL_USERNAME = dotenv.get("MYSQL_USERNAME");
        MYSQL_PASSWORD = dotenv.get("MYSQL_PASSWORD");
        MYSQL_DATABASE_NAME = dotenv.get("MYSQL_DATABASE_NAME");
        MYSQL_HOST = dotenv.get("MYSQL_HOST");
    }
}

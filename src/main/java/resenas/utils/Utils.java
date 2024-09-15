package resenas.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class Utils {

    private static final Dotenv dotenv = Dotenv.load();

    public static final int PASSWORD_TOKEN_EXPIRATION;
    public static final String PASSWORD_TOKEN;
    public static final String MYSQL_PORT;
    public static final String MYSQL_USERNAME;
    public static final String MYSQL_PASSWORD;
    public static final String MYSQL_DATABASE_NAME;
    public static final String MYSQL_HOST;
    public static final String PASSWORD_ENCRYPT;
    public static final String CIPHER_INSTAC;
    public static final String PASSWORD_GOOGLE;
    public static final String REMITENTE_GOOGLE;
    public static final String NAME_FILE_BINARY_PATH;

    static {
        PASSWORD_TOKEN = dotenv.get("PASSWORD_TOKEN");
        PASSWORD_TOKEN_EXPIRATION = Integer.parseInt(dotenv.get("PASSWORD_TOKEN_EXPIRATION"));
        MYSQL_PORT = dotenv.get("MYSQL_PORT");
        MYSQL_USERNAME = dotenv.get("MYSQL_USERNAME");
        MYSQL_PASSWORD = dotenv.get("MYSQL_PASSWORD");
        MYSQL_DATABASE_NAME = dotenv.get("MYSQL_DATABASE_NAME");
        MYSQL_HOST = dotenv.get("MYSQL_HOST");
        PASSWORD_ENCRYPT = dotenv.get("PASSWORD_ENCRYPT");
        CIPHER_INSTAC = dotenv.get("CIPHER_INSTACE");
        PASSWORD_GOOGLE = dotenv.get("PASSWORD_GOOGLE");
        REMITENTE_GOOGLE = dotenv.get("REMITENTE_GOOGLE");
        NAME_FILE_BINARY_PATH = dotenv.get("NAME_FILE_BINARY_PATH");
    }
}

package taskpilot.utils;

public class DataFormatValidation{

    public static boolean validation(String email, String username, String password) {

        boolean emailValidation = email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$") || email.equals("");
        boolean usernameValidation = username.length() >= 4 || username.equals("");
        boolean passwordValidation = password.length() >= 8 || password.equals("");
        
        return (emailValidation && usernameValidation && passwordValidation);
    }
}

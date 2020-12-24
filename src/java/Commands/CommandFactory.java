package Commands;

public class CommandFactory {

    public static Command createCommand(String action) {
        Command c;
        if(action == null){
             action = "";
        }
        
        switch (action) {
            case "Register":
            c = new RegisterCommand();    
            break;
            
            case "Login":
            c = new LoginCommand();
            break;
            
            case "Logout":        
            c = new LogoutCommand();
            break;
            
            case "PasswordReset":
            c = new PasswordResetCommand();    
            break;

            case "changeLanguage":
            c = new ChangeLanguageCommand();     
            break;
            
            case "PutAddress":
            c = new InsertAddressDetailsCommand();     
            break;
            
            case "ChangeUsername":
            c = new ChangeUsernameCommand();     
            break;
            
            case "ForgotPasswordReset":
            c = new ForgotPasswordResetCommand();     
            break;
            
            case "SendEmailCode":
            c = new SendEmailCodeCommand();    
            break;
            
            case "ValidateEmailCode":
            c = new ValidateEmailCodeCommand();    
            break;
                                            
            default:
            // Logic for incorrect action
            c = new NoActionSuppliedCommand();
        }

        return c;
    }
}

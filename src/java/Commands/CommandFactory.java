package Commands;

public class CommandFactory {

    public static Command createCommand(String action) {
        Command c;
        if(action == null){
             action = "";
        }
        
        switch (action) {
            case "register":
            c = new RegisterCommand();    
            break;
            
            case "logout":        
            c = new LogoutCommand();
            break;
            
            case "loan":        
            c = new LoanBookCommand();
            break;
            
            case "PasswordReset":
            c = new PasswordResetCommand();    
            break;

            case "changeLanguage":
            c = new ChangeLanguageCommand();     
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
                      
            case "login":
            default:
            c = new LoginCommand();
        }

        return c;
    }
}

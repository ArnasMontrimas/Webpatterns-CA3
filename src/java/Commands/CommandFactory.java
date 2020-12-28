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
            
            case "returnLoan":
            c = new ReturnLoanCommand();
            break;
            
            case "changePassword":
            c = new ChangePasswordCommand();    
            break;

            case "changeLanguage":
            c = new ChangeLanguageCommand();     
            break;
            
            case "editProfile":
            c = new EditProfileCommand();     
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
            
            case "searchBook":
            c = new SearchBookCommand();
            break;    
            
            case "login":
            c = new LoginCommand();
            break;

            default:
            c = new LoginCommand(true);
        }

        return c;
    }
}

package Commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    public String doAction(HttpServletRequest request, HttpServletResponse response);
}
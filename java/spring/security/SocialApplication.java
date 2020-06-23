package java.spring.security;

public class SocialApplication {
    
    @GetMapping("/error")
    public String error() {
	    String message = (String) request.getSession().getAttribute("error.message");
	    request.getSession().removeAttribute("error.message");
	    return message;
    }
}
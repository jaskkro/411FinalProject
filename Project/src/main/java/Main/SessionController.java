package Main;

import java.util.Arrays;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class SessionController {


	public SessionController() {
	}

	@GetMapping("/start-session")
	public String startSession(HttpServletResponse response, Model model) throws Exception {
            
            if(!Application.sessionManager.checkPendingSession()) {
                SessionModel newSession = Application.sessionManager.launchNewSession();
                response.addCookie(new Cookie("sessionID", newSession.getSessionID().toString()));
                response.addCookie(new Cookie("userID", newSession.getClientID().toString()));
            }
            else {
                SessionModel currentSession = Application.sessionManager.getPendingSession();
                response.addCookie(new Cookie("sessionID", currentSession.getSessionID().toString()));
                response.addCookie(new Cookie("userID", currentSession.getClientID().toString()));
                Application.sessionManager.bumpPendingSession();
            }
            
            return "redirect:/started-session";
	}
        
        @GetMapping("/started-session")
        public String startedSession(Model model, HttpServletRequest request, @CookieValue("userID") String userid) {
            
            //Block out fields to be attatched to model
            String sessionStatus = "";
            String usrid = userid;
            String sessid = "";
            String turn = "";
            String message = "";
            
            
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                Arrays.stream(cookies).forEach(c -> System.out.println(c.getName() + "=" + c.getValue()));
            }
            
            //Check if session has begun (both parties connected)
            Integer sessionid = Application.sessionManager.checkSessionByUser(userid);
            
            //Session still waiting for additional party
            if (sessionid == 0) {
                sessionStatus = "Session setup in progress. Waiting on one more user!";
                message = "Please be patient! This should only take a moment.";
            }
            //Session underway
            if (sessionid > 0) {
                sessionStatus = "Session in progress!";
                sessid = sessionid.toString();
                //Determine if session is over
                boolean finished = Application.sessionManager.checkSessionComplete(userid);
                
                if(!finished) {
                    //Determine who's turn it is
                    boolean myTurn = Application.sessionManager.checkTurn(sessionid, userid);

                    //My turn
                    if (myTurn) {
                        turn = "It's your turn.";
                        message = "You have 15 seconds to make a change to the meme..";
                    }//Not my turn
                    else {
                        turn = "It's not your turn..";
                        message = "Please wait for the other user to finish their turn.";
                    }

                    //Process a turn's requested action if valid submitter
                    //(Checkpoint 3)
                    
                }//Else session is completed..
                else {
                    return("redirect:/session-tagging");
                }
                      
            }
            
            //Attatch model fields
            model.addAttribute("sessionStatus", sessionStatus);
            model.addAttribute("userID", usrid);
            model.addAttribute("sessionID", sessid);
            model.addAttribute("turnMessage", turn);
            model.addAttribute("descriptionMessage", message);
        
            
            return "started-session";
        }
        
        @GetMapping("/session-tagging")
        public String finishedSession(Model model, HttpServletRequest request, @CookieValue("userID") String userid) {
            String sessionStatus = "Session Complete!";
            String message = "You may add tags to your creation now.";
            model.addAttribute("sessionStatus", sessionStatus);
            model.addAttribute("descriptionMessage", message);
            model.addAttribute("tags", new TagSet());
            return "session-tagging";
        }
        
        @PostMapping("/session-tagging")
        public String greetingSubmit(@ModelAttribute TagSet tags) {
            //Process tags & save image
            return "redirect:/gallery";
    }
}
                    
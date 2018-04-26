/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Big Bertha
 */
public class SessionManager {
    
    private ArrayList<SessionModel> currentSessions = new ArrayList<>();
    private SessionModel pendingSession = null;
    
    private final int turnLimit = 5;
    
    //Check if a session is currently queuing
    public boolean checkPendingSession() {
        return(pendingSession != null);
    }
    
    //Load a new session to queue
    public SessionModel launchNewSession() throws IOException {
        pendingSession = new SessionModel();
        return pendingSession;
    }
    
    //Get session currently queuing
    public SessionModel getPendingSession() {
        return pendingSession;
    }
    
    //Remove session from queuing and begin session
    public void bumpPendingSession() {
        pendingSession.startSession();
        currentSessions.add(pendingSession);
        pendingSession = null;       
    }
    
    //Verify if user is in an active session
    public Integer checkSessionByUser(String userid) {
        if (pendingSession != null && pendingSession.containsUser(userid)) {
            return 0;
        }
        else {
            for (int i = 0; i < currentSessions.size(); i++) {
                if (currentSessions.get(i).containsUser(userid))
                    return currentSessions.get(i).getSessionID();
            }
        }
        
        //This case will require additional handling in the future as the session does not exist..
        return 0;
    }
    
    //Check who's turn it is
    public boolean checkTurn(Integer sessionid, String userid) {
        for (int i = 0; i < currentSessions.size(); i++) {
            if (currentSessions.get(i).containsUser(userid)) {
                SessionModel check = currentSessions.get(i);
                return check.isTurn(userid);
            }   
        }
        return false;
    }
    
    //Check if session is over
    public boolean checkSessionComplete(String userid) {
        for (int i = 0; i < currentSessions.size(); i++) {
            if (currentSessions.get(i).containsUser(userid)) {
                SessionModel check = currentSessions.get(i);
                return check.isComplete();
            }
        }
        return true;
    }

    public boolean addTags(String userid, TagSet tags) {
        for (int i = 0; i < currentSessions.size(); i++) {
            if (currentSessions.get(i).containsUser(userid)) {
                SessionModel check = currentSessions.get(i);
                check.addTags(tags);
                if (check.getTagCount() > 3)
                    return true;
            }
        }
        return false;
    }

    public FinishedImage getFinishedSessionImage(String userid) {
        for (int i = 0; i < currentSessions.size(); i++) {
            if (currentSessions.get(i).containsUser(userid)) {
                SessionModel check = currentSessions.get(i);
                return(check.getCanvas());
            }
        }
        return null;
    }

    public String getCanvasByID(String userid) {
        for (int i = 0; i < currentSessions.size(); i++) {
            if (currentSessions.get(i).containsUser(userid)) {
                SessionModel check = currentSessions.get(i);
                return(check.getCanvas().getName());
            }
        }
        return null;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


/**
 *
 * @author Big Bertha
 */
public class SessionModel {
    
    private static int sessionCount = 0;
    
    private final Integer sessionID;
    private final Integer clientOneID;
    private final Integer clientTwoID;
    private boolean clientOneSet = false;
    private boolean clientTwoSet = false;
    
    private int turnCount = 0;
    
    private CleanImage original;
    private FinishedImage canvas;
    
    //Setup new session with unique identifiers
    public SessionModel() throws IOException {
        sessionID = 100 + sessionCount;
        sessionCount +=1;
        clientOneID = sessionID+10000;
        clientTwoID = sessionID+20000;
        canvas = new FinishedImage("src/main/resources/static/finished/finished"+System.currentTimeMillis()+".jpg");
        original = Application.cleanImages.getRandomImage();       
        Files.copy(Paths.get(original.getName()), Paths.get(canvas.getName()), StandardCopyOption.REPLACE_EXISTING);
    }
    
    //Returns an unassigned client ID
    public Integer getClientID() {
        if (!clientOneSet) {
            clientOneSet = true;
            return clientOneID;
        }
        clientTwoSet = true;
        return clientTwoID;
    }
    
    //Verify all IDs have been assigned
    public boolean checkClientsAssigned() {
        return clientOneSet && clientTwoSet;
    }
    
    //Reference session ID
    public Integer getSessionID() {
        return sessionID;
    }
    
    //Get turn count
    public int getTurnCount() {
        return turnCount;
    }
    
    //Incrememnt turn
    public void incrementTurn() {
        turnCount++;
    }
    
    //Verify if user is in session
    public boolean containsUser(String userid) {
        if (clientOneID.toString().contentEquals(userid) || clientTwoID.toString().contentEquals(userid))
            return true;
        return false;
    }
    
    //Verify if it is currently the user's turn
    public Boolean isTurn(String userid) {
        if (turnCount%2 == 0 && clientOneID.toString().contentEquals(userid))
            return true;
        if (turnCount%2 == 1 && clientTwoID.toString().contentEquals(userid))
            return true;
        return false;
    }
    
    //Recursive function calls scheduled tasks on another thread to increment turns at 15s intervals
    public void startSession() {
        if (turnCount < 5) {
            new java.util.Timer().schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    incrementTurn();
                    startSession();
                }
            }, 21000);
        }
        else
            turnCount = 100;
    }
    
    //Verify if a session has ticked through to completion
    public boolean isComplete() {
        return(turnCount == 100);
    }
    
    //Add tags to finished image
    public void addTags(TagSet tags) {
        canvas.addTag(tags.getTag1());
        canvas.addTag(tags.getTag2());
    }

    public int getTagCount() {
        return canvas.getTagCount();
    }

    public FinishedImage getCanvas() {
        return canvas;
    }
    
    
}

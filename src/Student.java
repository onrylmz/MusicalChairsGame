
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//***************************************************************************************************************************************************

// Given class (needs further implementation)

public class Student extends Thread
{
  //=================================================================================================================================================

  // Given entities

  private final String name ;
  private final MusicalChairsGame game;
  private String satChairs;
  private int whichRoundLost;
  private int whichRoundWon;
  private boolean lostGame;

  //=================================================================================================================================================

  public Student ( String name , MusicalChairsGame game )
  {
      this.name = name;
      this.game = game;
      this.satChairs = "";
      this.whichRoundLost = 0;
      this.whichRoundWon = 0;
      this.lostGame = false;
    // ...
  }

    //=================================================================================================================================================

    public String getSatChairs() {
        return satChairs;
    }

    public void setSatChairs(String satChairs) {
        this.satChairs = this.satChairs + " " + satChairs;
    }

    
    public boolean isLostGame() {
        return lostGame;
    }

    public void setLostGame(boolean lostGame) {
        this.lostGame = lostGame;
    }

    public int getWhichRoundLost() {
        return whichRoundLost;
    }

    public int getWhichRoundWon() {
        return whichRoundWon;
    }

    public void setWhichRoundLost(int whichRoundLost) {
        this.whichRoundLost = whichRoundLost;
    }

    public void setWhichRoundWon(int whichRoundWon) {
        this.whichRoundWon = whichRoundWon;
    }
    
    public String getStudentName()
    {
        return name;
    } 
    
    
    
    public void waitingMusic()
    {
        System.out.println( getStudentName() + " : " + "I'm waiting for the music to start.");
    }
    
    public void walkingAroundChairs()
    {
        System.out.println(getStudentName() + " : " + "I'm walking around the chairs");
    }
    
    public void sitOnChairs(Chair c)
    {
        System.out.println(getStudentName() + " : " + "Phew, I got chair " + c.getID() + ".");
    }
    
    public void notSitOnChairs()
    {
        System.out.println(getStudentName() + " : " + "I couldn't sit. Sigh...");
    }
    
    public void report()
    {
        if(whichRoundLost != 0)
        {
            if(satChairs.length() == 0)
            {
                System.out.println(getStudentName() + " : " + "I lost the game in round " + whichRoundLost + "! :( I never got any chairs.");
            }
            else if(satChairs.length() > 2)
            {
                System.out.println(getStudentName() + " : " + "I lost the game in round " + whichRoundLost + "! :( I sat on " + "chairs " + satChairs.replace(" ", ",").replaceFirst(",", "") + ".");
            }
            else
            {
                System.out.println(getStudentName() + " : " + "I lost the game in round " + whichRoundLost + "! :( I sat on " + "chair" + satChairs + ".");
            }
            
        }
        if(whichRoundWon != 0)
        {
            System.out.println(getStudentName() + " : " + "I won the game in round " + whichRoundWon + "! :) I sat on " + "chairs " + satChairs.replace(" ", ",").replaceFirst(",", "") + ".");
        }
        
    }
  
    @Override
    public void run ()
    {
        while(true)
        {
            game.barrierRoundBegins.await();
            waitingMusic();
            game.barrierMusicStarts.await();
            walkingAroundChairs();
            game.barrierMusicStops.await();
            Chair sittedChair = game.sitDownOnAnAvailableChair(this, game.getWhichRoundExecuted());
            if(sittedChair != null)
            {
                sitOnChairs(sittedChair); 
                game.barrierPlayersSettle.await();
            }
            else
            {
                notSitOnChairs();
                game.barrierPlayersSettle.await();
                game.barrierReporting.await();
                report();
                break;
            }
            
            if(game.getWhichRoundExecuted() == game.getNumberOfPlayers())
            {
                setWhichRoundWon(game.getNumberOfRounds());
                game.barrierReporting.await();
                report();
                break;
            }
            
        }
        
    }
    

  //=================================================================================================================================================
}

//***************************************************************************************************************************************************


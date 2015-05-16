
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Given class (needs further implementation)

public class MusicalChairsGame
{
  //=================================================================================================================================================

  // Given physical entities

  private List<Chair>       chairs                  ;
  private List<Student>     students                ;
  private final int         numberOfPlayers         ;
  private int               numberOfCurrentPlayers  ;
  private final int         numberOfRounds          ;
  private int               whichRoundExecuted      ;

  // Suggested logical entities

  public Barrier      barrierRoundBegins   ;
  public Barrier      barrierMusicStarts   ;
  public Barrier      barrierMusicStops    ;
  public Barrier      barrierPlayersSettle ;
  public Barrier      barrierReporting     ;

  // ...

  //=================================================================================================================================================

  public MusicalChairsGame ( int numberOfPlayers )
  {
      chairs = new ArrayList<>(numberOfPlayers);
      
      for(int i = 0; i < numberOfPlayers - 1; i++)
      {
          Chair chair = new Chair(i + 1);
          chairs.add(chair);
      }
      
      students = new ArrayList<>(numberOfPlayers);
      this.numberOfPlayers = numberOfPlayers;
      this.numberOfRounds  = numberOfPlayers - 1;
      this.numberOfCurrentPlayers = numberOfPlayers;
      
      this.whichRoundExecuted = 1;
      
      barrierRoundBegins = new Barrier(numberOfCurrentPlayers);
      barrierMusicStarts = new Barrier(numberOfCurrentPlayers);
      barrierMusicStops = new Barrier(numberOfCurrentPlayers);
      barrierPlayersSettle = new Barrier(numberOfCurrentPlayers);
      barrierReporting = new Barrier(numberOfRounds);
      
    // ...
  }

  //=================================================================================================================================================

  // Suggested service

  public int getNumberOfPlayers ()
  {
      return numberOfPlayers;
  }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public int getWhichRoundExecuted() {
        return whichRoundExecuted;
    }

    public void setWhichRoundExecuted() {
        this.whichRoundExecuted += 1;
    }

    public List<Chair> getChairs() {
        return chairs;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setNumberOfCurrentPlayers() {
        this.numberOfCurrentPlayers -= 1;
    }

    
  //=================================================================================================================================================

  // Suggested service

  public void playMusicForARandomDuration () throws InterruptedException
  {
            
        // Sleep for a random amount of time between 500 and 1000 milliseconds

        Random rand = new Random();
        
        int waitingTime = rand.nextInt(500) + 500;
        
        Thread.sleep(waitingTime);
  }

  //=================================================================================================================================================

  // Suggested service

  public synchronized Chair sitDownOnAnAvailableChair (Student s, int whichRound)
  {
      for(Chair chair : getChairs())
      {
          if(chair.isAvailable())
          {
              chair.setAvailable(false);
              s.setSatChairs(chair.getID() + "");
              
              return chair;
          }
      }
      
      s.setWhichRoundLost(whichRound);
      return null;
  }

  //=================================================================================================================================================

  // Suggested service

  public synchronized void standUpFromChair ()
  {       
      for(Chair chair : chairs)
          chair.setAvailable(true);
  }

  //=================================================================================================================================================

  // Suggested service

  public synchronized void removeOneChair ()
  {
      int index = this.chairs.size() - 1;
      
      this.chairs.remove(index);
  }

  //=================================================================================================================================================
}

//***************************************************************************************************************************************************


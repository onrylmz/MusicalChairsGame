
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;




//***************************************************************************************************************************************************

// Given class (needs further implementation)

public class Teacher extends Thread
{
    
    private final MusicalChairsGame game;
    
    //=================================================================================================================================================

    public Teacher ( MusicalChairsGame game )
    {
        this.game = game;
      // ...
    }

    //=================================================================================================================================================

    //=================================================================================================================================================

    public void roundAboutBegin(int round)
    {
        System.out.println("Teacher : " + "Round " + round + " is about the begin now.");
    }
    
    public void startMusic()
    {
        System.out.println("Teacher : I started the music right now");
    }
    
    public void playMusic() throws InterruptedException
    {
        System.out.println("Teacher : I will keep playing music for a random amount of time...");
    }
    
    public void stopMusic()
    {
        System.out.println("Teacher : I stopped the music!");
    }
    
    public void removeOneChair()
    {
        System.out.println("Teacher : I am removing one chair.");
    }
    
    public void tellGameExperience()
    {
        System.out.println("Teacher : Now tell me about your game experience now.");
    }
    //=================================================================================================================================================

    @Override
    public void run ()
    {
        while(true)
        {     
            if(!game.barrierReporting.isAllReachToBarrier())
            {
                if(game.barrierRoundBegins.isAllReachToBarrier())
                {
                    roundAboutBegin(game.getWhichRoundExecuted());
                    game.barrierRoundBegins.setNumberOfThreadsCurrentlyWaiting(0);
                    game.barrierRoundBegins.awakeThreads();
                }
                if(game.barrierMusicStarts.isAllReachToBarrier())
                {
                    startMusic();

                    game.barrierMusicStarts.setNumberOfThreadsCurrentlyWaiting(0);
                    game.barrierMusicStarts.awakeThreads();                    

                    try {
                        playMusic();
                        game.playMusicForARandomDuration();
                    } catch (InterruptedException ex) {
                        System.err.println("Playing Music Interrupt!");
                    }
                }
                if(game.barrierMusicStops.isAllReachToBarrier())
                {
                    stopMusic();
                    game.barrierMusicStops.setNumberOfThreadsCurrentlyWaiting(0);
                    game.barrierMusicStops.awakeThreads();

                }
                if(game.barrierPlayersSettle.isAllReachToBarrier())
                {
                    System.out.println("");

                    if(game.getChairs().size() != 1)
                    {
                        removeOneChair();
                        game.removeOneChair();
                    }
                    
                    game.setWhichRoundExecuted();
                    game.setNumberOfCurrentPlayers();

                    game.standUpFromChair();
                    
                    game.barrierRoundBegins.setNumberOfThreadsToReachBarrierPoint();
                    game.barrierMusicStarts.setNumberOfThreadsToReachBarrierPoint();
                    game.barrierMusicStops.setNumberOfThreadsToReachBarrierPoint();
                    game.barrierPlayersSettle.setNumberOfThreadsToReachBarrierPoint();

                    game.barrierPlayersSettle.setNumberOfThreadsCurrentlyWaiting(0);
                    game.barrierPlayersSettle.awakeThreads();
                }
            }
            else
            {
                tellGameExperience();
                game.barrierReporting.setNumberOfThreadsCurrentlyWaiting(0);
                game.barrierReporting.awakeThreads();
                break;                
            }
        }
        
    }

    //=================================================================================================================================================

    // main method that is going to be used during evaluation
    //
    // (If you plan to have bonus extensions like GUIs which require a differently structured main method, please inform the staff beforehand.)

    public static void main ( String[] args )
    {
      //-----------------------------------------------------------------------------------------------------------------------------------------------

      if ( args.length == 0 )  {args = new String[]{ "Student1" , "Student2" , "Student3" , "Student4" , "Student5" } ; }

      //-----------------------------------------------------------------------------------------------------------------------------------------------

      int               numberOfStudents = args.length                               ;
      MusicalChairsGame game             = new MusicalChairsGame( numberOfStudents ) ;

      //-----------------------------------------------------------------------------------------------------------------------------------------------

      for ( int i = 0 ; i < numberOfStudents ; i++ )  
      { 
          Student student = new Student( args[i] , game );

          game.getStudents().add(student);

          student.start();
      }



      new Teacher( game ).start() ;

      //-----------------------------------------------------------------------------------------------------------------------------------------------
    }

    //=================================================================================================================================================
}

//***************************************************************************************************************************************************


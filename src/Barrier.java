
import java.util.logging.Level;
import java.util.logging.Logger;

//***************************************************************************************************************************************************

// Given class (needs further implementation)

public class Barrier
{
  //=================================================================================================================================================

  // Given entities

  private int numberOfThreadsCurrentlyWaiting    ;
  private int numberOfThreadsToReachBarrierPoint ;

  //=================================================================================================================================================

    public Barrier ( int numberOfThreadsToReachBarrierPoint )
    {
        this.numberOfThreadsToReachBarrierPoint = numberOfThreadsToReachBarrierPoint;
        this.numberOfThreadsCurrentlyWaiting = 0;
      // ...
    }

    public void setNumberOfThreadsCurrentlyWaiting(int numberOfThreadsCurrentlyWaiting) {
        this.numberOfThreadsCurrentlyWaiting = numberOfThreadsCurrentlyWaiting;
    }

    public int getNumberOfThreadsCurrentlyWaiting() {
        return numberOfThreadsCurrentlyWaiting;
    }

    public void setNumberOfThreadsToReachBarrierPoint() {
        this.numberOfThreadsToReachBarrierPoint -= 1;
    }
    
    public int getNumberOfThreadsToReachBarrierPoint() {
        return numberOfThreadsToReachBarrierPoint;
    }
    
    public synchronized void awakeThreads()
    {
        notifyAll();
    }
    public boolean isAllReachToBarrier()
    {
        return numberOfThreadsCurrentlyWaiting == numberOfThreadsToReachBarrierPoint;
    }
  //=================================================================================================================================================

  public synchronized void await ()
  {
        
        try {
            numberOfThreadsCurrentlyWaiting += 1;
            wait();
        } catch (InterruptedException ex) {
            Logger.getLogger(Barrier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
  }

  //=================================================================================================================================================
}

//***************************************************************************************************************************************************


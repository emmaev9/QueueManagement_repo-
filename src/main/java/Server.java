import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {//server
    private BlockingQueue<Client> queue;
    private AtomicInteger waitingPeriod = new AtomicInteger();
    private int nrServer;
    private boolean isActive = true;
    private Strategy strategy;
    private Scheduler scheduler;
    public int currentID = 0, currentServiceTime = 0, currentArrivalTime = 0, finishTime = 0, timeOfArrival = 0;
    public boolean isClientBeingProcessed = false;

    public void setNrServer(int nrServer) {
        this.nrServer = nrServer;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getNrServer() {
        return nrServer;
    }

    public BlockingQueue<Client> getQueue() {
        return queue;
    }

    public void setQueue(BlockingQueue<Client> queue) {
        this.queue = queue;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public Server(BlockingQueue<Client> q, AtomicInteger waitingPeriod) {
        this.queue = q;
        this.waitingPeriod = waitingPeriod;
    }

    public Server(int ID, int maxNumber) {
        this.nrServer = ID;
        this.queue = new ArrayBlockingQueue<Client>(1000);
        this.waitingPeriod.set(0);
    }

    public void addClient(Client newClient) {
        try {
            //scheduler.changeStartegy(SelectionPolicy.SHORTEST_TIME);
            queue.put(newClient);
            waitingPeriod.getAndAdd(newClient.getServiceTime());

        } catch (InterruptedException e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    public String toString() {
        String res;
        if (queue.size() == 0 && isClientBeingProcessed == false) {
            res = "closed queue";
        } else {
            res = queue.toString();
        }
        return res;
    }

    @Override
    public void run() {
        while (isActive) {
            try {
                Client c = queue.take();
                isClientBeingProcessed = true;
                currentArrivalTime = c.getArrivalTime();
                currentServiceTime = c.getServiceTime();
                currentID = c.getId();
                timeOfArrival = SimulationManager.currentTime;
                finishTime = SimulationManager.currentTime + c.getServiceTime();


                Thread.sleep(currentServiceTime * 1000);
                while (SimulationManager.currentTime < finishTime)
                    Thread.sleep(100);

                waitingPeriod.getAndAdd((-1) * c.getServiceTime());
                isClientBeingProcessed = false;
                finishTime = 0;
                timeOfArrival = 0;

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        setActive(false);
    }


}
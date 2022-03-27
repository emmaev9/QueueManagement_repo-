import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class Scheduler {
    private List<Server> servers;
    private int maxNumberServers;
    private int maxClientsPerServer;
    private Strategy strategy;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    private ArrayList<Thread> threads;

    public Scheduler(int maxNumberServers, int maxClientsPerServer) {
        this.maxNumberServers = maxNumberServers;
        this.maxClientsPerServer = maxClientsPerServer;
        this.servers = new ArrayList<Server>(maxNumberServers);
        this.threads = new ArrayList<Thread>(maxNumberServers);

        for (int i = 0; i < maxNumberServers; i++) {
            servers.add(new Server(i, maxNumberServers));
            servers.get(i).setActive(true);
            threads.add(new Thread(servers.get(i)));
            threads.get(i).start();
        }
        changeStartegy(selectionPolicy);
    }

    public void changeStartegy(SelectionPolicy pol) {
        if (pol == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStategyQueue();
        }
        if (pol == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStategyTime();
        }
    }

    public void deactivateServers() {
        for (Server s : servers) {
            s.setActive(false);
        }
    }

    public void activateServers() {
        for (Server s : servers) {
            s.setActive(true);
        }
    }

    public void dispatchTask(Client client) {
        try {
            strategy.addClient(servers, client);

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Server> getServers() {
        return servers;
    }

    public String toString() {
        String result = "";
        for (Server i : servers) {
            result = result + "Queue " + i.getNrServer() + ": " + i.toString();
            if (i.isClientBeingProcessed) {
                result = result + " current: " + "(" + i.currentID + "," + i.currentArrivalTime + "," + (i.currentServiceTime - (SimulationManager.currentTime - i.timeOfArrival)) + ")";
            }
            result += '\n';
        }

        return result;
    }

}

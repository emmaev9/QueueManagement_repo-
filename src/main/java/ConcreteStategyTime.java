import java.util.ArrayList;
import java.util.List;

public class ConcreteStategyTime implements Strategy {
    @Override
    public void addClient(List<Server> servers, Client client) {
        //Scheduler sch = new Scheduler(3,9);
        //sch.changeStartegy(SelectionPolicy.SHORTEST_TIME);
        int minTime = 1000;
        int nrServer = -1;
        for (Server s : servers) {
            if (s.getWaitingPeriod().get() < minTime) {
                minTime = s.getWaitingPeriod().get();
                nrServer = s.getNrServer();
            }
        }
        for (Server s : servers) {
            if (s.getNrServer() == nrServer) {
                s.addClient(client);
            }
        }

    }
}

import java.util.List;

public class ConcreteStategyQueue implements Strategy {
    @Override
    public void addClient(List<Server> servers, Client client) {
        Scheduler sch = new Scheduler(3, 9);
        sch.changeStartegy(SelectionPolicy.SHORTEST_QUEUE);
        int minQueue = 1000;
        int nrServer = -1;
        for (Server s : servers) {
            // System.out.println("Waiting period:" + minQueue + "for server" + nrServer);
            if (s.getQueue().size() < minQueue) {
                minQueue = s.getQueue().size();
                nrServer = s.getNrServer();
            }
        }
        System.out.println("Min waiting period:" + minQueue + "for server" + nrServer);
        for (Server s : servers) {
            if (s.getNrServer() == nrServer) {
                s.setActive(true);
                s.addClient(client);

            }
        }
    }
}

import java.util.Comparator;

public class SortByArrivalTime implements Comparator<Client> {
    @Override
    public int compare(Client o1, Client o2) {
        return o1.getArrivalTime() - o2.getArrivalTime();
    }
}

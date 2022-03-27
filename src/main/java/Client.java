import java.util.Collections;
import java.util.List;

public class Client {
    private int id;
    private int serviceTime;
    private int arrivalTime;

    public Client(int id, int servTime, int arrivTime) {
        this.id = id;
        this.arrivalTime = arrivTime;
        this.serviceTime = servTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String toString() {
        return "(" + id + "," + arrivalTime + "," + serviceTime + ")";
    }
}

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class SimulationManager implements Runnable {
    public int timeLimit = 10;
    public int maxServiceTime = 4;
    public int minServiceTime = 2;
    public int maxArrivalTime = 5;
    public int minArrivalTime = 0;
    public int numberOfQueues = 3;
    public int numberOfClients = 10;
    public double averageTime = 0;
    private File fOut = new File("file3.txt");
    public float totalServiceTime = 0;
    public int peakTime = 0, peakNumberOfClients = 0;
    public static volatile int currentTime = 0;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    private Scheduler scheduler;
    private GUI frame;
    private List<Client> generatedClients;

    public SimulationManager() {
        this.frame = new GUI();
        this.frame.setStartActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.display.setText("");
                frame.displayClients.setText("");
                timeLimit = Integer.parseInt(frame.getInterval());
                numberOfQueues = Integer.parseInt(frame.getNumberOfQueues());
                numberOfClients = Integer.parseInt(frame.getNumberOfClients());
                maxServiceTime = Integer.parseInt(frame.getMaxService());
                minServiceTime = Integer.parseInt(frame.getMinService());
                minArrivalTime = Integer.parseInt(frame.getMinArrivalTime());
                maxArrivalTime = Integer.parseInt(frame.getMaxArrivalTime());
                generatedClients = new ArrayList<Client>();
                generateNRandomClients();
                startSimulation();
            }
        });


    }

    public void startSimulation() {
        Thread t = new Thread(this);
        t.start();
    }

    public void generateNRandomClients() {
        Random rand = new Random();
        for (int i = 0; i < numberOfClients; i++) {
            int processingTime = rand.nextInt(maxServiceTime - minServiceTime) + minServiceTime;
            int arrivalTime = rand.nextInt(maxArrivalTime - minArrivalTime) + minArrivalTime;
            Client c = new Client(i, processingTime, arrivalTime);
            generatedClients.add(c);
        }

        printClients();
        String clients = "";
        for (Client c : generatedClients) {
            clients = clients + "Client: " + c.getId() + "  (" + c.getArrivalTime() + ", " + c.getServiceTime() + ")" + "\n";
        }
        frame.printClients(clients);
        Collections.sort(generatedClients, new SortByArrivalTime());
    }

    public void printClients() {
        for (int i = 0; i < numberOfClients; i++) {
            System.out.println("Client " + generatedClients.get(i).getId() + "  " + "arrivalTime:" + generatedClients.get(i).getArrivalTime() + "  " + "serviceTime:" + generatedClients.get(i).getServiceTime());
        }
    }

    private String getResult(int currentTime) {
        String result = "\nTime: " + currentTime + "\n";
        result = result + "Waiting clients: ";

        for (Client i : generatedClients) {
            result = result + i.toString();
        }

        result += "\n" + scheduler.toString();
        return result;
    }

    @Override
    public void run() {
        scheduler = new Scheduler(numberOfQueues, numberOfClients);

        try {
            FileWriter file = null;
            file = new FileWriter(this.fOut.toString());
            currentTime = 0;
            String result;
            while (currentTime < timeLimit) {
                int i = 0;
                while (i < generatedClients.size()) {
                    if (generatedClients.get(i).getArrivalTime() == currentTime) {
                        scheduler.dispatchTask(generatedClients.get(i));
                        totalServiceTime += generatedClients.get(i).getServiceTime();
                        generatedClients.remove(i);
                    } else {
                        i++;
                    }
                }
                try {
                    Thread.sleep(1000);
                    result = getResult(currentTime);
                    System.out.println(result);
                    file.write(result);
                    frame.printConsole(result);
                    checkIfPeakTime();
                    currentTime++;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            scheduler.deactivateServers();
            file.write("Average service time: " + totalServiceTime / numberOfClients + '\n');
            frame.printConsole("Average service time: " + totalServiceTime / numberOfClients + '\n');
            file.write("Peak time: " + peakTime + '\n');
            frame.printConsole("Peak time: " + peakTime + '\n');
            try {
                file.close();
            } catch (Exception ex) {
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void checkIfPeakTime() {
        int totalClients = 0;
        for (Server s : scheduler.getServers()) {
            totalClients += s.getQueue().size();
            if (s.isClientBeingProcessed) totalClients++;
        }
        if (totalClients > peakNumberOfClients) {
            peakNumberOfClients = totalClients;
            peakTime = currentTime;
        }
    }

}

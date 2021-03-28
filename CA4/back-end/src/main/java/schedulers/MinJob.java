package schedulers;

import Bolbolestan.Bolbolestan;

public class MinJob implements Runnable {

    @Override
    public void run() {
        System.out.println("Checking Waiting Lists");
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        try {
            bolbolestan.checkWaitingLists();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
package HeaterXRD.main;

import HeaterXRD.HeaterXRDBoard;
import core.LoopManager;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        LoopManager Manager = new LoopManager();

        Manager.add_mode(HeaterXRDBoard.getTaskManager());
        Manager.add_mode(HeaterXRDBoard.getHeater());
        Manager.add_mode(HeaterXRDBoard.getChamberHumidity());
        Manager.add_mode(HeaterXRDBoard.getChamberTemperature());

        HeaterXRDBoard app = HeaterXRDBoard.getInstance();
        app.buildMenuBar();
        app.displayTemperatureVsTime();
        app.setVisible(true);

        app.startUpdaterThread();

        Thread t1 = new Thread(Manager);
        t1.start();
    }
}

package ICAROGUI.main;

import ICAROGUI.ICAROBoard;
import core.LoopManager;

public class App {
    public static void main(String[] args) {
        LoopManager Manager = new LoopManager();

        Manager.add_mode(ICAROBoard.getTaskManager());
        Manager.add_mode(ICAROBoard.getHeater());
        Manager.add_mode(ICAROBoard.getChamberHumidity());
        Manager.add_mode(ICAROBoard.getChamberTemperature());
        Manager.add_mode(ICAROBoard.getTemperatureController());

        ICAROBoard app = ICAROBoard.getInstance();
        app.buildMenuBar();
        app.displayTemperatureVsTime();
        app.setVisible(true);

        app.startUpdaterThread();

        Thread t1 = new Thread(Manager);
        t1.start();
    }

}

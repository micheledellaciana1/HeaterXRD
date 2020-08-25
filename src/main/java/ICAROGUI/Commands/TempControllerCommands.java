package ICAROGUI.Commands;

import SingleSensorBoard.Commands.ACommands;
import core.SerialBuffer;

public class TempControllerCommands extends ACommands {
    public TempControllerCommands(SerialBuffer serial) {
        super(serial);
    }

    public double getControllerTemperature() {
        if (isASimulation)
            return 0;

        _serial.println("EventMeasureControllerTemperature");
        String ControllerTemperature = null;
        while (ControllerTemperature == null)
            ControllerTemperature = _serial.readLine();
        return Double.valueOf(ControllerTemperature);
    }
}
package ICAROGUI;

import ICAROGUI.Commands.TempControllerCommands;
import core.ADataStream;

public class ModeTemperatureController extends ADataStream {

    private TempControllerCommands _Commands;

    public ModeTemperatureController(TempControllerCommands Commands, String name, double period) {
        super(name, period);
        _Commands = Commands;
    }

    @Override
    public double acquireData() {
        return _Commands.getControllerTemperature();
    }
}
package HeaterXRD;

import HeaterXRD.Commands.ACommandsICARO;
import core.ADataStream;

public class ModeTemperatureController extends ADataStream {

    private ACommandsICARO _Commands;

    public ModeTemperatureController(ACommandsICARO Commands, String name, double period) {
        super(name, period);
        _Commands = Commands;
    }

    @Override
    public double acquireData() {
        return _Commands.getControllerBoardTemperature();
    }
}
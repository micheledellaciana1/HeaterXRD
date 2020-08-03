package HeaterXRD.Commands;

import SingleSensorBoard.Commands.ASingleBoardCommands;

public abstract class ACommandsICARO extends ASingleBoardCommands {

    public abstract void setAverangeTimeADC(long value);

    public abstract long getAverangeTimeADC();

    public abstract void SetVoltageHeater(double value);

    public abstract double GetVoltageHeater();

    public abstract double measureResistanceHeater();

    public abstract double measurePowerHeater();

    public abstract double getChamberHumidity();

    public abstract double getChamberTemperature();

    public abstract double getControllerBoardTemperature();

    @Override
    public void SetVoltageFall(double value) {
        // TODO Auto-generated method stub

    }

    @Override
    public double GetVoltageFall() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double measureVoltageFall() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double measureCurrent() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setFeedbackExternal(boolean flag) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setSumInputWithExternalSignal(boolean flag) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean getFeedbackExternal() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getSumInputWithExternalSignal() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setAutorangeAmpMeter(boolean flag) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean getAutorangeAmpMeter() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setAmpMeterRange(int IndexRange) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getAmpMeterRange() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getAmpMeterResistor() {
        // TODO Auto-generated method stub
        return 0;
    }
}
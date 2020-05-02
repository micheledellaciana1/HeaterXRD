package HeaterXRD;

import SingleSensorBoard.Commands.ICommands;

public class CommandsHeaterXRD implements ICommands {

    @Override
    public void initCommands() {
        // TODO Auto-generated method stub

    }

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
    public void setAverangeTimeADC(long value) {
        // TODO Auto-generated method stub

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
    public void SetVoltageHeater(double value) {
        // TODO Auto-generated method stub

    }

    @Override
    public double GetVoltageHeater() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double measureResistanceHeater() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double measurePowerHeater() {
        // TODO Auto-generated method stub
        return 0;
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

    @Override
    public double getChamberHumidity() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getChamberTemperature() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void ResetDevice() {
        // TODO Auto-generated method stub

    }

}
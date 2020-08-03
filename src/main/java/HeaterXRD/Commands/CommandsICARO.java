package HeaterXRD.Commands;

import javax.swing.JOptionPane;

import com.fazecast.jSerialComm.SerialPort;

import core.SerialBuffer;

public class CommandsICARO extends ACommandsICARO {
    private static CommandsICARO _instance = new CommandsICARO();
    SerialBuffer _serial;

    static public CommandsICARO getInstance() {
        return _instance;
    }

    CommandsICARO() {
        super();

        SerialPort port = null;

        if (SerialPort.getCommPorts().length == 0) {
            System.out.println("No Ports");
            System.exit(0);
        }

        if (SerialPort.getCommPorts().length > 1) {
            while (true) {
                String labelPanel = "Input Port index: <Index> \n Ports available: \n";
                for (int i = 0; i < SerialPort.getCommPorts().length; i++) {
                    labelPanel += Integer.toString(i) + ". " + SerialPort.getCommPorts()[i].getSystemPortName() + "\n";
                }

                String Input = JOptionPane.showInputDialog(null, labelPanel);

                try {
                    port = SerialPort.getCommPorts()[Integer.valueOf(Input)];
                    break;
                } catch (Exception e) {
                }
            }
        } else {
            System.out.println("Open def. Port");
            port = SerialPort.getCommPorts()[Integer.valueOf(0)];
        }

        port.openPort();
        port.setBaudRate(9600);
        _serial = new SerialBuffer(port);

        _serial.EstablishConnection();

        ResetDevice();
    }

    private long _AvernageTimeADC;

    @Override
    public void setAverangeTimeADC(long value) {
        _AvernageTimeADC = value;
        _serial.println("EventAverangeTimeADC");
        _serial.println(Long.toString(value));
    }

    @Override
    public long getAverangeTimeADC() {
        return _AvernageTimeADC;
    }

    private double _VoltageHeaterSetted;

    @Override
    public void SetVoltageHeater(double value) {
        _VoltageHeaterSetted = value;
        _serial.println("EventSetHeaterVoltage");
        _serial.println(Double.toString(value));
    }

    @Override
    public double GetVoltageHeater() {
        return _VoltageHeaterSetted;
    }

    @Override
    public double measureResistanceHeater() {
        _serial.println("EventMeasureThermistorResistance");
        String resistanceHeater = null;
        while (resistanceHeater == null)
            resistanceHeater = _serial.readLine();

        return Double.valueOf(resistanceHeater);
    }

    @Override
    public double measurePowerHeater() {
        return 0;
        /*
         * _serial.println("EventGetPowerHeater"); String PowerHeater = null; while
         * (PowerHeater == null) PowerHeater = _serial.readLine();
         * 
         * return Double.valueOf(PowerHeater);
         */
    }

    @Override
    public double getChamberHumidity() {
        _serial.println("EventMeasureRHChamber");
        String ChamberHumidity = null;
        while (ChamberHumidity == null)
            ChamberHumidity = _serial.readLine();
        return Double.valueOf(ChamberHumidity);
    }

    @Override
    public double getChamberTemperature() {
        _serial.println("EventMeasureBoardTemperature");
        String ChamberTemperature = null;
        while (ChamberTemperature == null)
            ChamberTemperature = _serial.readLine();
        return Double.valueOf(ChamberTemperature);
    }

    @Override
    public double getControllerBoardTemperature() {
        _serial.println("EventMeasureControllerTemperature");
        String ControllerBoardTemperature = null;
        while (ControllerBoardTemperature == null)
            ControllerBoardTemperature = _serial.readLine();
        return Double.valueOf(ControllerBoardTemperature);
    }

    @Override
    public void ResetDevice() {
        SetVoltageHeater(0);
    }

    @Override
    public void closeDevice() {
        ResetDevice();
        _serial.println("EventCloseSerial");
    }
}
package ICAROGUI.Commands;

import javax.swing.JOptionPane;

import com.fazecast.jSerialComm.SerialPort;

import SingleSensorBoard.Commands.ACommands;
import SingleSensorBoard.Commands.HeaterCommands;
import SingleSensorBoard.Commands.TempHumidityCommands;
import core.SerialBuffer;

public class ICAROCommands extends ACommands {
    private HeaterCommands _HeaterCommands;
    private TempHumidityCommands _TempHumidityCommands;
    private TempControllerCommands _TempControllerCommands;

    SerialBuffer _serial;

    public HeaterCommands getHeaterCommands() {
        return _HeaterCommands;
    }

    public TempControllerCommands getTempControllerCommands() {
        return _TempControllerCommands;
    }

    public TempHumidityCommands getTempHumidityCommands() {
        return _TempHumidityCommands;
    }

    public ICAROCommands() {
        super();

        SerialPort port = null;

        if (SerialPort.getCommPorts().length == 0) {
            _serial = null;
            _HeaterCommands = new HeaterCommands(_serial);
            _TempHumidityCommands = new TempHumidityCommands(_serial);
            _TempControllerCommands = new TempControllerCommands(_serial);
            return;
        }

        if (SerialPort.getCommPorts().length == 1)
            port = SerialPort.getCommPorts()[Integer.valueOf(0)];

        if (SerialPort.getCommPorts().length > 1) {
            while (true) {
                String labelPanel = "Input Port index: \n Ports available: \n";
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
        }

        port.openPort();
        port.setBaudRate(9600);
        _serial = new SerialBuffer(port);
        _HeaterCommands = new HeaterCommands(_serial);
        _HeaterCommands.isASimulation = false;
        _TempHumidityCommands = new TempHumidityCommands(_serial);
        _TempHumidityCommands.isASimulation = false;
        _TempControllerCommands = new TempControllerCommands(_serial);
        _TempControllerCommands.isASimulation = false;
        isASimulation = false;
        _serial.EstablishConnection();

        Reset();
    }

    @Override
    public void Reset() {
        _HeaterCommands.Reset();
        _TempHumidityCommands.Reset();
    }

    public void closeDevice() {
        Reset();
        if (!isASimulation)
            _serial.println("EventCloseSerial");
    }
}

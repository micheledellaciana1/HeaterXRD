package ICAROGUI.Commands;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.fazecast.jSerialComm.SerialPort;

import SingleSensorBoard.Commands.ACommands;
import SingleSensorBoard.Commands.HeaterCommands;
import SingleSensorBoard.Commands.TempHumidityCommands;
import core.LoopManager;
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

        final JFrame frame = new JFrame();
        frame.setTitle("Ports");
        frame.setResizable(false);

        String list[] = new String[SerialPort.getCommPorts().length + 1];
        for (int i = 0; i < SerialPort.getCommPorts().length; i++)
            list[i] = SerialPort.getCommPorts()[i].getSystemPortName();

        list[SerialPort.getCommPorts().length] = "Run as Simulation";
        JComboBox<String> portsMenu = new JComboBox<String>(list);
        portsMenu.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        portsMenu.setPreferredSize(new Dimension(500, 50));

        JButton okButton = new JButton(new AbstractAction("Ok") {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });

        okButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel Label = new JLabel("Select the Serial Port");

        Label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        frame.add(Label, BorderLayout.NORTH);
        frame.add(portsMenu, BorderLayout.CENTER);
        frame.add(okButton, BorderLayout.SOUTH);
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        while (frame.isVisible()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e1) {
            }
        }

        if (portsMenu.getSelectedIndex() < SerialPort.getCommPorts().length) {
            port = SerialPort.getCommPorts()[portsMenu.getSelectedIndex()];
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
        } else {
            _serial = null;
            _HeaterCommands = new HeaterCommands(_serial);
            _TempHumidityCommands = new TempHumidityCommands(_serial);
            _TempControllerCommands = new TempControllerCommands(_serial);
        }

        frame.dispose();
        LoopManager.startingTime = System.currentTimeMillis();
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

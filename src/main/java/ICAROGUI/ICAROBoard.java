package ICAROGUI;

import org.jfree.chart.ChartPanel;

import java.awt.event.*;

import ICAROGUI.Commands.ICAROCommands;
import ICAROGUI.Menu.MenuEditorICARO;
import ICAROGUI.ThermistorLUT.NTC_100K;
import ICAROGUI.ThermistorLUT.PT100;
import SingleSensorBoard.ModeChamberHumidity;
import SingleSensorBoard.ModeChamberTemperature;
import SingleSensorBoard.ModeHeater;
import core.ATask;
import core.ChartFrame;
import core.ModChartPanel;
import core.TaskManager;

public class ICAROBoard extends ChartFrame {

    static ICAROBoard _istance = new ICAROBoard(new ModChartPanel(), "ICARO");
    static ICAROCommands _commands;
    static TaskManager _TM;
    static ModeHeater _heater;
    static ModeChamberHumidity _ChamberHumidity;
    static ModeChamberTemperature _ChamberTemperature;
    static ModeTemperatureController _TemperatureController;
    static MenuEditorICARO _MenuEditor;

    private ICAROBoard(ChartPanel panel, String Title) {
        super(panel, Title);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                _TM.addTask(new ATask() {
                    @Override
                    public void execution() {
                        ResetUI();
                        _commands.closeDevice();
                        System.exit(0);
                    }
                });
            }
        });

        _commands = new ICAROCommands();
        _TM = new TaskManager("TaskManagerICARO");
        _heater = new ModeHeater("Heater", 10, _commands.getHeaterCommands());
        _heater.NAverange = 1;

        _heater.setLUT(PT100.instance);
        _heater.getFeedBackController().setParameters(0, 0.015);
        _heater.getFeedBackController().setParameters(1, 0.00006);
        _heater.getFeedBackController().setParameters(2, 0.);
        _heater.getFeedBackController().setParameters(3, 0.5);

        _ChamberHumidity = new ModeChamberHumidity(_commands.getTempHumidityCommands(), "ChamberHumidity", 100);
        _ChamberTemperature = new ModeChamberTemperature(_commands.getTempHumidityCommands(), "ChamberTemperature",
                100);
        _TemperatureController = new ModeTemperatureController(_commands.getTempControllerCommands(),
                "TemperatureController", 100);

        _MenuEditor = new MenuEditorICARO(this, _commands.getHeaterCommands(), _commands.getTempHumidityCommands(),
                _commands.getTempControllerCommands(), _commands, _TM);
    }

    public void buildMenuBar() {
        setJMenuBar(_MenuEditor.constructMenuBar());
    }

    static public ICAROBoard getInstance() {
        return _istance;
    }

    static public ModeHeater getHeater() {
        return _heater;
    }

    static public ICAROCommands getCommands() {
        return _commands;
    }

    static public TaskManager getTaskManager() {
        return _TM;
    }

    static public ModeChamberHumidity getChamberHumidity() {
        return _ChamberHumidity;
    }

    static public ModeChamberTemperature getChamberTemperature() {
        return _ChamberTemperature;
    }

    static public ModeTemperatureController getTemperatureController() {
        return _TemperatureController;
    }

    public void displayTemperatureVsTime() {
        clearData();
        addSeries(_heater.getTemperature(), "Temperature");
        _panel.getChart().getXYPlot().getDomainAxis().setLabel("Time [S]");
        _panel.getChart().getXYPlot().getRangeAxis().setLabel("Temperature [°C]");
    }

    public void displayResistanceHeaterVsTime() {
        clearData();
        addSeries(_heater.getResistance(), "Resistance");
        _panel.getChart().getXYPlot().getDomainAxis().setLabel("Time [S]");
        _panel.getChart().getXYPlot().getRangeAxis().setLabel("Resistance [Ohm]");
    }

    public void displayPowerVsTime() {
        clearData();
        addSeries(_heater.getPower(), "Power");
        _panel.getChart().getXYPlot().getDomainAxis().setLabel("Time [S]");
        _panel.getChart().getXYPlot().getRangeAxis().setLabel("Power [au]");
    }

    public void displayVoltageHeaterVsTime() {
        clearData();
        addSeries(_heater.getVoltageHeater(), "Voltage");
        _panel.getChart().getXYPlot().getDomainAxis().setLabel("Time [S]");
        _panel.getChart().getXYPlot().getRangeAxis().setLabel("Voltage [au]");
    }

    public void displayChamberHumidity() {
        clearData();
        addSeries(_ChamberHumidity.getData(), "Humidity");
        _panel.getChart().getXYPlot().getDomainAxis().setLabel("Time [S]");
        _panel.getChart().getXYPlot().getRangeAxis().setLabel("Humidity [%]");
    }

    public void displayChambertTemperature() {
        clearData();
        addSeries(_ChamberTemperature.getData(), "Chamber Temperature");
        _panel.getChart().getXYPlot().getDomainAxis().setLabel("Time [S]");
        _panel.getChart().getXYPlot().getRangeAxis().setLabel("Temperature [°C]");
    }

    public void displayTemperatureController() {
        clearData();
        addSeries(_TemperatureController.getData(), "Temperature Controller");
        _panel.getChart().getXYPlot().getDomainAxis().setLabel("Time [S]");
        _panel.getChart().getXYPlot().getRangeAxis().setLabel("Temperature [°C]");
    }

    public void ResetUI() {
        _ChamberHumidity.EraseData();
        _ChamberTemperature.EraseData();
        _TemperatureController.EraseData();
        _heater.EraseData();
        _heater.setFeedbakON(false);
        _heater.setTargetTemperature(0);
    }
}
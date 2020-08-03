package HeaterXRD;

import org.jfree.chart.ChartPanel;

import java.awt.event.*;

import HeaterXRD.Commands.ACommandsICARO;
import HeaterXRD.Commands.CommandsICARO;
import HeaterXRD.Menu.MenuEditorXRDHeater;
import HeaterXRD.ThermistorLUT.NTC_100K;
import HeaterXRD.ThermistorLUT.PT100;
import SingleSensorBoard.ModeChamberHumidity;
import SingleSensorBoard.ModeChamberTemperature;
import SingleSensorBoard.ModeHeater;
import SingleSensorBoard.Commands.ASingleBoardCommands;
import SingleSensorBoard.Commands.SimCommands;
import core.ATask;
import core.ChartFrame;
import core.ModChartPanel;
import core.TaskManager;

public class ICAROBoard extends ChartFrame {

    static ICAROBoard _istance = new ICAROBoard(new ModChartPanel(), "HeaterXRD");
    static ACommandsICARO _commands;
    static TaskManager _TM;
    static ModeHeater _heater;
    static ModeChamberHumidity _ChamberHumidity;
    static ModeChamberTemperature _ChamberTemperature;
    static ModeTemperatureController _TemperatureController;
    static MenuEditorXRDHeater _MenuEditor;

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

        _commands = CommandsICARO.getInstance();
        _TM = new TaskManager("TaskManagerHeaterXRD");
        _heater = new ModeHeater("Heater", 10, _commands);
        _heater.NAverange = 1;

        _heater.setLUT(PT100.instance);
        _heater.getFeedBackController().setParameters(0, 0.01);
        _heater.getFeedBackController().setParameters(1, 0.00003);
        _heater.getFeedBackController().setParameters(2, 0.);
        _heater.getFeedBackController().setParameters(3, 0.5);

        _ChamberHumidity = new ModeChamberHumidity(_commands, "ChamberHumidity", 50);
        _ChamberTemperature = new ModeChamberTemperature(_commands, "ChamberTemperature", 50);
        _TemperatureController = new ModeTemperatureController(_commands, "TemperatureController", 50);

        _MenuEditor = new MenuEditorXRDHeater(_TM, _commands, this);
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

    static public ASingleBoardCommands getCommands() {
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
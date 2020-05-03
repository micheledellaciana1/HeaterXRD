package HeaterXRD;

import org.jfree.chart.ChartPanel;

import HeaterXRD.Menu.MenuEditorXRDHeater;
import SingleSensorBoard.ModeChamberHumidity;
import SingleSensorBoard.ModeChamberTemperature;
import SingleSensorBoard.ModeHeater;
import SingleSensorBoard.Commands.ICommands;
import SingleSensorBoard.Commands.SimCommands;
import core.ChartFrame;
import core.ModChartPanel;
import core.TaskManager;

public class HeaterXRDBoard extends ChartFrame {

    static HeaterXRDBoard _istance = new HeaterXRDBoard(new ModChartPanel(), "HeaterXRD");
    static ICommands _Commands;
    static TaskManager _TM;
    static ModeHeater _heater;
    static ModeChamberHumidity _ChamberHumidity;
    static ModeChamberTemperature _ChamberTemperature;
    static MenuEditorXRDHeater _MenuEditor;

    private HeaterXRDBoard(ChartPanel panel, String Title) {
        super(panel, Title);

        _Commands = SimCommands.getInstance();
        _TM = new TaskManager("TaskManagerHeaterXRD");
        _heater = new ModeHeater("Heater", 10, _Commands);
        _ChamberHumidity = new ModeChamberHumidity(_Commands, "ChamberHumidity", 50);
        _ChamberTemperature = new ModeChamberTemperature(_Commands, "ChamberTemperature", 50);

        _MenuEditor = new MenuEditorXRDHeater(_TM, _Commands, this);
    }

    public void buildMenuBar() {
        setJMenuBar(_MenuEditor.constructMenuBar());
    }

    static public HeaterXRDBoard getInstance() {
        return _istance;
    }

    static public ModeHeater getHeater() {
        return _heater;
    }

    static public ICommands getCommands() {
        return _Commands;
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

    public void ResetUI() {
        _heater.EraseData();
        _heater.setFeedbakON(false);
        _heater.setTargetTemperature(0);
    }
}
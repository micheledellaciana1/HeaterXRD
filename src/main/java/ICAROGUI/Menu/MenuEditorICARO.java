package ICAROGUI.Menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import ICAROGUI.ICAROBoard;
import ICAROGUI.Commands.ICAROCommands;
import ICAROGUI.Commands.TempControllerCommands;
import SingleSensorBoard.ModeHeater;
import SingleSensorBoard.Commands.HeaterCommands;
import SingleSensorBoard.Commands.TempHumidityCommands;
import SingleSensorBoard.Menu.MenuEditorSingleSensorBoard;
import core.ATask;
import core.ChartFrame;
import core.LoopManager;
import core.TaskManager;

public class MenuEditorICARO extends MenuEditorSingleSensorBoard {

    TempControllerCommands _TempControllerCommands;
    ICAROCommands _ICAROCommands;

    public MenuEditorICARO(ChartFrame chart, HeaterCommands HCommands, TempHumidityCommands THumidityCommands,
            TempControllerCommands TCCommands, ICAROCommands iCommands, TaskManager TM) {
        super(chart, HCommands, null, THumidityCommands, null, TM);
        _TempControllerCommands = TCCommands;
        _ICAROCommands = iCommands;
    }

    @Override
    public JMenuBar constructMenuBar() {
        JMenuBar menubar = new JMenuBar();
        menubar.add(BuildFileMenu());
        menubar.add(BuildDisplayMenu());
        menubar.add(BuildSetMenu());
        return menubar;
    }

    protected JMenu BuildFileMenu() {
        JMenu menu = new JMenu("File");
        menu.add(BuildChartPropertyMenu());
        menu.add(BuildExportMenu());

        menu.add(new AbstractAction("Reset") {

            @Override
            public void actionPerformed(ActionEvent e) {
                _TM.addTask(new ATask() {

                    @Override
                    public void execution() {
                        int answer = JOptionPane.showConfirmDialog(null,
                                "Are you sure to erase all data and reset device?", "Clean all",
                                JOptionPane.YES_NO_OPTION);
                        try {
                            switch (answer) {
                                case 0:
                                    LoopManager.startingTime = System.currentTimeMillis();
                                    ICAROBoard.getInstance().ResetUI();
                                    _ICAROCommands.Reset();
                                    break;
                                case 1:
                                    break;
                            }
                        } catch (Exception _e) {
                            if (verbose) {
                                JOptionPane.showMessageDialog(null, _e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
                                _e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });

        return menu;
    }

    protected JMenu BuildSetMenu() {
        JMenu menu = new JMenu("Set");
        menu.add(BuildSetMenuHeater(ICAROBoard.getHeater()));
        return menu;
    }

    @Override
    protected JMenu BuildSetMenuHeater(final ModeHeater heater) {
        JMenu menu = new JMenu("Heater");
        menu.add(new AbstractAction("Temperature") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String answer = JOptionPane.showInputDialog("Set Temperature");
                try {
                    heater.getFeedBackController().set_target_value(Double.valueOf(answer));
                } catch (Exception _e) {
                    if (verbose) {
                        JOptionPane.showMessageDialog(null, _e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
                        _e.printStackTrace();
                    }
                }
            }
        });

        menu.add(new AbstractAction("Voltage") {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String answer = JOptionPane.showInputDialog("Set Voltage");
                _TM.addTask(new ATask() {
                    @Override
                    public void execution() {
                        try {
                            _HeaterCommands.SetVoltageHeater(Double.valueOf(answer));
                        } catch (Exception _e) {
                            if (verbose) {
                                JOptionPane.showMessageDialog(null, _e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
                                _e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });

        menu.add(BuildFeedbackMenu(heater));
        // menu.add(BuildCalibrationHeaterMenu(heater));

        return menu;
    }

    @Override
    protected JMenu BuildDisplayMenu() {
        JMenu menu = new JMenu("Display");
        menu.add(BuildDisplayChamberMenu());

        menu.add(new AbstractAction("Temperature") {

            @Override
            public void actionPerformed(ActionEvent e) {
                ICAROBoard.getInstance().displayTemperatureVsTime();
            }
        });

        menu.add(new AbstractAction("Resistance") {

            @Override
            public void actionPerformed(ActionEvent e) {
                ICAROBoard.getInstance().displayResistanceHeaterVsTime();
            }
        });

        menu.add(new AbstractAction("Voltage") {

            @Override
            public void actionPerformed(ActionEvent e) {
                ICAROBoard.getInstance().displayVoltageHeaterVsTime();
            }
        });

        menu.add(BuildDuplicateView());

        return menu;
    }

    @Override
    protected JMenu BuildDisplayChamberMenu() {
        JMenu menu = new JMenu("Chamber");

        menu.add(new AbstractAction("Temperature") {
            @Override
            public void actionPerformed(ActionEvent e) {
                ICAROBoard.getInstance().displayChambertTemperature();
            }
        });

        menu.add(new AbstractAction("Humidity") {
            @Override
            public void actionPerformed(ActionEvent e) {
                ICAROBoard.getInstance().displayChamberHumidity();
            }
        });

        menu.add(new AbstractAction("Temperature Controller") {
            @Override
            public void actionPerformed(ActionEvent e) {
                ICAROBoard.getInstance().displayTemperatureController();
            }
        });

        return menu;
    }
}
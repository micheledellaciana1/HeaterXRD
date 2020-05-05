package HeaterXRD.Menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import HeaterXRD.HeaterXRDBoard;
import SingleSensorBoard.Commands.ICommands;
import SingleSensorBoard.Menu.MenuEditorSingleSensorBoard;
import core.ATask;
import core.ChartFrame;
import core.LoopManager;
import core.TaskManager;

public class MenuEditorXRDHeater extends MenuEditorSingleSensorBoard {

    public MenuEditorXRDHeater(TaskManager TM, ICommands commands, ChartFrame chart) {
        super(TM, commands, chart);
    }

    @Override
    public JMenuBar constructMenuBar() {
        JMenuBar menubar = new JMenuBar();
        menubar.add(BuildFileMenu());
        menubar.add(BuildDisplayMenu());
        menubar.add(BuildSetMenu());
        return menubar;
    }

    private JMenu BuildFileMenu() {
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
                                    HeaterXRDBoard.getInstance().ResetUI();
                                    _commands.ResetDevice();
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

    private JMenu BuildSetMenu() {
        JMenu menu = new JMenu("Set");
        menu.add(BuildSetMenuHeater(HeaterXRDBoard.getHeater()));
        return menu;
    }

    private JMenu BuildDisplayMenu() {
        JMenu menu = new JMenu("Display");
        menu.add(BuildDisplayChamberMenu());

        menu.add(new AbstractAction("Temperature") {

            @Override
            public void actionPerformed(ActionEvent e) {
                HeaterXRDBoard.getInstance().displayTemperatureVsTime();
            }
        });

        menu.add(new AbstractAction("Resistance") {

            @Override
            public void actionPerformed(ActionEvent e) {
                HeaterXRDBoard.getInstance().displayResistanceHeaterVsTime();
            }
        });

        menu.add(new AbstractAction("Power") {

            @Override
            public void actionPerformed(ActionEvent e) {
                HeaterXRDBoard.getInstance().displayPowerVsTime();
            }
        });

        menu.add(BuildDuplicateView());

        return menu;
    }

    private JMenu BuildDisplayChamberMenu() {
        JMenu menu = new JMenu("Chamber");

        menu.add(new AbstractAction("Temperature") {
            @Override
            public void actionPerformed(ActionEvent e) {
                HeaterXRDBoard.getInstance().displayChambertTemperature();
            }
        });

        menu.add(new AbstractAction("Humidity") {
            @Override
            public void actionPerformed(ActionEvent e) {
                HeaterXRDBoard.getInstance().displayChamberHumidity();
            }
        });

        return menu;
    }
}
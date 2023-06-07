package com.mouse.simulator;

import com.mouse.simulator.gui.MouseSimulatorGUI;
import org.jnativehook.GlobalScreen;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameStart {

    public static void main(String[] args){
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        SwingUtilities.invokeLater(() -> {
            MouseSimulatorGUI mouseSimulatorGUI = new MouseSimulatorGUI();
            mouseSimulatorGUI.setVisible(true);
        });
    }
}

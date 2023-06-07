package com.simulator.gui;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MouseSimulatorGUI extends JFrame {

    private final JTextField x1TextField;
    private final JTextField x2TextField;
    private final JTextField x3TextField;
    private final JTextField x4TextField;
    private final JTextField y1TextField;
    private final JTextField y2TextField;
    private final JTextField y3TextField;
    private final JTextField y4TextField;
    private final JTextField timeTextField1;
    private final JTextField timeTextField2;
    private final JTextField timeTextField3;
    private final JTextField timeTextField4;
    private final JTextField cyclesField;
    private final JTextField fileNameField;
    private JTextArea consoleTextArea;
    private JList<File> fileList;
    private int numPosition;
    private boolean isRunning = false;

    public MouseSimulatorGUI() {

        super("GBF按鍵精靈");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLayout(null);

        // line1
        JLabel x1Label = new JLabel("1. X:");
        x1Label.setBounds(20, 20, 40, 40);
        x1TextField = new JTextField(5);
        x1TextField.setBounds(50, 30, 60, 20);
        JLabel y1Label = new JLabel("Y:");
        y1Label.setBounds(120, 20, 40, 40);
        y1TextField = new JTextField(5);
        y1TextField.setBounds(140, 30, 60, 20);
        JButton xyButton1 = new JButton("GetXY1");
        xyButton1.setBounds(210, 30, 80, 20);
        JLabel timeLabel1 = new JLabel("wait(sec): ");
        timeLabel1.setBounds(300, 20, 60, 40);
        timeTextField1 = new JTextField(5);
        timeTextField1.setBounds(370, 30, 60, 20);
        add(x1Label);
        add(x1TextField);
        add(y1Label);
        add(y1TextField);
        add(xyButton1);
        add(timeLabel1);
        add(timeTextField1);

        // line2
        JLabel x2Label = new JLabel("2. X:");
        x2Label.setBounds(20, 60, 40, 40);
        x2TextField = new JTextField(5);
        x2TextField.setBounds(50, 70, 60, 20);
        JLabel y2Label = new JLabel("Y:");
        y2Label.setBounds(120, 60, 40, 40);
        y2TextField = new JTextField(5);
        y2TextField.setBounds(140, 70, 60, 20);
        JButton xyButton2 = new JButton("GetXY2");
        xyButton2.setBounds(210, 70, 80, 20);
        JLabel timeLabel2 = new JLabel("wait(sec): ");
        timeLabel2.setBounds(300, 60, 60, 40);
        timeTextField2 = new JTextField(5);
        timeTextField2.setBounds(370, 70, 60, 20);
        add(x2Label);
        add(x2TextField);
        add(y2Label);
        add(y2TextField);
        add(xyButton2);
        add(timeLabel2);
        add(timeTextField2);

        // line3
        JLabel x3Label = new JLabel("3. X:");
        x3Label.setBounds(20, 100, 40, 40);
        x3TextField = new JTextField(5);
        x3TextField.setBounds(50, 110, 60, 20);
        JLabel y3Label = new JLabel("Y:");
        y3Label.setBounds(120, 100, 40, 40);
        y3TextField = new JTextField(5);
        y3TextField.setBounds(140, 110, 60, 20);
        JButton xyButton3 = new JButton("GetXY3");
        xyButton3.setBounds(210, 110, 80, 20);
        JLabel timeLabel3 = new JLabel("wait(sec): ");
        timeLabel3.setBounds(300, 100, 60, 40);
        timeTextField3 = new JTextField(5);
        timeTextField3.setBounds(370, 110, 60, 20);
        add(x3Label);
        add(x3TextField);
        add(y3Label);
        add(y3TextField);
        add(xyButton3);
        add(timeLabel3);
        add(timeTextField3);

        // line4
        JLabel x4Label = new JLabel("4. X:");
        x4Label.setBounds(20, 140, 40, 40);
        x4TextField = new JTextField(5);
        x4TextField.setBounds(50, 150, 60, 20);
        JLabel y4Label = new JLabel("Y:");
        y4Label.setBounds(120, 140, 40, 40);
        y4TextField = new JTextField(5);
        y4TextField.setBounds(140, 150, 60, 20);
        JButton xyButton4 = new JButton("GetXY4");
        xyButton4.setBounds(210, 150, 80, 20);
        JLabel timeLabel4 = new JLabel("wait(sec): ");
        timeLabel4.setBounds(300, 140, 60, 40);
        timeTextField4 = new JTextField(5);
        timeTextField4.setBounds(370, 150, 60, 20);
        add(x4Label);
        add(x4TextField);
        add(y4Label);
        add(y4TextField);
        add(xyButton4);
        add(timeLabel4);
        add(timeTextField4);

        // cycle times
        JLabel cyclesLabel = new JLabel("Cycles: ");
        cyclesLabel.setBounds(20, 180, 60, 40);
        cyclesField = new JTextField(5);
        cyclesField.setBounds(70, 190, 60, 20);
        add(cyclesLabel);
        add(cyclesField);

        // list for file
        DefaultListModel<File> listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.setVisibleRowCount(5);
        loadFileList();
        JScrollPane scrollPane = new JScrollPane(fileList); // set file into scrollPane
        scrollPane.setBounds(20, 220, 200, 150);
        add(scrollPane);

        // new file name
        JLabel fileName = new JLabel("Filename: ");
        fileName.setBounds(230, 220, 60, 40);
        fileNameField = new JTextField(5);
        fileNameField.setBounds(290, 230, 90, 20);
        add(fileName);
        add(fileNameField);

        // function buttons
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(390, 230, 70, 20);
        JButton loadButton = new JButton("Load");
        loadButton.setBounds(230, 260, 220, 30);
        JButton startButton = new JButton("Start!");
        startButton.setBounds(230, 300, 220, 30);
        JButton stopButton = new JButton("Stop!");
        stopButton.setBounds(230, 340, 220, 30);
        add(saveButton);
        add(loadButton);
        add(startButton);
        add(stopButton);

        // set console msg to textArea
        consoleTextArea = new JTextArea(20, 50);
        PrintStream printStream = new PrintStream(new ConsoleOutputStream(consoleTextArea));
        System.setOut(printStream);
        // set textArea into scrollpane
        JScrollPane console = new JScrollPane(consoleTextArea);
        console.setBounds(20, 380, 440, 140);
        add(console);

        // action listeners
        ActionListener xyButtonListener = new ActionListener() {
            private MouseInputListener mouseInputListener;
            private KeyInputListener keyInputListener;

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JButton source = (JButton) e.getSource();
                    // to know which line position of the button
                    switch (source.getText()) {
                        case "GetXY1":
                            numPosition = 1;
                            break;
                        case "GetXY2":
                            numPosition = 2;
                            break;
                        case "GetXY3":
                            numPosition = 3;
                            break;
                        case "GetXY4":
                            numPosition = 4;
                            break;
                    }

                    GlobalScreen.registerNativeHook();
                    mouseInputListener = new MouseInputListener();
                    keyInputListener = new KeyInputListener();
                    GlobalScreen.addNativeMouseListener(mouseInputListener);
                    GlobalScreen.addNativeMouseMotionListener(mouseInputListener);
                    GlobalScreen.addNativeKeyListener(keyInputListener);

                } catch (NativeHookException ex) {
                    Logger.getLogger(MouseSimulatorGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        xyButton1.addActionListener(xyButtonListener);
        xyButton2.addActionListener(xyButtonListener);
        xyButton3.addActionListener(xyButtonListener);
        xyButton4.addActionListener(xyButtonListener);

        // save settings
        saveButton.addActionListener(e -> {
            savePreferences();

            loadFileList();
        });

        // load settings
        loadButton.addActionListener(e -> {
            File selectedFile = fileList.getSelectedValue();
            try {
                FileInputStream fis = new FileInputStream(selectedFile);
                Properties properties = new Properties();
                properties.load(fis);
                x1TextField.setText(properties.getProperty("x1"));
                x2TextField.setText(properties.getProperty("x2"));
                x3TextField.setText(properties.getProperty("x3"));
                x4TextField.setText(properties.getProperty("x4"));
                y1TextField.setText(properties.getProperty("y1"));
                y2TextField.setText(properties.getProperty("y2"));
                y3TextField.setText(properties.getProperty("y3"));
                y4TextField.setText(properties.getProperty("y4"));
                timeTextField1.setText(properties.getProperty("time1"));
                timeTextField2.setText(properties.getProperty("time2"));
                timeTextField3.setText(properties.getProperty("time3"));
                timeTextField4.setText(properties.getProperty("time4"));
                cyclesField.setText(properties.getProperty("cycles"));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        startButton.addActionListener(e -> {
            isRunning = true;
            int cycles = Integer.parseInt(cyclesField.getText());

            SwingWorker<Void, String> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    System.out.println("Start Time: " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
                    for (int i = 0; i < cycles && isRunning; i++) {
                        System.out.println("Round: " + (i + 1));
                        int k = 1;
                        for (int j = 0; j < 4 && isRunning; j++) {
                            try {
                                switch (k) {
                                    case 1:
                                        startRobot(Integer.parseInt(x1TextField.getText()), Integer.parseInt(y1TextField.getText()), 1.5);
                                        startRobot(Integer.parseInt(x1TextField.getText()), Integer.parseInt(y1TextField.getText()), Double.parseDouble(timeTextField1.getText()));
                                        break;
                                    case 2:
                                        startRobot(Integer.parseInt(x2TextField.getText()), Integer.parseInt(y2TextField.getText()), Double.parseDouble(timeTextField2.getText()));
                                        break;
                                    case 3:
                                        startRobot(Integer.parseInt(x3TextField.getText()), Integer.parseInt(y3TextField.getText()), Double.parseDouble(timeTextField3.getText()));
                                        break;
                                    case 4:
                                        long startTime = System.currentTimeMillis();
                                        startRobot(Integer.parseInt(x4TextField.getText()), Integer.parseInt(y4TextField.getText()), Double.parseDouble(timeTextField4.getText()));
                                        System.out.println("TimeCost: " + ((System.currentTimeMillis() - startTime)/1000.0) + " sec");
                                        break;
                                }
                                k++;
                            } catch (AWTException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                    return null;
                }
            };
            worker.execute();
        });

        stopButton.addActionListener(e -> {
            isRunning = false;
            System.out.println("Stop at: " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        });

    }

    private void loadFileList() {
        File folder = new File("preferences/");
        File[] files = folder.listFiles();
        if (files != null) {
            DefaultListModel<File> listModel = (DefaultListModel<File>) fileList.getModel();
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".properties")) {
                    listModel.addElement(file);
                }
            }
        }
    }

    private void savePreferences() {
        try {
            Properties properties = new Properties();
            properties.setProperty("x1", x1TextField.getText());
            properties.setProperty("y1", y1TextField.getText());
            properties.setProperty("x2", x2TextField.getText());
            properties.setProperty("y2", y2TextField.getText());
            properties.setProperty("x3", x3TextField.getText());
            properties.setProperty("y3", y3TextField.getText());
            properties.setProperty("x4", x4TextField.getText());
            properties.setProperty("y4", y4TextField.getText());
            properties.setProperty("time1", timeTextField1.getText());
            properties.setProperty("time2", timeTextField2.getText());
            properties.setProperty("time3", timeTextField3.getText());
            properties.setProperty("time4", timeTextField4.getText());
            properties.setProperty("cycles", cyclesField.getText());

            FileOutputStream fileOut = new FileOutputStream("preferences/" + fileNameField.getText() + ".properties");
            properties.store(fileOut, "properties");
            fileOut.close();
        } catch (IOException e) {
        }
    }

    public static void meat() throws AWTException {
        // 创建一个Robot对象
        Robot robot = new Robot();
        robot.delay(2000);

        for (int i = 0; i < 33; i++) {
            System.out.println("Round: " + (i + 1));
            if (i % 2 == 0) {

                robot.mouseMove(967, 89); // 書籤
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(2500);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                robot.delay(1600);

                robot.mouseMove(1230, 492); // 召喚
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                robot.delay(1600);

                robot.mouseMove(1305, 758); // 確認
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                robot.delay(2000);

                robot.mouseMove(997, 532); // 自動戰鬥
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(35000);
            } else {
                robot.mouseMove(934, 93); // 書籤
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(2500);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                robot.delay(1600);

                robot.mouseMove(1199, 625); // 召喚
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                robot.delay(1600);

                robot.mouseMove(1248, 757); // 確認
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                robot.delay(2000);

                robot.mouseMove(1021, 534); // 自動戰鬥
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(35000);
            }
        }
    }

    public static void startRobot(int x, int y, double sec) throws AWTException {
        Robot robot = new Robot();
        robot.delay(2000);
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay((int) (sec * 1000));
    }


    private class MouseInputListener implements NativeMouseInputListener {
        @Override
        public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
            switch (numPosition) {
                case 1:
                    x1TextField.setText(String.valueOf(nativeMouseEvent.getX()));
                    y1TextField.setText(String.valueOf(nativeMouseEvent.getY()));
                    break;
                case 2:
                    x2TextField.setText(String.valueOf(nativeMouseEvent.getX()));
                    y2TextField.setText(String.valueOf(nativeMouseEvent.getY()));
                    break;
                case 3:
                    x3TextField.setText(String.valueOf(nativeMouseEvent.getX()));
                    y3TextField.setText(String.valueOf(nativeMouseEvent.getY()));
                    break;
                case 4:
                    x4TextField.setText(String.valueOf(nativeMouseEvent.getX()));
                    y4TextField.setText(String.valueOf(nativeMouseEvent.getY()));
                    break;
            }

        }

        @Override
        public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {

        }

        @Override
        public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {

        }

        @Override
        public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {

        }

        @Override
        public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {

        }
    }

    private class KeyInputListener implements NativeKeyListener {

        @Override
        public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
            if (nativeKeyEvent.getKeyCode() == 59) {
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (NativeHookException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
            if (nativeKeyEvent.getKeyCode() == 59) {
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (NativeHookException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
            if (nativeKeyEvent.getKeyCode() == 59) {
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (NativeHookException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // 自定义OutputStream，用于将数据写入JTextArea
    private static class ConsoleOutputStream extends OutputStream {
        private JTextArea consoleTextArea;

        public ConsoleOutputStream(JTextArea consoleTextArea) {
            this.consoleTextArea = consoleTextArea;
        }

        @Override
        public void write(int b) {
            consoleTextArea.append(String.valueOf((char) b));
            consoleTextArea.setCaretPosition(consoleTextArea.getDocument().getLength());
        }
    }


}

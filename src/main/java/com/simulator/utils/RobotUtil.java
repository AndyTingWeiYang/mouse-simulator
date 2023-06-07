package com.simulator.utils;

import java.awt.*;
import java.awt.event.InputEvent;

public class RobotUtil {
    public static void oneClick(int x, int y, double sec) throws AWTException {
        Robot robot = new Robot();
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay((int) (sec * 1000));
    }
}

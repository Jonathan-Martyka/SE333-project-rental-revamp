package shop.main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shop.data.Inventory;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MainTest {
    @Test
    @DisplayName("Run main() method starting program, then exit (tester enters \"9\" then \"1\") without exception")
    void testMainRunsExecuteProper() {
        try {
            JOptionPane.showMessageDialog(null, "Tester:\nEnter \"9\" then \"1\"",
                    "Tester Prompt testMainRunsExecuteProper",JOptionPane.INFORMATION_MESSAGE);
            Main.main(null);
            assertEquals(true, true);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @DisplayName("Run main() method starting program, then exit (tester enters \"9\", \"2\", \"9\", \"1\") without exception")
    void testMainRunsExecuteImproperThenProper() {
        try {
            JOptionPane.showMessageDialog(null, "Tester:\nEnter \"9\", \"2\", \"9\", \"1\"",
                    "Tester Prompt testMainRunsExecuteImproperThenProper",JOptionPane.INFORMATION_MESSAGE);
            Main.main(null);
            assertEquals(true, true);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @DisplayName("Start program, tester clicks \"X\" in corner, then enters \"9\", \"1\" without exception")
    void testImproperExit() {
        try {
            JOptionPane.showMessageDialog(null, "Tester:\nOn next prompt, click \"X\" in corner, then enter \"9\", \"1\"",
                    "Tester Prompt testImproperExit",JOptionPane.INFORMATION_MESSAGE);
            Main.main(null);
            assertEquals(true, true);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @DisplayName("Start program, tester generate bogus data, print it; Tester enters \"9\", \"1\" without exception")
    void testDisplayPhony() {
        try {
            JOptionPane.showMessageDialog(null, "Tester:\nGenerate bogus data, print it:\nEnter \"10\", \"4\", \"9\", \"1\"",
                    "Tester Prompt testDisplayPhony",JOptionPane.INFORMATION_MESSAGE);
            Main.main(null);
            assertEquals(true, true);
        } catch (Exception e) {
            fail();
        }
    }
}

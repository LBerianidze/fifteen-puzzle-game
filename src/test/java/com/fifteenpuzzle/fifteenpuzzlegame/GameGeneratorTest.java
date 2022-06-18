package com.fifteenpuzzle.fifteenpuzzlegame;

import com.fifteenpuzzle.fifteenpuzzlegame.game.PuzzleGenerator;
import com.fifteenpuzzle.fifteenpuzzlegame.model.PuzzleButton;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameGeneratorTest
{

    @BeforeAll
    public static void Do() throws InterruptedException
    {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() ->
        {
            new JFXPanel();
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testGenerator() throws InterruptedException
    {
        PuzzleGenerator generator = new PuzzleGenerator();
        PuzzleButton[] buttons = generator.createPuzzle();

        assertEquals(buttons.length, 16);
    }

    @Test
    void testWin() throws InterruptedException
    {
        PuzzleGenerator generator = new PuzzleGenerator();
        PuzzleButton[] buttons = generator.createPuzzleDone();

        assertEquals(generator.checkWin(buttons), true);
    }

    @Test
    void testCanMove() throws InterruptedException
    {
        PuzzleGenerator generator = new PuzzleGenerator();
        PuzzleButton[] buttons = generator.createPuzzleDone();
        boolean isEmpty = buttons[0].getPuzzleItemNumber() == 0;
        assertEquals(buttons[0].canMove(), isEmpty);
    }
}
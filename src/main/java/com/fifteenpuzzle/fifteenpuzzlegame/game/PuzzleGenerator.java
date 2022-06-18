package com.fifteenpuzzle.fifteenpuzzlegame.game;

import com.fifteenpuzzle.fifteenpuzzlegame.model.PuzzleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PuzzleGenerator
{
    private static final int COLS = 4, ROWS = 4;
    Random rand = new Random();

    public PuzzleButton[] createPuzzleDone()
    {
        List<PuzzleButton> buttons = new ArrayList<>();
        int counter = 1;
        while (buttons.size() != (COLS * ROWS)-1)
        {
            int next = rand.nextInt((COLS * ROWS)-1);
            if (buttons.stream().filter(p -> p.getPuzzleItemNumber() == next).findAny().isEmpty())
            {
                PuzzleButton button = new PuzzleButton(counter++);
                buttons.add(button);
            }
        }
        buttons.add(new PuzzleButton(0));
        PuzzleButton[] array = new PuzzleButton[buttons.size()];
        buttons.toArray(array);

        for (int i = 0; i < COLS * ROWS; i++)
        {
            int currentRow = i / ROWS;
            int currentColumn = i - (currentRow * ROWS);
            PuzzleButton currentButton = array[i];
            if (currentRow != 0)
                currentButton.setAbove(array[ROWS * (currentRow - 1) + currentColumn]);
            if (currentColumn != 0)
                currentButton.setLeft(array[i - 1]);
            if (currentColumn != COLS - 1)
                currentButton.setRight(array[i + 1]);
            if (currentRow != ROWS - 1)
                currentButton.setBelow(array[ROWS * (currentRow + 1) + currentColumn]);
        }
        return array;
    }
    public PuzzleButton[] createPuzzle()
    {
        List<PuzzleButton> buttons = new ArrayList<>();
        while (buttons.size() != COLS * ROWS)
        {
            int next = rand.nextInt(COLS * ROWS);
            if (buttons.stream().filter(p -> p.getPuzzleItemNumber() == next).findAny().isEmpty())
            {
                PuzzleButton button = new PuzzleButton(next);
                buttons.add(button);
            }
        }
        PuzzleButton[] array = new PuzzleButton[buttons.size()];
        buttons.toArray(array);

        for (int i = 0; i < COLS * ROWS; i++)
        {
            int currentRow = i / ROWS;
            int currentColumn = i - (currentRow * ROWS);
            PuzzleButton currentButton = array[i];
            if (currentRow != 0)
                currentButton.setAbove(array[ROWS * (currentRow - 1) + currentColumn]);
            if (currentColumn != 0)
                currentButton.setLeft(array[i - 1]);
            if (currentColumn != COLS - 1)
                currentButton.setRight(array[i + 1]);
            if (currentRow != ROWS - 1)
                currentButton.setBelow(array[ROWS * (currentRow + 1) + currentColumn]);
        }
        return array;
    }
    public boolean checkWin(PuzzleButton[] buttons)
    {
        for (int i = 1; i < 16; i++)
        {
            if (buttons[i - 1].getPuzzleItemNumber() != i)
                return false;
        }
        return true;
    }
}

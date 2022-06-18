package com.fifteenpuzzle.fifteenpuzzlegame.game;

import com.fifteenpuzzle.fifteenpuzzlegame.model.PuzzleButton;

public class PuzzleSolver implements Runnable
{
    private PuzzleButton[] puzzleButtons;

    public PuzzleSolver(PuzzleButton[] puzzleButtons)
    {
        this.puzzleButtons = puzzleButtons;
    }

    /*
            Just imagine algorithm is hard and we need non-blocking UI
         */
    @Override
    public void run()
    {
        try
        {
            Thread.sleep(10000);
            System.out.println("Puzzle was solved");
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}

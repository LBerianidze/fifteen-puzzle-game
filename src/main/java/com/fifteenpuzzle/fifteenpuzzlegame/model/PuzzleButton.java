package com.fifteenpuzzle.fifteenpuzzlegame.model;

import javafx.scene.Cursor;
import javafx.scene.control.Button;

import static java.lang.Double.MAX_VALUE;

public class PuzzleButton extends Button
{
    private int puzzleItemNumber;
    private PuzzleButton left;
    private PuzzleButton right;
    private PuzzleButton above;
    private PuzzleButton below;

    public PuzzleButton(int _puzzleItemNumber)
    {
        this.puzzleItemNumber = _puzzleItemNumber;
        this.setMaxWidth(MAX_VALUE);
        this.setMaxHeight(MAX_VALUE);
        this.setCursor(Cursor.HAND);
        this.setText(_puzzleItemNumber == 0 ? "" : Integer.toString(_puzzleItemNumber));
        if (_puzzleItemNumber == 0)
        {
            this.setDisable(true);
            this.setBorder(null);
            setEmptyStyle();
        }
        else
            setDefaultStyle();
    }

    private void setDefaultStyle()
    {
        this.setStyle("-fx-background-color: coral; -fx-border-color: red; -fx-background-radius: 15;-fx-border-radius: 15; -fx-font-size: 18; ");

    }
    private void setEmptyStyle()
    {
        this.setStyle("-fx-background-color: transparent;");
    }

    public int getPuzzleItemNumber()
    {
        return puzzleItemNumber;
    }

    public void setPuzzleItemNumber(int puzzleItemNumber)
    {
        this.puzzleItemNumber = puzzleItemNumber;
    }

    public PuzzleButton getLeft()
    {
        return left;
    }

    public void setLeft(PuzzleButton left)
    {
        this.left = left;
    }

    public PuzzleButton getRight()
    {
        return right;
    }

    public void setRight(PuzzleButton right)
    {
        this.right = right;
    }

    public PuzzleButton getAbove()
    {
        return above;
    }

    public void setAbove(PuzzleButton above)
    {
        this.above = above;
    }

    public PuzzleButton getBelow()
    {
        return below;
    }

    public void setBelow(PuzzleButton below)
    {
        this.below = below;
    }

    public boolean canMove()
    {
        if (left != null && left.puzzleItemNumber == 0) return true;
        if (right != null && right.puzzleItemNumber == 0) return true;
        if (below != null && below.puzzleItemNumber == 0) return true;
        if (above != null && above.puzzleItemNumber == 0) return true;
        return false;
    }

    public void move()
    {
        int tmpValue = this.puzzleItemNumber;
        PuzzleButton emptyButton = left;
        if (right != null && right.puzzleItemNumber == 0) emptyButton = right;
        if (below != null && below.puzzleItemNumber == 0) emptyButton = below;
        if (above != null && above.puzzleItemNumber == 0) emptyButton = above;

        this.puzzleItemNumber = emptyButton.puzzleItemNumber;
        this.setDisable(true);
        this.setEmptyStyle();
        this.updateText();

        emptyButton.puzzleItemNumber = tmpValue;
        emptyButton.setDisable(false);
        emptyButton.setDefaultStyle();
        emptyButton.updateText();
    }

    private void updateText()
    {
        this.setText(puzzleItemNumber == 0 ? "" : Integer.toString(puzzleItemNumber));

    }
}

package UI;

import UI.UIElements.GameTimer;
import UI.UIElements.ImagesGetter;
import UI.UIElements.LevelDifficulty;
import UI.panels.*;
import game.ActionField;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Sergey on 08.07.2017.
 */

public class SwingPaint {
    public static final int RIGHT_LEFT_PADDING = 68 * 2;
    public static final int TOP_PADDING = 160;
    public static final int BOTTOM_PADDING = 20;
    private static PanelTop panelTop;
    private static LevelDifficulty levelDifficulty;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SwingPaint::createAndShowGUI);
    }

    private static void setFrameSize(final int row, final int column) {
        f.setSize(row * FieldPainter.CELL_WIDTH + RIGHT_LEFT_PADDING,
                column * FieldPainter.CELL_HEIGHT + TOP_PADDING + BOTTOM_PADDING);
    }

    private static void createPanels(LevelDifficulty curLevel) {
        setFrameSize(9, 9);
        levelDifficulty = curLevel;
        ActionField actionField = new game.ActionField(9, 9, 10);
        GameTimer gameTimer = new GameTimer();
        PanelTopListener topListener = new PanelTopListener();
        GamePanelListener gpl = new GamePanelListener();
        panelTop = new PanelTop(actionField, topListener, gpl, gameTimer);
        GamePanel gamePanel = new GamePanel(actionField, topListener, gpl);
        f.add(panelTop, BorderLayout.PAGE_START);
        f.add(gamePanel, BorderLayout.CENTER);
    }

    private static ActionField chooseActionField(LevelDifficulty curLevel) {
        switch (curLevel) {
            case BEGINNER:
                return new ActionField(5, 5, 5);
            case EASY:
                return new ActionField(9, 9, 10);
            case NORMAL:
                return new ActionField(16, 16, 50);
            case HARD:
                return new ActionField(30, 16, 99);
            case INTENSE:
                return new ActionField(40, 17, 200);
            default:
                return chooseActionField(levelDifficulty);
        }
    }

    public static void recreatePanels(LevelDifficulty newLevel) {
        ActionField actionField = chooseActionField(newLevel);
        levelDifficulty = newLevel;
        panelTop.recreatePanel(actionField);
        setFrameSize(actionField.getRow(), actionField.getColumn());
    }

    private static JFrame f;

    private static void createAndShowGUI() {
        f = new JFrame("Sapper");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameMenu menu = new GameMenu();
        f.setJMenuBar(menu.createMenuBar());
        f.validate();
        f.setLayout(new BorderLayout());
        f.setResizable( false );
        createPanels(LevelDifficulty.EASY);
        f.setIconImage(ImagesGetter.GAME_ICON);
        f.setVisible(true);
    }
}


package com.example.GUI;

import com.example.Engine.BitBoard;
import com.example.Engine.GameBoard;

import static com.example.Engine.BitBoard.*;
import static com.example.Engine.GameBoard.*;
import static com.example.Engine.Search.*;
import static com.example.Game.*;
import static com.example.Engine.MoveGen.*;

import static com.example.Game.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.awt.GridBagConstraints.*;

public class GUI {
    public JFrame mainFrame;
    public BoardPanel chessBoard;
    public InfoPanel info;
    public OptionsPanel options;
    public DebugPanel debug;
    public int guiScore;
    public JLabel scoreLabel = new JLabel();
    public JButton reduceDepth = new JButton("Reduce Depth");
    public JButton increaseDepth = new JButton("Increase Depth");
    public JLabel depthLabel = new JLabel("depth: ");


    public static Dimension windowDimensions = Toolkit.getDefaultToolkit().getScreenSize();
    public static double debugAreaHeightY;
    public static double capEmptyAreaWidthX;
    public static int boardSize;
    public static Border whiteLine = BorderFactory.createLineBorder(Color.black, 0);
    public static Color darkColor;
    public static Color lightColor;
    public static Color darkColorHighlight;
    public static Color lightColorHighlight;

    public static final double optionsAreaWidthWeight;

    static {
        capEmptyAreaWidthX = 0.3;
        debugAreaHeightY = 0.3;
        optionsAreaWidthWeight = 0.3;
        boardSize = 560;

        lightColor = new Color(255, 255, 255);
        lightColorHighlight = new Color(210, 210, 210);

        darkColor = new Color(50, 110, 50);
        darkColorHighlight = new Color(50, 80, 50);
    }

    public GUI() {
        //define main frame and set layout


        JFrame bugfixer = new JFrame();
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setResizable(true);
//        mainFrame.setSize(new Dimension(width, height));


        GridBagConstraints gbc = new GridBagConstraints();

        //set icon to transparent
        Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
        mainFrame.setIconImage(icon);
//        final double windowHeight =  mainFrame.getHeight();
//        final double windowWidth = mainFrame.getWidth();

        //create menu bar
        createMenuBar();

        //info
        gbc.anchor = FIRST_LINE_START;
        gbc.fill = HORIZONTAL;
        info = new InfoPanel();
        info.setBackground(new Color(127, 127, 127));
        scoreLabel.setText("");
        info.add(depthLabel);
        info.add(scoreLabel);//add score in gui
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0;
        gbc.weightx = 0;
        //
        mainFrame.add(info, gbc);
        //


        //make chessboard
        chessBoard = new BoardPanel();
        chessBoard.setBackground(Color.BLUE);
        gbc.gridx = 1;

        gbc.fill = NONE;
        gbc.weightx = 0;
        gbc.anchor = FIRST_LINE_START;

        mainFrame.add(chessBoard, gbc);

        //options

        options = new OptionsPanel();
        options.setBackground(Color.DARK_GRAY);
        reduceDepth.addActionListener(e -> {
            if(searchDepth>1) {
                searchDepth--;
                updateDepth(searchDepth);
            }
        });
        increaseDepth.addActionListener(e -> {
            searchDepth++;
            updateDepth(searchDepth);
        });
        options.add(reduceDepth);
        options.add(increaseDepth);
        gbc.anchor = FIRST_LINE_START;
        gbc.fill = HORIZONTAL;
        gbc.gridx = 2;
        gbc.weightx = 1;

        mainFrame.add(options, gbc);

        //debug

        debug = new DebugPanel();

        debug.setBackground(Color.BLACK);
        gbc.anchor = FIRST_LINE_START;
        gbc.fill = BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;

        mainFrame.add(debug, gbc);

        //make visible
        mainFrame.setVisible(true);
        mainFrame.pack();



    }
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem FENLoader = new JMenuItem("Load FEN String");
        FENLoader.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                JDialog FENAsker = new JDialog();
                FENAsker.setSize(500, 60);
                FENAsker.setVisible(true);
                JTextField FEN = new JTextField("");
                FENAsker.add(FEN);

                FEN.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                            try {
                                loadFEN(FEN.getText());
                                FENAsker.dispose();
                                chessBoard.updateBoard();
                                newFEN = true;
                            } catch(Exception e1) {
                                FENAsker.dispose();
                            }
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        fileMenu.add(FENLoader);

        menuBar.add(fileMenu);
        //
        //
        this.mainFrame.setJMenuBar(menuBar);
    }

    public void updateScore(float score) {
        info.removeAll();
        scoreLabel.setText("Score: " + score);
        if(score >= 0) {
            scoreLabel.setForeground(Color.WHITE);
        } else {
            scoreLabel.setForeground(Color.BLACK);
        }
        info.add(scoreLabel);
        info.add(depthLabel);
        info.repaint();
        info.revalidate();

    }

    public void updateDepth(int depth) {
        info.removeAll();
        info.add(scoreLabel);
        depthLabel.setText("Depth: " + depth);
        info.add(depthLabel);
        info.repaint();
        info.revalidate();
    }

    public class BoardPanel extends JPanel {
        Tile[] tiles;
        public BoardPanel() {
            super(new GridLayout(8, 8));
            tiles = new Tile[64];
            for(int i = 0; i < 64; i ++) {

            }
            for(int i = 7; i >= 0; i--) {
                for(int j = 0; j <= 7; j++) {
                    Tile tile = new Tile(i*8+j);
                    tiles[i*8+j] = tile;
                    add(tile);
                }
            }




        }

        public void updateBoard() {
            for(int i = 0; i < 64; i++) {
                tiles[i].updateTile();
            }

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(boardSize, boardSize);
        }
    }
    private class Tile extends JPanel {
        public int id;
        public JLabel piece;
        public Tile(int id) {
            super(new GridBagLayout());
            this.id = id;
            this.setBackground(((id + id / 8) % 2 == 0)?darkColor:lightColor);
            this.piece = new JLabel();
            updateTile();

            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
//                    System.out.println(".");
//                    if(userStart == 64) {
//                        System.out.println("start made");
//                        userStart = id;
//                    } else {
//                        System.out.println("target made");
//                        userTarget = id;
//                        Game.moveMade = true;
//
//                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
//                    if(userStart == 64) {
//                        System.out.println("start made");
//                        System.out.println(squareToCoords[id]);
//                        userStart = id;
//                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
//                    System.out.println("target made");
//                    System.out.println(squareToCoords[id]);
//                    userTarget = id;
//                    Game.moveMade = true;
                    if(userStart == 64) {
                        System.out.println("start made");
                        userStart = id;
                    } else {
                        System.out.println("target made");
                        userTarget = id;
                        moveMade = true;

                    }

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                    highlightBackground();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if(id!=userStart && userTarget==64) {
                        darkenBackground();
                    }
                }
            });

        }

        public void highlightDarkenBackground() {
            if(this.getBackground() == darkColorHighlight) {
                this.setBackground(darkColor);
            } else if(this.getBackground() == darkColor) {
                this.setBackground(darkColorHighlight);
            } else if(this.getBackground() == lightColorHighlight) {
                this.setBackground(lightColor);
            } else {
                this.setBackground(lightColorHighlight);
            }
        }

        public void darkenBackground() {
            if(this.getBackground() == darkColorHighlight) {
                this.setBackground(darkColor);
            }
            else if(this.getBackground() == lightColorHighlight) {
                this.setBackground(lightColor);
            }
        }

        public void highlightBackground() {
            if(this.getBackground() == lightColor) {
                this.setBackground(lightColorHighlight);
            } else if(this.getBackground() == darkColor) {
                this.setBackground(darkColorHighlight);
            }
        }


        public void updateTile() {
            String[] BBIDS = new String[] {"e", "wP", "wN", "wB", "wR", "wQ", "wK", "bP", "bN", "bB", "bR", "bQ", "bK"};
            String[] allSBoards = new String[13];

            for(int k = P, exit = 0; k <= GameBoard.bitboards.length && exit == 0; k ++) {
                if(k == GameBoard.k+1) {
                    try {
                        this.removeAll();
                        BufferedImage image = ImageIO.read(new File("Res/1x1.png"));
                        this.piece = new JLabel(new ImageIcon(image));
                        this.add(piece);
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                        System.exit(0);
                    }
                    break;
                }
                if(BitBoard.get_bit(bitboards[k], this.id) != 0) {
                    this.removeAll();

                    BufferedImage image = null;
                    try {
                        image = ImageIO.read(new File("Res/" + BBIDS[k] + ".png"));

                    } catch(IOException e) {
                        e.printStackTrace();
                        System.exit(0);
                    }

                    this.piece = new JLabel(new ImageIcon(image));

                    this.add(piece);
                    exit = 1;
                }
            }
            darkenBackground();
            this.validate();
            this.repaint();


        }
    }

    private class InfoPanel extends JPanel {
        public InfoPanel() {
            super(new GridLayout(20, 1));
        }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension((int)(capEmptyAreaWidthX*boardSize), boardSize);
        }
    }
    private class DebugPanel extends JPanel {
        public DebugPanel() {
            super();
        }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, (int)(debugAreaHeightY*boardSize));
        }
    }
    private class OptionsPanel extends JPanel {
        public OptionsPanel() {
            super();
        }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension((int)(optionsAreaWidthWeight*boardSize), boardSize);

        }
    }

}

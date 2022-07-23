package picture;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class PictureFrame extends JFrame {

    //define a 2d array to store the picture with num
    private int[][] data = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    //define two int variables to record the position of 0
    private int x0;
    private int y0;

    //define the 2d array after successfully arranged
    private int[][] finalData = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    //define buttons
    private JButton upBtn;
    private JButton downBtn;
    private JButton leftBtn;
    private JButton rightBtn;
    private JButton helpBtn;
    private JButton resetBtn;

    private JPanel imagePanel;


    public PictureFrame() {

        initFrame();

        randomData();

        paintView();

        addButtonEvent();

        this.setVisible(true);
    }

    //initilize the frame
    public void initFrame() {
        this.setSize(960, 560);
        this.setTitle("puzzle");
        this.setLocationRelativeTo(null); //center of the view
        this.setDefaultCloseOperation(3); //close the program after close the window
        this.setAlwaysOnTop(true);
        this.setLayout(null);
    }

    //setting size and adding picture to each element in 2d array
    public void paintView() {
        //create a panel
        imagePanel = new JPanel();
        imagePanel.setBounds(150, 114, 360, 360);
        imagePanel.setLayout(null);

        //loop the 2d array to get the number of pic
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                //create Jlabel object and load the picture
                JLabel imageLabel = new JLabel(new ImageIcon("...\\src\\picture\\images\\" + data[i][j] + ".png"));
                imageLabel.setBounds(j * 90, i * 90, 90, 90);
                imagePanel.add(imageLabel);
            }
        }
        this.add(imagePanel);

        //reference picture
        JLabel refLabel = new JLabel(new ImageIcon("...\\src\\picture\\images\\ref.png"));
        refLabel.setBounds(574, 114, 122, 121);
        this.add(refLabel);

        //buttons
        upBtn = new JButton(new ImageIcon("...\\src\\picture\\images\\up.png"));
        upBtn.setBounds(732, 265, 57, 57);
        this.add(upBtn);

        downBtn = new JButton(new ImageIcon("...\\src\\picture\\images\\down.png"));
        downBtn.setBounds(732, 347, 57, 57);
        this.add(downBtn);

        leftBtn = new JButton(new ImageIcon("...\\src\\picture\\images\\left.png"));
        leftBtn.setBounds(650, 347, 57, 57);
        this.add(leftBtn);

        rightBtn = new JButton(new ImageIcon("...\\src\\picture\\images\\right.png"));
        rightBtn.setBounds(813, 347, 57, 57);
        this.add(rightBtn);

        helpBtn = new JButton(new ImageIcon("...\\images\\help.png"));
        helpBtn.setBounds(626, 444, 108, 45);
        this.add(helpBtn);

        resetBtn = new JButton(new ImageIcon("...\\src\\picture\\images\\reset.png"));
        resetBtn.setBounds(786, 444, 108, 45);
        this.add(resetBtn);

        JLabel backgroundLabel = new JLabel(new ImageIcon("...\\src\\picture\\images\\background.png"));
        backgroundLabel.setBounds(0, 0, 960, 530);
        this.add(backgroundLabel);
    }

    //random the 2d array elements
    public void randomData() {
        Random r = new Random();

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                int x = r.nextInt(data.length);
                int y = r.nextInt(data[x].length);

                int temp = data[i][j];
                data[i][j] = data[x][y];
                data[x][y] = temp;
            }
        }

        //find the position of 0, break wc means break the whole two loops
        wc:
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == 0) {
                    x0 = i;
                    y0 = j;
                    break wc;
                }
            }
        }

        System.out.println(x0 + ", " + y0);
    }

    //re-paint the view
    public void rePaintView() {
        //remove all componenet
        imagePanel.removeAll();

        imagePanel = new JPanel();
        imagePanel.setBounds(150, 114, 360, 360);
        imagePanel.setLayout(null);

        //loop the 2d array to get the number of pic
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                //create Jlabel object and load the picture
                JLabel imageLabel = new JLabel(new ImageIcon("...\\src\\picture\\images\\" + data[i][j] + ".png"));
                imageLabel.setBounds(j * 90, i * 90, 90, 90);
                imagePanel.add(imageLabel);
            }
        }
        this.add(imagePanel);
        imagePanel.repaint();
    }

    //final result
    public void success() {
        data = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        upBtn.setEnabled(false);
        downBtn.setEnabled(false);
        leftBtn.setEnabled(false);
        rightBtn.setEnabled(false);
    }

    //first 15 pics are in the position and dont need to move anymore
    public boolean isSuccess() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != finalData[i][j]) return false;
            }
        }
        return true;
    }

    //add event listen to the buttons
    public void addButtonEvent() {
        upBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("up");
                //bound issue
                if (x0 == 3) {
                    return;
                }
                //exchange the position of 0 and next one
                data[x0][y0] = data[x0 + 1][y0];
                data[x0 + 1][y0] = 0;
                x0 = x0 + 1;

                //if can move the 0 position
                if (isSuccess()) {
                    success();
                }

                rePaintView();
            }
        });

        downBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("down");
                if (x0 == 0) return;
                data[x0][y0] = data[x0 - 1][y0];
                data[x0 - 1][y0] = 0;
                x0 = x0 - 1;

                //if can move the 0 position
                if (isSuccess()) {
                    success();
                }

                rePaintView();
            }
        });

        leftBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("left");
                if (y0 == 3) return;
                data[x0][y0] = data[x0][y0 + 1];
                data[x0][y0 + 1] = 0;
                y0 = y0 + 1;

                //if can move the 0 position
                if (isSuccess()) {
                    success();
                }

                rePaintView();
            }

        });
        rightBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("right");
                if (y0 == 0) return;
                data[x0][y0] = data[x0][y0 - 1];
                data[x0][y0 - 1] = 0;
                y0 = y0 - 1;

                //if can move the 0 position
                if (isSuccess()) {
                    success();
                }

                rePaintView();
            }

        });

        helpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("help");
                success();
                rePaintView();
            }
        });

        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("rest");

                //reset the 2d- array
                data = new int[][]{
                        {1, 2, 3, 4},
                        {5, 6, 7, 8},
                        {9, 10, 11, 12},
                        {13, 14, 15, 0}
                };

                randomData();
                rePaintView();
                upBtn.setEnabled(true);
                downBtn.setEnabled(true);
                leftBtn.setEnabled(true);
                rightBtn.setEnabled(true);
            }

        });

    }
}

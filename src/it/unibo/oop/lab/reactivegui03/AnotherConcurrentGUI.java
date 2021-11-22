package it.unibo.oop.lab.reactivegui03;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.unibo.oop.lab.reactivegui02.ConcurrentGUI;

public class AnotherConcurrentGUI {
    private static final long serialVersionUID = 1L;
    private static final double WIDTH_PERC = 0.2;
    private static final double HEIGHT_PERC = 0.1;
    private final JLabel display = new JLabel();
    private final JButton up = new JButton("up");
    private final JButton down = new JButton("down");
    private final JButton stop = new JButton("stop");
    int count1;
    
    public AnotherConcurrentGUI() {
        this.count1 = 0;
        final JFrame frame = new JFrame();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (screenSize.getWidth() * WIDTH_PERC), (int) (screenSize.getHeight() * HEIGHT_PERC));
        final JPanel panel = new JPanel(new FlowLayout());
        panel.add(display);
        panel.add(up);
        panel.add(down);
        panel.add(stop); 
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);        
        
        final Agent agent1 = new Agent();
        new Thread(agent1).start();
        
        up.addActionListener(new ActionListener() {
            /**
             * event handler associated to action event on button up.
             * 
             * @param e
             *            the action event that will be handled by this listener
             */
            @Override
            public void actionPerformed(final ActionEvent e) {
                agent1.incCounting();
            }
            
        });
        
        down.addActionListener(new ActionListener() {
            /**
             * event handler associated to action event on button down.
             * 
             * @param e
             *            the action event that will be handled by this listener
             */
            @Override
            public void actionPerformed(final ActionEvent e) {
                agent1.decCounting();
            }
            
        });
        
        stop.addActionListener(new ActionListener() {
            /**
             * event handler associated to action event on button stop.
             * 
             * @param e
             *            the action event that will be handled by this listener
             */
            @Override
            public void actionPerformed(final ActionEvent e) {
                // Agent should be final
                agent1.stopCounting();
                up.setEnabled(false);
                down.setEnabled(false);
            }
        }); 
    }
    
    
    private class Agent implements Runnable{
        
        private volatile boolean stop1;
        private volatile boolean flag;
        private volatile int tmp = 0;

        @Override
        public void run() {
            while (!this.stop1) {
                try {
                    check();
                    SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            AnotherConcurrentGUI.this.display.setText(Integer.toString(count1));
                        }
                    });
                    if(!flag) {
                        count1++;
                    }else {
                        count1--;
                    }
                    Thread.sleep(1000);                    
                } catch (InvocationTargetException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }  
        /**
         * External command to stop counting.
         */
        public void stopCounting() {
            this.stop1 = true;
        }
        
        /**
         * External command to increment the counting
         */
        public void incCounting() {
            this.flag = false;
        }
        
        /**
         * External command to decrement the counting
         */
        public void decCounting() {
            this.flag = true;
        }
         /**
          * check if 10 seconds have passed, if yes disable all the buttons and stop the count
          */
        public void check() {
            if(tmp==10) {
                up.setEnabled(false);
                down.setEnabled(false);
                stop.setEnabled(false);
                this.stopCounting();
            }
            tmp++;
        }
    }
    
}

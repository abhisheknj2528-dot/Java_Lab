import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class Experiment02_StringAnimationApplet extends Applet implements Runnable {

    private String message = "Welcome to Java Programming Lab! Multithreaded String Animation";

    private int xPos;
    private int yPos;
    private int step = 4;
    private volatile boolean isRunning = false;

    private Thread animationThread = null;

    private Font bannerFont;
    private Color bannerColor = new Color(37, 99, 235);

    @Override
    public void init() {
        setBackground(new Color(241, 245, 249));
        bannerFont = new Font("SansSerif", Font.BOLD, 20);
        xPos = -350;
        yPos = 160;
    }

    @Override
    public void start() {
        if (animationThread == null) {
            isRunning = true;
            animationThread = new Thread(this, "StringAnimationWorker");
            animationThread.start();
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            try {

                xPos += step;

                int currentWidth = getWidth() > 0 ? getWidth() : 700;
                if (xPos > currentWidth) {
                    FontMetrics fm = getFontMetrics(bannerFont);
                    int textWidth = fm.stringWidth(message);
                    xPos = -textWidth;
                }

                repaint();

                Thread.sleep(33);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(new Color(30, 41, 59));
        g.fillRect(0, 0, getWidth(), 50);

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        g.drawString("EXPERIMENT 02: MULTITHREADED APPLET STRING ANIMATION", 20, 32);

        g.setColor(Color.WHITE);
        g.fillRoundRect(20, 80, getWidth() - 40, 130, 15, 15);
        g.setColor(new Color(203, 213, 225));
        g.drawRoundRect(20, 80, getWidth() - 40, 130, 15, 15);

        g.setFont(bannerFont);
        g.setColor(bannerColor);
        g.drawString(message, xPos, yPos);

        g.setColor(new Color(100, 116, 139));
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString("Worker Thread: " + (animationThread != null ? animationThread.getName() : "None") +
                     " | X-Coord: " + xPos + " px", 25, 260);
    }

    @Override
    public void stop() {
        isRunning = false;
        if (animationThread != null) {
            animationThread.interrupt();
            animationThread = null;
        }
    }

    @Override
    public void destroy() {
        stop();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("University Lab - Exp 02: Applet String Animation");
        Experiment02_StringAnimationApplet applet = new Experiment02_StringAnimationApplet();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 350);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(applet);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                applet.stop();
                applet.destroy();
            }
        });

        frame.setVisible(true);
        applet.init();
        applet.start();
    }
}

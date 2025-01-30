import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
public class MandelbrotSet extends JFrame {

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1200;
    private static final int MAX_ITERATION = 300;
    private static final double DEFAULT_ZOOM = 100.0;
    private static final double DEFAULT_TOP_LEFT_X = -8.0;
    private static final double DEFAULT_TOP_LEFT_Y = 5.0;

    private final Canvas canvas;
    private final BufferedImage fractalImage;
    private double zoomFactor = DEFAULT_ZOOM;
    private double topLeftX = DEFAULT_TOP_LEFT_X;
    private double topLeftY = DEFAULT_TOP_LEFT_Y;
    private boolean isFullscreen = false;
    private final GraphicsDevice graphicsDevice;


    public MandelbrotSet() {
        setTitle("Fractal Explorer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);

        fractalImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        canvas = new Canvas();
        add(canvas, BorderLayout.CENTER);

        graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        canvas.addKeyStrokeEvents();
        updateFractal();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MandelbrotSet::new);
    }

    private double getXPos(double x) {
        return x / zoomFactor + topLeftX;
    }

    private double getYPos(double y) {
        return y / zoomFactor - topLeftY;
    }

    private void updateFractal() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                double cR = getXPos(x);
                double cI = getYPos(y);
                int iterations = computeIterations(cR, cI);
                fractalImage.setRGB(x, y, computeColor(iterations));
            }
        }
        canvas.repaint();
    }

    private int computeColor(int iterations) {
        int color = 0b011011100001100101101000;
        int mask  = 0b000000000000010101110111;
        int shiftMag = iterations / 13;

        if (iterations == MAX_ITERATION)
            return Color.BLACK.getRGB();

        return color | (mask << shiftMag);
    }

    private int computeIterations(double cR, double cI) {
        double zR = 0.0, zI = 0.0;
        int iterations = 0;
        while (zR * zR + zI * zI <= 4.0 && iterations < MAX_ITERATION) {
            double tempZR = zR * zR - zI * zI + cR;
            zI = 2 * zR * zI + cI;
            zR = tempZR;
            iterations++;
        }
        return iterations;
    }

    private void toggleFullscreen() {
        dispose();

        if (!isFullscreen) {
            setUndecorated(true);
            graphicsDevice.setFullScreenWindow(this);
        } else {
            setUndecorated(false);
            graphicsDevice.setFullScreenWindow(null);
            setSize(WIDTH, HEIGHT);
            setLocationRelativeTo(null);
        }

        isFullscreen = !isFullscreen;
        setVisible(true);
    }

    private void move(double deltaX, double deltaY) {
        topLeftX += deltaX / zoomFactor;
        topLeftY -= deltaY / zoomFactor;
        updateFractal();
    }

    private void adjustZoom(double mouseX, double mouseY, double newZoomFactor) {
        topLeftX += mouseX / zoomFactor;
        topLeftY -= mouseY / zoomFactor;
        zoomFactor = newZoomFactor;
        topLeftX -= ((double) WIDTH / 2) / zoomFactor;
        topLeftY += ((double) HEIGHT / 2) / zoomFactor;
        updateFractal();
    }

    private void moveUp() {
        move(0, HEIGHT / 6.0);
    }

    private void moveDown() {
        move(0, -HEIGHT / 6.0);
    }

    private void moveLeft() {
        move(-WIDTH / 6.0, 0);
    }

    private void moveRight() {
        move(WIDTH / 6.0, 0);
    }

    private class Canvas extends JPanel implements MouseListener {

        public Canvas() {
            addMouseListener(this);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(WIDTH, HEIGHT);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(fractalImage, 0, 0, null);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            double x = e.getX();
            double y = e.getY();
            if (e.getButton() == MouseEvent.BUTTON1) {
                adjustZoom(x, y, zoomFactor * 2);
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                adjustZoom(x, y, zoomFactor / 2);
            }
        }

        public void addKeyStrokeEvents() {
            this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "w_key");
            this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "a_key");
            this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "s_key");
            this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "d_key");
            this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "fullscreen_toggle");

            this.getActionMap().put("w_key", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moveUp();
                }
            });
            this.getActionMap().put("a_key", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moveLeft();
                }
            });
            this.getActionMap().put("s_key", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moveDown();
                }
            });
            this.getActionMap().put("d_key", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moveRight();
                }
            });
            this.getActionMap().put("fullscreen_toggle", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toggleFullscreen();
                }
            });
        }

        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }
}

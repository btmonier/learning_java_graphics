import java.awt.*;
import java.awt.image.BufferStrategy;

public class Animation implements Runnable {
    private Display display;
    public int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;

    public Animation(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;

    }

    private void init() {
        display = new Display(title, width, height);
    }

    private void tick() {

    }

    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();

        // Clear screen

        // Draw here
        int gb = width / 10;
        int pre = 0;
        for (int i = 0; i < 10; i++) {
            g.drawLine(i + pre, 0, i + pre, height);
            g.drawLine(0, i + pre, width, i + pre);
            pre += gb;
        }

        // End draw
        bs.show();
        g.dispose();
    }

    public void run() {
        init();

        while(running) {
            tick();
            render();
        }

        stop();
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

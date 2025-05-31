package twisk.outils;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class that manages all threads.
 */
public class ThreadsManager {

    // The single instance of the ThreadsManager
    private static ThreadsManager instance;

    // List of threads managed by this class
    private final List<Thread> threads;

    /**
     * Private constructor (Singleton).
     */
    private ThreadsManager() {
        threads = new ArrayList<>();
    }

    /**
     * Returns the singleton instance.
     */
    public static ThreadsManager getInstance() {
        if (instance == null) {
            instance = new ThreadsManager();
        }
        return instance;
    }

    /**
     * Launches a task by wrapping it in a thread and starting it.
     *
     * @param task The task to run.
     */
    public void lancer(Runnable task) {
        Thread thread = new Thread(task);
        threads.add(thread);
        thread.start();
    }

    /**
     * Destroys all threads by interrupting them.
     */
    public void detruireTout() {
        for (Thread thread : threads) {
            if (thread.isAlive()) {
                thread.interrupt();
            }
        }
        threads.clear();
    }

    public Thread getLastThread() {
        if (threads.isEmpty()) return null;
        return threads.get(threads.size() - 1);
    }



}

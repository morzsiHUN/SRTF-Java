public class Task {
    /**
     * A folyamat fontossága (0 vagy 1), de nem használjuk.
     **/
    private int priotity;
    /**
     * Mikor lett elindítva a folyamat
     * (egész szám >= 0), a következő időszeletben már
     * futhat (0: az ütemező indításakor már létezik),
     * egyszerre érkező taszkok esetén az ABC-sorrend dönt.
     **/
    private int startTime;
    /**
     * Mennyi idő kell hogy lefusson a folyamat a processzoron. (egész szám >= 1).
     **/
    private int cpuTime;
    /**
     * Folyamat azonosító (A, B, C...).
     */
    private char name;
    /**
     * Amount of time spent waiting, after the task startTime
     * reached simulation Time.
     */
    private int waitTime = 0;

    /**
     * A representation of a Task.
     * 
     * @param name      A char representing the name of the task (A-Z).
     * @param priotity  Integer representing the task's priority (0/1). !!!ISN'T
     *                  IMPLEMENTED!!!
     * @param startTime Integer representing the creation time of the task, after
     *                  which it can be either waiting or executing (>= 0).
     * @param execTime  Integer representing the needed execution time for the task
     *                  (>= 1).
     */
    public Task(char name, int priotity, int startTime, int execTime) {
        this.name = name;
        this.priotity = priotity;
        this.startTime = startTime;
        this.cpuTime = execTime;
    }

    public char getName() {
        return name;
    }

    public int getPriotity() {
        return priotity;
    }

    public int getCpuTime() {
        return cpuTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    /**
     * Simulates 1 CPU tick.
     * 
     * @param isTaskActive True if the current task is selected bye the scheduler;
     *                     False otherwise.
     */
    public void cpuWorking(boolean isTaskActive) {
        if (isTaskActive) {
            excecuteOneTick();
        } else {
            waitOneTick();
        }
    }

    /**
     * Removes {@code -1} CPU time as if the task was being executed.
     */
    private void excecuteOneTick() {
        cpuTime--;
    }

    /**
     * Adds {@code +1} to the wait time as if the task was not being executed.
     */
    private void waitOneTick() {
        this.waitTime++;
    }
}

public class TaskScheduler {
    private Task[] tasks = new Task[10];
    private int simTime = 0;
    private String TaskExecOrder = "";

    public String getTaskExecOrder() {
        return TaskExecOrder;
    }

    public String getWaitTimes() {
        String wtime = "";
        for (Task task : tasks) {
            if (task == null) {
                continue;
            }

            wtime += task.getName() + ":" + task.getWaitTime() + ",";
        }
        return wtime.substring(0, wtime.length() - 1);
    }

    /**
     * Adds a task to a internal array, which can hold 10 Tasks.
     * 
     * @param name      A char representing the name of the task (A-Z).
     * @param priotity  Integer representing the task's priority (0/1). !!!ISN'T
     *                  IMPLEMENTED!!!
     * @param startTime Integer representing the creation time of the task, after
     *                  which it can be either waiting or executing (>= 0).
     * @param execTime  Integer representing the needed execution time for the task
     *                  (>= 1).
     * @return {@code True} if operation was succesfull; {@code False} otherwise.
     */
    public boolean addTask(char name, int priotity, int startTime, int execTime) {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] == null) {
                tasks[i] = new Task(name, priotity, startTime, execTime);
                return true;
            }
        }
        return false;
    }

    /**
     * Starts simulating the SRTF with the data stored in the internal task array.
     * 
     * @param verboseTicks If {@code True} the tick will be counted and printed.
     */
    public void startSim(boolean verboseTicks) {
        while (IsThereUnexecutedTask()) {
            int[] inTimeTaskIndexes = getInTimeTaskIndexes();
            if (inTimeTaskIndexes[0] != -1) {
                int shortestJobIndex = getShortestJobIndex(inTimeTaskIndexes);
                storeTaskName(shortestJobIndex);
                Simulate(inTimeTaskIndexes, shortestJobIndex);
            }

            simTime++;
            if (verboseTicks) {
                System.out.println(simTime + " tick done");
            }
        }
    }

    /**
     * To test if a {@code Task} is within the current simulation time.
     * 
     * @param task A {@code Task} to be tested.
     * @return {@code True} if task is below or equal to the simulation time;
     *         {@code False} otherwise.
     */
    private boolean isInTime(Task task) {
        if (task.getStartTime() <= simTime) {
            return true;
        }
        return false;
    }

    /**
     * Gets the Tasks which can be executed at the current simulation time.
     * <p>
     * <b>Warning</b>: if there are no avaliable Tasks to be executed,
     * the array will consist of a single
     * {@code -1} followed by {@code zeroes}.
     * 
     * @return The indexes of ready tasks, with a {@code -1} indicating the end of
     *         the data.
     */
    private int[] getInTimeTaskIndexes() {
        int[] readyTaskIndexes = new int[tasks.length + 1];
        int numberOfReadyTasks = 0;
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] == null || tasks[i].getCpuTime() == 0) {
                continue;
            }

            if (isInTime(tasks[i])) {
                readyTaskIndexes[numberOfReadyTasks] = i;
                numberOfReadyTasks++;
            }
        }
        readyTaskIndexes[numberOfReadyTasks] = -1;

        return readyTaskIndexes;
    }

    /**
     * Checks if there are Tasks left with CPU time.
     * 
     * @return {@code True} if there are still unfinished Tasks.
     *         <li>{@code False} if every Task was finished.</li>
     */
    private boolean IsThereUnexecutedTask() {
        for (Task task : tasks) {
            if (task == null) {
                continue;
            }

            if (task.getCpuTime() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the active Task's name to a string, except if the last and
     * current names are the same.
     * 
     * @param shortestJobIndex Name of the active (shortest) job,
     */
    private void storeTaskName(int shortestJobIndex) {
        if (TaskExecOrder != "") {
            char lastName = TaskExecOrder.charAt(TaskExecOrder.length() - 1);
            if (lastName == tasks[shortestJobIndex].getName()) {
                return;
            }
        }

        TaskExecOrder += tasks[shortestJobIndex].getName();
    }

    /**
     * Advances the execution or wait times for the Tasks, as if time had passed.
     * 
     * @param inTimeTaskIndexes Array of Tasks asks which can be executed at
     *                          the current simulation time.
     * @param shortesJobIndex   shortes Task index based on CPU time.
     */
    private void Simulate(int[] inTimeTaskIndexes, int shortesJobIndex) {
        for (int i : inTimeTaskIndexes) {
            if (i == -1) {
                return;
            }

            if (i == shortesJobIndex) {
                tasks[shortesJobIndex].cpuWorking(true);
                continue;
            }

            if (tasks[i] == null || tasks[i].getCpuTime() == 0) {
                continue;
            }

            tasks[i].cpuWorking(false);
        }
    }

    /**
     * Searches for the shortes Task based on CPU time.
     * <p>
     * If there are two matching CPU times, the lowest ANSII name goes first.
     * </p>
     * 
     * @param readyIndexes A list of the executable Tasks.
     * @return Task with the least amount of time to execute.
     */
    private int getShortestJobIndex(int[] readyIndexes) {
        int shortstCpuTime = Integer.MAX_VALUE;
        int shortestJobIndex = -1;
        for (int i = 0; i < readyIndexes.length; i++) {
            if (readyIndexes[i] == -1) {
                break;
            }

            int currentCpuTime = tasks[readyIndexes[i]].getCpuTime();
            if (currentCpuTime < shortstCpuTime) {
                shortestJobIndex = readyIndexes[i];
                shortstCpuTime = currentCpuTime;
                continue;
            }
            if (currentCpuTime == shortstCpuTime) {
                int currentLetterCode = (int) Character.toUpperCase(tasks[readyIndexes[i]].getName());
                int shortestLetterCode = (int) Character.toUpperCase(tasks[shortestJobIndex].getName());
                if (currentLetterCode < shortestLetterCode) {
                    shortestJobIndex = readyIndexes[i];
                    shortstCpuTime = currentCpuTime;
                }
            }
        }
        return shortestJobIndex;
    }

}

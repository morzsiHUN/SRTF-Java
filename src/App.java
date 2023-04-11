import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        // SCANNER TALÁN ILLEGÁLIS!!!!!
        // SCANNER TALÁN ILLEGÁLIS!!!!!
        // SCANNER TALÁN ILLEGÁLIS!!!!!
        // SCANNER TALÁN ILLEGÁLIS!!!!!
        Scanner scanner = new Scanner(System.in);
        // Read in the data from Standard input
        TaskScheduler tScheduler = getDataIn(scanner);
        // Start running the SRTF
        tScheduler.startSim(false);
        // Print out the results
        System.out.println(tScheduler.getTaskExecOrder());
        System.out.println(tScheduler.getWaitTimes());
    }

    public static TaskScheduler getDataIn(Scanner scanner) {
        TaskScheduler tScheduler = new TaskScheduler();
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            if ("" == data) {
                break;
            }
            String[] splData = data.split(",");

            if (splData.length != 4) {
                System.out.println("Hibás adatformátum! \n(Helyes formátum: A-Z, 0-1, >=0, >=1)");
                continue;
            }

            try {
                char n = splData[0].charAt(0);
                int p = Integer.parseInt(splData[1]);
                int s = Integer.parseInt(splData[2]);
                int e = Integer.parseInt(splData[3]);

                if (!tScheduler.addTask(n, p, s, e)) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Hibás szám adat! " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ismeretlen hiba!");
                e.printStackTrace();
            }
        }
        System.out.println("Beolvasás vége!");
        return tScheduler;
    }
}

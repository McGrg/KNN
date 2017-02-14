package gregory.reading;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Дополнительный метод для заполнения log.txt файла
 */
public class Writer {

    public static void logWriter(String data) {
        String time = "Current date is: " + LocalDate.now()+ " time is: " + LocalTime.now();
        try {
            OutputStream os = null;
            File file = new File("log.txt");
            if (file.createNewFile()) {
                System.out.println("log file is created!");
            }
            os = new FileOutputStream(file, true);
            data = time + " milliseconds to process " + data + "\r\n"; //строка для записи в файл
            os.write(data.getBytes(), 0, data.length());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

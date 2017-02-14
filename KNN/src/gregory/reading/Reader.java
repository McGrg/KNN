package gregory.reading;

import java.io.*;
import java.util.*;

import gregory.Calculating.Entity;
import org.apache.commons.io.*;

/**
 * Created by MSI GT70 on 10.01.2017.
 */
public class Reader {

    static public List<Entity> fileReader(String imagePathname, String lablePathname, int numberofValues) throws Exception {
        /**
         * закачивание файла в структуру HashMap
         */
        int startImageadress = 16; //начало существенной информации в файле
        int startLabeladress = 8;  //начало существенной информации в файле
        byte[] images = IOUtils.toByteArray(new FileInputStream(new File(imagePathname)));
        byte[] labels = IOUtils.toByteArray(new FileInputStream(new File(lablePathname)));
        List<Entity> list = new LinkedList<>();

        /**
         * Заполнение байтовым вектором сущности List <Entity>
         */

        for (int n = 1; n <= numberofValues; n++) {
            int[] temp= new int[784]; // вспомогательный массив

            for (int i = 1; i < 785; i++) {
                temp[i-1]=unsignedToBytes(images[(startImageadress + (i - 1))]); //заполнение вспомогательного массива temp
            }
            startImageadress = startImageadress + 784; // переход к следуещей группе данных
            Entity entity = new Entity(temp, labels[startLabeladress + (n - 1)]); // создание Entity.values, который содержит байтовый вектор
            list.add(entity);
        }
        return list;
    }

    /**
     * Перевод массива byte в массив unsigned int
     */

    public static int unsignedToBytes(byte b) {
        return b & 0xFF;
    }
}

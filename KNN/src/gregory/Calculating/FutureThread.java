package gregory.Calculating;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by gtsepennikov on 16.01.2017.
 */
public class FutureThread implements Callable<Result> {

    private List<Entity> sourceList;
    private Entity checkList;
    private int k;
    private int currentvalue;
    private int numberofValuesToCheck;

    public FutureThread(List<Entity> sourceList, Entity checkList, int k, int numberofValuesToCheck, int currentvalue) {
        this.sourceList = sourceList;
        this.checkList = checkList;
        this.k = k;
        this.numberofValuesToCheck = numberofValuesToCheck;
        this.currentvalue = currentvalue;
    }

    @Override
    public Result call() {
        Result res = new Result();
        try {
            res = Analizator.fileCompare(sourceList, checkList, k);

            int percent;
            percent = (int) (((double) currentvalue / (double) numberofValuesToCheck) * 100);
            System.out.print("\r");
            System.out.print("Выполнено: " + String.valueOf(percent) + "%");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}

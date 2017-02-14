package gregory.Calculating;

/**
 * Created by MSI GT70 on 11.01.2017.
 */
public class Entity {
    private int[] values;
    private byte label;

    public int getValue(int i){ return values[i]; }
    public byte getLabel(){ return label; }

    public Entity(int[] values, byte label){
        this.values = values;
        this.label = label;
    }

    public Entity(){
        this.values = null;
        this.label = 0;
    }

    public void printEntity(){
        System.out.println(label);
        for (int i = 0; i < 784; i = i + 28) {
            for (int k = 0; k < 28; k++) {  // вывод на печать матрицы 28х28
                if (values[i + k] != 0) {
                    System.out.print("x");
                } else System.out.print("-");
            }
            System.out.println("");
        }
    }

}

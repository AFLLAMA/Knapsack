package lama;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static int[] vals;
    private static int[] wts;
    private static int capacity;
    private static int len;

    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();
        String fileName = "src/lama/7";
        readAndSave(fileName);
        System.out.println("Capacity: " + capacity);
        Answer answer = knapSack(capacity,wts,vals,len);
        System.out.println("Characteristic vector:");
        int sum = 0;
        int sumValCheck = 0;
        for (int i = 0; i < len; i++) {
            sumValCheck += answer.cv[i]*vals[i];
            sum += answer.cv[i]*wts[i];
            System.out.print(answer.cv[i] + "; ");
        }
        System.out.println();
        System.out.println("Sum of Weights: " + sum);
        System.out.println("Sum of Values: " + answer.val);
//        System.out.println("Sum of Values Check: " + sumValCheck);

        int sumWt = 0;
        int sumVal = 0;
        for (int w:
             wts)
            sumWt+=w;
        for (int v:
             vals) {
            sumVal+=v;
        }
        System.out.println("Sum of all input Weights: " + sumWt);
        System.out.println("Sum of all input Values: " + sumVal);

        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime)/1000000000;
        System.out.println("Running time of program: " + totalTime + "sec.");
    }
    static void readAndSave(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        capacity = Integer.parseInt(bufferedReader.readLine());
        String line;
        int length = 0;
        while ((bufferedReader.readLine()) != null) {
            length++;
        }
        len = length;
        bufferedReader = new BufferedReader(new FileReader(fileName));
        vals = new int[len];
        wts = new int[len];
        int i = 0;
        bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {

            String[] s1 = line.split(" ");

            vals[i] = Integer.parseInt(s1[0]);
            wts[i] = Integer.parseInt(s1[1]);
            i++;
        }
    }
    static Answer max(Answer a, Answer b, int index) {
        if (a.val > b.val) {
            a.cv[index] = 1;
            return a;
        }
        else {
            return b;
        }
    }
    static Answer knapSack(int cap, int[] wt, int[] val, int size) {

        if (size == 0 || cap == 0) {
            int[] tmp = new int[len];
            for (int i = 0; i < len; i++) {
                tmp[i] = 0;
            }
            return new Answer(tmp,0);
        }
        if (wt[size-1] > cap)
            return knapSack(cap, wt, val, size-1);

        else {
            Answer case1 = knapSack(cap - wt[size - 1], wt, val, size - 1);
            case1.val += val[size - 1];
            Answer case2 = knapSack(cap, wt, val, size - 1);
            return max(case1, case2, size - 1);
        }
    }
}

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class Two_Way {
    public static void main(String[] args) {

        String text = "#basketball";
        String word = "#ba";
        int n = text.length()-1;
        int m = word.length()-1;
        List<Integer> results = runner(text,word,n,m);

        for (int item : results)
            System.out.println("Index: " + item);
        System.out.println(results.size() + " occurences.");
        if (results.size()>0)
            capitilizer(text,results,m);

    }
    static void capitilizer(String text, List<Integer> results,int m){
        int idx = 0;
        for (int i = 1; i<text.length(); i++){
            int x = 0;
            if (idx < results.size() && i == results.get(idx)){
                while (x<m) {
                    System.out.print((char)(text.charAt(i) - 32));
                    x++;
                    if (m-x>0) i++;
                }
                idx++;
            }
            else
                System.out.print(text.charAt(i));
        }
    }

    static List<Integer> runner(String text, String word, int n, int m){
        List<Integer> l1 = new ArrayList<>();

        int[] x = constant_space(word,m);
        boolean use_memory = true;
        int index = x[0], p = x[1];

        if (index - 1> m/2 || !word.substring(index,index+p).endsWith(word.substring(1,index))){
            p = Math.max(word.substring(1,index).length(),word.substring(index).length()+1);
            use_memory = false;

        }
        int i = 1, memory = 0;

        while (i <= n - m + 1){
            int j = Math.max(index-1,memory);

            while (j < m && text.charAt(i+j) == word.charAt(j +1))
                j++;

            if (j < m){
                i = i + j + 2 - index;
                memory = 0;
                continue;
            }
            j = Math.max(index-1,memory);

            while (j>memory && text.charAt(i+j-1) == word.charAt(j))
                j--;

            if (j == memory) {
                l1.add(i);
            }

            i+=p;
            memory = use_memory ? m - p : 0;

        }
        return l1;
    }

    static int[] constant_space(String text, int n){

        int[] x = constant_space1(text,n,true);
        int[] y = constant_space1(text,n,false);
        if (x[0]>y[0])
            return x;
        return y;
    }

    static int[] constant_space1(String text, int n, boolean flag){
        int out=1, p=1, i=2;

        while (i<=n){
            int r = (i - out) % p;
            if (text.charAt(i)==text.charAt(out+r))
                i++;
            else if (less_or_great(text.charAt(i),text.charAt(out+r),flag)) {
                p = i + 1 - out;
                i++;
            }
            else {
                out = i-r;
                i = i-r+1;
                p = 1;
            }
        }
        return new int[]{out, p};

    }

    static boolean less_or_great(char a, char b,boolean flag){
        if (flag) return a<b;
        return a>b;
    }
}
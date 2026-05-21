import java.util.Scanner;
import java.util.HashMap;

public class Ejercicio1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();       
        String s = sc.next();       

        HashMap<Character, Integer> llaves = new HashMap<>();

        int extras = 0;

        for (int i = 0; i < s.length() - 1; i += 2) {
            char llave  = s.charAt(i);       
            char puerta = s.charAt(i + 1);   
            llaves.put(llave, llaves.getOrDefault(llave, 0) + 1);
            char necesaria = Character.toLowerCase(puerta);
            if (llaves.getOrDefault(necesaria, 0) > 0) {
                llaves.put(necesaria, llaves.get(necesaria) - 1);
            } else {
                extras++;
            }
        }

        System.out.println(extras);
        sc.close();
    }
}
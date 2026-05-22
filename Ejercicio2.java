import java.util.Scanner;

public class Ejercicio2 {

    static final int TABLE_SIZE = 131071; //primo grande

    static String[] nombreParticipante = new String[TABLE_SIZE];
    static int[] puntaje   = new int[TABLE_SIZE];
    static int[] maxPuntaje= new int[TABLE_SIZE];
    static int[] rondaDelMax = new int[TABLE_SIZE]; //ronda en que alcanza maxPuntaje

 
    static int hash1(String nombre) {
        long h = 0;
        for (char c : nombre.toCharArray()) {
            h = (h * 31 + c) % TABLE_SIZE;
        }
        return (int) h;
    }

    static int hash2(String nombre) {
        long h = 0;
        for (char c : nombre.toCharArray()) {
            h = (h * 37 + c) % (TABLE_SIZE - 1);
        }
        return (int) (h + 1); // +1 para que nunca sea 0
    }

    //devuelve el índice en la tabla
    static int getIndex(String nombre) {
        int h1 = hash1(nombre);
        int h2 = hash2(nombre);
        int i  = h1;

        while (nombreParticipante[i] != null && !nombreParticipante[i].equals(nombre)) {
            i = (i + h2) % TABLE_SIZE;
        }
        return i;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        for (int ronda = 1; ronda <= n; ronda++) {
            String nombre = sc.next();
            int puntos    = sc.nextInt();

            int index = getIndex(nombre);

            if (nombreParticipante[index] == null) {
                nombreParticipante[index] = nombre;
                puntaje[index] = 0;
                maxPuntaje[index] = 0;
                rondaDelMax[index] = 0;
            }

            puntaje[index] += puntos;

            if (puntaje[index] > maxPuntaje[index]) {
                maxPuntaje[index]   = puntaje[index];
                rondaDelMax[index] = ronda; 
            }
        }

        String ganador = null;
        int    maxFinal = Integer.MIN_VALUE;
        int    mejorRonda = Integer.MAX_VALUE;
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (nombreParticipante[i] != null) {
                if (puntaje[i] > maxFinal) {
                    maxFinal = puntaje[i];
                    ganador = nombreParticipante[i];
                    mejorRonda = rondaDelMax[i];
                } else if (puntaje[i] == maxFinal && rondaDelMax[i] < mejorRonda) {
                    mejorRonda = rondaDelMax[i];
                    ganador = nombreParticipante[i];
                }
            }
        }

        System.out.println(ganador);
        sc.close();
    }
}
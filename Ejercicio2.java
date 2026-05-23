import java.util.Scanner;

public class Ejercicio2 {

    static final int TABLE_SIZE = 131071; // primo grande

    static class Participante {
        String nombre;
        int puntaje;
        int maxPuntaje;
        int rondaDelMax;

        Participante(String nombre) {
            this.nombre     = nombre;
            this.puntaje    = 0;
            this.maxPuntaje = 0;
            this.rondaDelMax = 0;
        }
    }

    static Participante[] tabla = new Participante[TABLE_SIZE];

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
        return (int) (h + 1); //para que nunca sea 0
    }

    static int getIndice(String nombre) {
        int h1 = hash1(nombre);
        int h2 = hash2(nombre);
        int i  = h1;

        while (tabla[i] != null && !tabla[i].nombre.equals(nombre)) {
            i = (i + h2) % TABLE_SIZE;
        }
        return i;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        for (int ronda = 1; ronda <= n; ronda++) {
            String nombre = sc.next();
            int puntos = sc.nextInt();

            int index = getIndice(nombre);

            if (tabla[index] == null) {
                tabla[index] = new Participante(nombre);
            }

            tabla[index].puntaje += puntos;

            if (tabla[index].puntaje > tabla[index].maxPuntaje) {
                tabla[index].maxPuntaje  = tabla[index].puntaje;
                tabla[index].rondaDelMax = ronda;
            }
        }

        String ganador = null;
        int maxFinal = Integer.MIN_VALUE;
        int mejorRonda = Integer.MAX_VALUE;

        for (int i = 0; i < TABLE_SIZE; i++) {
            if (tabla[i] != null) {
                if (tabla[i].puntaje > maxFinal) {
                    maxFinal = tabla[i].puntaje;
                    ganador = tabla[i].nombre;
                    mejorRonda = tabla[i].rondaDelMax;
                } else if (tabla[i].puntaje == maxFinal && tabla[i].rondaDelMax < mejorRonda) {
                    mejorRonda = tabla[i].rondaDelMax;
                    ganador = tabla[i].nombre;
                }
            }
        }

        System.out.println(ganador);
        sc.close();
    }
}
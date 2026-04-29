import java.util.Scanner;

public class Ejercicio2 {  
    import java.util.Scanner;

public class Ejercicio2 {

    // Tamaño de la tabla (primo grande para menos colisiones)
    static final int TABLE_SIZE = 131071; // primo

    // Cada entrada de la tabla
    static String[] keys    = new String[TABLE_SIZE];
    static int[]    score   = new int[TABLE_SIZE];
    static int[]    maxScore= new int[TABLE_SIZE];
    static int[]    firstRound = new int[TABLE_SIZE]; // ronda en que alcanzó maxScore

    // Hash 1: función principal
    static int hash1(String name) {
        long h = 0;
        for (char c : name.toCharArray()) {
            h = (h * 31 + c) % TABLE_SIZE;
        }
        return (int) h;
    }

    // Hash 2: función secundaria para el paso en caso de colisión
    // Debe ser distinta a hash1 y nunca devolver 0
    static int hash2(String name) {
        long h = 0;
        for (char c : name.toCharArray()) {
            h = (h * 37 + c) % (TABLE_SIZE - 1);
        }
        return (int) (h + 1); // +1 para que nunca sea 0
    }

    // Buscar o insertar un nombre, devuelve el índice en la tabla
    static int getIndex(String name) {
        int h1 = hash1(name);
        int h2 = hash2(name);
        int i  = h1;

        while (keys[i] != null && !keys[i].equals(name)) {
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

            int idx = getIndex(nombre);

            // Si es nuevo participante, inicializarlo
            if (keys[idx] == null) {
                keys[idx]       = nombre;
                score[idx]      = 0;
                maxScore[idx]   = 0;
                firstRound[idx] = 0;
            }

            // Actualizar puntaje
            score[idx] += puntos;

            // ¿Superó su propio máximo histórico?
            if (score[idx] > maxScore[idx]) {
                maxScore[idx]   = score[idx];
                firstRound[idx] = ronda; // actualizar ronda
            }
        }

        // Buscar el puntaje máximo FINAL entre todos
        String ganador     = null;
        int maxFinal = Integer.MIN_VALUE;
        int    mejorRonda  = Integer.MAX_VALUE;
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (keys[i] != null && score[i] >= maxFinal) {
                if (keys[i] != null && score[i] > maxFinal) {
                maxFinal = score[i];
                } else {
                    if (firstRound[i] < mejorRonda) {
                    mejorRonda = firstRound[i];
                    ganador    = keys[i];
                    } 
                }    
            }
        }

        // Entre los que tienen ese puntaje final, buscar
        // quien alcanzó ese puntaje máximo final primero

        System.out.println(ganador);
        sc.close();
    }
}
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // TODO 
    }
}
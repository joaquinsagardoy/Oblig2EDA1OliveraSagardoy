import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Ejercicio5 {

    static ArrayList<ArrayList<Integer>> grafo;

    //-1 = sin colorear, 0 = equipo a, 1 = Equipo b
    static int[] color;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int V = sc.nextInt(); 
        int A = sc.nextInt(); 

        grafo = new ArrayList<>();
        for (int i = 0; i <= V; i++) {
            grafo.add(new ArrayList<>());
        }

        for (int i = 0; i < A; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            grafo.get(u).add(v); 
            grafo.get(v).add(u); 
        }

        color = new int[V + 1];
        for (int i = 0; i <= V; i++) {
            color[i] = -1;
        }

        boolean esBipartito = true;
        for (int i = 1; i <= V; i++) {
            if (color[i] == -1) {
                if (!bfs(i)) {
                    esBipartito = false;
                    break;
                }
            }
        }

        if (esBipartito) {
            System.out.println("SI");
        } else {
            System.out.println("NO");
        }
    }

    static boolean bfs(int inicio) {
        Queue<Integer> cola = new LinkedList<>();
        cola.add(inicio);
        color[inicio] = 0; // Equipo A

        while (!esVacia(cola)) {
            int u = desencolar(cola); 

            for (int v : grafo.get(u)) { 
                if (color[v] == -1) {
                    if (color[u] == 0) {
                         color[v] = 1;
                    } else {
                        color[v] = 0;
                    }
                    cola.add(v);
                } else if (color[v] == color[u]) {
                    return false;
                }
            }
        }

        return true;
    }

    static boolean esVacia(Queue<Integer> cola) {
        return cola.isEmpty();
    }

    static int desencolar(Queue<Integer> cola) {
        return cola.poll();
    }
}
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Ejercicio5 {

    // Lista de adyacencia: grafo[i] tiene los vecinos (incompatibles) del estudiante i
    static ArrayList<ArrayList<Integer>> grafo;

    // Array de colores: -1 = sin colorear, 0 = Equipo A, 1 = Equipo B
    static int[] color;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int V = sc.nextInt(); // cantidad de estudiantes (vertices)
        int A = sc.nextInt(); // cantidad de incompatibilidades (aristas)

        // Inicializar grafo con V nodos (1-indexed: usamos indices 1..V)
        grafo = new ArrayList<>();
        for (int i = 0; i <= V; i++) {
            grafo.add(new ArrayList<>());
        }

        // Leer las A incompatibilidades
        for (int i = 0; i < A; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            grafo.get(u).add(v); // u incompatible con v
            grafo.get(v).add(u); // v incompatible con u (no dirigido)
        }

        // Inicializar colores: todos sin colorear
        color = new int[V + 1];
        for (int i = 0; i <= V; i++) {
            color[i] = -1;
        }

        // Recorrer todos los nodos (puede haber componentes desconectadas)
        boolean esBipartito = true;
        for (int i = 1; i <= V; i++) {
            if (color[i] == -1) {
                // Nodo sin colorear: hacemos BFS desde aqui
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

    // BFS desde nodoInicio intentando colorear con 2 colores
    // Retorna true si la componente es bipartita, false si no
    static boolean bfs(int nodoInicio) {
        Queue<Integer> cola = new LinkedList<>();
        cola.add(nodoInicio);
        color[nodoInicio] = 0; // Equipo A

        while (!esVacia(cola)) {
            int u = desencolar(cola); // sacar el siguiente nodo a procesar

            for (int v : grafo.get(u)) { // recorrer incompatibles de u
                if (color[v] == -1) {
                    // Sin colorear: asignar color opuesto a u
                    color[v] = 1 - color[u];
                    cola.add(v);
                } else if (color[v] == color[u]) {
                    // Mismo color que u: conflicto, no es bipartito
                    return false;
                }
                // Si tiene color diferente: perfecto, no hay conflicto
            }
        }

        return true; // componente coloreada sin conflictos
    }

    static boolean esVacia(Queue<Integer> cola) {
        return cola.isEmpty();
    }

    static int desencolar(Queue<Integer> cola) {
        return cola.poll();
    }
}
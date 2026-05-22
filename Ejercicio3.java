import java.util.Scanner;
 
public class Ejercicio3 {
 
    static class Paciente {
        int p;      // identificador
        int t;      // hora de llegada
        int u;      // urgencia
        int orden;  // orden de ingreso al sistema
 
        Paciente(int p, int t, int u, int orden) {
            this.p     = p;
            this.t     = t;
            this.u     = u;
            this.orden = orden;
        }
    }
 
    static Paciente[] heap;
    static int heapSize = 0;
 
    // Devuelve true si el paciente en i tiene mayor prioridad que el de j
    static boolean mayorPrioridad(int i, int j) {
        Paciente a = heap[i];
        Paciente b = heap[j];
        if (a.u != b.u) return a.u > b.u;           // regla 1: mayor urgencia
        if (a.t != b.t) return a.t < b.t;           // regla 2: menor hora de llegada
        return a.orden < b.orden;                    // regla 3: orden de ingreso
    }
 
    static void swap(int i, int j) {
        Paciente tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }
 
    static boolean esVacia() {
        return heapSize == 0;
    }
 
    // Sube el elemento en la posicion i hasta su lugar correcto
    static void flotar(int i) {
        while (i > 0) {
            int padre = (i - 1) / 2;
            if (mayorPrioridad(i, padre)) {
                swap(i, padre);
                i = padre;
            } else {
                break;
            }
        }
    }
 
    // Baja el elemento en la posicion i hasta su lugar correcto
    static void hundir(int i) {
        while (true) {
            int hijoIzq = 2 * i + 1;
            int hijoDer = 2 * i + 2;
            int mayor   = i;
            if (hijoIzq < heapSize && mayorPrioridad(hijoIzq, mayor)) mayor = hijoIzq;
            if (hijoDer < heapSize && mayorPrioridad(hijoDer, mayor)) mayor = hijoDer;
            if (mayor == i) break;
            swap(i, mayor);
            i = mayor;
        }
    }
 
    static void insertar(int p, int t, int u, int orden) {
        heap[heapSize] = new Paciente(p, t, u, orden);
        flotar(heapSize);
        heapSize++;
    }
 
    // Elimina y devuelve el identificador del paciente de mayor prioridad
    static int desencolar() {
        int resultado = heap[0].p;
        heapSize--;
        heap[0] = heap[heapSize];
        if (!esVacia()) hundir(0);
        return resultado;
    }
 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
 
        heap = new Paciente[n];
 
        for (int i = 0; i < n; i++) {
            int p = sc.nextInt();
            int t = Integer.parseInt(sc.next());
            int u = sc.nextInt();
            insertar(p, t, u, i);
        }
 
        for (int i = 0; i < n; i++) {
            System.out.println(desencolar());
        }
 
        sc.close();
    }
}
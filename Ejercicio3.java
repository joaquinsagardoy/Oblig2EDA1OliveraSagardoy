import java.util.Scanner;

public class Ejercicio3 {

    static int[] heapP;
    static int[] heapT;
    static int[] heapU;
    static int[] heapOrder;
    static int heapSize = 0;

    // Devuelve true si i tiene mayor prioridad que j
    static boolean mayorPrioridad(int i, int j) {
        if (heapU[i] != heapU[j]) return heapU[i] > heapU[j];
        if (heapT[i] != heapT[j]) return heapT[i] < heapT[j];
        return heapOrder[i] < heapOrder[j];
    }

    static void swap(int i, int j) {
        int tmp;
        tmp = heapP[i]; heapP[i] = heapP[j]; heapP[j] = tmp;
        tmp = heapT[i]; heapT[i] = heapT[j]; heapT[j] = tmp;
        tmp = heapU[i]; heapU[i] = heapU[j]; heapU[j] = tmp;
        tmp = heapOrder[i]; heapOrder[i] = heapOrder[j]; heapOrder[j] = tmp;
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
        int i = heapSize++;
        heapP[i]     = p;
        heapT[i]     = t;
        heapU[i]     = u;
        heapOrder[i] = orden;
        flotar(i);
    }

    // Devuelve el elemento de mayor prioridad sin eliminarlo
    static int tope() {
        return heapP[0];
    }

    // Elimina y devuelve el elemento de mayor prioridad
    static int desencolar() {
        int resultado = heapP[0];
        int ultimo    = --heapSize;
        heapP[0]     = heapP[ultimo];
        heapT[0]     = heapT[ultimo];
        heapU[0]     = heapU[ultimo];
        heapOrder[0] = heapOrder[ultimo];
        if (!esVacia()) hundir(0);
        return resultado;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        heapP     = new int[n];
        heapT     = new int[n];
        heapU     = new int[n];
        heapOrder = new int[n];

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

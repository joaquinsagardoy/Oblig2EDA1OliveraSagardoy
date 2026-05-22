import java.util.*;

public class Ejercicio4 {

    static class Entrada {
        String nombre;
        int    indice;

        Entrada(String nombre, int indice) {
            this.nombre = nombre;
            this.indice = indice;
        }
    }

    static Entrada[] tabla;
    static int       tamano;
    static int       cantidad;

    static void iniciarTabla(int capacidad) {
        tamano   = capacidad * 2;
        tabla    = new Entrada[tamano];
        cantidad = 0;
    }

    static int hash(String nombre) {
        long h = 0;
        for (int i = 0; i < nombre.length(); i++) {
            h = (h * 31 + nombre.charAt(i)) % tamano;
        }
        return (int) h;
    }

    static int obtenerOCrear(String nombre) {
        int pos = hash(nombre);
        while (tabla[pos] != null) {
            if (tabla[pos].nombre.equals(nombre)) {
                return tabla[pos].indice;
            }
            pos = (pos + 1) % tamano;
        }
        tabla[pos] = new Entrada(nombre, cantidad);
        return cantidad++;
    }

    static int dijkstra(List<int[]>[] adyacentes, int origen, int destino, int cantPlanetas) {
        int[]     costos    = new int[cantPlanetas];
        boolean[] visitados = new boolean[cantPlanetas];
        Arrays.fill(costos, Integer.MAX_VALUE);
        costos[origen] = 0;

        PriorityQueue<int[]> colaPrioridad = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        colaPrioridad.offer(new int[]{0, origen});

        while (!colaPrioridad.isEmpty()) {
            int[] actual      = colaPrioridad.poll();
            int   costoActual = actual[0];
            int   v           = actual[1];

            if (visitados[v]) continue;
            visitados[v] = true;

            if (v == destino) break;

            for (int[] vecino : adyacentes[v]) {
                int w          = vecino[0];
                int nuevoCosto = costoActual + vecino[1];
                if (!visitados[w] && costos[w] > nuevoCosto) {
                    costos[w] = nuevoCosto;
                    colaPrioridad.offer(new int[]{nuevoCosto, w});
                }
            }
        }

        return costos[destino] == Integer.MAX_VALUE ? -1 : costos[destino];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int cantPlanetas = sc.nextInt();
        int cantPortales = sc.nextInt();

        iniciarTabla(cantPlanetas + 1);

        List<int[]>[] adyacentes = new ArrayList[cantPlanetas];
        for (int i = 0; i < cantPlanetas; i++) {
            adyacentes[i] = new ArrayList<>();
        }

        for (int i = 0; i < cantPortales; i++) {
            String planetaA = sc.next();
            String planetaB = sc.next();
            int    costo    = sc.nextInt();

            int u = obtenerOCrear(planetaA);
            int v = obtenerOCrear(planetaB);

            adyacentes[u].add(new int[]{v, costo});
            adyacentes[v].add(new int[]{u, costo});
        }

        String nombreOrigen  = sc.next();
        String nombreDestino = sc.next();

        int origen  = obtenerOCrear(nombreOrigen);
        int destino = obtenerOCrear(nombreDestino);

        System.out.println(dijkstra(adyacentes, origen, destino, cantPlanetas));
    }
}
package aed;

import java.util.ArrayList;

// InvRep: Respeta el invariante de representación de un DiccionarioDigital
// 1. Es un árbol: los nodos tienen un solo padre salvo la raíz la cual no tiene
// 2. No hay nodos inútiles: si no tienen significado (valor), el nodo tiene hijos
public class DiccionarioDigital<T> implements Diccionario<String,T> {
    private final static int VALORES_ASCII = 256;
    private Nodo _raiz;
    private int _tamanio;

    private class Nodo {
        private ArrayList<Nodo> _siguientes;
        private T _valor;

        public Nodo() {
            this._siguientes = new ArrayList<Nodo>();
            this._valor = null;

            for(int i = 0; i < VALORES_ASCII; i++) {
                this._siguientes.add(null);
            }
        }

        public int cantidadHijos() {
            int cantHijos = 0;
            int i = 0;

            while(i < this._siguientes.size()) {
                Nodo n = this._siguientes.get(i);
                if(n != null) {
                    cantHijos += 1;
                }

                i += 1;
            }

            return cantHijos;
        } // O(1) (Vector de longitud acotada)
    }

    public DiccionarioDigital() {
        this._raiz = new Nodo();
        this._tamanio = 0;
    }

    public boolean esta(String clave) {
        Nodo n = this._raiz;
        int i = 0;

        while(n != null && i < clave.length()) {
            char letra = clave.charAt(clave.charAt(i));
            n = n._siguientes.get((int) letra);
            i += 1;
        } // TOTAL CICLO: O(|clave|)

        return n != null && n._valor != null;
    } // TOTAL: O(|clave|)

    public void definir(String clave, T valor) {
        Nodo n = this._raiz;
        int i = 0;

        while(i < clave.length()) {
            char letra = clave.charAt(i);
            Nodo nuevoNodo = n._siguientes.get((int) letra);

            if(nuevoNodo == null) {
                nuevoNodo = new Nodo();
                n._siguientes.set((int) letra, nuevoNodo);
            }

            i += 1;
            n = n._siguientes.get((int) letra);
        } // TOTAL CICLO: O(|clave|)

        this._tamanio += n._valor == null ? 1 : 0;
        n._valor = valor;
    } // TOTAL: O(|clave|)

    public T obtener(String clave) {
        Nodo n = this._raiz;
        int i = 0;

        while(n != null && i < clave.length()) {
            char letra = clave.charAt(i);
            n = n._siguientes.get((int) letra);

            i += 1;
        } // TOTAL CICLO: O(|clave|)

        return n != null && n._valor != null ? n._valor : null;
    } // TOTAL: O(|clave|)

    public void borrar(String clave) {
        Nodo n = this._raiz;
        Nodo nodoUtil = this._raiz;
        int indiceUtil = 0;
        int i = 0;

        while(i < clave.length()) {
            char letra = clave.charAt(i);
            n = n._siguientes.get((int) letra);
            if((n._valor != null || n.cantidadHijos() > 1) && i != clave.length() - 1) {
                nodoUtil = n;
                indiceUtil = i;
            } 

            i += 1;
        } // TOTAL CICLO: O(|clave|)

        n._valor = null;
        if(n.cantidadHijos() == 0) {
            char letraDescarte = clave.charAt(indiceUtil + 1);
            nodoUtil._siguientes.set((int) letraDescarte, null);
        }

        this._tamanio -= 1;
    } // TOTAL: O(|clave|)

    public int tamanio() {
        return this._tamanio;
    }

    public String[] obtenerClaves() {
        String[] claves = new String[this._tamanio];

        Indice indice = new Indice();
        this._acumularClaves(this._raiz, "", claves, indice); // O(SUM_|clave|)

        return claves;
    }   // TOTAL: O(SUM_|clave|) (sumatoria de la longitud de las ditintas claves del diccionario)

    private void _acumularClaves(Nodo n, String prefijo, String[] claves, Indice indice) {
        if(n != null) {
            if(n._valor != null) {
                claves[indice.valor] = prefijo;
                indice.valor += 1;
            }

            for (int i = 0; i < VALORES_ASCII; i += 1) {
                Nodo nodoActual = n._siguientes.get(i);

                if(nodoActual != null) {
                    this._acumularClaves(nodoActual, prefijo + (char) i, claves, indice);
                }
            }
        }
    } 

    // Necesitamos guardar el indice en esta clase "wrapper" para poder pasar la referncia del indice en un objeto entre cada paso recursivo
    // si usasemos un int primitivo como indice estariamos pasando una copia del valor del indice
    // y los cambios al indice no se reflejarian correctamente
    private static class Indice {
        int valor = 0;
    }
}

package aed;

import java.util.ArrayList;

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
        }
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
        }

        return n != null && n._valor != null;
    }

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
        }

        this._tamanio += n._valor == null ? 1 : 0;
        n._valor = valor;
    }

    public T obtener(String clave) {
        Nodo n = this._raiz;
        int i = 0;

        while(n != null && i < clave.length()) {
            char letra = clave.charAt(i);
            n = n._siguientes.get((int) letra);

            i += 1;
        }

        return n != null && n._valor != null ? n._valor : null;
    }

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
        }

        n._valor = null;
        if(n.cantidadHijos() == 0) {
            char letraDescarte = clave.charAt(indiceUtil + 1);
            nodoUtil._siguientes.set((int) letraDescarte, null);
        }

        this._tamanio -= 1;
    }

    public int tamanio() {
        return this._tamanio;
    }

    public ArrayList<String> obtenerClaves() {
        ArrayList<String> claves = new ArrayList<String>();

        this._acumularClaves(this._raiz, "", claves);

        return claves;
    }

    private void _acumularClaves(Nodo n, String prefijo, ArrayList<String> claves) {
        if(n != null) {
            if(n._valor != null) {
                claves.add(prefijo);
            }

            for (int i = 0; i < VALORES_ASCII; i += 1) {
                Nodo nodoActual = n._siguientes.get(i);

                if(nodoActual != null) {
                    this._acumularClaves(nodoActual, prefijo + (char) i, claves);
                }
            }
        }
    }
}

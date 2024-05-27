package aed;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    // Agregar atributos privados del Conjunto
    private Nodo raiz;
    private int elementos;

    private class Nodo {
        // Agregar atributos privados del Nodo

        // Crear Constructor del nodo
        Nodo padre;
        Nodo izq;
        Nodo der;
        private T valor;

        public Nodo() {
            this.padre = null;
            this.izq = null;
            this.der = null;
            this.valor = null;
        }

        public int cantidadHijos() {
            if(this.izq != null && this.der != null) {
                return 2;
            } else if(this.izq != null || this.der != null){ 
                return 1;
            }
            return 0;
        }
    }

    public ABB() {
        this.raiz = null;
        this.elementos = 0;
    }

    public int cardinal() {
        return this.elementos;
    }

    public T minimo(){
        Nodo minimoNodo = this.minimo(this.raiz);

        return minimoNodo.valor;
    }

    private Nodo minimo(Nodo n){
        if(n.izq == null) {
            return n;
        }

        return this.minimo(n.izq);
    }

    public T maximo(){
        Nodo maximoNodo = this.maximo(this.raiz);

        return maximoNodo.valor;
    }

    private Nodo maximo(Nodo n){
        if (n.der == null) {
            return n;
        }

        return maximo(n.der);
    }

    public void insertar(T elem){
        if(this.raiz == null) {
            this.raiz = new Nodo();
            this.raiz.valor = elem;
            this.elementos += 1;
        } else if(!this.pertenece(elem)) {
            this.insertar(this.raiz, elem);
        }
    }

    public void insertar(Nodo n, T elem) {
        int resto = elem.compareTo(n.valor);
        if(resto != 0) {
            if((n.izq == null && resto < 0)||(n.der == null && resto > 0)) {
                Nodo nuevoNodo = new Nodo();
                nuevoNodo.padre = n;
                nuevoNodo.valor = elem;
                this.elementos += 1;
                if(n.izq == null && resto < 0) {
                    n.izq = nuevoNodo;
                } else {
                    n.der = nuevoNodo;
                }
            } else if(resto < 0 && n.izq != null) {
                insertar(n.izq, elem);
            } else if(resto > 0 && n.der != null)  {
                insertar(n.der, elem);
            }
        }
    }

    public boolean pertenece(T elem){
        return pertenece(this.raiz, elem);
    }

    public boolean pertenece(Nodo n, T elem) {
        if(n == null) {
            return false;
        }

        int resto = elem.compareTo(n.valor);
        Nodo nuevoNodo = resto > 0 ? n.der : n.izq;

        return resto == 0 || this.pertenece(nuevoNodo, elem);
    }

    public void eliminar(T elem){
        if(this.pertenece(elem)) {
            eliminar(this.raiz, elem);
        }
    }

    private void eliminar(Nodo n, T elem){
        int resto = elem.compareTo(n.valor);
        if(resto == 0) {
            eliminarNodo(n);
        } else if(n != null) {
            n = resto > 0 ? n.der : n.izq;
            eliminar(n, elem);
        }
    }

    private void eliminarNodo(Nodo n) {
        if(n.padre == null && this.elementos == 0) {
            this.raiz = null;
        } else {
            int cantidadDeHijos = n.cantidadHijos();

            if(cantidadDeHijos == 0) {
                int restoHijo = n.valor.compareTo(n.padre.valor);
                if(restoHijo > 0) {
                    n.padre.der = null;
                } else {
                    n.padre.izq = null;
                }
                n = null;
            } else if(cantidadDeHijos == 1) {
                Nodo padre = n.padre;
                if(n.izq != null) {
                    n = n.izq;
                    padre.izq = n;
                } else {
                    n = n.der;
                    padre.der = n;
                    // n.padre = padre;
                }
            } else {
                Nodo minimoDerecha = this.minimo(n.der);
                Nodo minimoPadre = minimoDerecha.padre;
                n.valor = minimoDerecha.valor;

                if(minimoDerecha.der != null && minimoDerecha.padre != null) {
                    minimoDerecha.der.padre = minimoPadre;
                }
                minimoPadre.izq = minimoDerecha.der != null ? minimoDerecha.der : null;
                minimoDerecha = minimoDerecha.der;
            }
        }

        this.elementos -= 1;
    }

    public Nodo sucesor(Nodo inicio){
        if(inicio.der != null) {
            return minimo(inicio.der);
        }

        if(inicio.valor.compareTo(this.maximo(this.raiz).valor) == 0) {
            return inicio;
        }
        /* 
        if(inicio.padre == null) {
            return inicio;
        }
        if(inicio.padre.valor.compareTo(inicio.valor) > 0) {
            return inicio.padre;
        }
        */

        Nodo ret = subir(inicio, inicio);
        return ret;
    }

    private Nodo subir(Nodo inicio, Nodo origen) {
        if(inicio.padre == null) {
            return origen;
        }
        Nodo padre = inicio.padre;
        int restoPadreInicio = padre.valor.compareTo(inicio.valor);
        if(restoPadreInicio < 0) {
            return subir(padre, origen);
        } else {
            return padre;
        }

        /* 
        if(inicio.padre == null) {
            int restoInicio = inicio.valor.compareTo(origen.valor);
            if(restoInicio < 0) {
                return inicio.der;
            } else if(restoInicio > 0) {
                return null;
            }

            return inicio;
        }

        Nodo padre = inicio.padre;
        int restoPadre = padre.valor.compareTo(inicio.valor);
        if(restoPadre < 0) {
            return subir(padre, origen);
        } else if(restoPadre != 0) {
            return padre;
        }
        
        return null;
        */

    }

    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
        Iterador<T> iterador = this.iterador();
        T siguiente;

        stringBuffer.append("{");
        if(iterador.haySiguiente()) {
            siguiente = iterador.siguiente();
            stringBuffer.append(siguiente);
        }

        while(iterador.haySiguiente()) {
            siguiente = iterador.siguiente();

            stringBuffer.append("," + siguiente);
        }
        stringBuffer.append("}");

        return stringBuffer.toString();
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;
        private int n;

        public ABB_Iterador(){
            this._actual = minimo(raiz);
            n = 0;
        }

        public boolean haySiguiente() {            
            // return this._actual != null;
            return n < elementos;
        }
    
        public T siguiente() {
            T siguienteValor = this._actual.valor;
            Nodo sucesor = sucesor(this._actual);
            // this._actual = sucesor.valor.compareTo(siguienteValor) != 0 ? sucesor : null;
            this._actual = sucesor;

            n+=1;

            return siguienteValor;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}

package aed;

public class ListaEnlazada<T> implements Secuencia<T> {
    // Completar atributos privados
    private int size;
    private Nodo first, last;

    private class Nodo {
        Nodo next;
        Nodo previous;
        T value;

        Nodo(T value) {
            this.value = value;
        }
    }

    public ListaEnlazada() {
        this.size = 0;
    }

    public int longitud() {
        return this.size;
    }

    public void agregarAtras(T elem) {
        Nodo n = new Nodo(elem);

        if (this.size == 0) {
            this.first = n;
            this.last = n;
        } else {
            this.last.next = n;
            n.previous = this.last;
            this.last = n;
        }

        this.size += 1;
    }

    public void agregarAdelante(T elem) {
        Nodo n = new Nodo(elem);

        if (this.size == 0) {
            this.first = n;
            this.last = n;
        } else {
            this.first.previous = n;
            n.next = first;
            this.first = n;
        }

        this.size += 1;
    }

    public T obtener(int i) {
        Nodo n = this.first;

        while(i != 0) {
            n = n.next;
            i -= 1;
        }

        return n.value;
    }

    public void eliminar(int i) {
        Nodo current = this.first;

        while(i != 0) {
            current = current.next;

            i -= 1;
        }

        if(current.previous != null && current.next != null) {
            Nodo previous = current.previous;
            Nodo next = current.next;

            previous.next = next;
            next.previous = previous;
        } else if (current.previous != null) {
            Nodo previous = current.previous;

            previous.next = null;
            this.last = previous;
        } else if (current.next != null) {
            Nodo next = current.next;

            next.previous = null;
            this.first = next;
        }

        current = null;
        this.size -= 1;
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo n = this.first;

        while(indice != 0) {
            n = n.next;
            indice -= 1;
        }

        n.value = elem;
    }

    public ListaEnlazada<T> copiar() {
        return new ListaEnlazada<T>(this);
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        Nodo n = lista.first;

        while(n != null) {
            this.agregarAtras(n.value);
            n = n.next;
        }
    }
    
    @Override
    public String toString() {
        Nodo n = this.first;
        StringBuffer sBuffer = new StringBuffer();

        while(n != null) {
            if (n == this.first) {
                sBuffer.append("[" + n.value);
            } else {
                sBuffer.append(", " + n.value);
            }

            n = n.next;
        }

        sBuffer.append("]");
        return sBuffer.toString();
    }

    private class ListaIterador implements Iterador<T> {
    	// Completar atributos privados
        private Nodo current;
        private Nodo previous;

        public ListaIterador() {
            this.current = first;
            this.previous = null;
        }

        public boolean haySiguiente() {
            return this.current != null;
        }
        
        public boolean hayAnterior() {
            return this.previous != null;
        }

        public T siguiente() {
            T currentValue = this.current.value;

            this.previous = this.current;
            this.current = this.current.next;

            return currentValue;
        }
        

        public T anterior() {
            T currentValue = this.previous.value;

            this.current = this.previous;
            this.previous = this.current.previous;
            
            return currentValue;
        }
    }

    public Iterador<T> iterador() {
        return new ListaIterador();
    }
}

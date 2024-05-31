package aed;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    // Agregar atributos privados del Conjunto
    private Nodo root;

    private class Nodo {
        // Agregar atributos privados del Nodo

        // Crear Constructor del nodo
        Nodo father;
        Nodo left;
        Nodo right;
        private T value;

        public Nodo() {
            this.father = null;
            this.left = null;
            this.right = null;
            this.value = null;
        }

        public boolean hasTwoChildren() {
            return this.left != null && this.right != null;
        }

        public int compareValue(T value) {
            return this.value.compareTo(value);
        }
    }

    public ABB() {
        this.root = null;
    }

    private boolean isEmpty() {
        return this.root == null;
    }

    public int cardinal() {
        return this.cardinal(this.root);
    }

    private int cardinal(Nodo n) {
        if(n == null) {
            return 0;
        }

        return 1 + this.cardinal(n.left) + this.cardinal(n.right);
    }

    public T minimo(){
        return this.minimumNode(this.root).value;
    }

    private Nodo minimumNode(Nodo root) {
        Nodo n = root;
        while(n.left != null) {
            n = n.left;
        }

        return n;
    }

    public T maximo(){
        return this.maximumNode(this.root).value;
    }

    private Nodo maximumNode(Nodo n) {
        while(n.right != null) {
            return maximumNode(n.right);
        }

        return n;
    }

    public void insertar(T elem){
        if(!this.pertenece(elem)) {
            if(this.isEmpty()) {
                Nodo n = new Nodo();
                n.value = elem;

                this.root = n;
            } else {
                Nodo current = this.root;
                Nodo father = null;

                while(current != null) {
                    boolean isCurrentBigger = current.compareValue(elem) > 0;
                    father = current;

                    if(isCurrentBigger) {
                        current = current.left;
                    } else {
                        current = current.right;
                    }
                }

                Nodo newNode = new Nodo();
                newNode.value = elem;
                newNode.father = father;
                boolean isFatherBigger = father.compareValue(elem) > 0;
                if(isFatherBigger) {
                   father.left = newNode;
                } else {
                   father.right = newNode;
                }
            }

        }
    }

    public boolean pertenece(T elem){
        Nodo n = findNode(elem);

        return n != null;
    }
    
    private Nodo findNode(T elem) {
        Nodo n = this.root;

        while(n != null) {
            int nodeDiff = n.compareValue(elem);
            if(nodeDiff != 0) {
                n = nodeDiff > 0 ? n.left : n.right;
            } else {
                return n;
            }
        }

        return n;
    }

    public void eliminar(T elem){
        Nodo n = findNode(elem);

        if(n != null) {
            this.deleteNode(n);
        }
    }

    private void deleteNode(Nodo n) {
        boolean hasTwoChildren = n.hasTwoChildren();

        if(hasTwoChildren) {
            Nodo minRight = this.minimumNode(n.right);
            T newValue = minRight.value;
            this.deleteNode(minRight);

            n.value = newValue;
        } else {
            this.replaceNode(n, n.left != null ? n.left : n.right);
        }    
    }

    private void replaceNode(Nodo oldNode, Nodo newNode) {
        if(oldNode.father == null) {
            this.root = newNode;
        } else if (oldNode == oldNode.father.left) {
            oldNode.father.left = newNode;
        } else {
            oldNode.father.right = newNode;
        }

        if(newNode != null) {
            newNode.father = oldNode.father;
        }
    }

    public Nodo getNext(Nodo start) {
        if(start == null) {
            return null;
        }

        if(start.right != null) {
            return minimumNode(start.right);
        }

        Nodo father = start.father;
        while(father != null && start == father.right)  {
            start = father;
            father = start.father;
        }

        return father;
    }

    public String toString(){
        String ret = "{";
        Iterador<T> iterator = this.iterador();

        if(iterator.haySiguiente()) {
            ret += iterator.siguiente();
        }

        while(iterator.haySiguiente()) {
            ret += "," + iterator.siguiente();
        }

        ret += "}";


        return ret;
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo current;

        public ABB_Iterador(){
            this.current = minimumNode(root);
        }

        public boolean haySiguiente() {        
            return this.current != null;
        }
    
        public T siguiente() {
            T nextValue = this.current.value;
            Nodo nextNode = getNext(this.current);
            this.current = nextNode;


            return nextValue;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }
}

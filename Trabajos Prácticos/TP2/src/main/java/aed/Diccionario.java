package aed;

interface Diccionario<K,T> {
    public boolean esta(K key);

    public void definir(K key, T value);

    public T obtener(K key);

    public void borrar(K key);

    public int tamanio();

    // Este metodo esta fuera del modulo basico de TAD Diccionario
    // pero nos va a ser muy util
    public K[] obtenerClaves();
}

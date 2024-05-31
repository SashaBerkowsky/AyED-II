package aed;

class ArregloRedimensionableDeRecordatorios implements SecuenciaDeRecordatorios {

    private Recordatorio[] recordatorios; 

    public ArregloRedimensionableDeRecordatorios() {
        this.recordatorios = new Recordatorio[0];
    }

    public ArregloRedimensionableDeRecordatorios(ArregloRedimensionableDeRecordatorios vector) {
        this.recordatorios = new Recordatorio[0];
        for(int i = 0; i < vector.longitud(); i++){
            Recordatorio r = vector.obtener(i);
            this.agregarAtras(new Recordatorio(r.mensaje(), r.fecha(), r.horario()));
        }
    }

    public int longitud() {
        return this.recordatorios.length;
    }

    public void agregarAtras(Recordatorio i) {
        int longitudAnterior = this.longitud();
        Recordatorio[] nuevoRecordatorios = new Recordatorio[longitudAnterior + 1]; 
        for (int idx = 0; idx < longitudAnterior; idx++) {
            nuevoRecordatorios[idx] = this.recordatorios[idx];
        }
        nuevoRecordatorios[longitudAnterior] = i;

        this.recordatorios = nuevoRecordatorios;
    }

    public Recordatorio obtener(int i) {
        return this.recordatorios[i];
    }

    public void quitarAtras() {
        int nuevaLongitud = this.longitud() - 1;
        Recordatorio[] nuevoRecordatorios = new Recordatorio[nuevaLongitud]; 
        for (int idx = 0; idx < nuevaLongitud; idx++) {
            nuevoRecordatorios[idx] = this.recordatorios[idx];
        }

        this.recordatorios = nuevoRecordatorios;
    }

    public void modificarPosicion(int indice, Recordatorio valor) {
        this.recordatorios[indice] = valor;
    }

    public ArregloRedimensionableDeRecordatorios copiar() {
        return new ArregloRedimensionableDeRecordatorios(this);
    }

}

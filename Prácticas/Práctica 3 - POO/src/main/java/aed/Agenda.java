package aed;

public class Agenda {
    private ArregloRedimensionableDeRecordatorios recordatorios;
    private Fecha fechaActual;

    public Agenda(Fecha fechaActual) {
        this.fechaActual = fechaActual;
        this.recordatorios = new ArregloRedimensionableDeRecordatorios();
    }

    public void agregarRecordatorio(Recordatorio recordatorio) {
        this.recordatorios.agregarAtras(recordatorio);
    }

    @Override
    public String toString() {
        StringBuffer sBuffer = new StringBuffer();
        
        sBuffer.append(this.fechaActual.toString()+ "\n");
        sBuffer.append("=====\n");

        for(int i = 0; i < this.recordatorios.longitud(); i++) {
            Recordatorio r = this.recordatorios.obtener(i);

            if (this.fechaActual.equals(r.fecha())) {
                sBuffer.append(r.toString()+ "\n");
            }
        }

        return sBuffer.toString();
    }

    public void incrementarDia() {
        this.fechaActual.incrementarDia();
    }

    public Fecha fechaActual() {
        return this.fechaActual;
    }

}

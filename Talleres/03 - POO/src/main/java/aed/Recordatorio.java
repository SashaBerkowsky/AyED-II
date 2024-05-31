package aed;

public class Recordatorio {
    Fecha fecha;
    Horario horario;
    String mensaje;

    public Recordatorio(String mensaje, Fecha fecha, Horario horario) {
        this.mensaje = mensaje;
        this.fecha = new Fecha(fecha);
        this.horario = horario;
    }

    public Horario horario() {
        return this.horario;
    }

    public Fecha fecha() {
        return new Fecha(this.fecha);
    }

    public String mensaje() {
        return this.mensaje;
    }

    @Override
    public String toString() {
        return this.mensaje + " @ " + this.fecha.toString() + " " + this.horario.toString();
    }

    @Override
    public boolean equals(Object otro) {
        boolean otroEsNull = otro == null;
        boolean otroEsRecordatorio = otro.getClass() == this.getClass();

        if(otroEsNull || !otroEsRecordatorio) {
            return false;
        }

        Recordatorio otroRecordatorio = (Recordatorio) otro;
        return this.mensaje.equals(otroRecordatorio.mensaje()) && this.fecha.equals(otroRecordatorio.fecha()) && this.horario.equals(otroRecordatorio.horario());
    }

}

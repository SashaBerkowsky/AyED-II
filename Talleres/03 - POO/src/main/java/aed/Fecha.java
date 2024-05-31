package aed;

public class Fecha {
    private int dia;
    private int mes;

    public Fecha(int dia, int mes) {
        this.dia = dia;
        this.mes = mes;
    }

    public Fecha(Fecha fecha) {
        this.dia = fecha.dia();
        this.mes = fecha.mes();
    }

    public Integer dia() {
        return this.dia;
    }

    public Integer mes() {
        return this.mes;
    }

    @Override
    public String toString() {
        return dia + "/" + mes;
    }

    @Override
    public boolean equals(Object otra) {
        boolean otraEsNull = otra == null;
        boolean otraEsFecha = otra.getClass() == this.getClass();

        if(otraEsNull || !otraEsFecha) {
            return false;
        }

        Fecha otraFecha = (Fecha) otra;
        return otraFecha.mes() == this.mes && otraFecha.dia() == this.dia;
    }

    public void incrementarDia() {
        if(this.diasEnMes(this.mes) == this.dia) {
            this.dia = 1;
            this.mes = this.mes < 12 ? this.mes + 1 : 1;
        } else {
            this.dia += 1;
        }
    }

    private int diasEnMes(int mes) {
        int dias[] = {
                // ene, feb, mar, abr, may, jun
                31, 28, 31, 30, 31, 30,
                // jul, ago, sep, oct, nov, dic
                31, 31, 30, 31, 30, 31
        };
        return dias[mes - 1];
    }

}

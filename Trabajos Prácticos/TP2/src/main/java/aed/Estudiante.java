package aed;

// InvRep: _cantidadDeMaterias siempre será un número mayor o igual a 0
public class Estudiante {
    private int _cantidadDeMaterias;

    public Estudiante() {
        this._cantidadDeMaterias = 0;
    }

    public void dejarMateria() {
        // Para no romper con el InvRep
        if(this._cantidadDeMaterias > 0) {
            this._cantidadDeMaterias -= 1;
        }
    }

    public void agregarMateria() {
        this._cantidadDeMaterias += 1;
    }

    public int obtenerCantidadMaterias() {
        return this._cantidadDeMaterias;
    }
}

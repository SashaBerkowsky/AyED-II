package aed;

// InvRep: True
public class Carrera {
    private DiccionarioDigital<Materia> _materias;

    public Carrera() {
        this._materias = new DiccionarioDigital<Materia>();
    }

    public void agregarMateria(String nombreMateria, Materia materia) {
        this._materias.definir(nombreMateria, materia);
    }

    public Materia obtenerMateria(String nombreMateria) {
        return this._materias.obtener(nombreMateria); // O(|nombreMateria|)
    }   // TOTAL: O(|nombreMateria|)

    public String[] obtenerMaterias() {
        return this._materias.obtenerClaves();        // O(SUM_|nombreMateria|)
    }

    public void cerrarMateria(String nombreMateria) {
        this._materias.borrar(nombreMateria); // O(|nombreMateria|)
    } // TOTAL: O(|nombreMateria|)
}

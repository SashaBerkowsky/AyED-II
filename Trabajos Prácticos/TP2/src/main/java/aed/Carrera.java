package aed;

// InvRep:  Cumple con el invariante de representacion de DiccionarioDigital y ninguna materia en _materias es nula
//          La cantidad de materias es mayor o igual a 0
public class Carrera {
    private DiccionarioDigital<Materia> _materias;

    public Carrera() {
        this._materias = new DiccionarioDigital<Materia>();
    }

    public void agregarMateria(String nombreMateria, Materia materia) {
        // Para no romper con el InvRep
        if(materia != null) {
            this._materias.definir(nombreMateria, materia); // O(|nombreMateria|)
        }
    } // TOTAL: O(|nombreMateria|)

    public Materia obtenerMateria(String nombreMateria) {
        return this._materias.obtener(nombreMateria); // O(|nombreMateria|)
    } // TOTAL: O(|nombreMateria|)

    public String[] obtenerMaterias() {
        return this._materias.obtenerClaves();        // O(SUM_|nombreMateria|)
    }

    public void cerrarMateria(String nombreMateria) {
        this._materias.borrar(nombreMateria); // O(|nombreMateria|)
    } // TOTAL: O(|nombreMateria|)
}

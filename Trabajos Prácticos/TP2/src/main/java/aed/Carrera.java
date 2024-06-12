package aed;

// InvRep: True
public class Carrera {
    private DiccionarioDigital<Integer> _indiceMaterias;

    public Carrera() {
        this._indiceMaterias = new DiccionarioDigital<Integer>();

    }

    public void crearMateria(String nombreMateria, Integer indice) {
        this._indiceMaterias.definir(nombreMateria, indice);
    }

    public Integer obtenerIndiceMateria(String nombreMateria) {
        return this._indiceMaterias.obtener(nombreMateria);
    }

    public String[] obtenerMaterias() {
        String[] clavesMaterias = new String[this._indiceMaterias.tamanio()];
        this._indiceMaterias.obtenerClaves().toArray(clavesMaterias);

        return clavesMaterias;
    }

    public void cerrarMateria(String nombreMateria) {
        this._indiceMaterias.borrar(nombreMateria);
    }
}

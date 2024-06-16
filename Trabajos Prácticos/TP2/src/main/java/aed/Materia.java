package aed;

import java.util.ArrayList;

// InvRep:  _profesores, _jtp, _ay1 y _ay2 son todas mayor o igual a 0
//          La longitud de _carreras y _nombres es la misma (cada materia tiene un nombre por carrera) 
//          Para cualquier i entre 0 y la longitud de _carreras y _nombres, el _nombre i es el nombre de la materia en la carrera i
public class Materia {
    private final static int ESTUDIANTES_POR_PROFESOR = 250;
    private final static int ESTUDIANTES_POR_JTP = 100;
    private final static int ESTUDIANTES_POR_AY1 = 20;
    private final static int ESTUDIANTES_POR_AY2 = 30;

    private DiccionarioDigital<Estudiante> _estudiantes;
    private ArrayList<Carrera> _carreras;
    private ArrayList<String> _nombres;
    private int _profesores;
    private int _jtp;
    private int _ay1;
    private int _ay2;

    public Materia() {
        this._estudiantes = new DiccionarioDigital<Estudiante>();
        this._carreras = new ArrayList<Carrera>();
        this._nombres = new ArrayList<String>();
        this._profesores = 0;
        this._jtp = 0;
        this._ay1 = 0;
        this._ay2 = 0;
    }

    public void inscribir(String libreta, Estudiante estudiante) {
        this._estudiantes.definir(libreta, estudiante);
    }

    public int cantidadEstudiantes() {
        return this._estudiantes.tamanio();
    }

    public int cantidadDeMaterias(String libreta) {
        return this._estudiantes.obtener(libreta).obtenerCantidadMaterias();
    }

    public void agregarProfesor() {
        this._profesores += 1;
    }

    public void agregarJtp() {
        this._jtp += 1;
    }

    public void agregarAy1() {
        this._ay1 += 1;
    }

    public void agregarAy2() {
        this._ay2 += 1;
    }

    public int[] obtenerPlantelDocente() {
        return new int[]{ this._profesores, this._jtp, this._ay1, this._ay2 };
    }

    public void agregarCarrera(Carrera carrera) {
        this._carreras.add(carrera);
    }

    public void agregarNombre(String nombreMateria) {
        this._nombres.add(nombreMateria);
    }

    public ArrayList<Carrera> obtenerCarreras() {
        return this._carreras;
    }

    public ArrayList<String> obtenerNombresMateria() {
        return this._nombres;
    }

    public boolean excedeCupo() {
        int cantidadEstudiantes = this._estudiantes.tamanio();
        boolean excedeCupoProfesores = cantidadEstudiantes > ESTUDIANTES_POR_PROFESOR * this._profesores;  // O(1)
        boolean excedeCupoJTP = cantidadEstudiantes > ESTUDIANTES_POR_JTP * this._jtp;                     // O(1) 
        boolean excedeCupoAY1 = cantidadEstudiantes > ESTUDIANTES_POR_AY1 * this._ay1;                     // O(1)
        boolean excedeCupoAY2 = cantidadEstudiantes > ESTUDIANTES_POR_AY2 * this._ay2;                     // O(1)

        return excedeCupoProfesores || excedeCupoJTP || excedeCupoAY1 || excedeCupoAY2;                     // O(1)
    } // TOTAL: 5 * O(1) = O(1)

    public void vaciarAlumnos() {
        String[] libretas = this._estudiantes.obtenerClaves();

        for(int i = 0; i < libretas.length; i += 1) {
            Estudiante e = this._estudiantes.obtener(libretas[i]);
            e.dejarMateria();
        }
    }
}

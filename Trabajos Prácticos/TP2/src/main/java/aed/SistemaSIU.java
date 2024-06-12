package aed;

import java.util.ArrayList;

public class SistemaSIU {
    // Completar atributos privados
    private DiccionarioDigital<Carrera> _carreras;
    private DiccionarioDigital<Estudiante> _estudiantes;
    private ArrayList<Materia> _materias;

    enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
    }

    // TODO MODIFICAR COMPLEJIDAD (ESTA MAL HECHO)
    public SistemaSIU(InfoMateria[] materiasEnCarreras, String[] libretasUniversitarias){
        this._carreras = new DiccionarioDigital<Carrera>();
        this._estudiantes = new DiccionarioDigital<Estudiante>();
        this._materias = new ArrayList<Materia>();


        for(int i = 0; i < materiasEnCarreras.length; i += 1) { // |M|
            InfoMateria materia = materiasEnCarreras[i];
            this._materias.add(new Materia());

            // |materia.nombresEnCarreras| = |materia.carreras| <= |C|
            for(int j = 0; j < materia.nombresEnCarreras.length; j += 1) { // |M_c|
                String nombreCarrera = materia.carreras[j];
                String nombreMateria = materia.nombresEnCarreras[j];

                Carrera c = this._carreras.obtener(nombreCarrera); // O(|nombreCarrera|)
                if(c != null) {
                    c.crearMateria(nombreMateria, i);
                } else {
                    Carrera nuevaCarrera = new Carrera();
                    this._carreras.definir(nombreCarrera, nuevaCarrera); // O(|nombreCarrera|)
                    nuevaCarrera.crearMateria(nombreMateria, i);         // O(|nombreMateria|)
                }

                this._materias.get(i).agregarCarrera(nombreCarrera, nombreMateria); // O(1)
            } // TOTAL CICLO: O(#materia.nombresEnCarreras) = O(|M_c|)
        } // TOTAL CICLO: O(|nombreCarreras|) + O(|nombreCarreras|) + O(|nombreMaterias|) = O(|nombreCarreras| + |nombreMaterias|)

        for(int i = 0; i < libretasUniversitarias.length; i += 1) {
            Estudiante nuevoEstudiante = new Estudiante();
            String libreta = libretasUniversitarias[i];

            this._estudiantes.definir(libreta, nuevoEstudiante); // O(|libreta|) = O(1)
        } // TOTAL CICLO: O(#libretasUniversitarias) = O(E)
    } // TOTAL: O(|nombresCarreras| + |nombreMaterias|) 

    public void inscribir(String estudiante, String carrera, String materia){
        Materia m = this._obtenerMateria(carrera, materia); // O(|c| + |m|)

        Estudiante e = this._estudiantes.obtener(estudiante); // O(|estudiante|) = O(1)
        e.agregarMateria();

        m.inscribir(estudiante, e); // O(|estudiante|) = O(1)
    }   // TOTAL: O(|c| + |m|) + O(1) = O(|c| + |m|)

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        Materia m = this._obtenerMateria(carrera, materia); // O(|c| + |m|)

        switch(cargo) {
            case PROF: 
                m.agregarProfesor();                                // O(1)
                break;
            case JTP:
                m.agregarJtp();                                     // O(1)
                break;
            case AY1:
                m.agregarAy1();                                     // O(1)
                break;
            case AY2:
                m.agregarAy2();                                     // O(1)
                break;
        }
    } // TOTAL: O(|c| + |m|) + O(1) = O(|c| + |m|)

    public int[] plantelDocente(String materia, String carrera){
        Materia m = this._obtenerMateria(carrera, materia); // O(|c| + |m|)
        
        return m.obtenerPlantelDocente();
    } // TOTAL: O(|c| + |m|)

    public void cerrarMateria(String materia, String carrera){
        Carrera c = this._carreras.obtener(carrera);
        Integer indiceMateria = c.obtenerIndiceMateria(materia);
        Materia m = this._materias.get(indiceMateria);

        ArrayList<String> carreras = m.obtenerCarreras();
        ArrayList<String> nombresMateria = m.obtenerNombresMateria();

        for (int i = 0; i < carreras.size(); i += 1) {
            String nombreCarreraMateria = carreras.get(i);
            String nombreMateria = nombresMateria.get(i);

            Carrera carreraDeMateria = this._carreras.obtener(nombreCarreraMateria);
            carreraDeMateria.cerrarMateria(nombreMateria);
        }

        m.vaciarAlumnos();
        this._materias.set(indiceMateria, null);
    }

    public int inscriptos(String materia, String carrera){
        Materia m = this._obtenerMateria(carrera, materia); //  O(|c| + |m|)

        return m.cantidadEstudiantes();                     //  O(1)
    }   // TOTAL: O(|c| + |m|) + O(1) = O(|c| + |m|)

    public boolean excedeCupo(String materia, String carrera){
        Materia m = this._obtenerMateria(carrera, materia); // O(|c| + |m|)

        return m.excedeCupo();                              // O(1)
    } // TOTAL: O(|c| + |m|) + O(1) = O(|c| + |m|)

    public String[] carreras(){
        String[] claves = new String[this._carreras.tamanio()];
        this._carreras.obtenerClaves().toArray(claves);

        return claves;
    }

    public String[] materias(String carrera){
        Carrera c = this._carreras.obtener(carrera);

        return c.obtenerMaterias();
    }

    public int materiasInscriptas(String estudiante){
        return this._estudiantes.obtener(estudiante).obtenerCantidadMaterias();
    }

    private Materia _obtenerMateria(String carrera, String materia) {
        Carrera c = this._carreras.obtener(carrera);                // O(|carrera|) = O(|c|)
        Integer indiceMateria = c.obtenerIndiceMateria(materia);    // O(|materia|) = O(|m|)

        return this._materias.get(indiceMateria);
    } // TOTAL: O(|c|) + O(|m|) = O(|c| + |m|)
}

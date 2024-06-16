package aed;

import java.util.ArrayList;

public class SistemaSIU {
    // Completar atributos privados
    private DiccionarioDigital<Carrera> _carreras;
    private DiccionarioDigital<Estudiante> _estudiantes;

    enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
    }

    public SistemaSIU(InfoMateria[] materiasEnCarreras, String[] libretasUniversitarias){
        this._carreras = new DiccionarioDigital<Carrera>();
        this._estudiantes = new DiccionarioDigital<Estudiante>();

        // materiasEnCarreras contiene |M_c| veces la carrera c
        // recorre todas las materias
        for(int i = 0; i < materiasEnCarreras.length; i += 1) {     
            InfoMateria materia = materiasEnCarreras[i];    
            Materia nuevaMateria = new Materia();

            for(int j = 0; j < materia.nombresEnCarreras.length; j += 1) { 
                String nombreCarrera = materia.carreras[j];
                String nombreMateria = materia.nombresEnCarreras[j];
                Carrera carreraBuscada = this._carreras.obtener(nombreCarrera);     // O(|nombreCarrera|)
                boolean existeCarrera = carreraBuscada != null;

                if(existeCarrera) {
                    nuevaMateria.agregarCarrera(carreraBuscada);
                    carreraBuscada.agregarMateria(nombreMateria, nuevaMateria);     // O(|nombreMateria|)
                } else {
                    Carrera nuevaCarrera = new Carrera();
                    nuevaCarrera.agregarMateria(nombreMateria, nuevaMateria);       // O(|nombreMateria|)
                    nuevaMateria.agregarCarrera(nuevaCarrera);
                    this._carreras.definir(nombreCarrera, nuevaCarrera);            // O(|nombreCarrera|)
                }
                // 2*O(|nombreCarrera|) = O(|nombreCarrera|)
                // 2*O(|nombreMateria|) = O(|nombreMateria|)

                nuevaMateria.agregarNombre(nombreMateria);
                // Para cada carrera, se va a agregar a su diccionario correspondiente que toma O(|nombreCarrera|) (Se ejecuta |M_c| veces)
                // En el peor de los escenarios se tiene que buscar la carrera para ver si existe y luego si no existe, definirla, lo que toma 2*O(|nombreCarrera|) = O(|nombreCarrera|)
                // Para cada nombre de materia, se agrega al diccionario de materias por medio de nuevaCarrera.crearMateria(...), esto es de O(|nombreMateria|)
            } 
        }   // En conclusion, podemos afirmar que la complejidad del ciclo es: O(SUM_|nombreCarrera| * |M_c| + SUM_|nombreMateria|) 


        for(int i = 0; i < libretasUniversitarias.length; i += 1) {
            Estudiante nuevoEstudiante = new Estudiante();
            String libreta = libretasUniversitarias[i];

            this._estudiantes.definir(libreta, nuevoEstudiante); // O(|libreta|) = O(1)
        } // TOTAL CICLO: O(|libretasUniversitarias|) = O(E)
    } // TOTAL: O(SUM_|nombreCarrera| * |M_c| + SUM_|nombreMateria|) + O(E) = O(SUM_|nombreCarrera| * |M_c| + SUM_|nombreMateria| + E)

    public void inscribir(String estudiante, String carrera, String materia){
        Materia m = this._obtenerMateria(carrera, materia);     // O(|c| + |m|)

        Estudiante e = this._estudiantes.obtener(estudiante);   // O(|estudiante|) = O(1)
        e.agregarMateria();

        m.inscribir(estudiante, e);                             // O(|estudiante|) = O(1)
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
        Materia m = this._obtenerMateria(carrera, materia);

        ArrayList<Carrera> carreras = m.obtenerCarreras(); // O(1)
        ArrayList<String> nombresMateria = m.obtenerNombresMateria(); // O(1)

        for (int i = 0; i < carreras.size(); i += 1) {  // |N_m| veces (nombres de la materia)
            Carrera carreraActual = carreras.get(i);
            String nombreMateria = nombresMateria.get(i);

            carreraActual.cerrarMateria(nombreMateria);                              // O(|nombreMateria|)
            // O(|nombreMateria|) + O(|nombreMateria|) = O(|nombreMateria|)
        } // O(SUM_|nombreMateria|)

        m.vaciarAlumnos(); // O(|E_m|) (cant Estudiantes de la materia)
    }   // TOTAL: O(|c| + |m|) + O(SUM_|nombreMateria|) + O(|E_m|) = O(|c| + |m| + SUM_|nombreMateria| + |E_m|)

    public int inscriptos(String materia, String carrera){
        Materia m = this._obtenerMateria(carrera, materia); //  O(|c| + |m|)

        return m.cantidadEstudiantes();                     //  O(1)
    }   // TOTAL: O(|c| + |m|) + O(1) = O(|c| + |m|)

    public boolean excedeCupo(String materia, String carrera){
        Materia m = this._obtenerMateria(carrera, materia); // O(|c| + |m|)

        return m.excedeCupo();                              // O(1)
    }   // TOTAL: O(|c| + |m|) + O(1) = O(|c| + |m|)

    public String[] carreras(){
        return this._carreras.obtenerClaves(); // O(SUM_|nombreCarrera|)
    }

    public String[] materias(String carrera){
        Carrera c = this._carreras.obtener(carrera);    // O(|nombreCarrera|)

        return c.obtenerMaterias();                     // O(SUM_|nombreMateria|)
    }   // TOTAL: O(|nombreCarrera| + SUM_|nombreMateria|)

    public int materiasInscriptas(String estudiante){
        Estudiante e = this._estudiantes.obtener(estudiante);   // O(|estudiante|), como |estudiante| es acotado -> O(1)
        return e.obtenerCantidadMaterias();                     // O(1)
    }   // TOTAL: O(1)

    private Materia _obtenerMateria(String carrera, String materia) {
        Carrera c = this._carreras.obtener(carrera);            // O(|carrera|) = O(|c|)

        return c.obtenerMateria(materia);
    }   // TOTAL: O(|c|) + O(|m|) = O(|c| + |m|)
}

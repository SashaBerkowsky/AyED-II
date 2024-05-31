package aed;

class Funciones {
    int cuadrado(int x) {
        // COMPLETAR
        return x * x;
    }

    double distancia(double x, double y) {
        // COMPLETAR
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    boolean esPar(int n) {
        // COMPLETAR
        return n % 2 == 0;
    }

    boolean esBisiesto(int n) {
        // COMPLETAR
        return (n % 4 == 0 && n % 100 != 0) || n % 400 == 0;
    }

    int factorialIterativo(int n) {
        // COMPLETAR
        int res = 1;

        for (int i = n; i > 1; i--) {
            res *= i;
        }

        return res;
    }

    int factorialRecursivo(int n) {
        // COMPLETAR
        if(n > 1) {
            return n * factorialRecursivo(n - 1);
        } else {
            return 1;
        }
    }

    boolean esPrimo(int n) {
        // COMPLETAR
        int i = n - 1;

        while(n != 1 && n % i != 0) {
            i -= 1;
        }

        return i == 1;
    }

    int sumatoria(int[] numeros) {
        // COMPLETAR
        int res = 0;

        for (int i = 0; i < numeros.length; i++) {
            res += numeros[i];
        }
        
        return res;
    }

    int busqueda(int[] numeros, int buscado) {
        // COMPLETAR
        int i = 0; 

        while(numeros[i] != buscado) {
            i += 1;
        }

        return i;
    }

    boolean tienePrimo(int[] numeros) {
        // COMPLETAR
        int i = 0;

        while(i < numeros.length && !esPrimo(numeros[i])) {
            i += 1;
        }

        return i < numeros.length;
    }

    boolean todosPares(int[] numeros) {
        // COMPLETAR
        int i = 0;

        while(i < numeros.length && esPar(numeros[i])) {
            i += 1;
        }

        return i == numeros.length;
    }

    boolean esPrefijo(String s1, String s2) {
        // COMPLETAR
        int i = 0;

        if(s1.length() <= s2.length()) {
            while(i < s1.length() && s1.charAt(i) == s2.charAt(i)) {
                i += 1;
            }
        }

        return i == s1.length();
    }

    boolean esSufijo(String s1, String s2) {
        // COMPLETAR
        int i = s2.length() - s1.length();
        if(i >= 0) {
            int j = 0;
            while(i < s2.length() && s2.charAt(i) == s1.charAt(j)) {
                i += 1;
                j += 1;
            }

        }

        return i == s2.length();
    }
}

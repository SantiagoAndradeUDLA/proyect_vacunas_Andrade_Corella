    public class Vacuna {
        private String nombre;
        private int dosisRequeridas;

        public Vacuna(String nombre, int dosisRequeridas) {
            this.nombre = nombre;
            this.dosisRequeridas = dosisRequeridas;
        }

        public String getNombre() {
            return nombre;
        }

        public int getDosisRequeridas() {
            return dosisRequeridas;
        }

        @Override
        public String toString() {
            return nombre + " (" + dosisRequeridas + " dosis)";
        }
    }

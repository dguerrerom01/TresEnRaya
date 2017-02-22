package Juego;

public class Principal {

	public static void main(String[] args) {
		/*
		 * 1, 2.1.4, 2.2.2, 3 y 5 (José Antonio) 2, 4 y 6 (Ismael)
		 * El metodo de comprobar contigua no lo he acabdo, me da fallos
		 */
		int[][] tablero = new int[3][3];
		int [] posicion = new int[2];
		boolean linea = false;
		int turno = 2;
		//Proceso general
		tablero = MetodosTER.iniciarJugada(tablero);
		
		MetodosTER.mostrarTablero(tablero);
		
		do {
			System.out.println("Es el turno de: '" + (turno == 1?"O'":"X'"));
			MetodosTER.realizarMovimiento(tablero, turno);
			
			linea = MetodosTER.comprobarLinea(tablero);
//			if (!linea) {
				turno = MetodosTER.cambiarTurno(turno);
//			}
			MetodosTER.mostrarTablero(tablero);
		} while (!linea);
		
		MetodosTER.felicitarJugador(turno);

	}
}

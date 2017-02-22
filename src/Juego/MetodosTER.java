package Juego;

import java.util.Scanner;

public class MetodosTER {

	/**
	 * Rellenara el tablero de ceros para que este esté vacío y en la casilla
	 * central pondrá un uno como si fuera una ficha
	 * 
	 * @param tablero
	 * @return tablero relleno de ceros y una ficha central
	 */
	public static int[][] iniciarJugada(int[][] tablero) {
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero.length; j++) {
				tablero[i][j] = 0;
			}
		}
		tablero[1][1] = 1;
		return tablero;
	}

	/**
	 * Se solicita la información del movimiento. En principio sólo se llama a
	 * pedir posición, a partir del sexto movimiento se llama primero a mover
	 * ficha
	 * 
	 * @param tablero
	 * @param turno
	 * @return el tablero con el movimiento realizado
	 */
	public static int[][] realizarMovimiento(int[][] tablero, int turno) {
		int fichas = 0;
		// Recorre el tablero para contar las fichas
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j] != 0) {
					fichas++;
				}
			}
		}

		int[] posicionAntigua = new int[2];
		System.out.println("Introduzca la posicion");
		// int[] posicion = pedirPosicion();
		// posicionAntigua[0] = posicion[0];
		// posicionAntigua[1] = posicion[1];

		if (fichas < 6) {
			tablero = colocarFicha(tablero, posicionAntigua, turno);
		} else {
			tablero = moverFicha(tablero, posicionAntigua,turno);
			tablero = colocarFicha(tablero, posicionAntigua, turno);
		}
		return tablero;
	}

	/**
	 * Comprueba si tiene alguna linea en el tablero con una combinación
	 * ganadora
	 * 
	 * @param tablero
	 * @return true si tiene o false en caso contrario
	 */
	public static boolean comprobarLinea(int[][] tablero) {
		boolean linea = false;
		linea = comprobarHorizontal(tablero) || comprobarVertical(tablero) || comprobarDiagonal(tablero);
		return linea;
	}

	/**
	 * Muestra por consola la situacion actual del tablero
	 * 
	 * @param tablero
	 */
	public static void mostrarTablero(int[][] tablero) {
		int ficha = 0;
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print("╔�?�?�?╗");
				System.out.print(" ");
			}
			System.out.println();
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j] == 1) {
					ficha = 'O'; // O <- 79
				} else if (tablero[i][j] == 2) {
					ficha = 'X'; // X <-88
				} else if (tablero[i][j] == 0) {
					ficha = ' '; // " " <-32
				}
				System.out.print("║ " + (char) ficha + " ║");
				System.out.print(" ");
			}
			System.out.println();
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print("╚�?�?�?�?");
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	/**
	 * Cambia de turno
	 * 
	 * @param turno
	 * @return el turno contraio al actual
	 */
	public static int cambiarTurno(int turno) {
		return turno == 1 ? 2 : 1;
	}

	/**
	 * Muestra un mensaje por consola para saber que ha finalizado el juego y ha
	 * ganado un jugador
	 */
	public static void felicitarJugador(int turno) {
		System.out.println("Ehnorabuena ha ganado el jugador '" + (turno == 1 ? "X'" : "O'"));
	}

	/* Métodos para realizarMoviento */

	public static int[][] moverFicha(int[][] tablero, int[] posicionAntigua,int turno) {
		boolean valido = false;
//		int turno = tablero[posicionAntigua[0]][posicionAntigua[0]];
		int[] posicion = new int[2];
		do {
			System.out.println("Introduzca la casilla de su ficha a eliminar");
			posicion = pedirPosicion();
			if (comprobarFichaPropia(tablero, turno, posicion)) {
				if (!comprobarEncerrada(tablero, posicion)) {
					tablero = borrarFicha(tablero, posicion);
					valido = true;
				} else {
					System.out.println("Movimiento no valido, está encerrada");
				}
			} else {
				System.out.println("Esta ficha no es tuya");
			}
		} while (!valido);
		return tablero;
	}

	/**
	 * Se pide una posición y se comprueba si esta vacia y si es así se
	 * comprueba si es contigua a la posicionantigua que acabamos de borrar. En
	 * caso afirmativo se coloca
	 * 
	 * @param tablero
	 * @param posicionAntigua
	 * @param turno
	 * @return El tablero con la ficha colocada
	 */
	public static int[][] colocarFicha(int[][] tablero, int[] posicionAntigua, int turno) {
		boolean fichaCambiada = false;
		do {
			System.out.println("Introduzca la posicion en la que lo desea colocar la ficha");
			int[] posicion = pedirPosicion();
			if (comprobarCasillaVacia(tablero, posicion)) {
				if (comprobarContigua(tablero, posicion, posicionAntigua)) {
					tablero = colocarPosicion(tablero, turno, posicion);
					fichaCambiada = true;
				} else {
					System.out.println("Esta casilla no es contigua");
				}
			} else {
				System.out.println("No esta vacía");
			}
		} while (!fichaCambiada);
		return tablero;
	}

	/* Metodos para comprobarLinea */
	/**
	 * Comprueba si hay alguna combinacion en horizontal
	 * 
	 * @param tablero
	 * @return true si la hay o false si no
	 */
	public static boolean comprobarHorizontal(int[][] tablero) {
		boolean linea = false;
		for (int i = 0; i < tablero.length; i++) {
			if (tablero[i][0] == tablero[i][1] && tablero[i][1] == tablero[i][2] && tablero[i][0] != 0) {
				return true;
			}
		}
		return linea;
	}

	/**
	 * Comprueba si hay alguna combinacion en vertical
	 * 
	 * @param tablero
	 * @return true si la hay o false si no
	 */
	public static boolean comprobarVertical(int[][] tablero) {
		boolean linea = false;
		for (int j = 0; j < tablero.length; j++) {
			if (tablero[0][j] == tablero[1][j] && tablero[1][j] == tablero[2][j] && tablero[0][j] != 0) {
				return true;
			}
		}
		return linea;
	}

	/**
	 * Comprueba si hay alguna combinacion en diagonal
	 * 
	 * @param tablero
	 * @return true si la hay o false si no
	 */
	public static boolean comprobarDiagonal(int[][] tablero) {
		boolean linea = false;
		for (int j = 0; j < tablero.length; j++) {
			if (tablero[0][0] == tablero[1][1] && tablero[1][1] == tablero[2][2] && tablero[0][0] != 0
					|| tablero[0][2] == tablero[1][1] && tablero[1][1] == tablero[2][0] && tablero[0][2] != 0) {
				return true;
			}
		}
		return linea;
	}

	/* Metodos para moverFicha */

	/**
	 * Se encarga de solicitar al usuario las coordenas de la posición de una
	 * determinada ficha o casilla en el tablero.
	 * 
	 * @return array de dos posiciones, una es la X y otra la Y
	 */
	public static int[] pedirPosicion() {
		int posiciones[] = new int[2];
		Scanner leer = new Scanner(System.in);

		int posX = -1, posY = -1;
		// Para la X
		do {
			System.out.println("Inserte la posicion X");
			posX = leer.nextInt();
		} while (posX < 0 || posX > 2);

		posiciones[0] = posX;
		do {
			System.out.println("Inserte la posicion Y");
			posY = leer.nextInt();
		} while (posY < 0 || posY > 2);
		posiciones[1] = posY;

		return posiciones;
	}

	/**
	 * Comprueba si la ficha seleccionada es la que corresponde al turno
	 * 
	 * @param tablero
	 * @param turno
	 * @param posion
	 * @return true en caso de que sea una ficha propia, o false en caso
	 *         contrario
	 */
	public static boolean comprobarFichaPropia(int[][] tablero, int turno, int[] posion) {
		if (tablero[posion[0]][posion[1]] == turno) {
			return true;
		}
		return false;
	}

	/**
	 * Comprueba si hay alguna casilla libre alrededor de esa posicion en el
	 * tablero
	 * 
	 * @param tablero
	 * @param posicion
	 * @return true si esta encerrada y por lo tanto no podría moverse o false
	 *         en caso contrario
	 */
	public static boolean comprobarEncerrada(int[][] tablero, int[] posicion) {
		for (int x = posicion[0] - 1; x <= posicion[0] + 1; x++)
			for (int y = posicion[1] - 1; y <= posicion[1] + 1; y++)
				if (x > -1 && x < 3 && y > -1 && y < 3) {
					if (tablero[x][y] == 0) {
						return false;
					}
				}
		return true;
	}

	/**
	 * Borra la ficha elegida y pone la casilla a 0 para que indique que esta
	 * esta vacía
	 * 
	 * @param tablero
	 * @param posicion
	 * @return el tablero con la ficha quitada
	 */
	public static int[][] borrarFicha(int[][] tablero, int[] posicion) {
		tablero[posicion[0]][posicion[1]] = 0;
		return tablero;

	}

	/* Metodos para colocar ficha */
	/**
	 * Comprueba si la posicion elegida está vacía, es decir, si es 0 o no
	 * 
	 * @param tablero
	 * @param posicion
	 * @return true si esta vacía o false en caso contrario
	 */
	public static boolean comprobarCasillaVacia(int[][] tablero, int[] posicion) {
		return tablero[posicion[0]][posicion[1]] == 0 ? true : false;
	}

	/**
	 * Se comprueba si la casilla vacía recogida es contigua a la
	 * posicioncontigua que se borró antes. Está comprobación sólo se hace a
	 * partir del sexto movimiento
	 * 
	 * @param tablero
	 * @param posicion
	 * @param posicionAntigua
	 * @return
	 */
	public static boolean comprobarContigua(int[][] tablero, int[] posicion, int[] posicionAntigua) {
		//TODO: Si tengo el metodo no me accede a la segunda posicion de los arrays de la matriz
//		int x = posicion[0] - posicionAntigua[0], y = posicion[1] - posicionAntigua[1];
//		if (x > -2 && x < 2 && y > -2 && y < 2) {
//			return true;
//		}
//		return false;
		return true;
	}

	/**
	 * Colocara una ficha del jugador al que corresponda en dicha posicion
	 * 
	 * @param tablero
	 * @param turno
	 * @param posicion
	 * @return el tablero con la posicion ya colocada
	 */
	public static int[][] colocarPosicion(int[][] tablero, int turno, int[] posicion) {
		tablero[posicion[0]][posicion[1]] = turno;
		return tablero;
	}
}

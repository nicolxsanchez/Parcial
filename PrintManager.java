package logic;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class PrintManager {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		PrintQueue printQueue = new PrintQueue();
		PrintService printService = new PrintService(printQueue);

		while (true) {
			System.out.println("\nMenu de Gestion de Impresion:");
			System.out.println("1-Enviar Trabajo de Impresion");
			System.out.println("2-Procesar Trabajo de Impresion");
			System.out.println("3-Ver Trabajos de Impresiones");
			System.out.println("4-Salir");
			System.out.print("Seleccione una Opcion: ");
			int opcion = scanner.nextInt();
			scanner.nextLine();

			switch (opcion) {
			case 1:
				System.out.print("Ingrese Usuario: ");
				String usuario = scanner.nextLine();
				System.out.print("Ingrese Prioridad: ");
				String prioridadInput = scanner.nextLine().toUpperCase();
				char prioridad = prioridadInput.isEmpty() ? 'M' : prioridadInput.charAt(0);

				printService.enviarTrabajo(usuario, prioridad);
				break;

			case 2:
				printService.procesarTrabajo();
				break;

			case 3:
				printQueue.verTrabajos();
				break;

			case 4:
				System.out.println("Ha salido del programa.");
				scanner.close();
				return;

			default:
				System.out.println("Opcion no valida, intente de nuevo.");
			}
		}
	}
}



class PrintJob {

	private String usuario;
	private LocalTime horaEnvio;
	private char prioridad;

	public PrintJob(String usuario, char prioridad) {
		super();
		this.usuario = usuario;
		this.horaEnvio = LocalTime.now();
		this.prioridad = prioridad;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public LocalTime getHoraEnvio() {
		return horaEnvio;
	}

	public void setHoraEnvio(LocalTime horaEnvio) {
		this.horaEnvio = horaEnvio;
	}

	public char getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(char prioridad) {
		this.prioridad = prioridad;
	}

	public String toString() {
		return "usuario: " + usuario + ", prioridad: " + prioridad + ", hora de envio: " + horaEnvio;
	}
}

class PrintQueue {

	private ArrayList<PrintJob> trabajos;

	public PrintQueue() {
		this.trabajos = new ArrayList<>();

	}

	// este metoodo devuelve el valor numerico de la prioridad
	private int prioridadValor(char prioridad) {
		switch (prioridad) {
		case 'H':
			return 3;
		case 'M':
			return 2;
		case 'L':
			return 1;
		default:
			return 2;
		}
	}

	// este metodo agrega un trabajo a la lista
	public void agregarTrabajo(PrintJob trabajo) {
		trabajos.add(trabajo);
		System.out.println("\nTrabajo Agregado: " + trabajo);
	}

	// este metodo ordena por prioridad y hora de envio
	public void ordenarTrabajos() {
		trabajos.sort((trabajo1, trabajo2) -> {
			if (prioridadValor(trabajo1.getPrioridad()) == prioridadValor(trabajo2.getPrioridad())) {
				return trabajo1.getHoraEnvio().compareTo(trabajo2.getHoraEnvio());
			}
			return prioridadValor(trabajo2.getPrioridad()) - prioridadValor(trabajo1.getPrioridad());
		});
	}

	// este metodo elimina y devuelve el primer trabajo
	public PrintJob procesarTrabajo() {
		if (!trabajos.isEmpty()) {
			return trabajos.remove(0);
		}
		return null;
	}

	// este metodo muestra la lista de trabajos
	public void verTrabajos() {
		System.out.println("\nLista de Impresiones:");
		for (PrintJob job : trabajos) {
			System.out.println(job);
		}
	}

	// este metodo verifica si la lista esta vacia
	public boolean estaVacia() {
		return trabajos.isEmpty();
	}
}



class PrintService {

	private PrintQueue cola;
	public PrintService(PrintQueue cola) {
		super();
		this.cola = cola;
	}

	public PrintQueue getCola() {
		return cola;
	}

	public void setCola(PrintQueue cola) {
		this.cola = cola;
	}

	//este metodo crea un trabajo y lo agrega a la cola
	public void enviarTrabajo(String usuario, char prioridad) {
		PrintJob trabajo = new PrintJob(usuario, prioridad);
		cola.agregarTrabajo(trabajo);
		cola.ordenarTrabajos();
	}

	//este metodo procesa e imprime el primer trabajo de la cola 
	public void procesarTrabajo() {
		PrintJob trabajo = cola.procesarTrabajo();
		if (trabajo != null) {
			System.out.println("\nProcesando Trabajo: " + trabajo);

		} else {
			System.out.println("No hay trabajos en la cola.");
		}
	}

}

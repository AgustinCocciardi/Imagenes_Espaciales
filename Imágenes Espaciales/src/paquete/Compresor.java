package paquete;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Compresor {
	
	private ImagenEspacial imagenDescomprimida;
	private ImagenEspacial imagenComprimida;
	
	public Compresor(String imagenDescomprimida, String imagenComprimida) {
		this.imagenDescomprimida = new ImagenEspacial(imagenDescomprimida);
		this.imagenComprimida = new ImagenEspacial(imagenComprimida);
	}
	
	public void setComprimir(String imagenComprimida) {
		this.imagenDescomprimida.setColores(imagenComprimida);
	}
	
	public void setDescomprimir(String imagenDescomprimida) {
		this.imagenComprimida.setColores(imagenDescomprimida);
	}
	
	public String getImagenDescomprimida() {
		return imagenDescomprimida.getColores();
	}

	public String getImagenComprimida() {
		return imagenComprimida.getColores();
	}

	public void resolver() {
		this.comprimir();
		this.descomprimir();
	}
	
	private void comprimir() {
		String imagen = this.imagenDescomprimida.getColores();
		String nuevaImagen = "";
		int cantidadRepeticiones = 0;
		char letraActual = 0;
		boolean letraAsignada = false;
		int i = 0, longitud = imagen.length();
		while(i<longitud) {
			if(letraAsignada == false) {
				cantidadRepeticiones = 1;
				letraAsignada = true;
				letraActual = imagen.charAt(i);
			}
			else {
				if(imagen.charAt(i) == letraActual) {
					cantidadRepeticiones++;
				}
				else {
					i--;
					letraAsignada = false;
					if(cantidadRepeticiones > 4) {
						nuevaImagen += "(" + letraActual + cantidadRepeticiones + ")";
					}
					else {
						for(int j=0; j<cantidadRepeticiones; j++) {
							nuevaImagen += letraActual;
						}
					}
					
				}
				if(i+1 == longitud) {
					if(cantidadRepeticiones > 4) {
						nuevaImagen += "(" + letraActual + cantidadRepeticiones + ")";
					}
					else {
						for(int j=0; j<cantidadRepeticiones; j++) {
							nuevaImagen += letraActual;
						}
					}
				}
			}
			i++;
		}
		this.setComprimir(nuevaImagen);
	}
	
	private void descomprimir() {
		String imagen = this.imagenComprimida.getColores();
		String nuevaImagen = "";
		String numero="";
		int i = 0, longitud = imagen.length(), inicio=0;
		char letraActual = 0;
		char letraRepetida = 0;
		int repeticiones = 0;
		while(i < longitud) {
			letraActual = imagen.charAt(i);
			if(letraActual == '(') {
				repeticiones = 0;
				letraRepetida = imagen.charAt(++i);
				letraActual = imagen.charAt(++i);
				inicio = i;
				while( letraActual != ')' ) {
					repeticiones++;
					letraActual = imagen.charAt(++i);
				}
				repeticiones+=inicio;
				numero = imagen.substring(inicio,repeticiones);
				repeticiones = Integer.parseInt(numero);
				for (int j=0; j<repeticiones; j++) {
					nuevaImagen += letraRepetida;
				}
			}
			else {
				nuevaImagen += letraActual;
			}
			i++;
		}
		this.setDescomprimir(nuevaImagen);
	}
	
	public static void main(String[] args) throws IOException {
		Scanner entrada = new Scanner(new FileReader("imagenes.in"));
		String imagenDescomprimida = entrada.nextLine();
		String imagenComprimida = entrada.nextLine();
		Compresor compresor = new Compresor(imagenDescomprimida , imagenComprimida);
		entrada.close();
		compresor.resolver();
		PrintWriter salida = new PrintWriter(new FileWriter("imagenes.out"));
		salida.println(compresor.getImagenDescomprimida());
		salida.println(compresor.getImagenComprimida());
		salida.close();
	}
	
}

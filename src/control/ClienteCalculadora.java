package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteCalculadora {
	public static void main(String[] args){
		Socket socket;
		
		Scanner leitor = new Scanner(System.in);
		
		InputStream canalEntrada;
		OutputStream canalSaida;
		PrintWriter saida;
		BufferedReader entrada;
		
		System.out.println("Digite uma operacao (ex.: 1 + 1 * a - f ...):");
		String leitura = leitor.nextLine();
		
		leitor.close();
		
		try {
			socket = new Socket("127.0.0.1", 4003);

			canalEntrada = socket.getInputStream();
			canalSaida = socket.getOutputStream();

			entrada = new BufferedReader(new InputStreamReader(canalEntrada));
			saida = new PrintWriter(canalSaida, true);
			
			saida.println(leitura);
			
			// Lendo as respostas do servidor
			for (;;) {
				String resultado = entrada.readLine();
				if (resultado == null)
					break;
				System.out.println(resultado);
			}
			// Termino a conexão com o servidor

			socket.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}

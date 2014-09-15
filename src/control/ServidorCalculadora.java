package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.EmptyStackException;
import java.util.Scanner;

public class ServidorCalculadora {
	private ServerSocket serverSocket;

	public ServidorCalculadora() throws IOException {
		this.serverSocket = new ServerSocket(4003);

		for (;;) {
			Socket requisicao = this.serverSocket.accept();


			InputStream canalEntrada;
			OutputStream canalSaida;
			BufferedReader entrada;
			PrintWriter saida;

			try {
				canalEntrada = requisicao.getInputStream();
				canalSaida = requisicao.getOutputStream();

				entrada = new BufferedReader(new InputStreamReader(canalEntrada));
				saida = new PrintWriter(canalSaida, true);

				saida.println("Requisição Aceita pelo Servidor");

				while (true) {
					String linhaPedido = entrada.readLine();

					if (linhaPedido == null || linhaPedido.length() == 0)
						break;

					String reg = "((?<=[<=|>=|==|\\+|\\*|\\-|<|>|/|=])|(?=[<=|>=|==|\\+|\\*|\\-|<|>|/|=]))";

					ParseadorCalculadora parser = new ParseadorCalculadora();

					System.out.println("Digite sua expressão");
					String[] input = linhaPedido .split(reg);
					String[] output = parser.expToRPN(input);
					System.out.print("Arvore: ");
					for (String token : output) {
						System.out.print("[ ");System.out.print(token + " "); System.out.print("]");
					}
					System.out.println(" ");
					// Feed the RPN string to RPNtoDouble to give result
					Double retorno = parser.RPNtoDouble( output );
					saida.println("Resultado: " + retorno);

				}

				//				requisicao.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}

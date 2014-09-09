package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorCalculadora {
	private ServerSocket serverSocket;

	public ServidorCalculadora() throws IOException {
		this.serverSocket = new ServerSocket(4003);

		for (;;) {
			Socket requisicao = this.serverSocket.accept();

			Calculadora calc = new Calculadora();
			
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
					
					String[] str = linhaPedido.split(" ");
					
					if (str.length > 3)
						break;
					
					double valor1 = Double.parseDouble(str[0]);
					double valor2 = Double.parseDouble(str[2]);
					
					String operacao = str[1];
					
					double retorno = 0;
					
					if(operacao.equals("+")){
						retorno = calc.somar(valor1, valor2);
					} else if(operacao.equals("-")){
						retorno = calc.subtrair(valor1, valor2);
					} else if(operacao.equals("*")){
						retorno = calc.multiplicar(valor1, valor2);
					} else if(operacao.equals("/")){
						retorno = calc.dividir(valor1, valor2);
					}
					
					saida.println("Resultado: " + retorno);
				}
				
//				requisicao.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}

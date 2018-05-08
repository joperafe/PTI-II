import java.net.*;
import java.io.*;
import java.lang.Runnable.*;

class ThreadedServer implements Runnable
{
	Socket socket = new Socket();

	public ThreadedServer(Socket socket)
	{
		this.socket=socket;
	}

	public void run()
	{
		try
		{
			InputStreamReader ir = new InputStreamReader(socket.getInputStream());
			PrintStream ps = new PrintStream(socket.getOutputStream());
			BufferedReader br = new BufferedReader(ir);

			String pedidoDesporto = "Desporto";
			String pedidoCultura = "Cultura";

			String[] noticiasDesporto = {"Porto Campeão Liga NOS", "Oliveirense primeira classificada na Fase Regular da LPB", "Real Madrid na final da Champions"};
			String[] noticiasCultura = {"Daniel Sampaio apresenta novo livro", "Diretora Geral das Artes demitida", "Nobel da literatura adiado"};

			if(br.readLine().equals(pedidoCultura))
			{
				ps.println(noticiasCultura[0]);
				ps.println(noticiasCultura[1]);
				ps.println(noticiasCultura[2]);
			}
			else
			{
				ps.println(noticiasDesporto[0]);
				ps.println(noticiasDesporto[1]);
				ps.println(noticiasDesporto[2]);
			}
		
			ps.close();
			ir.close();
			socket.close();		
		}catch (IOException e){} catch (NullPointerException e){}
	}
}

public class ServidorNoticias1
{
	public static void main(String[] args)
	{
		try
		{
			ServerSocket serverSocket = new ServerSocket(3333);
			while(true)
			{
				Socket socket = serverSocket.accept();
				ThreadedServer threadedServer = new ThreadedServer(socket);
				Thread t1 = new Thread(threadedServer);
				t1.start();
			}
		}catch (IOException e){}
	}
}
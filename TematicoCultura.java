import java.net.*;
import java.io.*;
import java.lang.Runnable.*;
import java.net.InetAddress;

public class TematicoCultura
{
	public static void main(String[] args)
	{
		try
		{
			//n1
			String serv1 = "2001:0690:2280:0822:200:ff:feaa:2";
			//n2
			String serv2 = "2001:0690:2280:0822:200:ff:feaa:0";
			String pedidoCultura = "Cultura";
			int k=2;
			int timeout=300;

			do
			{
				try
				{
					if(InetAddress.getByName(serv1).isReachable(timeout))
					{
						k=0;
					}
					else if(InetAddress.getByName(serv2).isReachable(timeout))
					{
						k=1;
					}
				}catch (UnknownHostException e){}
			}while(k==2);
			
			if(k==0)
			{
				//n1
				Socket socket1 = new Socket("2001:0690:2280:0822:200:ff:feaa:2", 3333);
				InputStreamReader ir1 = new InputStreamReader(socket1.getInputStream());
				PrintStream ps1 = new PrintStream(socket1.getOutputStream());
				BufferedReader br1 = new BufferedReader(ir1);

				ps1.println(pedidoCultura);

				System.out.println("Recebida noticia: " + br1.readLine());
				System.out.println("Recebida noticia: " + br1.readLine());
				System.out.println("Recebida noticia: " + br1.readLine());

				ir1.close();
				ps1.close();
				socket1.close();
			}
			if(k==1)
			{
				//n2
				Socket socket2 = new Socket("2001:0690:2280:0822:200:ff:feaa:0", 4444);
				InputStreamReader ir2 = new InputStreamReader(socket2.getInputStream());
				PrintStream ps2 = new PrintStream(socket2.getOutputStream());
				BufferedReader br2 = new BufferedReader(ir2);

				ps2.println(pedidoCultura);

				System.out.println("Recebida noticia: " + br2.readLine());
				System.out.println("Recebida noticia: " + br2.readLine());
				System.out.println("Recebida noticia: " + br2.readLine());

				ir2.close();
				ps2.close();
				socket2.close();
			}
		}catch (IOException e){} catch (NullPointerException e){}
	}
}
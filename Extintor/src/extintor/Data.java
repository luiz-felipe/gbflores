package extintor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.csvreader.CsvReader;

public class Data {

	static String fileName = "extintores.csv";

	public static ArrayList<String> id = new ArrayList<String>();
	public static ArrayList<String> cliente = new ArrayList<String>();
	public static ArrayList<String> clienteID = new ArrayList<String>();
	public static ArrayList<String> extintorClasse = new ArrayList<String>();
	public static ArrayList<String> extintorCarga = new ArrayList<String>();
	public static ArrayList<String> extintorCapacidade = new ArrayList<String>();
	public static ArrayList<String> extintorVencimento = new ArrayList<String>();
	public static ArrayList<String> extintorFabricacao = new ArrayList<String>();
	public static ArrayList<String> extintorUltimaAtualizacao = new ArrayList<String>();
	public static ArrayList<String> extintorPrimeiraAtualizacao = new ArrayList<String>();
	public static String headers[];
	
	
	
//	Data de fabrica��o do extintor(oculto)
//	Endere�o do cliente (Oculto)

	public Data() {
		read();
	}

	public static void read(){

		try{
			CsvReader reader = null;

			File fileTeste = new File(fileName);
			if(fileTeste.exists()){
				reader = new CsvReader(fileName);
			}
			else {
				reader = new CsvReader(Data.class.getResourceAsStream(fileName),
						Charset.defaultCharset());
			}

			reader.readHeaders();
			headers = getItensHeaders();

			while(reader.readRecord()){

				Data.id.add( reader.get(0));
				Data.cliente.add( reader.get(1));
				Data.clienteID.add( reader.get(2));
				Data.extintorClasse.add( reader.get(3));
				Data.extintorCarga.add( reader.get(4));
				Data.extintorCapacidade.add( reader.get(5));
				Data.extintorVencimento.add( reader.get(6));
				Data.extintorFabricacao.add( reader.get(7));
				Data.extintorUltimaAtualizacao.add( reader.get(8));
				Data.extintorPrimeiraAtualizacao.add( reader.get(9));

			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object getData(){
		Object[][] data = new Object[Data.id.size()][10];

		for (int i = 0; i < Data.id.size(); i++) {	
			data[i][0] = Data.id.get(i);
			data[i][1] = Data.cliente.get(i);
			data[i][2] = Data.clienteID.get(i);
			data[i][3] = Data.extintorClasse.get(i);
			data[i][4] = Data.extintorCarga.get(i);
			data[i][5] = Data.extintorCapacidade.get(i);
			data[i][6] = Data.extintorVencimento.get(i);
			data[i][7] = Data.extintorFabricacao.get(i);
			data[i][8] = Data.extintorUltimaAtualizacao.get(i);
			data[i][9] = Data.extintorPrimeiraAtualizacao.get(i);
		}
		return data;
	}


	public void write() {
		PrintWriter writer; // creates this writer 
		try {
			writer = new PrintWriter(new FileWriter(fileName));
			for (int i = 0; i < getItens().length; i++) {
				if (i==getItens().length-1)
					writer.print("\""+getItens()+"\"\n");
				else
					writer.print("\""+getItens()+"\",");
			}
			//			writer.println("\"First Name\",\"Last Name\",\"Sport\"," +
			//					"\"# of Years\",\"Vegetarian\",\"Date\"");

			for (int i = 0; i < id.size(); i++) {
				writer.println(Data.id.get(i)+","+
						Data.cliente.get(i)+","+
						Data.clienteID.get(i)+","+
						Data.extintorClasse.get(i)+","+
						Data.extintorCarga.get(i)+","+
						Data.extintorCapacidade.get(i)+","+
						Data.extintorVencimento.get(i)+","+
						Data.extintorFabricacao.get(i)+","+
						Data.extintorUltimaAtualizacao.get(i)+","+
						Data.extintorPrimeiraAtualizacao.get(i));
		}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String[] getItens(){
		String item[] = {
				"Id",
				"Cliente",
				"Cliente ID",
				"Classe",
				"Carga",
				"Capacidade",
				"Vencimento",
				"Fabrica��o",
				"Ultima atualiza��o",
				"Primeira atualiza��o"};
		return item;
	}
	public static String[] getItensHeaders(){
		String item[] = {
				"Id",
				"Cliente",
				"Cliente ID",
				"Classe",
				"Carga",
				"Capacidade",
				"Vencimento",
				"Fabrica��o"};
		return item;
	}


}

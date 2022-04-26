package marsana.logicmanager;


import java.io.IOException;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

/* 
  Prende una busta paga dalla cartella (lavorazioni)
  Analizza il contenuto
  Fa un insert sul db con i dati della busta paga
*/

public class BustaDataExtractor {

	private String pdfContent;
	private PdfReader pdfFile;
	private String importoNetto;
	private String dataEmissione;
	
	public BustaDataExtractor(String fPath) {
		try {
			pdfFile = new PdfReader(fPath);
			setPdfContent(PdfTextExtractor.getTextFromPage(pdfFile, 1));
		} 
		catch (IOException e) 	{
			e.printStackTrace();
		}
	}
	

	//questo metodo ritorna l'importo netto di una busta paga
	public float getImportoNetto() {
		//conoscendo la struttura di una busta paga mi posiziono in prossimità del valore sul pdf dell'importo netto
		int index = getPdfContent().indexOf("****") + 4;
		//recupero all'interno di una stringa il valore dell'importo
		importoNetto = getPdfContent().substring(index, index + 7);
		importoNetto = importoNetto.replace(",",".");
		return Float.parseFloat(importoNetto);
	}
	
	//questo metodo ritorna giorno/mese/anno di emissione
	public String getDataEmissione() {
		int index = getPdfContent().indexOf("Data") + 5;
		dataEmissione = getPdfContent().substring(index, index + 10);
		return dataEmissione;
	}
	
	//questo metodo ritorna il mese e l'anno di riferimento pagamento di una paga
	public String getPeriodoRetr() {
		int mese, anno;
		String periodoRetribuito = getDataEmissione();
		periodoRetribuito = periodoRetribuito.replace(periodoRetribuito.substring(0, 3), "");
		mese = Integer.parseInt(periodoRetribuito.substring(0,2));
		
		//la data è relativa al mese e anno di emissione e non al periodo retribuito
		//provvede a regolare l'incongruenza
		if(mese == 1) {  
			mese = 12;
			periodoRetribuito = periodoRetribuito.replace(periodoRetribuito.substring(0, 2), String.valueOf(mese));
			
			anno = Integer.valueOf(periodoRetribuito.substring(3));
			periodoRetribuito = periodoRetribuito.replace(periodoRetribuito.substring(3), String.valueOf(anno - 1));
		}
		else {
			mese -= 1;
			periodoRetribuito = periodoRetribuito.replace(periodoRetribuito.substring(0,1), String.valueOf(mese));
		}
		
		return periodoRetribuito;
	}
	
	
	
	public String getPdfContent() {
		return pdfContent;
	}


	public void setPdfContent(String pdfContent) {
		this.pdfContent = pdfContent;
	}

}

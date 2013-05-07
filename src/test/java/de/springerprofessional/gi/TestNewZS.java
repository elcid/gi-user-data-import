package de.springerprofessional.gi;

import com.bsl.CheckOutput;
import com.bsl.Subscriber;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

public class TestNewZS extends AbstractImportNewData {
	
	
	@Test
	public void testJob() throws Exception {

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		//InputStream is = loader.getResourceAsStream("newZS_29042013.csv");
		
		InputStream is = loader.getResourceAsStream("newZS_3.csv");
		Reader in = new InputStreamReader(is);
		CSVParser apparser = new CSVParser(in, CSVFormat.EXCEL);
		List<CSVRecord> records = apparser.getRecords();

		if (records.size() > 1) {
			ListIterator<CSVRecord> it = records.listIterator(1);			
			while (it.hasNext()) {
				CSVRecord rec = it.next();				
				
				Map<String, String> newZSData = setNewData(rec);
				if (MapUtils.isNotEmpty(newZSData)) {
					if (StringUtils.isNotEmpty(newZSData.get(EMAIL))) {												
						CheckOutput output = soap.checkSubscriber(securityToken, newZSData.get("USERNAME").trim());						
						if (output.getStatus() == 1) {							
							Subscriber user = soap.getSubscriberInfo(securityToken, output.getSubscriberId(), false);
							validateZS(user, newZSData, output.getSubscriberId(), rec.getRecordNumber());
						} else {
							System.out.println("NR: " + rec.getRecordNumber() + " - user unknown: " + newZSData.get(EMAIL));
						}
					} else{
						System.out.println("NR: " + rec.getRecordNumber() + " - User hat keine E-Mail Adresse");
					}
				}
				
				if (rec.getRecordNumber() % 1000 == 0) {
					System.out.println("NR: " + rec.getRecordNumber() + " - bearbeitet");
				}
			}			
			System.out.println("Process finished. Records: " + (records.size()-1));
		}
	}
	
	
	private void validateZS(Subscriber user, Map<String, String> newZSData, int subscriberId, long number) {
		try {
			Map<String, String> customProperties = getCustomProperties(user);

			if (!user.getFirstname().equals(newZSData.get(VORNAME))) {
				System.out.println("NR: " + number + " - VORNAME falsch => CSV: " + newZSData.get(VORNAME) + " - ODS: " + user.getFirstname()  + " - EMAIL: " + newZSData.get(EMAIL) + " - SID: " + subscriberId);
			}

			if (!user.getLastname().equals(newZSData.get(NAME))) {
				System.out.println("NR: " + number + " - NAME falsch: => CSV: " + newZSData.get(NAME) + " - ODS: " + user.getLastname()  + " - EMAIL: " + newZSData.get(EMAIL) + " - SID: " + subscriberId);
			}
			
			if (!customProperties.get("memberships").contains("GI:"+newZSData.get(MITGLIEDNR))) {
				System.out.println("NR: " + number + " - memberships falsch: => CSV: " + newZSData.get(MITGLIEDNR) + " - ODS: " + customProperties.get("memberships")  + " - EMAIL: " + newZSData.get(EMAIL) + " - SID: " + subscriberId);
			}
			
			if (user.getProducts() == null || !getProductCodes(user.getProducts().getProduct()).contains(newZSData.get(MATERIALNUMMER))) {
				System.out.println("NR: " + number + " - Product not available: => CSV: " + newZSData.get(MATERIALNUMMER) + " - EMAIL: " + newZSData.get(EMAIL) + " - SID: " + subscriberId);
			} 							
												
		} catch (Exception e) {
			System.out.println("NR: " + number + " - Fehler bei Validierung: " + newZSData.get(EMAIL) + " - SID: " + subscriberId);
		}
	}
	
	protected Map<String, String> setNewData(CSVRecord record) {
		Map<String, String> newZSData = new HashMap<String, String>();
		Iterator<String> it = record.iterator();

        for (String newZSVariable : newZSVariables) {
            if (it.hasNext())
                newZSData.put(newZSVariable, it.next());
        }
		return newZSData;
	}
}

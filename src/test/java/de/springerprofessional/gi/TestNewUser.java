package de.springerprofessional.gi;

import com.bsl.LoginOutput;
import com.bsl.Subscriber;
import de.springerprofessional.gi.model.Gender;
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

public class TestNewUser extends AbstractImportNewData {
	
	@Test
	public void testJob() throws Exception {

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		//InputStream is = loader.getResourceAsStream("newUser_29042013.csv");
		
		InputStream is = loader.getResourceAsStream("newUser_3.csv");
		Reader in = new InputStreamReader(is);
		CSVParser apparser = new CSVParser(in, CSVFormat.EXCEL);
		List<CSVRecord> records = apparser.getRecords();

		if (records.size() > 1) {
			ListIterator<CSVRecord> it = records.listIterator(1);			
			while (it.hasNext()) {
				CSVRecord rec = it.next();				
				Map<String, String> newSubscriberData = setNewData(rec);
				if (MapUtils.isNotEmpty(newSubscriberData)) {
					if (StringUtils.isNotEmpty(newSubscriberData.get(EMAIL))) {												
						LoginOutput output = soap.loginSubscriber(securityToken, newSubscriberData.get(USERNAME).trim(), newSubscriberData.get(PASSWORT), "");						
						if (output.getStatus() != 1) {
							switch (output.getStatus() ) {
							case -1:
								System.out.println("NR: " + rec.getRecordNumber() + " - user unknown: " + newSubscriberData.get(EMAIL));
								break;								
							case -2:
								System.out.println("NR: " + rec.getRecordNumber() + " - password wrong: " + newSubscriberData.get(EMAIL) + " - Password: " + newSubscriberData.get("PASSWORT"));
								break;															
							case -4:
								System.out.println("NR: " + rec.getRecordNumber() + " - subscriber is not active: " + newSubscriberData.get(EMAIL));
								break;
							default:
								System.out.println("NR: " + rec.getRecordNumber() + " - Unknown error: " + newSubscriberData.get(EMAIL));
								break;
							}
						} else {
							Subscriber user = soap.getSubscriberInfo(securityToken, output.getSubscriberId(), false);
							validateSubscriber(user, newSubscriberData, output.getSubscriberId(), rec.getRecordNumber());
						}
					} else{
						System.out.println("NR: " + rec.getRecordNumber() + " - User hat keine E-Mail Adresse");
					}
				}
			}			
			System.out.println("process finished");
		}
	}
						
	private void validateSubscriber(Subscriber user, Map<String, String> newSubscriberData, int subscriberId, long number) {						
		
		try {
			Map<String, String> customProperties = getCustomProperties(user);

			if (!user.getFirstname().equals(newSubscriberData.get(VORNAME))) {
				System.out.println("NR: " + number + " - VORNAME falsch => CSV: " + newSubscriberData.get(VORNAME) + " - ODS: " + user.getFirstname()  + " - EMAIL: " + newSubscriberData.get(EMAIL) + " - SID: " + subscriberId);
			}

			if (!user.getLastname().equals(newSubscriberData.get(NAME))) {
				System.out.println("NR: " + number + " - NAME falsch: => CSV: " + newSubscriberData.get(NAME) + " - ODS: " + user.getLastname()  + " - EMAIL: " + newSubscriberData.get(EMAIL) + " - SID: " + subscriberId);
			}
			
			
			if (!user.getGender().equals(getGenderInODSFormat(newSubscriberData.get(ANREDE)))) {
				System.out.println("NR: " + number + " - Anrede falsch: => CSV: " + Gender.valueOf(ANREDE) + " - ODS: " + user.getGender() + newSubscriberData.get(EMAIL) + " - SID: " + subscriberId);
			}


			if (StringUtils.isNotEmpty(newSubscriberData.get(TITEL))) {
				if (StringUtils.isNotEmpty(customProperties.get("title"))) {
					if(!customProperties.get("title").equals(newSubscriberData.get(TITEL))) {
						System.out.println("NR: " + number + " - Titel falsch => CSV: " + newSubscriberData.get(TITEL) + " - ODS: " + customProperties.get("title")  + " - EMAIL: " + newSubscriberData.get(EMAIL) + " - SID: " + subscriberId);
					}
				} else {
					System.out.println("NR: " + number + " - Titel nicht gesetzt: " + newSubscriberData.get(EMAIL) + " - " + subscriberId);
				}
			}

			if (!customProperties.get(SPECIALTYCODE).equals(newSubscriberData.get(SPECIALTYCODE))) {
				System.out.println("NR: " + number + " - specialtyCode falsch: => CSV: " + newSubscriberData.get(SPECIALTYCODE) + " - ODS: " + customProperties.get(SPECIALTYCODE)  + " - EMAIL: " + newSubscriberData.get(EMAIL) + " - SID: " + subscriberId);
			}

			if (!customProperties.get("memberships").contains("GI:"+newSubscriberData.get(MITGLIEDNR))) {
				System.out.println("NR: " + number + " - memberships falsch: => CSV: " + newSubscriberData.get(MITGLIEDNR) + " - ODS: " + customProperties.get("memberships")  + " - EMAIL: " + newSubscriberData.get(EMAIL) + " - SID: " + subscriberId);
			}
		} catch (Exception e) {
			System.out.println("NR: " + number + " - Fehler bei Validierung: " + newSubscriberData.get(EMAIL) + " - SID: " + subscriberId);
		}
	}
		
	protected Map<String, String> setNewData(CSVRecord record) {
		Map<String, String> newSubscriberData = new HashMap<String, String>();
		Iterator<String> it = record.iterator();

        for (String newSubscriberVariable : newSubscriberVariables) {
            if (it.hasNext())
                newSubscriberData.put(newSubscriberVariable, it.next());
        }
		return newSubscriberData;
	}
}

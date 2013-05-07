package de.springerprofessional.gi;

import com.bsl.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


public abstract class AbstractImportNewData {
	
	protected static List<String> newZSVariables = new LinkedList<String>();
	protected static List<String> newSubscriberVariables = new LinkedList<String>();
	protected static String securityToken = "E1E3E91C-4BC0-46BF-8A26-72667FFB4A28";		
		
	protected static final Map<String, String> ODS_GENDER_MAP = new HashMap<String, String>(3);
	
	protected static final String MITGLIEDNR = "MITGLIEDNR";
	protected static final String ANREDE = "ANREDE";
	protected static final String TITEL = "TITEL";
	protected static final String VORNAME = "VORNAME";
	protected static final String NAME = "NAME";		
	protected static final String EMAIL = "EMAIL";
	protected static final String USERNAME = "USERNAME";
	protected static final String MATERIALNUMMER = "Materialnummer";
	protected static final String BEGINN = "Beginn";
	protected static final String ENDE = "Ende";
	protected static final String USERSTATUS = "Userstatus";	
	protected static final String FIRMA = "FIRMA";
	protected static final String STRASSE = "STRASSE";	
	protected static final String HAUSNUMMER = "HAUSNUMMER";	
	protected static final String PLZ = "PLZ";
	protected static final String ORT = "ORT";
	protected static final String LAND = "LAND";	
	protected static final String PASSWORT = "PASSWORT";
	protected static final String SPECIALTYCODE = "specialtyCode";
	protected static final String ACCEPTAGB = "acceptAgb";
		
	static final String UMS_GENDER_FEMALE = "Frau";
	static final String UMS_GENDER_MALE = "Herr";
	static final String UMS_GENDER_COMPANY = "Firma";
	static final String ODS_GENDER_FEMALE = "F";
	static final String ODS_GENDER_MALE = "M";
	static final String ODS_GENDER_UNKNOWN = "U";
				
	static {
		newZSVariables.add(MITGLIEDNR);
		newZSVariables.add(ANREDE);
		newZSVariables.add(TITEL);
		newZSVariables.add(VORNAME);
		newZSVariables.add(NAME);		
		newZSVariables.add(EMAIL);
		newZSVariables.add(USERNAME);
		newZSVariables.add(MATERIALNUMMER);
		newZSVariables.add(BEGINN);
		newZSVariables.add(ENDE);
		newZSVariables.add(USERSTATUS);
		
		newSubscriberVariables.add(MITGLIEDNR);
		newSubscriberVariables.add(ANREDE);
		newSubscriberVariables.add(TITEL);
		newSubscriberVariables.add(VORNAME);
		newSubscriberVariables.add(NAME);
		newSubscriberVariables.add(FIRMA);
		newSubscriberVariables.add(STRASSE);
		newSubscriberVariables.add(HAUSNUMMER);
		newSubscriberVariables.add(PLZ);
		newSubscriberVariables.add(ORT);
		newSubscriberVariables.add(LAND);
		newSubscriberVariables.add(EMAIL);
		newSubscriberVariables.add(USERNAME);
		newSubscriberVariables.add(PASSWORT);
		newSubscriberVariables.add(SPECIALTYCODE);
		newSubscriberVariables.add(ACCEPTAGB);
		newSubscriberVariables.add(USERSTATUS);
		
		ODS_GENDER_MAP.put(UMS_GENDER_MALE, ODS_GENDER_MALE);
		ODS_GENDER_MAP.put(UMS_GENDER_FEMALE, ODS_GENDER_FEMALE);
		ODS_GENDER_MAP.put(UMS_GENDER_COMPANY, ODS_GENDER_UNKNOWN);
	}
	
	protected Services odsService = new Services();
	protected ServicesSoap soap = odsService.getServicesSoap();
	
	protected Map<String, String> getCustomProperties(Subscriber user) {
		Map<String, String> customProperties = new HashMap<String, String>();
		ArrayOfProperty properties = user.getProperties();
		if (!isEmpty(properties)) {
			customProperties = new HashMap<String, String>(properties.getProperty().size());
			for (Property property : properties.getProperty()) {
				customProperties.put(property.getName(), property.getValue());
			}
		}
		return customProperties;
	}
	
	
	protected abstract Map<String, String> setNewData(CSVRecord record);
	
	private boolean isEmpty(ArrayOfProperty properties) {
		return properties == null || CollectionUtils.isEmpty(properties.getProperty());
	}
	
	protected String getGenderInODSFormat(String gender) {
		if (StringUtils.isBlank(gender)) {
			return StringUtils.EMPTY;
		}
		return StringUtils.isNotEmpty(ODS_GENDER_MAP.get(gender)) ? ODS_GENDER_MAP.get(gender) : gender;
	}
	
	protected List<String> getProductCodes(List<Product> productList) {
		List<String> productCodes = new ArrayList<String>();		
		for (Product product : productList) {
			productCodes.add(product.getProductCode());			
		}
		return productCodes;		
	}


}

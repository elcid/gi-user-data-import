package de.springerprofessional.gi.webservice;

import com.bsl.*;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WSHelper {

    protected static final String SECURITY_TOKEN = "E1E3E91C-4BC0-46BF-8A26-72667FFB4A28";
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

    private boolean isEmpty(ArrayOfProperty properties) {
        return properties == null || CollectionUtils.isEmpty(properties.getProperty());
    }

    protected List<String> getProductCodes(List<Product> productList) {
        List<String> productCodes = new ArrayList<String>();
        for (Product product : productList) {
            productCodes.add(product.getProductCode());
        }
        return productCodes;
    }
}

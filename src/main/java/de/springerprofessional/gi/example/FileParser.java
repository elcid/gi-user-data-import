package de.springerprofessional.gi.example;


import de.springerprofessional.gi.example.GIUser;
import de.springerprofessional.gi.example.GIUserItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Component
public class FileParser {

	public List<GIUser> parse(String content) {
		List<GIUser> result = new ArrayList<GIUser>();
		StringTokenizer stringTokenizer = new StringTokenizer(content, ",");
		while (stringTokenizer.hasMoreTokens()) {
			GIUser giUser = new GIUser();
			giUser.addOrderItem(new GIUserItem(Integer.parseInt(stringTokenizer
                    .nextToken()), stringTokenizer.nextToken(), Integer
                    .parseInt(stringTokenizer.nextToken())));
            result.add(giUser);
		}
		return result;
	}

}

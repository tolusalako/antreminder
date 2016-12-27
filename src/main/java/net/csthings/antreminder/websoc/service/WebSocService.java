/** Copyright (c) 2016 SalakoTech.
 * This file and all of its contents belong to SalakoTech and should not be shared.
 * Created on: Jul 2, 2016
 * @author Toluwanimi Salako
 * Last edited: Jul 2, 2016
 */
package net.csthings.antreminder.websoc.service;

import org.springframework.stereotype.Service;

@Service("webSocService")
public interface WebSocService {

    public String generateInnerFormHtml();

}

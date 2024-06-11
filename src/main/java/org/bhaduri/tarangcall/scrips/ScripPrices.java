/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangcall.scrips;

import java.util.List;
import org.bhaduri.tarangcall.generate.CallFactory;
import org.bhaduri.tarangdbservice.services.MasterDataServices;
import org.bhaduri.tarangdto.CallResultsIntermediate;
import org.bhaduri.tarangdto.ScripsDTO;

/**
 *
 * @author bhaduri
 */
public class ScripPrices {
    private ScripsDTO scripsDTO;

    public ScripPrices(ScripsDTO scripsDTO) {
        this.scripsDTO = scripsDTO;
    }
    
    
    public CallFactory getCallFactory() {
        MasterDataServices mds = new MasterDataServices();
        
        CallResultsIntermediate callResultsInermediate = mds.getLastTransactionPriceList(scripsDTO.getScripId());
        CallFactory callFactory = new CallFactory(callResultsInermediate);
        return callFactory;
    }
    
}

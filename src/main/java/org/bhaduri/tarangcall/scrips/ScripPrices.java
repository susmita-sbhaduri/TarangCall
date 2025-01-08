/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangcall.scrips;


import org.bhaduri.tarangcall.generate.CallFactory;
import org.bhaduri.tarangdbservice.services.MasterDataServices;
import org.bhaduri.tarangdto.CallResultsIntermediate;
import org.bhaduri.tarangdto.ScripsDTO;
import org.bhaduri.tarangdto.VolumeIntermediate;

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
        
        VolumeIntermediate listOfVolumePerScripId = mds.getLastTransactionVolumeList(scripsDTO.getScripId());
//        CallFactory callFactory = new CallFactory(callResultsInermediate);
        CallFactory callFactory = new CallFactory(callResultsInermediate, listOfVolumePerScripId);
        return callFactory;
    }
    
}

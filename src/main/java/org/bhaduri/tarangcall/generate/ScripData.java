/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangcall.generate;

import java.util.List;
import org.bhaduri.tarangdbservice.services.MasterDataServices;
import org.bhaduri.tarangdto.LastTransactionPrice;

/**
 *
 * @author bhaduri
 */
public class ScripData {
    private List<LastTransactionPrice> lastTransactrionPriceList;
    String scripid;
    

    public ScripData(String scripid) {
        MasterDataServices mds = new MasterDataServices();
        
        this.lastTransactrionPriceList = mds.getLastTransactionPriceList(scripid);
    }
    
    public SmoothAndCallCreation smoothAndCallCreation() {
        SmoothAndCallCreation smoothAndCallCreation = new SmoothAndCallCreation(lastTransactrionPriceList);
        return smoothAndCallCreation;
    }
    
    
    
}

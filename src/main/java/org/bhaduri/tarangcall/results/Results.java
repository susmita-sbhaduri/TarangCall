/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangcall.results;

import org.bhaduri.tarangdbservice.services.MasterDataServices;
import org.bhaduri.tarangdto.CallResults;

/**
 *
 * @author bhaduri
 */
public class Results {
    private CallResults callResults;

    public Results(CallResults callResults) {
        this.callResults = callResults;
    }
    
    public void insertResults() {
        MasterDataServices mds = new MasterDataServices();
        mds.insterCallsInTable(callResults);
    }
    
}

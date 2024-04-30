/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangcall.generate;

import java.util.List;
import org.bhaduri.tarangcall.TARANGPARAMS;
import org.bhaduri.tarangcall.utils.TarangUtils;
import org.bhaduri.tarangdbservice.services.MasterDataServices;
import org.bhaduri.tarangdto.CallResultsInermediate;
import org.bhaduri.tarangdto.LastTransactionPrice;

/**
 *
 * @author bhaduri
 */
public class CallGenerationLayers {

    
    private String callVersionTwo;
    private Double retraceVersionTwo;
    private String callVersionOne;
    private Double retraceVersionOne;
    private String scripid;

    public CallGenerationLayers(String scripid) {
        this.scripid = scripid;
    }

    public void smoothAndCallCreate() {
        List<LastTransactionPrice> lastTransactrionPriceList = getScripLastTransactionPriceList(scripid);
        CallResultsInermediate callResultsInermediate = new CallResultsInermediate(lastTransactrionPriceList);
        

        for (int i = 1; i < TARANGPARAMS.CALL_GENERATION_LAYERS + 1; i++) {

            callResultsInermediate = new SmoothData(callResultsInermediate.getIntermediateLTPList(), i).removeDupsAndKeepReversals().generateCalls();
            TarangUtils.printLTP(callResultsInermediate.getIntermediateLTPList());

        }
        callVersionOne = callResultsInermediate.getLastCallVersionOne();
        retraceVersionOne = callResultsInermediate.getRetraceVersionOne();
        callVersionTwo = callResultsInermediate.getLastCallVersionTwo();
        retraceVersionTwo = callResultsInermediate.getRetraceVersionTwo();
        
    }
    
    private List<LastTransactionPrice> getScripLastTransactionPriceList(String scripid) {
        MasterDataServices mds = new MasterDataServices();
        
        List<LastTransactionPrice> lastTransactrionPriceList = mds.getLastTransactionPriceList(scripid);
        return lastTransactrionPriceList;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangcall.generate;

import java.util.Date;
import java.util.List;
import org.bhaduri.tarangcall.TARANGPARAMS;
import org.bhaduri.tarangcall.utils.TarangUtils;
import org.bhaduri.tarangdbservice.services.MasterDataServices;
import org.bhaduri.tarangdto.CallResults;
import org.bhaduri.tarangdto.CallResultsIntermediate;
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
    private Date callGenerationTimeStamp;
    private String scripid;

    public CallGenerationLayers(String scripid) {
        this.scripid = scripid;
    }

    public void smoothAndCallCreate() {
        CallResultsIntermediate callResultsIntermediate = getScripLastTransactionPriceList(scripid);
        
        

        for (int i = 1; i < TARANGPARAMS.CALL_GENERATION_LAYERS + 1; i++) {

            callResultsIntermediate = new SmoothData(callResultsIntermediate, i).removeDupsAndKeepReversals().generateCalls();
            //TarangUtils.printLTP(callResultsIntermediate.getIntermediateLTPList());

        }
        callVersionOne = callResultsIntermediate.getCallVersionOne();
        retraceVersionOne = callResultsIntermediate.getRetraceVersionOne();
        callVersionTwo = callResultsIntermediate.getCallVersionTwo();
        retraceVersionTwo = callResultsIntermediate.getRetraceVersionTwo();
        callGenerationTimeStamp = callResultsIntermediate.getCallGenerationTimeStamp();
        System.out.println(callVersionOne+" "+callVersionTwo+" "+callGenerationTimeStamp);
        CallResults callResults = callResultsIntermediate;
        
    }
    
    private CallResultsIntermediate getScripLastTransactionPriceList(String scripid) {
        MasterDataServices mds = new MasterDataServices();
        
        CallResultsIntermediate callResultsInermediate = mds.getLastTransactionPriceList(scripid);
        return callResultsInermediate;
    }
}

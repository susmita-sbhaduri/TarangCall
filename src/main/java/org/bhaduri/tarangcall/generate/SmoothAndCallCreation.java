/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangcall.generate;

import java.util.List;
import org.bhaduri.tarangcall.TARANGPARAMS;
import org.bhaduri.tarangcall.utils.TarangUtils;
import org.bhaduri.tarangdto.CallResultsForEachLayer;
import org.bhaduri.tarangdto.LastTransactionPrice;

/**
 *
 * @author bhaduri
 */
public class SmoothAndCallCreation {

    private List<LastTransactionPrice> lastTransactrionPriceList;


    public SmoothAndCallCreation(List<LastTransactionPrice> lastTransactrionPriceList) {
        this.lastTransactrionPriceList = lastTransactrionPriceList;
    }

    public void smoothAndCallCreate() {
        CallResultsForEachLayer callResultsForEachLayer = new CallResultsForEachLayer();
        callResultsForEachLayer.setOutputLastTransationPriceList(lastTransactrionPriceList);
        
        for (int i = 1; i < TARANGPARAMS.CALL_GENERATION_LAYERS + 1; i++) {
            
            callResultsForEachLayer = new SmoothData(callResultsForEachLayer.getOutputLastTransationPriceList(), i).removeDupsAndKeepReversals().generateCalls();
            TarangUtils.printLTP(callResultsForEachLayer.getOutputLastTransationPriceList());
            
        }
    }
}

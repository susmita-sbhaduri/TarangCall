/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangcall.generate;

import java.util.ArrayList;
import java.util.List;
import org.bhaduri.tarangcall.TARANGPARAMS;
import org.bhaduri.tarangdto.LastTransactionPrice;

/**
 *
 * @author bhaduri
 */
public class SmoothData {

    private List<LastTransactionPrice> lastTransactrionPriceList;
    private int callGenerationLavel;
    public SmoothData(List<LastTransactionPrice> lastTransactrionPriceList, int callGenerationLavel) {
        this.lastTransactrionPriceList = lastTransactrionPriceList;
        this.callGenerationLavel = callGenerationLavel;
    }

    public CallCreation removeDupsAndKeepReversals() {
        List<Double> consecutiveDiffLTP = new ArrayList<>();
        for (int i = 0; i < lastTransactrionPriceList.size() - 1; i++) {
            consecutiveDiffLTP.add(lastTransactrionPriceList.get(i + 1).getLastTransactionPrice() - lastTransactrionPriceList.get(i).getLastTransactionPrice());
        }
        List<LastTransactionPrice> trendReversalPricesWithNoConsDups = new ArrayList<>();
        
        //add the first item as there is nothing to smooth
        int  smoothenedLTPCounter= 0;        
        trendReversalPricesWithNoConsDups.add(lastTransactrionPriceList.get(smoothenedLTPCounter));
        
        int reversalFlag = TARANGPARAMS.UNKNOWN_TREND;
        while (smoothenedLTPCounter < consecutiveDiffLTP.size()) {
            
            //process trend reversal towards up
            if (consecutiveDiffLTP.get(smoothenedLTPCounter)> 0) {
                //if previous trend is downtrend trend reversal is present as this is uptrend
                if (reversalFlag == TARANGPARAMS.DOWNTREND) {
                    trendReversalPricesWithNoConsDups.add(lastTransactrionPriceList.get(smoothenedLTPCounter));
                }
                reversalFlag = TARANGPARAMS.UPTREND;
            }
            //process trend reversal towards down
            if (consecutiveDiffLTP.get(smoothenedLTPCounter)< 0) {
                //if previous trend is uptrend trend reversal is present as this is downtrend
                if (reversalFlag == TARANGPARAMS.UPTREND) {
                    trendReversalPricesWithNoConsDups.add(lastTransactrionPriceList.get(smoothenedLTPCounter));
                }
                reversalFlag = TARANGPARAMS.DOWNTREND;
            }
            smoothenedLTPCounter++;
        }
        trendReversalPricesWithNoConsDups.add(lastTransactrionPriceList.get(smoothenedLTPCounter));
        CallCreation callCreation = new CallCreation(trendReversalPricesWithNoConsDups,callGenerationLavel);
        return callCreation;
        //return (trendReversalPricesWithNoConsDups);
        //TarangUtils.printLTP(trendReversalPricesWithNoConsDups);
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangcall.generate;

import java.util.ArrayList;
import java.util.List;
import org.bhaduri.tarangcall.TARANGPARAMS;
import org.bhaduri.tarangdto.CallResultsIntermediate;
import org.bhaduri.tarangdto.LastTransactionPrice;
import org.bhaduri.tarangdto.LastTransactionVolume;
import org.bhaduri.tarangdto.VolumeIntermediate;

/**
 *
 * @author bhaduri
 */
public class SmoothData {
    CallResultsIntermediate callResultsIntermediate;
    private List<LastTransactionPrice> lastTransactrionPriceList;
    private int callGenerationLavel;
    private int volumeTrendFlag;
    private List<LastTransactionVolume> lastTransactrionVolumeList;
    private VolumeIntermediate listOfVolumePerScripIdIntermediate;
    
    public SmoothData(CallResultsIntermediate callResultsIntermediate, int callGenerationLavel, 
            int volumeTrendFlag) {
        this.callResultsIntermediate = callResultsIntermediate;
        this.callGenerationLavel = callGenerationLavel;
        this.volumeTrendFlag = volumeTrendFlag;
        
    }
    
    public SmoothData(VolumeIntermediate listOfVolumePerScripIdIntermediate) {        
        this.listOfVolumePerScripIdIntermediate = listOfVolumePerScripIdIntermediate;
    }

    public Trends removeDupsAndKeepReversals() {
        lastTransactrionPriceList = callResultsIntermediate.getIntermediateLTPList();
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
        callResultsIntermediate.setIntermediateLTPList(trendReversalPricesWithNoConsDups);
        Trends callCreation = new Trends(callResultsIntermediate,callGenerationLavel,volumeTrendFlag);
        return callCreation;
        //return (trendReversalPricesWithNoConsDups);
        //TarangUtils.printLTP(trendReversalPricesWithNoConsDups);
    }
    
    public VolumeIntermediate removeDupsAndKeepReversalsVolume() {
        lastTransactrionVolumeList = listOfVolumePerScripIdIntermediate.getIntermediateVolumeList();
//        List<int> lastTransactrionVolumeList = new ArrayList<>();
        List<Integer> consecutiveDiffVolume = new ArrayList<>();
        for (int i = 0; i < lastTransactrionVolumeList.size() - 1; i++) {
            consecutiveDiffVolume.add(lastTransactrionVolumeList.get(i + 1).getLastTransactionVolume() - lastTransactrionVolumeList.get(i).getLastTransactionVolume());
        }
        List<LastTransactionVolume> trendReversalVolumeWithNoConsDups = new ArrayList<>();
        
        //add the first item as there is nothing to smooth
        int  smoothenedVolumeCounter= 0;        
        trendReversalVolumeWithNoConsDups.add(lastTransactrionVolumeList.get(smoothenedVolumeCounter));
        
        int reversalFlag = TARANGPARAMS.UNKNOWN_TREND_VOL;
        while (smoothenedVolumeCounter < consecutiveDiffVolume.size()) {
            
            //process trend reversal towards up
            if (consecutiveDiffVolume.get(smoothenedVolumeCounter)> 0) {
                //if previous trend is downtrend trend reversal is present as this is uptrend
                if (reversalFlag == TARANGPARAMS.DOWNTREND_VOLUME) {
                    trendReversalVolumeWithNoConsDups.add(lastTransactrionVolumeList.get(smoothenedVolumeCounter));
                }
                reversalFlag = TARANGPARAMS.UPTREND_VOLUME;
            }
            //process trend reversal towards down
            if (consecutiveDiffVolume.get(smoothenedVolumeCounter)< 0) {
                //if previous trend is uptrend trend reversal is present as this is downtrend
                if (reversalFlag == TARANGPARAMS.UPTREND_VOLUME) {
                    trendReversalVolumeWithNoConsDups.add(lastTransactrionVolumeList.get(smoothenedVolumeCounter));
                }
                reversalFlag = TARANGPARAMS.DOWNTREND_VOLUME;
            }
            smoothenedVolumeCounter++;
        }
        trendReversalVolumeWithNoConsDups.add(lastTransactrionVolumeList.get(smoothenedVolumeCounter));
        listOfVolumePerScripIdIntermediate.setIntermediateVolumeList(trendReversalVolumeWithNoConsDups);
        listOfVolumePerScripIdIntermediate.setTrendFlag(reversalFlag);
//        
        return listOfVolumePerScripIdIntermediate;
        //TarangUtils.printLTP(trendReversalPricesWithNoConsDups);
    }

}

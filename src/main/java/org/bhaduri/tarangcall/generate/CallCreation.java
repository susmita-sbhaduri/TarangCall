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

/**
 *
 * @author bhaduri
 */
public class CallCreation {
    
    private CallResultsIntermediate callResultsIntermediate;
    private List<LastTransactionPrice> inputLastTransationPriceList;
    private int callGenerationLavel;
    private List<LastTransactionPrice> outputLastTransationPriceList;

    public CallCreation(CallResultsIntermediate callResultsIntermediate, int callGenerationLavel) {
        this.callResultsIntermediate = callResultsIntermediate;
        
        this.callGenerationLavel = callGenerationLavel;
    }

    public CallResultsIntermediate generateCalls() {
        //TarangUtils.printLTP(inputLastTransationPriceList);
        this.inputLastTransationPriceList = callResultsIntermediate.getIntermediateLTPList();
        outputLastTransationPriceList = new ArrayList<>();
        outputLastTransationPriceList.add(inputLastTransationPriceList.get(0));
        int startLevelOne = 0;
        int trendFlag = TARANGPARAMS.UNKNOWN_TREND;
        String call = "";
        //process the first 2nd 3rd and 4th data points
        if (inputLastTransationPriceList.get(1).getLastTransactionPrice() > inputLastTransationPriceList.get(2).getLastTransactionPrice()) {
            startLevelOne = 3; //starting with second point rising above the first, so 4th element onwards comparison done
            if (inputLastTransationPriceList.get(1).getLastTransactionPrice() < inputLastTransationPriceList.get(3).getLastTransactionPrice()) {
                // we compare the first higher highs
                trendFlag = TARANGPARAMS.UPTREND; //uptrend
            }
            if (inputLastTransationPriceList.get(1).getLastTransactionPrice() > inputLastTransationPriceList.get(3).getLastTransactionPrice()) {
                // we compare the first higher high than the fourth one
                outputLastTransationPriceList.add(inputLastTransationPriceList.get(1));
                call = "sell";
                trendFlag = TARANGPARAMS.DOWNTREND; //downtrend as second element is a sell call
            }
        }

        if (inputLastTransationPriceList.get(1).getLastTransactionPrice() < inputLastTransationPriceList.get(2).getLastTransactionPrice()) {
            startLevelOne = 2; //starting with second point falling from the first, so 3rd element onwards comparison done
            if (inputLastTransationPriceList.get(0).getLastTransactionPrice() < inputLastTransationPriceList.get(2).getLastTransactionPrice()) {
                // we compare the first higher highs
                outputLastTransationPriceList.add(inputLastTransationPriceList.get(1));
                call = "buy";
                trendFlag = TARANGPARAMS.UPTREND; //uptrend as the current call is buy
            }
            if (inputLastTransationPriceList.get(0).getLastTransactionPrice() > inputLastTransationPriceList.get(2).getLastTransactionPrice()) {
                trendFlag = TARANGPARAMS.DOWNTREND; //downtrend remains
            }
        }
        int endLoop = 0;
        for (int i = startLevelOne; i < inputLastTransationPriceList.size() - 2; i = i + 2) {
            endLoop = i;
            if (inputLastTransationPriceList.get(i).getLastTransactionPrice() < inputLastTransationPriceList.get(i + 2).getLastTransactionPrice()) {
                if (trendFlag == TARANGPARAMS.DOWNTREND) {
                    if (inputLastTransationPriceList.get(i - 1).getLastTransactionPrice() < inputLastTransationPriceList.get(i + 1).getLastTransactionPrice()) {
                        outputLastTransationPriceList.add(inputLastTransationPriceList.get(i - 1));
                    } else {
                        outputLastTransationPriceList.add(inputLastTransationPriceList.get(i + 1));
                    }
                    call = "buy";
                }
                trendFlag = TARANGPARAMS.UPTREND; //uptrend
            }
            if (inputLastTransationPriceList.get(i).getLastTransactionPrice() > inputLastTransationPriceList.get(i + 2).getLastTransactionPrice()) {
                if (trendFlag == TARANGPARAMS.UPTREND) {
                    outputLastTransationPriceList.add(inputLastTransationPriceList.get(i));
                    call = "sell";
                }
                trendFlag = TARANGPARAMS.DOWNTREND; //downtrend
            }
        }

//      #####################################lastcallversion1#########################################
        String inputCallVersionTwo = call;
        String lastCallVersionOne = "";
        Double retraceVersionOne = 0.0;
        Double retraceDown = 0.0;
        Double retraceUp = 0.0;

        if (call.equals("buy")) {
            if ((inputLastTransationPriceList.size() - (endLoop + 3)) == 1) {
                retraceDown = inputLastTransationPriceList.get(inputLastTransationPriceList.size() - 2).getLastTransactionPrice()
                        - inputLastTransationPriceList.get(inputLastTransationPriceList.size() - 1).getLastTransactionPrice();
                retraceUp = inputLastTransationPriceList.get(inputLastTransationPriceList.size() - 2).getLastTransactionPrice()
                        - outputLastTransationPriceList.get(outputLastTransationPriceList.size() - 1).getLastTransactionPrice();
                if (((retraceDown / retraceUp) * 100) > 62) {
                    lastCallVersionOne = "buy";
                } else {
                    if (((retraceDown / retraceUp) * 100) < 38.2) {
                        lastCallVersionOne = "sell";
                    } else {
                        lastCallVersionOne = "no";
                        if (callGenerationLavel == 3) {
                            retraceVersionOne = (retraceDown / retraceUp) * 100;
                        }
                    }
                }

            } else {
                lastCallVersionOne = "sell";
            }
        }
        if (call.equals("sell")) {
            lastCallVersionOne = "buy";
        }

//      ###################################lastcallversiontwo###########################################
        String lastCallVersionTwo = "";
        Double retraceVersionTwo = 0.0;
        Double riseLength = 0.0;
        Double fallLength = 0.0;
        if (inputCallVersionTwo.equals("buy")) {
            if ((inputLastTransationPriceList.size() - (endLoop + 3)) == 1) {
                retraceDown = inputLastTransationPriceList.get(inputLastTransationPriceList.size() - 2).getLastTransactionPrice()
                        - inputLastTransationPriceList.get(inputLastTransationPriceList.size() - 1).getLastTransactionPrice();
                retraceUp = inputLastTransationPriceList.get(inputLastTransationPriceList.size() - 2).getLastTransactionPrice()
                        - outputLastTransationPriceList.get(outputLastTransationPriceList.size() - 1).getLastTransactionPrice();
                riseLength = inputLastTransationPriceList.get(inputLastTransationPriceList.size() - 1).getLastTransactionPrice()
                        - outputLastTransationPriceList.get(outputLastTransationPriceList.size() - 1).getLastTransactionPrice();

                if (riseLength < ((TARANGPARAMS.MARGIN_VALUE / 100) * outputLastTransationPriceList.get(outputLastTransationPriceList.size() - 1).getLastTransactionPrice())) {
                    lastCallVersionTwo = "buy";
                } else {
                    if (((retraceDown / retraceUp) * 100) > 62) {
                        lastCallVersionTwo = "buy";
                    } else {
                        if (((retraceDown / retraceUp) * 100) < 38.2) {
                            lastCallVersionTwo = "sell";
                        } else {
                            lastCallVersionTwo = "no";
                            if (callGenerationLavel == 3) {
                                retraceVersionTwo = (retraceDown / retraceUp) * 100;
                            }
                        }
                    }
                }
            } else {
                riseLength = inputLastTransationPriceList.get(inputLastTransationPriceList.size() - 1).getLastTransactionPrice()
                        - outputLastTransationPriceList.get(outputLastTransationPriceList.size() - 1).getLastTransactionPrice();
                if (riseLength < ((TARANGPARAMS.MARGIN_VALUE / 100) * outputLastTransationPriceList.get(outputLastTransationPriceList.size() - 1).getLastTransactionPrice())) {
                    lastCallVersionTwo = "buy";
                } else {
                    lastCallVersionTwo = "sell";
                }
            }
        }

        if (inputCallVersionTwo.equals("sell")) {
            fallLength = outputLastTransationPriceList.get(outputLastTransationPriceList.size() - 1).getLastTransactionPrice()
                    - inputLastTransationPriceList.get(inputLastTransationPriceList.size() - 1).getLastTransactionPrice();
            if (fallLength < ((TARANGPARAMS.MARGIN_VALUE / 100) * outputLastTransationPriceList.get(outputLastTransationPriceList.size() - 1).getLastTransactionPrice())) {
                lastCallVersionTwo = "sell";
            } else {
                lastCallVersionTwo = "buy";
            }

        }
        
        outputLastTransationPriceList.add(inputLastTransationPriceList.get(inputLastTransationPriceList.size() - 1));
        callResultsIntermediate.setIntermediateLTPList(outputLastTransationPriceList);
        callResultsIntermediate.setCallVersionOne(lastCallVersionOne);
        callResultsIntermediate.setCallVersionTwo(lastCallVersionTwo);
        callResultsIntermediate.setRetraceVersionOne(retraceVersionOne);
        callResultsIntermediate.setRetraceVersionTwo(retraceVersionTwo);
        
        return callResultsIntermediate;
    }

}

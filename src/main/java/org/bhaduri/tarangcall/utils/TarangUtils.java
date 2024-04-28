/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangcall.utils;

import java.util.List;
import org.bhaduri.tarangdto.LastTransactionPrice;

/**
 *
 * @author bhaduri
 */
public class TarangUtils {
    
    public static void printLTP (List<LastTransactionPrice> lastTransactrionPriceList) {
        System.out.println("==============="+lastTransactrionPriceList.size()+"==============");
        lastTransactrionPriceList.forEach(ltp-> System.out.println(ltp.getIndex()+" "+ltp.getLastTransactionPrice()));
    }
    
}

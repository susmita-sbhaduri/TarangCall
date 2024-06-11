/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.bhaduri.tarangcall;

import org.bhaduri.tarangcall.generate.CallFactory;
import org.bhaduri.tarangcall.scrips.ScripPrices;
import org.bhaduri.tarangcall.scrips.Scrips;
import org.bhaduri.tarangdto.CallResults;


/**
 *
 * @author bhaduri
 */
public class TarangCall {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        //CallFactory cf = new CallFactory();
//        CallResults callResults = cf.generateCall("ADANIENT");
        Scrips scrips = new Scrips();
        scrips.getScripList().stream().forEach(s-> new ScripPrices(s).getCallFactory().generateCall());
    }
}

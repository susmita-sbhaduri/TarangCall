/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.bhaduri.tarangcall;

import org.bhaduri.tarangcall.scrips.ScripPrices;
import org.bhaduri.tarangcall.scrips.Scrips;


/**
 *
 * @author bhaduri
 */
public class TarangCall {

    public static void main(String[] args) {
        System.out.println("Hello Worlds!");
        //CallFactory cf = new CallFactory();
//        CallResults callResults = cf.generateCall("ADANIENT");
        new Scrips().getScripList().stream().forEach(s-> {
            new ScripPrices(s).getCallFactory().generateCall().insertResults();
            });
        
        //callResultsList.stream().forEach(c->System.out.println(c.getCallGenerationTimeStamp()+" "+c.getCallVersionOne()+" "+" "+c.getCallVersionTwo()));
        //MasterDataServices mds = new MasterDataServices();
        //mds.insterCallsInTable(callResultsList);
    }
}

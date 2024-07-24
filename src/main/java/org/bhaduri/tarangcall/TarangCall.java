/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.bhaduri.tarangcall;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.bhaduri.tarangcall.scrips.ScripPrices;
import org.bhaduri.tarangcall.scrips.Scrips;
import org.bhaduri.tarangdbservice.services.MasterDataServices;
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
        new Scrips().getScripList().stream().forEach(s-> {
            new ScripPrices(s).getCallFactory().generateCall().insertResults();
            });
        
        //callResultsList.stream().forEach(c->System.out.println(c.getCallGenerationTimeStamp()+" "+c.getCallVersionOne()+" "+" "+c.getCallVersionTwo()));
        //MasterDataServices mds = new MasterDataServices();
        //mds.insterCallsInTable(callResultsList);
    }
}

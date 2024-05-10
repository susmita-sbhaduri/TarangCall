/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.tarangcall.scrips;

import java.util.List;
import org.bhaduri.tarangdbservice.services.MasterDataServices;
import org.bhaduri.tarangdto.ScripsDTO;

/**
 *
 * @author bhaduri
 */
public class Scrips {
    
    public List<ScripsDTO> getScripList() {
        MasterDataServices mds = new MasterDataServices();
        List<ScripsDTO> scripsDTOList =  mds.getScripsList();
        //scripsDTOList.stream().forEach(s->System.out.println(s.getScripId()));
        return scripsDTOList;
    }
    
}

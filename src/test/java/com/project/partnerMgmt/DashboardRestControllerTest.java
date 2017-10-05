package com.project.partnerMgmt;
 
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.partnerMgmt.controller.DashboardRestController;
import com.project.partnerMgmt.model.Partner;
import com.project.partnerMgmt.service.PartnerService;

@RunWith(SpringRunner.class)
@WebMvcTest(DashboardRestController.class)
public class DashboardRestControllerTest {
 
	@Autowired
    private MockMvc mockMvc;

	@MockBean
    private PartnerService partnerService;
    private final String RSC_BASE_URI = "/partners/";
    private final String RSC_URI_WITH_PARAM = RSC_BASE_URI+"{id}";
    
    //-------------------Retrieve Single Partner--------------------------------------------------------
    @Test
    public void testGetPartner() throws Exception{
    	long id=99;
    	Partner partner = new Partner(id,"partnerTestName","PartnerTestAdress","partneremail@email.com");
    	//String expectedData = asJsonString(partner);
    	when(partnerService.findById(id)).thenReturn(partner);
        this.mockMvc.perform(get(RSC_URI_WITH_PARAM,id))
        .andExpect(status().isOk())
        .andDo(print());
        //.andExpect(jsonPath("$.data").value(expectedData));
        verify(partnerService, times(1)).findById(id);
        verifyNoMoreInteractions(partnerService);
    }
    //-------------------Create a Partner--------------------------------------------------------
    
    @Test
    public void testCreatePartner() throws Exception {
    	long testPartnerId=99;
    	Partner newPartner=new Partner(testPartnerId,"partnerTestName","PartnerTestAdress","partneremail@email.com");

        doNothing().when(partnerService).savePartner(newPartner);

        mockMvc.perform(
                post(RSC_BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newPartner)))
                .andExpect(status().isCreated());

        verify(partnerService, times(1)).savePartner(newPartner);
        verifyNoMoreInteractions(partnerService);
    }
    
    //------------------- Update a Partner --------------------------------------------------------
    @Test
    public void testUpdatePartner() throws Exception {
    	long testPartnerId=99;
    	Partner partner=new Partner(testPartnerId,"partnerTestName","PartnerTestAdress","partneremail@email.com");

        when(partnerService.findById(partner.getId())).thenReturn(partner);
        doNothing().when(partnerService).updatePartner(partner);

        mockMvc.perform(
                put(RSC_URI_WITH_PARAM, partner.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(partner)))
                .andExpect(status().isOk());

        verify(partnerService, times(1)).findById(partner.getId());
        verify(partnerService, times(1)).updatePartner(partner);
        verifyNoMoreInteractions(partnerService);
    }

    @Test
    public void testUpdatePartnerNotFound() throws Exception {
    	long unknownId=99999;
    	Partner partner=new Partner(unknownId,"partnerTestName","PartnerTestAdress","partneremail@email.com");

        when(partnerService.findById(partner.getId())).thenReturn(null);

        mockMvc.perform(
                put(RSC_URI_WITH_PARAM, partner.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(partner)))
                .andExpect(status().isNotFound());

        verify(partnerService, times(1)).findById(partner.getId());
        verifyNoMoreInteractions(partnerService);
    }
    //------------------- Delete a Partner --------------------------------------------------------
    @Test
    public void testDeletePartner() throws Exception {
    	long testPartnerId=99;
    	Partner partner=new Partner(testPartnerId,"partnerTestName","PartnerTestAdress","partneremail@email.com");

        when(partnerService.findById(partner.getId())).thenReturn(partner);
        doNothing().when(partnerService).deletePartnerById(partner.getId());

        mockMvc.perform(
                delete(RSC_URI_WITH_PARAM, partner.getId()))
                .andExpect(status().isNoContent());

        verify(partnerService, times(1)).findById(partner.getId());
        verify(partnerService, times(1)).deletePartnerById(partner.getId());
        verifyNoMoreInteractions(partnerService);
    }

    @Test
    public void testDeletePartnerNotFound() throws Exception {
    	long unknownId=99999;
    	Partner partner=new Partner(unknownId,"partnerTestName","PartnerTestAdress","partneremail@email.com");

        when(partnerService.findById(partner.getId())).thenReturn(null);

        mockMvc.perform(
                delete(RSC_URI_WITH_PARAM, partner.getId()))
                .andExpect(status().isNotFound());

        verify(partnerService, times(1)).findById(partner.getId());
        verifyNoMoreInteractions(partnerService);
    }
    
    /*
     * converts a Java object into JSON representation
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
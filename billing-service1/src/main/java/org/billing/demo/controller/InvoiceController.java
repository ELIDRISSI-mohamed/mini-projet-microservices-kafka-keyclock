package org.billing.demo.controller;

import org.billing.demo.dto.InvoiceReqDTO;
import org.billing.demo.dto.InvoiceResDTO;
import org.billing.demo.service.InvoiceService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("api/invoice")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;
    private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @GetMapping("/{id}")
    public InvoiceResDTO getInvoice(@PathVariable Long id){
        return invoiceService.getInvoice(id);
    }
    @GetMapping("/byCustomer/{customerId}")
    public List<InvoiceResDTO> getInvoicesByCustomer(@PathVariable String name){
        return invoiceService.allInvoiceByCustomerName(name);
    }
    @PostMapping("/save")
    public InvoiceResDTO save(@RequestBody InvoiceReqDTO invoiceReqDTO){
        return invoiceService.save(invoiceReqDTO);
    }
    @GetMapping("/all")
    public List<InvoiceResDTO> getAllInvoices(){
        return invoiceService.getAllInvoices();
    }


}

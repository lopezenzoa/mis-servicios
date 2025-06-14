package com.group.mis_servicios.controller;

import com.group.mis_servicios.service.FlaggedProviderService;
import com.group.mis_servicios.view.dto.FlaggedProviderDTO;
import com.group.mis_servicios.view.dto.FlaggedProviderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flagged-providers")
public class FlaggedController {
    @Autowired
    private FlaggedProviderService flaggedProviderService;

    @PostMapping("/flag")
    public ResponseEntity<String> flagProvider(@RequestBody FlaggedProviderDTO dto) {
        flaggedProviderService.flagProvider(dto);
        return ResponseEntity.ok("Provider flagged for incompliace.");
    }

    @GetMapping("/history/{providerId}")
    public ResponseEntity<List<FlaggedProviderResponseDTO>> getFlagHistory(@PathVariable Long providerId) {
        return ResponseEntity.ok(flaggedProviderService.getProviderFlags(providerId));
    }

    @GetMapping("/count/{providerId}")
    public ResponseEntity<Long> getFlagCount(@PathVariable Long providerId) {
        return ResponseEntity.ok(flaggedProviderService.getFlagCount(providerId));
    }



}

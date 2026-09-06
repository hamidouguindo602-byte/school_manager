package com.schoolmanagement.comptabilite.controller;

import com.schoolmanagement.comptabilite.dto.response.RecuResponse;
import com.schoolmanagement.comptabilite.service.RecuService;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recus")
@PreAuthorize("hasRole('ADMIN') or hasAuthority('RECU_CONSULTER')")
public class RecuController {

  private final RecuService recuService;

  public RecuController(RecuService recuService) {
    this.recuService = recuService;
  }

  @GetMapping
  public ResponseEntity<List<RecuResponse>> obtenirTous() {

    return ResponseEntity.ok(recuService.obtenirTous());
  }

  @GetMapping("/{id}")
  public ResponseEntity<RecuResponse> obtenirParId(@PathVariable Long id) {

    return ResponseEntity.ok(recuService.obtenirParId(id));
  }

  @GetMapping("/{id}/pdf")
  public ResponseEntity<byte[]> genererPdf(@PathVariable Long id) {

    byte[] pdf = recuService.genererPdf(id);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=recu-" + id + ".pdf")
        .contentType(MediaType.APPLICATION_PDF)
        .body(pdf);
  }
}

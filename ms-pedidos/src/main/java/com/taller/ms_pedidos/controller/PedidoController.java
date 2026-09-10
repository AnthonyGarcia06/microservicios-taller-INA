package com.taller.ms_pedidos.controller;

import com.taller.ms_pedidos.dto.PedidoDTO;
import com.taller.ms_pedidos.dto.PedidoRequestDTO;
import com.taller.ms_pedidos.dto.PedidoResponseDTO;
import com.taller.ms_pedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping()
    public ResponseEntity<PedidoResponseDTO> crear(@Valid @RequestBody PedidoRequestDTO pedidoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.crearPedido(pedidoDTO));
    }

    @GetMapping()
    public ResponseEntity<List<PedidoDTO>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.listarPorId(id));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<PedidoResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.cancelarPedido(id));
    }

}

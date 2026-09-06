package com.taller.ms_productos.service;

import com.taller.ms_productos.dto.ProductoRequestDTO;
import com.taller.ms_productos.dto.ProductoResponseDTO;
import com.taller.ms_productos.exception.ProductoNotFoundException;
import com.taller.ms_productos.mapper.ProductoMapper;
import com.taller.ms_productos.model.Producto;
import com.taller.ms_productos.repository.ProductoRepository;
import com.taller.ms_productos.validator.ProductoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final ProductoValidator productoValidator;

    public List<ProductoResponseDTO> listarTodos() {
        List<Producto> lista = productoRepository.findAll();
        return productoMapper.toResponseList(lista);
    }

    public ProductoResponseDTO listarPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        return productoMapper.toResponse(producto);
    }

    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        productoValidator.validarNombreUnico(dto.getNombre());

        Producto producto = productoMapper.toEntity(dto);
        producto = productoRepository.save(producto);

        return productoMapper.toResponse(producto);
    }

    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto) {
        Producto productoActual = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        productoValidator.validarNombreUnicoParaActualizar(productoActual, dto.getNombre());

        productoMapper.updateEntity(productoActual, dto);
        productoActual = productoRepository.save(productoActual);

        return productoMapper.toResponse(productoActual);
    }

    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        productoValidator.validarSinStock(producto);

        productoRepository.delete(producto);
    }

    public ProductoResponseDTO descontarStock(Long id, Integer cantidad) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        productoValidator.validarStockSuficiente(producto, cantidad);

        producto.setStock(producto.getStock() - cantidad);
        producto = productoRepository.save(producto);

        return productoMapper.toResponse(producto);
    }

    public ProductoResponseDTO reponerStock(Long id, Integer cantidad) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        producto.setStock(producto.getStock() + cantidad);
        producto = productoRepository.save(producto);

        return productoMapper.toResponse(producto);
    }

}

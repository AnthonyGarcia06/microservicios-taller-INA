package com.taller.ms_productos.validator;

import com.taller.ms_productos.exception.NombreDuplicadoException;
import com.taller.ms_productos.exception.ProductoConStockException;
import com.taller.ms_productos.exception.StockInsuficienteException;
import com.taller.ms_productos.model.Producto;
import com.taller.ms_productos.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductoValidator {

    private final ProductoRepository productoRepository;

    // no se puede que existan dos productos con el mismo nombre, osea que se creen
    public void validarNombreUnico(String nombre) {
        if (productoRepository.existsByNombreIgnoreCase(nombre)) {
            throw new NombreDuplicadoException(nombre);
        }
    }

    // Lo mismo que el anterior pero para el editar (solo valida si el nombre cambió)
    public void validarNombreUnicoParaActualizar(Producto productoActual, String nuevoNombre) {
        boolean cambioNombre = !productoActual.getNombre().equalsIgnoreCase(nuevoNombre);

        if (cambioNombre && productoRepository.existsByNombreIgnoreCase(nuevoNombre)) {
            throw new NombreDuplicadoException(nuevoNombre);
        }
    }

    // no se puede descontar más stock del que hay
    public void validarStockSuficiente(Producto producto, Integer cantidad) {
        if (cantidad > producto.getStock()) {
            throw new StockInsuficienteException(producto.getStock(), cantidad);
        }
    }

    // no se puede eliminar un producto que todavía tiene stock
    public void validarSinStock(Producto producto) {
        if (producto.getStock() > 0) {
            throw new ProductoConStockException(producto.getStock());
        }
    }

}

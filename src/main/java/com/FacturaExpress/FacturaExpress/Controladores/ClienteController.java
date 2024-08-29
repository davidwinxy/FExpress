package com.FacturaExpress.FacturaExpress.Controladores;

import com.FacturaExpress.FacturaExpress.Entidades.Cliente;
import com.FacturaExpress.FacturaExpress.Entidades.Factura;
import com.FacturaExpress.FacturaExpress.Entidades.Sector;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.IClienteServices;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.ISectorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/Cliente")
public class ClienteController {

    @Autowired
    private IClienteServices clienteServices;

    @Autowired
    private ISectorServices sectorServices;


    @GetMapping("/informacion")
    public String informacion(Model model) {
        return "Cliente/informacion";  // Spring Boot buscará en src/main/resources/templates/empleados/manuales.html
    }




    @GetMapping("/infoContadores")
    public String infoContadores(Model model) {
        return "Cliente/infoContadores";  // Spring Boot buscará en src/main/resources/templates/empleados/manuales.html
    }


    @GetMapping("/manuales")
    public String manuales(Model model) {
        return "Cliente/manuales";  // Spring Boot buscará en src/main/resources/templates/empleados/manuales.html
    }

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Cliente> clientes = clienteServices.BuscarTodosPaginados(pageable);
        model.addAttribute("clientes", clientes);

        int totalPage = clientes.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumber = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumber", pageNumber);
        }
        return "cliente/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("sectores", sectorServices.ObtenerTodos()); // Agregar todos los sectores disponibles
        return "Cliente/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Cliente cliente, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("cliente", cliente);
            model.addAttribute("sectores", sectorServices.ObtenerTodos());
            attributes.addFlashAttribute("error", "No se puede guardar debido a un error");
            return "Cliente/create";
        }

        // Aquí se asigna el sector_id
        if (cliente.getSector() != null && cliente.getSector().getId() != null) {
            Sector sector = sectorServices.BuscarporId(cliente.getSector().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Sector no encontrado: " + cliente.getSector().getId()));
            cliente.setSector(sector);
        }

        boolean isEdit = (cliente.getId() != null && clienteServices.BuscarporId(cliente.getId()).isPresent());
        clienteServices.CreaOeditar(cliente);

        if (isEdit) {
            attributes.addFlashAttribute("msg", "Editado correctamente");
        } else {
            attributes.addFlashAttribute("msg", "Creado correctamente");
        }

        return "redirect:/Cliente";
    }
    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Optional<Cliente> clienteOpt = clienteServices.BuscarporId(id);
        if (clienteOpt.isPresent()) {
            model.addAttribute("cliente", clienteOpt.get());
            return "Cliente/details";
        } else {
            return "Cliente/not_found";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Cliente cliente = clienteServices.BuscarporId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + id));

        model.addAttribute("cliente", cliente);
        model.addAttribute("sectores", sectorServices.ObtenerTodos()); // Agregar todos los sectores disponibles
        return "Cliente/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Cliente cliente = clienteServices.BuscarporId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + id));
        model.addAttribute("cliente", cliente);
        return "Cliente/delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute Cliente cliente, RedirectAttributes attributes) {
        if (clienteServices.tieneFacturasAsignadas(cliente.getId())) {
            attributes.addFlashAttribute("error", "No se puede eliminar el cliente porque tiene facturas asociadas.");
        } else {
            clienteServices.EliminarPorId(cliente.getId());
            attributes.addFlashAttribute("msg", "Eliminado correctamente");
        }
        return "redirect:/Cliente";
    }

}

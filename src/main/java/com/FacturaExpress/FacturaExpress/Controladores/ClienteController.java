package com.FacturaExpress.FacturaExpress.Controladores;

import com.FacturaExpress.FacturaExpress.Entidades.Cliente;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/Cliente")
public class ClienteController {

    @Autowired
    private IClienteServices clienteServices;

    @Autowired
    private ISectorServices sectorServices; // Inyección del servicio de sectores

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
        return "cliente/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Cliente cliente, BindingResult result, Model model, RedirectAttributes attributes, @RequestParam(value = "sectores", required = false) List<Integer> sectoresIds) {
        if (result.hasErrors()) {
            model.addAttribute("cliente", cliente);
            model.addAttribute("sectores", sectorServices.ObtenerTodos());
            attributes.addFlashAttribute("error", "No se puede guardar debido a un error");
            return "cliente/create";
        }

        if (sectoresIds != null) {
            Set<Sector> sectores = sectoresIds.stream()
                    .map(id -> sectorServices.BuscarporId(id).orElseThrow(() -> new IllegalArgumentException("Sector no encontrado: " + id)))
                    .collect(Collectors.toSet());
            cliente.setSectores(sectores);
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
        Optional<Cliente> clientOpt = clienteServices.BuscarporId(id);
        if (clientOpt.isPresent()) {
            model.addAttribute("cliente", clientOpt.get());
            return "cliente/details";
        } else {
            return "cliente/not_found";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Cliente cliente = clienteServices.BuscarporId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + id));

        model.addAttribute("cliente", cliente);
        model.addAttribute("sectores", sectorServices.ObtenerTodos()); // Agregar todos los sectores disponibles
        return "cliente/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Cliente cliente = clienteServices.BuscarporId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + id));
        model.addAttribute("cliente", cliente);
        return "cliente/delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute Cliente cliente, RedirectAttributes attributes) {
        clienteServices.EliminarPorId(cliente.getId());
        attributes.addFlashAttribute("msg", "Eliminado correctamente");
        return "redirect:/Cliente";
    }
}

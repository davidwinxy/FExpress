package com.FacturaExpress.FacturaExpress.Controladores;


import com.FacturaExpress.FacturaExpress.Entidades.*;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.IClienteServices;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.ISectorClienteServices;
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
@RequestMapping("/SectorClientes")
public class SectorClienteController {
    @Autowired
    private ISectorClienteServices iSectorClienteServices;

    @Autowired
    private IClienteServices iClienteServices;

    @Autowired
    private ISectorServices iSectorServices;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<SectorCliente> sectorClientesPage = iSectorClienteServices.BuscarTodosPaginados(pageable);
        model.addAttribute("sectorClientes", sectorClientesPage);

        int totalPage = sectorClientesPage.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "sectorCliente/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("sectorCliente", new SectorCliente());
        List<Sector> sectores = iSectorServices.ObtenerTodos();
        List<Cliente> clientes = iClienteServices.ObtenerTodos();
        model.addAttribute("sectores", sectores);
        model.addAttribute("clientes", clientes);
        return "sectorCliente/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute SectorCliente sectorCliente, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("sectorCliente", sectorCliente);
            return "sectorCliente/create";
        }

        boolean isEdit = (sectorCliente.getId() != null && iSectorClienteServices.BuscarporId(sectorCliente.getId()).isPresent());
        iSectorClienteServices.CrearOEditar(sectorCliente);

        if (isEdit) {
            attributes.addFlashAttribute("msg", "Editado correctamente");
        } else {
            attributes.addFlashAttribute("msg", "Creado correctamente");
        }

        return "redirect:/SectorClientes";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Optional<SectorCliente> sectorCliente = iSectorClienteServices.BuscarporId(id);
        if (sectorCliente.isPresent()) {
            model.addAttribute("sectorCliente", sectorCliente.get());
            return "sectorCliente/details";
        } else {
            model.addAttribute("error", "SectorCliente no encontrado");
            return "sectorCliente/not_found";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Optional<SectorCliente> sectorCliente = iSectorClienteServices.BuscarporId(id);
        if (sectorCliente.isEmpty()) {
            model.addAttribute("error", "sectorCliente no encontrado: " + id);
            return "sectorClientes/not_found";
        }

        SectorCliente sectorClientes = sectorCliente.get();
        List<Cliente> clientes = iClienteServices.ObtenerTodos();
        List<Sector> sectores = iSectorServices.ObtenerTodos();
        model.addAttribute("sectorCliente", sectorClientes);
        model.addAttribute("clientes", clientes);
        model.addAttribute("sectores", sectores);
        return "sectorCliente/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        SectorCliente sectorCliente = iSectorClienteServices.BuscarporId(id)
                .orElseThrow(() -> new IllegalArgumentException("SectorCliente no encontrado: " + id));
        model.addAttribute("sectorCliente", sectorCliente);
        return "sectorCliente/delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute SectorCliente SectorCliente, RedirectAttributes attributes) {
        iSectorClienteServices.EliminarPorId(SectorCliente.getId());
        attributes.addFlashAttribute("msg", "Eliminado correctamente");
        return "redirect:/SectorClientes";
    }
}

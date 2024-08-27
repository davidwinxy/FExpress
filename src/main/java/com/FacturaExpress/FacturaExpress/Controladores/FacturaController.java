package com.FacturaExpress.FacturaExpress.Controladores;

import com.FacturaExpress.FacturaExpress.Entidades.Cliente;
import com.FacturaExpress.FacturaExpress.Entidades.Factura;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.IClienteServices;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.IFacturaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/Facturas")
public class FacturaController {
    @Autowired
    private IClienteServices iClienteServices;
    @Autowired
    private IFacturaServices iFacturaServices;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Factura> facturasPage = iFacturaServices.BuscarTodosPaginados(pageable);
        model.addAttribute("facturas", facturasPage);

        int totalPage = facturasPage.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "factura/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("factura", new Factura());
        List<Cliente> clientes = iClienteServices.ObtenerTodos();
        model.addAttribute("clientes", clientes);
        return "factura/create";
    }

//    @PostMapping("/save")
//    public String save(@ModelAttribute Factura factura, BindingResult result, Model model, RedirectAttributes attributes) {
//        if (result.hasErrors()) {
//            model.addAttribute("factura", factura);
//            return "factura/create";
//        }
//
//        iFacturaServices.CrearOEditar(factura);
//
//        boolean isEdit = (factura.getId() != null && iFacturaServices.BuscarporId(factura.getId()).isPresent());
//        if (isEdit) {
//            attributes.addFlashAttribute("msg", "Editado correctamente");
//        } else {
//            attributes.addFlashAttribute("msg", "Creado correctamente");
//        }
//
//        return "redirect:/Facturas";
//    }

    @PostMapping("/save")
    public String save(@ModelAttribute Factura factura, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("factura", factura);
            return "factura/create";
        }

        iFacturaServices.CrearOEditar(factura);

        boolean isEdit = (factura.getId() != null && iFacturaServices.BuscarporId(factura.getId()).isPresent());
        if (isEdit) {
            attributes.addFlashAttribute("msg", "Creado correctamente");
        } else {
            attributes.addFlashAttribute("msg", "Editado correctamente");
        }

        return "redirect:/Facturas";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Optional<Factura> factura = iFacturaServices.BuscarporId(id);
        if (factura.isPresent()) {
            model.addAttribute("factura", factura.get());
//            model.addAttribute("fechaEmisionFormatted", factura.getFechaEmision().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            return "factura/details";
        } else {
            model.addAttribute("error", "Factura no encontrada");
            return "factura/not_found";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Optional<Factura> factura = iFacturaServices.BuscarporId(id);
        if (factura.isEmpty()) {
            model.addAttribute("error", "Factura no encontrada: " + id);
            return "factura/not_found";
        }

        Factura facturaEntity = factura.get();
        List<Cliente> clientes = iClienteServices.ObtenerTodos();
        model.addAttribute("factura", facturaEntity);
        model.addAttribute("clientes", clientes);
        return "factura/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Factura factura = iFacturaServices.BuscarporId(id)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada: " + id));
        model.addAttribute("factura", factura);
        return "factura/delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute Factura factura, RedirectAttributes attributes) {
        iFacturaServices.EliminarPorId(factura.getId());
        attributes.addFlashAttribute("msg", "Eliminado correctamente");
        return "redirect:/Facturas";
    }
}

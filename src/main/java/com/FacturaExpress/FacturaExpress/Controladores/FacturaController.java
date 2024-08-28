package com.FacturaExpress.FacturaExpress.Controladores;

import com.FacturaExpress.FacturaExpress.Entidades.Cliente;
import com.FacturaExpress.FacturaExpress.Entidades.Factura;
import com.FacturaExpress.FacturaExpress.Entidades.Sector;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.IClienteServices;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.IFacturaServices;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.ISectorServices;
import com.FacturaExpress.FacturaExpress.utilidades.FacturaExportPDF;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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
           @RequestParam("size") Optional<Integer> size,
           @RequestParam("search") Optional<String> search,
           @RequestParam("sector") Optional<String> sector) {
    int currentPage = page.orElse(1) - 1;
    int pageSize = size.orElse(5);
    Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Factura> facturasPage;
        if (sector.isPresent() && !sector.get().isEmpty() && search.isPresent() && !search.get().isEmpty()) {
            facturasPage = iFacturaServices.BuscarPorSectorYNombresPaginados(sector.get(), search.get(), pageable);
        } else if (sector.isPresent() && !sector.get().isEmpty()) {
            facturasPage = iFacturaServices.BuscarPorSectorPaginados(sector.get(), pageable);
        } else if (search.isPresent() && !search.get().isEmpty()) {
            facturasPage = iFacturaServices.BuscarPorTerminoPaginados(search.get(), pageable);
        } else {
            facturasPage = iFacturaServices.BuscarTodosPaginados(pageable);
        }
        // Obtener lista de sectores únicos desde Factura
        List<Sector> sectores = iFacturaServices.obtenerSectoresUnicos();
        model.addAttribute("sectores", sectores);

        if (facturasPage.isEmpty()) {
            model.addAttribute("inf", "No se encontraron resultados para los criterios de búsqueda.");
        }
        model.addAttribute("facturas", facturasPage);
        model.addAttribute("search", search.orElse(""));
        model.addAttribute("sector", sector.orElse(""));

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

    @GetMapping("/exportarPDF/{id}")
    public void exportarFacturaPorCliente(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        // Configuración del tipo de contenido y encabezado
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());
        String filename = "Factura_" + id + "_" + fechaActual + ".pdf";
        response.setHeader("Content-Disposition", "inline; filename=" + filename);

        // Obtener la factura específica
        Optional<Factura> facturaOpt = iFacturaServices.BuscarporId(id);
        if (facturaOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Factura no encontrada");
            return;
        }

        Factura factura = facturaOpt.get();

        // Exportar la factura a PDF
        FacturaExportPDF exporter = new FacturaExportPDF(factura); // Pasar una sola factura
        exporter.Exportar(response);
    }

}

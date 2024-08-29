package com.FacturaExpress.FacturaExpress.Controladores;

import com.FacturaExpress.FacturaExpress.Entidades.Sector;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.ISectorServices;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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
@RequestMapping("/Sectores")
public class SectorController {
    @Autowired
    private ISectorServices sectorServices;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        //Cualquier nombre currentPage
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Sector> sectores = sectorServices.BuscarTodosPaginados(pageable);
        model.addAttribute("sectores", sectores);

        int totalPages = sectores.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "sectores/index";
    }
    @GetMapping("/create")
    public String create(Model model) {
          model.addAttribute("sector", new Sector());
          return "sectores/create";
        }

    @PostMapping("/save")
    public String save(@ModelAttribute("sector") @Valid Sector sector, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("error", "No se puede guardar debido a errores de validación.");
            return "redirect:/sectores/create";
        }
        try {
            boolean isEdit = (sector.getId() != null && sectorServices.BuscarporId(sector.getId()).isPresent());
            sectorServices.CrearOEditar(sector);
            if (isEdit) {
                attributes.addFlashAttribute("msg", "Editado correctamente");
            } else {
                attributes.addFlashAttribute("msg", "Creado correctamente");
            }
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "No se pudo guardar el sector. Por favor, inténtelo de nuevo.");
        }
        return "redirect:/Sectores";
    }


    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        Sector sector = sectorServices.BuscarporId(id).get();
        model.addAttribute("sector", sector);
        return "sectores/details";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Sector sector = sectorServices.BuscarporId(id).orElse(null);
            if (sector == null) {
                redirectAttributes.addFlashAttribute("error", "Sector no encontrado.");
                return "redirect:/Sectores";
            }
            model.addAttribute("sector", sector);
            return "sectores/edit";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al intentar editar el sector.");
            return "redirect:/Sectores";
        }
    }
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model){
        Sector sector = sectorServices.BuscarporId(id).get();
        model.addAttribute("sector", sector);
        return "sectores/delete";
    }

    @PostMapping("/delete")
    public String delete(Sector sector, RedirectAttributes attributes){
        if (sectorServices.tieneClientesAsignados(sector.getId())) {
            attributes.addFlashAttribute("error", "No se puede eliminar el sector porque tiene clientes asignados.");
            return "redirect:/Sectores";
        }
        sectorServices.EliminarPorId(sector.getId());
        attributes.addFlashAttribute("msg", "Sector eliminado correctamente");
        return "redirect:/Sectores";
    }
}
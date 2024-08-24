package com.FacturaExpress.FacturaExpress.Controladores;

import com.FacturaExpress.FacturaExpress.Entidades.Empleados;
import com.FacturaExpress.FacturaExpress.Entidades.Sector;
import com.FacturaExpress.FacturaExpress.Entidades.SectorEmpleado;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.IEmpleadosServices;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.ISectorEmpleadoServices;
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
@RequestMapping("/SectorEmpleados")
public class SectorEmpleadoController {

    @Autowired
    private ISectorEmpleadoServices iSectorEmpleadoServices;

    @Autowired
    private IEmpleadosServices iEmpleadoServices;

    @Autowired
    private ISectorServices iSectorServices;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<SectorEmpleado> sectorEmpleadosPage = iSectorEmpleadoServices.BuscarTodosPaginados(pageable);
        model.addAttribute("sectorEmpleados", sectorEmpleadosPage);

        int totalPage = sectorEmpleadosPage.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "sectorEmpleados/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("sectorEmpleado", new SectorEmpleado());
        List<Sector> sectores = iSectorServices.ObtenerTodos();
        List<Empleados> empleados = iEmpleadoServices.ObtenerTodos();
        model.addAttribute("sectores", sectores);
        model.addAttribute("empleados", empleados);
        return "sectorEmpleados/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute SectorEmpleado sectorEmpleado, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("sectorEmpleado", sectorEmpleado);
            return "sectorEmpleados/create";
        }

        boolean isEdit = (sectorEmpleado.getId() != null && iSectorEmpleadoServices.BuscarporId(sectorEmpleado.getId()).isPresent());
        iSectorEmpleadoServices.CrearOEditar(sectorEmpleado);

        if (isEdit) {
            attributes.addFlashAttribute("msg", "Editado correctamente");
        } else {
            attributes.addFlashAttribute("msg", "Creado correctamente");
        }

        return "redirect:/SectorEmpleados";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Optional<SectorEmpleado> sectorEmpleadoOpt = iSectorEmpleadoServices.BuscarporId(id);
        if (sectorEmpleadoOpt.isPresent()) {
            model.addAttribute("sectorEmpleado", sectorEmpleadoOpt.get());
            return "sectorEmpleados/details";
        } else {
            model.addAttribute("error", "SectorEmpleado no encontrado");
            return "sectorEmpleados/not_found";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Optional<SectorEmpleado> sectorEmpleadoOpt = iSectorEmpleadoServices.BuscarporId(id);
        if (sectorEmpleadoOpt.isEmpty()) {
            model.addAttribute("error", "SectorEmpleado no encontrado: " + id);
            return "sectorEmpleados/not_found";
        }

        SectorEmpleado sectorEmpleado = sectorEmpleadoOpt.get();
        List<Empleados> empleados = iEmpleadoServices.ObtenerTodos();
        List<Sector> sectores = iSectorServices.ObtenerTodos();
        model.addAttribute("sectorEmpleado", sectorEmpleado);
        model.addAttribute("empleados", empleados);
        model.addAttribute("sectores", sectores);
        return "sectorEmpleados/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        SectorEmpleado sectorEmpleado = iSectorEmpleadoServices.BuscarporId(id)
                .orElseThrow(() -> new IllegalArgumentException("SectorEmpleado no encontrado: " + id));
        model.addAttribute("sectorEmpleado", sectorEmpleado);
        return "sectorEmpleados/delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute SectorEmpleado sectorEmpleado, RedirectAttributes attributes) {
        iSectorEmpleadoServices.EliminarPorId(sectorEmpleado.getId());
        attributes.addFlashAttribute("msg", "Eliminado correctamente");
        return "redirect:/SectorEmpleados";
    }
}
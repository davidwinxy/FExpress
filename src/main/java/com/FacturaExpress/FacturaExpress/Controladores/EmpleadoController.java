package com.FacturaExpress.FacturaExpress.Controladores;

import com.FacturaExpress.FacturaExpress.Entidades.Empleados;
import com.FacturaExpress.FacturaExpress.Servicios.Interfaces.IEmpleadosServices;
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
@RequestMapping("/Empleados")
public class EmpleadoController {

    @Autowired
    private IEmpleadosServices empleadoServices;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Empleados> empleadosPage = empleadoServices.BuscarTodosPaginados(pageable);
        model.addAttribute("empleados", empleadosPage);

        int totalPage = empleadosPage.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumber = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumber", pageNumber);
        }
        return "empleados/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("empleado", new Empleados());
        return "empleados/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Empleados empleado, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("empleado", empleado);
            attributes.addFlashAttribute("error", "No se puede guardar debido a un error");
            return "empleados/create";
        }

        boolean isEdit = (empleado.getId() != null && empleadoServices.BuscarporId(empleado.getId()).isPresent());
        empleadoServices.CreaOeditar(empleado);

        if (isEdit) {
            attributes.addFlashAttribute("msg", "Editado correctamente");
        } else {
            attributes.addFlashAttribute("msg", "Creado correctamente");
        }

        return "redirect:/Empleados";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Optional<Empleados> empleadoOpt = empleadoServices.BuscarporId(id);
        if (empleadoOpt.isPresent()) {
            model.addAttribute("empleado", empleadoOpt.get());
            return "empleados/details";
        } else {
            return "empleado/not_found";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Empleados empleado = empleadoServices.BuscarporId(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado: " + id));

        model.addAttribute("empleado", empleado);
        return "empleados/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Empleados empleado = empleadoServices.BuscarporId(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado: " + id));
        model.addAttribute("empleado", empleado);
        return "empleados/delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute Empleados empleado, RedirectAttributes attributes) {
        empleadoServices.EliminarPorId(empleado.getId());
        attributes.addFlashAttribute("msg", "Eliminado correctamente");
        return "redirect:/Empleados";
    }
}

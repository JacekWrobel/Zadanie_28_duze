package pl.jwr.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MountController {

    private MountRepository mountRepository;
    private RangeRepository rangeRepository;

    public MountController(MountRepository mountRepository, RangeRepository rangeRepository) {
        this.mountRepository = mountRepository;
        this.rangeRepository = rangeRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Mount> mounts = mountRepository.findAll();
        model.addAttribute("mounts", mounts);
        return "mounts";
    }

    @GetMapping("/mounts/add")
    public String addNewMountForm(Model model) {
        Mount newMount = new Mount();
        newMount.setLikes(0);
        model.addAttribute("newMount", newMount);
        model.addAttribute("ranges", rangeRepository.findAll());
        return "addmount";
    }

    @PostMapping("/mounts/add")
    public String addNewMount(Mount newMount, Model model) {
        if (newMount.getName() != null && newMount.getName() != "") {
            mountRepository.save(newMount);
            return "redirect:/";
        } else {
            model.addAttribute("Tekst", "Nie podałeś nawet nazwy, spróbuj jeszcze raz!!!");
            model.addAttribute("newMount", newMount);
            model.addAttribute("ranges", rangeRepository.findAll());
            return "addmount";
        }
    }

    @GetMapping("/mounts")
    public String sortBySth(Model model, @RequestParam(required = false, defaultValue = "name") String sort) {
        List<Mount> list = new ArrayList<>();
        switch (sort) {
            case "name":
                list = mountRepository.findByOrderByNameAsc();
                break;
            case "altitude":
                list = mountRepository.findAllByOrderByAltitudeDesc();
                // list = mountRepository.findByOrderByAltitudeDesc();
                break;
            case "range":
                list = mountRepository.findByOrderByRangeAsc();
                break;
            case "likes":
                list = mountRepository.findByOrderByLikesDesc();
                break;
        }
        model.addAttribute("mounts", list);
        return "mounts";
    }

    @GetMapping("/mount")
    public String showMount(Model model, @RequestParam(required = false) Long id) {
        Optional<Mount> optional = mountRepository.findById(id);
        if (optional.isPresent()) {
            Mount mountToShow = optional.get();
            model.addAttribute("mountToShow", mountToShow);
            return "mount";
        } else {
            return "notFound";
        }
    }

    @GetMapping("/delete")
    public String deleteMount(@RequestParam(required = false) Long id) {
        mountRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/correct")
    public String correctMountForm(Model model, @RequestParam(required = false) Long id) {
        Optional<Mount> optional = mountRepository.findById(id);
        if (optional.isPresent()) {
            Mount mountToCorrect = optional.get();
            model.addAttribute("ranges", rangeRepository.findAll());
            model.addAttribute("mountToCorrect", mountToCorrect);
            return "correctmount";
        } else {
            return "notFound";
        }
    }

    @PostMapping("/mount/correct")
    public String correctMount(Mount mountToCorrect) {
        mountRepository.save(mountToCorrect);
        return "redirect:/mount?id=" + mountToCorrect.getId();
    }

    @GetMapping("/mount/addLike")
    public String addLikes(Model model, @RequestParam(required = false) Long id) {
        Optional<Mount> optional = mountRepository.findById(id);
        if (optional.isPresent()) {
            Mount mountAddLike = optional.get();
            mountAddLike.setLikes(mountAddLike.getLikes() + 1);
            mountRepository.save(mountAddLike);
            model.addAttribute("mountToShow", mountAddLike);
            return "mount";
        } else return "notFound";
    }
}




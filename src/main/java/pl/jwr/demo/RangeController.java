package pl.jwr.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class RangeController {
    private MountRepository mountRepository;
    private RangeRepository rangeRepository;

    public RangeController(MountRepository mountRepository, RangeRepository rangeRepository) {
        this.mountRepository = mountRepository;
        this.rangeRepository = rangeRepository;
    }

    //    public RangeController(RangeRepository rangeRepository) {
//        this.rangeRepository = rangeRepository;
//    }

    @GetMapping("/ranges")
    public String allranges(Model model) {
        List<Range> rangeList = rangeRepository.findAll();
        model.addAttribute("ranges", rangeList);
        return "ranges";
    }

    @GetMapping("/range/add")
    public String addForm(Model model) {
        Range range = new Range();
        model.addAttribute("range", range);
        return "addrange";
    }

    @PostMapping("/range/add")
    public String addRange(Range range, Model model) {
        if (range.getName() != null && range.getName() != "") {
            rangeRepository.save(range);
            return "redirect:/ranges";
        } else {
            model.addAttribute("Text", "Nie podałeś nawet nazwy, spróbuj jeszcze raz!!!");
            model.addAttribute("range", range);
            return "addrange";
        }
    }

    @GetMapping("/range")
    public String rangeShow(Model model, @RequestParam(required = false) Long id) {
        Optional<Range> optional = rangeRepository.findById(id);
        if (optional.isPresent()) {
            Range rangeToShow = optional.get();
            model.addAttribute("rangeToShow", rangeToShow);
            List<Mount> mountsOfRange = rangeToShow.mountains;
            model.addAttribute("mountsOfRange", mountsOfRange);
            return "range";
        } else {
            return "notFound";
        }
    }

    @GetMapping("/range/delete")
    public String deleteRange(@RequestParam(required = false) Long id) {
        mountRepository.deleteByRangeId(id);
        rangeRepository.deleteById(id);
        return "redirect:/ranges";
    }

    @GetMapping("/range/correct")
    public String correctRangeForm(Model model, @RequestParam(required = false) Long id) {
        Optional<Range> optional = rangeRepository.findById(id);
        if (optional.isPresent()) {
            Range rangeToCorrect = optional.get();
            model.addAttribute("rangeToCorrect", rangeToCorrect);
            return "correctrange";
        } else {
            return "notFound";
        }
    }

    @PostMapping("/range/correct")
    public String correctRange(Range rangeToCorrect) {
        rangeRepository.save(rangeToCorrect);
        return "redirect:/range?id=" + rangeToCorrect.getId();
    }
}
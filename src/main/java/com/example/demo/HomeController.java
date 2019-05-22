package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;


@Controller
public class HomeController {
    @Autowired
    ActorRepository actorRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listActor(Model model) {
        model.addAttribute("actor", new Actor());
        return "list";
    }

    @GetMapping("/add")
    public String newActor(Model model) {
        model.addAttribute("actor", new Actor());
        return "form";
    }

    @PostMapping("/add")
    public String processActor(@ModelAttribute Actor actor,
         @RequestParam("file")MultipartFile file){
        if (file.isEmpty()){
            return "redirect:/add";
        }
        try{
            Map uploadResult = cloudc.upload(file.getBytes()), ObjectUtils.asMap("resourcetype", "auto"));
                        actor.setHeadshot(uploadResult.get("url").toString());
                        actorRepository.save(actor);
        }
        return "redirect:/";
    }
}

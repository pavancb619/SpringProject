package com.eazyschool.controller;

import com.eazyschool.constants.HolidayType;
import com.eazyschool.entity.Holiday;
import com.eazyschool.entity.Person;
import com.eazyschool.repository.HolidayRepository;
import com.eazyschool.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("public")
public class PublicController {

    @Autowired
    private HolidayRepository holidayRepo;

    @Autowired
    private PersonService personService;

    @GetMapping("/home")
    public String homePage() {
        return "fragments/home";
    }

    @GetMapping(value = {"/holidays"})
    public String holidaysPage(Model model) {
        List<Holiday> holidays = holidayRepo.findAll();
        HolidayType[] types = HolidayType.values();
        for (HolidayType type : types) {
            model.addAttribute(type.toString(),
                    holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList()));
        }
        return "fragments/holidays";
    }

    @RequestMapping(value ="/login",method = { RequestMethod.GET, RequestMethod.POST })
    public String displayLoginPage(@RequestParam(value = "error", required = false) String error,
                                   @RequestParam(value = "logout", required = false) String logout,
                                   @RequestParam(value = "register", required = false) String register,
                                   Model model) {
        String errorMessage = null;
        if(error != null) {
            errorMessage = "Username or Password is incorrect !!";
        }
        if(logout != null) {
            errorMessage = "You have been successfully logged out !!";
        }
        if(register != null) {
            errorMessage = "You registration successful. Login with registered credentials !!";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "fragments/login";
    }

    @GetMapping("/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/public/login?logout=true";
    }

    @GetMapping("/register")
    public String displayRegisterPage(Model model) {
        model.addAttribute("person", new Person());
        return "fragments/register";
    }

    @PostMapping("/createUser")
    public String createUser(@Valid @ModelAttribute("person") Person person, Errors errors) {
        if(errors.hasErrors()){
            return "fragments/register";
        }
        boolean isSaved = personService.createNewPerson(person);
        if(isSaved){
            return "redirect:public/login?register=true";
        }else {
            return "fragments/register";
        }
    }
}

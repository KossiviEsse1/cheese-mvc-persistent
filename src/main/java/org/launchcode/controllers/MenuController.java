package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "menu")
public class MenuController {

    @Autowired
    MenuDao menuDao;

    @Autowired
    CheeseDao cheeseDao;

    @RequestMapping(value = "")
    public String index(Model model){

        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "Menus");

        return "menu/index";
    }

    @RequestMapping(value="add", method = RequestMethod.GET)
    public String displayAddMenuForm(Model model){
        model.addAttribute("title", "Menus");
        model.addAttribute("menu", new Menu());

        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddMenuForm(@ModelAttribute @Valid Menu newMenu,
                                     Errors errors, Model model){
        if (errors.hasErrors()) {
            model.addAttribute("title", "Menus");
            return "menu/add";
        }

        menuDao.save(newMenu);
        return "redirect:view/" + newMenu.getId();
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable int id){
        model.addAttribute("menu", menuDao.findOne(id));
        return "menu/view";
    }

    @RequestMapping(value = "add-item/{id}", method = RequestMethod.GET)
    public String displayAddItemForm(Model model, @PathVariable int id){
        Menu menu = menuDao.findOne(id);
        AddMenuItemForm aMiF = new AddMenuItemForm(menu, cheeseDao.findAll());
        model.addAttribute("form", aMiF);
        model.addAttribute("title", "Add item to menu:"+menu.getName());
        return "menu/add-item";

    }

    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public String processAddItemForm(@ModelAttribute @Valid AddMenuItemForm newAMIF, @RequestParam int cheeseId, @RequestParam int menuId, Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("title", "Add item to menu");
            return "menu/add-item";
        }

        Menu theMenu = menuDao.findOne(menuId);
        Cheese adChee = cheeseDao.findOne(cheeseId);
        theMenu.addItem(adChee);
        menuDao.save(theMenu);

        return "redirect:view/"+theMenu.getId();

    }

}

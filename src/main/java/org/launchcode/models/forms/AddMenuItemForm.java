package org.launchcode.models.forms;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;

import javax.validation.constraints.NotNull;

public class AddMenuItemForm {

    public Menu getMenu() {
        return menu;
    }

    private Menu menu;

    public Iterable<Cheese> getCheeses() {
        return cheeses;
    }

    private Iterable<Cheese> cheeses;

    public int getMenuId() {
        return menuId;
    }

    @NotNull
    private int menuId;

    public int getCheeseId() {
        return cheeseId;
    }

    @NotNull
    private int cheeseId;

    public AddMenuItemForm(Menu menu, Iterable<Cheese> cheeses){
        this.menu = menu;
        this.cheeses = cheeses;
    }

    public AddMenuItemForm(){}
}

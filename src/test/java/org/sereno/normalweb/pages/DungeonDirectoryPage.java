package org.sereno.normalweb.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

@DefaultUrl("/")
public class DungeonDirectoryPage extends PageObject {


    @FindBy(xpath = "//h2[text()='Dungeon Directory']")
    private WebElementFacade title;

    @FindBy(css = "#dungeon-search")
    private WebElementFacade searchBox;

    public void isDungeonDirectoryLoaded(){
        title.shouldBeVisible();
        searchBox.shouldBeVisible();
    }
}

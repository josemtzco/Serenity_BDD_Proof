package org.sereno.normalweb.steps;


import org.sereno.normalweb.pages.DungeonDirectoryPage;
import org.sereno.normalweb.pages.LoginPage;

import net.serenitybdd.annotations.Step;

public class LoginSteps {

    LoginPage loginPage;
    DungeonDirectoryPage dungeonDirectoryPage;


    @Step
    public void login(String user, String password){
        loginPage.loginToApp(user, password);
        // dungeonDirectoryPage.isDungeonDirectoryLoaded();
    }

    @Step
    public void verifyDungeonTeamFinderLoaded(){
    }

}

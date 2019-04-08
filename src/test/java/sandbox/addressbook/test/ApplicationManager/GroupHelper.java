package sandbox.addressbook.test.ApplicationManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import sandbox.addressbook.test.module.GroupData;

public class GroupHelper extends HelperBase {

    public GroupHelper(WebDriver wd) {
        super(wd);
        this.wd = wd;
    }

    public void returnToGroupPage() {
        click(By.linkText("group page"));
    }


    public void sumbitCreateGroup() {
        wd.findElement(By.name("submit")).click();
    }

    public void fillGroupFields(GroupData groupData) {
        type("group_name", groupData.getName());
        type("group_header", groupData.getHeader());
        type("group_footer", groupData.getFooter());


    }

    public void initNewGroup() {
        wd.findElement(By.name("new")).click();
    }


    public void deleteGroup() {
        wd.findElement(By.name("delete")).click();
    }

    public void selectGroup(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void initGroupModification() {
        click(By.name("edit"));
    }

    public void updateGroup() {
        wd.findElement(By.name("update")).click();
    }

    public void createGroup(GroupData group) {
        initNewGroup();
        fillGroupFields(group);
        sumbitCreateGroup();
        returnToGroupPage();
    }

    public boolean isThereAGroup() {
        return isElementPresent(By.name("selected[]"));
    }


    public int getGroupCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public void deleteSelectGroup() {
        deleteGroup();
        returnToGroupPage();

    }
}

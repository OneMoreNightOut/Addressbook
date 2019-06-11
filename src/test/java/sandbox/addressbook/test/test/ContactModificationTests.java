package sandbox.addressbook.test.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sandbox.addressbook.test.modele.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().list().size() == 0) {
            app.goTo().contactPage();
            ContactData contact = new ContactData().withFirstname("James").withLastname("Jones")
                    .withNickname("Jam.jones").withTitle("QA").withCompany("Infotecs")
                    .withAddress("Manchester, Stadium Old Trafford").withHome("2780857")
                    .withMobile("89053555178").withEmail("james.jones@oldtrafford.com")
                    .withBday("17").withBmonth("January").withByear("1985");
            app.contact().fillContactForm(contact);
            app.contact().submitNewContact();
            app.contact().returnHomePage();
        }
    }

    @Test
    public void testContactModification() {
        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;
        app.contact().selectedContact(index);
        app.contact().initContactModification();
        ContactData contact = new ContactData().withId(before.get(index).getId()).withFirstname("James").withLastname("Jones")
                .withNickname("Jam.jones").withTitle("QA").withCompany("Infotecs")
                .withAddress("Manchester, Stadium Old Trafford").withHome("2780857")
                .withMobile("89053555178").withEmail("james.jones@oldtrafford.com")
                .withBday("17").withBmonth("January").withByear("1985");
        app.contact().fillContactForm(contact);
        app.contact().submitContactUpdate();
        app.getSessionHelper();
        List<ContactData> after = app.contact().list();

        Assert.assertEquals(after.size(), before.size());

        before.remove(index);
        before.add(contact);
        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(before, after);

    }

}

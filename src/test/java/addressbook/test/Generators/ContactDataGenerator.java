package addressbook.test.Generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import addressbook.test.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

    @Parameter(names = "-c", description = "Contact count")
    public int count;


    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;

    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException ex){
            jCommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<ContactData> contacts = generateContacts(count);
        if (format.equals("csv")){
            saveAsCsv(contacts, new File(file));
        } else if (format.equals("xml")){
            saveAsXml(contacts, new File(file));
        } else if(format.equals("json")){
            saveAsJson(contacts, new File(file));
        }
    }

    private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
        XStream xStream = new XStream();
        xStream.processAnnotations(ContactData.class);
        String xml = xStream.toXML(contacts);
        Writer writer = new FileWriter(file);
        writer.write(xml);
        writer.close();
    }


    private static void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
        Writer writer = new FileWriter(file);
        for (ContactData contact : contacts){
            writer.write(String.format("%s; %s; %s; %s; %s; %s\n",
                    contact.getFirstname(), contact.getLastname(), contact.getNickname(),
                    contact.getTitle(), contact.getEmail(), contact.getHome()));
        }
        writer.close();
    }

    private static void saveAsJson(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        try(Writer writer = new FileWriter(file)){
            writer.write(json);
        }
    }

    private static List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++){
            contacts.add(new ContactData().withFirstname(String.format("firstname %s", i))
                    .withLastname(String.format("lastname %s", i))
                    .withNickname(String.format("nickname %s", i))
                    .withTitle(String.format("title %s",i))
                    .withEmail(String.format("email %s", i))
                    .withHome(String.format("home %s", i)));
        }
        return contacts;
    }
}

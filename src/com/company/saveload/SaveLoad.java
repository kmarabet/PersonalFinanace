package com.company.saveload;

import com.company.settings.Settings;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class SaveLoad {

    public static void load(SaveData sd) {
        try {
            JAXBContext context = JAXBContext.newInstance(Wrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            Wrapper wrapper = (Wrapper) um.unmarshal(Settings.getFileSave());
            sd.setAccounts(wrapper.getAccounts() != null ? wrapper.getAccounts(): sd.getAccounts());
            sd.setArticles(wrapper.getArticles());//todo
            sd.setTransactions(wrapper.getTransactions());//todo
            sd.setTransfers(wrapper.getTransfers());
            sd.setCurrencies(wrapper.getCurrencies());

        } catch (JAXBException e) {
//            e.printStackTrace();
            System.out.println("Файл не существует!");
        }
    }

    public static void save(SaveData sd){
        try {
            JAXBContext context = JAXBContext.newInstance(Wrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Wrapper wrapper = new Wrapper();
            wrapper.setAccounts(sd.getAccounts());
            wrapper.setArticles(sd.getArticles());
            wrapper.setTransactions(sd.getTransactions());
            wrapper.setTransfers(sd.getTransfers());
            wrapper.setCurrencies(sd.getCurrencies());
            m.marshal(wrapper, Settings.getFileSave());

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
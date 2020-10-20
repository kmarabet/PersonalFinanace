package com.company;

import com.company.settings.Format;
import com.company.settings.Settings;
import com.company.settings.Text;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonalFinance {

    public static void main(String[] args) {
        init();
        // write your code here
        System.out.println(Format.dateMonth(new Date()));
//        System.out.println(Text.get("PROGRAM_NAME"));
        System.out.println(Arrays.toString(Text.getMonths()));
    }

    private static void init() {
        try {
            Settings.init();
//            Settings.save();
            Text.init();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Consolas.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Settings.FONT_CONSOLAS));
        } catch (FontFormatException | IOException e) {
            Logger.getLogger(PersonalFinance.class.getName()).log(Level.SEVERE, null, e);
//            e.printStackTrace();
        }
    }
}

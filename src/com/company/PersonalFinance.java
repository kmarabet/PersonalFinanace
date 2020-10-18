package com.company;

import com.company.settings.Text;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonalFinance {

    public static void main(String[] args) {
        init();
	// write your code here
        System.out.println(Text.get("PROGRAM_NAME"));
        System.out.println(Text.getMonths());
    }

    private static void init() {
        Text.init();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Consolas.ttf")));
        } catch (FontFormatException | IOException e) {
            Logger.getLogger(PersonalFinance.class.getName()).log(Level.SEVERE, null, e);
//            e.printStackTrace();

        }
    }
}

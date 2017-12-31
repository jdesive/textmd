/*
 * Copyright (C) 2017  TextMd
 *
 * This file is part of TextMd.
 *
 * TextMd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.desive;

import com.desive.stages.EditorStage;
import com.desive.stages.SettingsStage;
import com.desive.stages.dialogs.DialogFactory;
import com.desive.utilities.Fonts;
import com.desive.utilities.Http;
import com.desive.utilities.Settings;
import com.desive.utilities.Utils;
import com.desive.utilities.constants.Dictionary;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;

import static java.lang.String.join;

/*
 Created by Jack DeSive on 10/7/2017 at 9:28 PM
*/
public class TextMd extends Application{

    private Fonts fonts;
    private String ARTIFACT_ID = null, VERSION = null, NAME = null, GROUP_ID = null;
    private final Logger logger = LoggerFactory.getLogger(TextMd.class);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.close(); // Throw away the default stage

        loadPomVariables();

        Dictionary languageDictionary = new Dictionary(NAME);
        Http http = new Http();
        fonts = new Fonts();
        DialogFactory dialogFactory = new DialogFactory(languageDictionary);

        loadUtilities();
        loadFonts();

        logger.info("Starting {} v{}" , join(".", GROUP_ID, ARTIFACT_ID), VERSION);

        SettingsStage settingsStage = new SettingsStage(languageDictionary);
        EditorStage editorStage = new EditorStage(languageDictionary, dialogFactory, settingsStage);
        settingsStage.initOwner(editorStage);
        dialogFactory.initOwner(editorStage);
    }

    private void loadFonts(){
        if(Settings.LOAD_FONTS_AT_RUNTIME) {
            logger.info("Using \'{}\' fonts", fonts.COURIER_PRIMAL_NAME);
            if(!fonts.fontExits(fonts.COURIER_PRIMAL_NAME)){
                logger.info("Downloading fonts...");
                Utils.printProgress(100, 1);
                fonts.registerFont(fonts.COURIER_PRIMAL_URL, fonts.COURIER_PRIMAL_NAME);
                Utils.printProgress(100, 25);
                fonts.registerFont(fonts.COURIER_PRIMAL_ITALICS_URL, fonts.COURIER_PRIMAL_ITALICS_NAME);
                Utils.printProgress(100, 50);
                fonts.registerFont(fonts.COURIER_PRIMAL_BOLD_URL, fonts.COURIER_PRIMAL_BOLD_NAME);
                Utils.printProgress(100, 75);
                fonts.registerFont(fonts.COURIER_PRIMAL_BOLD_ITALICS_URL, fonts.COURIER_PRIMAL_BOLD_ITALICS_NAME);
                Utils.printProgress(100, 100);
                System.out.println();
            }
        } else {
            logger.info("Using \'{}\' font", fonts.COURIER_REGULAR_NAME);
        }

    }

    private void loadPomVariables() {
        logger.debug("Loading maven pom.xml variables");
        MavenXpp3Reader reader = new MavenXpp3Reader();
        try {
            Model model = reader.read(new FileReader("pom.xml"));
            ARTIFACT_ID = model.getArtifactId();
            VERSION = model.getVersion();
            NAME = model.getName();
            GROUP_ID = model.getGroupId();
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private void loadUtilities(){
        new Utils();
        System.setProperty("javax.xml.parsers.SAXParserFactory", "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl"); // Fix xerces SAXParserFactory for language tool
    }

    public static void main(String[] args) {
        launch(args);
    }

}

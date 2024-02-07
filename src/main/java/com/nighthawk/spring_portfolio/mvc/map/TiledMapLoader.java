package com.nighthawk.spring_portfolio.mvc.map;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TiledMapLoader {
    
    public static void main(String[] args) {
        loadTiledMap("map.tmx");
    }

    public static void loadTiledMap(String tmxFilePath) {
        try {
            // Load TMX file
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(new File(tmxFilePath));
            Element root = document.getRootElement();

            // Extract tileset information
            List<Element> tilesetElements = root.getChildren("tileset");
            Map<Integer, BufferedImage> tileImages = new HashMap<>();
            for (Element tilesetElement : tilesetElements) {
                String imageSource = tilesetElement.getChild("image").getAttributeValue("source");
                BufferedImage image = ImageIO.read(new File(imageSource));
                int firstGID = Integer.parseInt(tilesetElement.getAttributeValue("firstgid"));
                tileImages.put(firstGID, image);
            }

            // Associate images with tiles
            List<Element> layerElements = root.getChildren("layer");
            for (Element layerElement : layerElements) {
                List<Element> tileElements = layerElement.getChild("data").getChildren("tile");
                for (Element tileElement : tileElements) {
                    int gid = Integer.parseInt(tileElement.getAttributeValue("gid"));
                    if (gid != 0) {
                        BufferedImage tileImage = tileImages.get(gid);
                        // Do something with the tile image, such as drawing it on a canvas
                    }
                }
            }
        } catch (IOException | NumberFormatException | org.jdom2.JDOMException e) {
            e.printStackTrace();
        }
    }
}

}

    

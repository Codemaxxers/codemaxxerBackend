package com.nighthawk.spring_portfolio.mvc.map;

import org.mapeditor.core.Map;
import org.mapeditor.core.Tile;
import org.mapeditor.core.MapLayer;
import org.mapeditor.core.TileLayer;
import org.mapeditor.core.TileSet;
import org.mapeditor.io.TMXMapReader;

import com.nighthawk.spring_portfolio.mvc.collision.KeyHandler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class MapRender extends JPanel implements Runnable {

    // Screen Settings
    final int originalTileSize = 16; // 16x16 default tile and character
    final int scale = 3;

    final int tileSize = originalTileSize * scale;

    final int maxScreenCol = 10; // 10 height
    final int maxScreenRow = 10; // 10 width

    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    int playerX;
    int playerY;
    int playerSpeed = 4;

    Map tiledMap;
    TileSet tileset;
    int mapWidth;
    int mapHeight;

    int FPS = 60;

    List<TileLayer> layersToRender = new ArrayList<>();

    public MapRender() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    
        // loading map
        loadTiledMap();
    
        // initialize player psoition 
        playerX = (mapWidth * tileSize - screenWidth) / 2;
        playerY = (mapHeight * tileSize - screenHeight) / 2;
    }    

    private void loadTiledMap() { //issues here >>>
        try {
            File file = new File("/com/nighthawk/spring_portfolio/mvc/map/main-map.tmx");
            TMXMapReader mapReader = new TMXMapReader();
            tiledMap = mapReader.readMap(file.getAbsolutePath());
            mapWidth = tiledMap.getWidth();
            mapHeight = tiledMap.getHeight();

            tileset = tiledMap.getTileSets().get(0);
    
        // render layer 2-18
            int startLayerIndex = 2;
            int endLayerIndex = 18;

for (int i = startLayerIndex; i <= endLayerIndex; i++) {
    MapLayer layer = tiledMap.getLayer(i);
    if (layer instanceof TileLayer) {
        layersToRender.add((TileLayer) layer);
    }
}

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle other Exception
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {
            update();
            repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (keyH.upPressed) {
            playerY -= playerSpeed;
        } else if (keyH.downPressed) {
            playerY += playerSpeed;
        } else if (keyH.leftPressed) {
            playerX -= playerSpeed;
        } else if (keyH.rightPressed) {
            playerX += playerSpeed;
        }

        // check bountry
        if (playerX < 0) {
            playerX = 0;
        } else if (playerX + screenWidth > mapWidth * tileSize) {
            playerX = mapWidth * tileSize - screenWidth;
        }

        if (playerY < 0) {
            playerY = 0;
        } else if (playerY + screenHeight > mapHeight * tileSize) {
            playerY = mapHeight * tileSize - screenHeight;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Render the visible portion of the map
        for (TileLayer layer : layersToRender) {
            renderTileLayer(g2, layer);
        }

        // Render the player on top of the map
        g2.setColor(Color.white);
        g2.fillRect(playerX % screenWidth, playerY % screenHeight, tileSize, tileSize);
        g2.dispose();
    }

    private void renderTileLayer(Graphics2D g, TileLayer layer) {
        int startTileX = playerX / tileSize;
        int startTileY = playerY / tileSize;
        int tilesToRenderX = Math.min(maxScreenCol, mapWidth - startTileX);
        int tilesToRenderY = Math.min(maxScreenRow, mapHeight - startTileY);

        for (int y = 0; y < tilesToRenderY; y++) {
            for (int x = 0; x < tilesToRenderX; x++) {
                int tileX = startTileX + x;
                int tileY = startTileY + y;

                if (tileX < mapWidth && tileY < mapHeight) {
                    Tile tile = layer.getTileAt(tileX, tileY);
                    if (tile != null) {
                        Image tileImage = tile.getImage();
                        if (tileImage != null) {
                            int renderX = x * tileSize - playerX % tileSize;
                            int renderY = y * tileSize - playerY % tileSize;
                            g.drawImage(tileImage, renderX, renderY, tileSize, tileSize, this);
                        }
                    }
                }
            }
        }
    }
}

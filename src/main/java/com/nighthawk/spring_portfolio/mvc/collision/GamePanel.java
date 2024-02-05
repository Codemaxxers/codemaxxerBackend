package com.nighthawk.spring_portfolio.mvc.collision;

import org.mapeditor.core.Map;
import org.mapeditor.core.MapLayer;
import org.mapeditor.core.TileLayer;
import org.mapeditor.core.Tileset;
import org.mapeditor.io.TMXMapReader;

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

public class GamePanel extends JPanel implements Runnable {

    // Screen Settings
    final int originalTileSize = 16; // 16x16 default tile/character size
    // scaling
    final int scale = 3;

    final int tileSize = originalTileSize * scale;
    // 16 x 12 map
    final int maxScreenCol = 20;
    final int maxScreenRow = 20;

    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // Setting player default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    Map tiledMap;
    Tileset tileset;
    int mapWidth;
    int mapHeight;

    int FPS = 60;

    List<TileLayer> layersToRender = new ArrayList<>();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        // Load Tiled map
        loadTiledMap("src/main/java/com/nighthawk/spring_portfolio/mvc/collision/main-map.tmx");
    }

    private void loadTiledMap(String tmxFilePath) {
        try {
            File file = new File(tmxFilePath);
            TMXMapReader mapReader = new TMXMapReader();
            tiledMap = mapReader.readMap(file.getAbsolutePath());
            mapWidth = tiledMap.getWidth();
            mapHeight = tiledMap.getHeight();

            // Assuming only one tileset for simplicity
            tileset = tiledMap.getTilesets().get(0);

            // Identify layers to render (TileLayers only)
            for (MapLayer layer : tiledMap) {
                if (layer instanceof TileLayer) {
                    layersToRender.add((TileLayer) layer);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
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

        // Check if the player reaches the map boundaries
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
                    int tileId = layer.getTileAt(tileX, tileY);
                    if (tileId != 0) {
                        Image tileImage = tileset.getTile(tileId).getImage();
                        int renderX = x * tileSize - playerX % tileSize;
                        int renderY = y * tileSize - playerY % tileSize;
                        g.drawImage(tileImage, renderX, renderY, tileSize, tileSize, this);
                    }
                }
            }
        }
    }
}

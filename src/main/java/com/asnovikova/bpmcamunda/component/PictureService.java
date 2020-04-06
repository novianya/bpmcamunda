package com.asnovikova.bpmcamunda.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;
import javax.imageio.ImageIO;

/**
 * @author Anna Novikova
 */
@Service
public class PictureService {


    private final RestTemplate restTemplate;

    private static final String PIC_URL = "https://picsum.photos/800/200";

    public static final String TMPFILE_SUFFIX = ".tmp";

    Logger logger  = LoggerFactory.getLogger(getClass().getName());


    @Autowired
    public PictureService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getPicture(String fact) throws IOException {
        String filename = UUID.randomUUID().toString();
        File tmpFile = File.createTempFile(filename, TMPFILE_SUFFIX);
        byte[] bt =  restTemplate.getForObject(PIC_URL, byte[].class);

        final BufferedImage image = ImageIO.read(new ByteArrayInputStream(bt));

        Graphics g = image.getGraphics();

        g.setColor(Color.MAGENTA);
        g.setFont(new Font("Arial Black", Font.BOLD, 15));

        g.drawString(fact, 10, 25);
        g.dispose();

        ImageIO.write(image, "png", tmpFile);
        logger.info("saved file {}", tmpFile.getAbsolutePath());
        return tmpFile.getAbsolutePath();
    }
}

package com.github.sahhiill.fileserver;

import com.github.sahhiill.fileserver.compression.SimpleCompression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FileServerApplicationTests {

    @Test
    void contextLoads() {

    }

       @Test
    void testDecompression() {
        String let = "1,2x2,3,4,5x3";
        SimpleCompression sc = new SimpleCompression();
        List<Byte> gives = sc.decompress(let);
        List<Byte> expected = List.of(
                Byte.parseByte("1"),
                Byte.parseByte("2"),
                Byte.parseByte("2"),
                Byte.parseByte("3"),
                Byte.parseByte("4"),
                Byte.parseByte("5"),
                Byte.parseByte("5"),
                Byte.parseByte("5")
        );

           assert gives.equals(expected);
       }

    }

package es.uji.al394752.recsys;

import es.uji.al394752.clases.TableWithLabels;
import es.uji.al394752.lectura.CSV;
import es.uji.al394752.lectura.CSVLabeledFileReader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SongRecSysTest {

    @Test
    void main() {
        String sep = System.getProperty("file.separator");
        String ruta = "src" + sep + "data" +sep;
        try {
            SongRecSys s = new SongRecSys("knn");
            CSV csv = new CSV();
            TableWithLabels tabla = csv.readTableWithLabels(ruta+"songs_test.csv");
            List<String> recommended = s.getRecommended_items();
            String liked = s.getLiked_name();
            Integer liked_indice = tabla.getLabelIndice(liked);
            for (String r : recommended)
                assertEquals(liked_indice,tabla.getLabelIndice(r));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            SongRecSys s = new SongRecSys("kmeans");
            CSV csv = new CSV();
            TableWithLabels tabla = csv.readTableWithLabels(ruta+"songs_test.csv");
            List<String> recommended = s.getRecommended_items();
            String liked = s.getLiked_name();
            Integer liked_indice = tabla.getLabelIndice(liked);
            for (String r : recommended)
                assertEquals(liked_indice,tabla.getLabelIndice(r));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
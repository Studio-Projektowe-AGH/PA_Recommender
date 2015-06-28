package test;

import models.ImmportantResult;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.math.Arrays;

//import rekomender.SampleRecommender.Rekomendacja;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.String;


public class SampleRecommender {

    public static class Rekomendacja {
        public long idUzytkownika;
        public String idKlubu;

        public Rekomendacja() {
        }

        ;

        public Rekomendacja(long idUzytkownika, String idKlubu) {
            this.idUzytkownika = idUzytkownika;
            this.idKlubu = idKlubu;
        }

        public String toString() {
            return "(idUzytkownika: " + idUzytkownika + ", idKlubu: " + idKlubu + ")";
        }

        List<Rekomendacja> lr;

    }


    public ImmportantResult importantFunction(String importantArgument) throws IOException, TasteException {

        int idUz = 0;
        boolean blnFound = false;
        String line = null;
        ArrayList<Long> t = new ArrayList<Long>();

        Scanner scanner = new Scanner(importantArgument);


        while ((line = scanner.nextLine()) != null) {
            int indPrz = line.indexOf(',');
            String idU = line.substring(0, indPrz);
            idUz = Integer.parseInt(idU);
            Long val = Long.valueOf(idUz);
            blnFound = t.contains(val);

            if (!blnFound) {
                t.add(val);
            }
        }
        scanner.close();

        BufferedWriter writer = null;
        DataModel model = null;
        try {
            File temp = File.createTempFile("dane", ".csv");

            writer = new BufferedWriter(new FileWriter(temp));
            writer.write(importantArgument);
            writer.close();

            String path = temp.getAbsolutePath();

            model = new FileDataModel(new File(path));
        } catch (IOException e) {
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
            }
        }

        if (model != null) {
            // cos sie odczytalo
        } else {
            // byly jakies bledy
        }
        // 			DataModel model = new FileDataModel(new File(writer));

        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
        UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);


        List<Rekomendacja> lr = new ArrayList<Rekomendacja>();

        for (Long item : t) {
            List<RecommendedItem> recommendations = recommender.recommend(item, 3);
            for (RecommendedItem recommendation : recommendations) {
                String tmp = recommendation.toString();
                int indPrz = tmp.indexOf(',');
                int indDwuKr = tmp.indexOf(':');
                String idKl = tmp.substring(indDwuKr + 1, indPrz);
//					System.out.println(idKl + " for user id " + item);
//					System.out.println("id klubu " + idKl);
//					System.out.println("user id " + item);

                Rekomendacja r = new Rekomendacja(item, idKl);
                lr.add(r);
            }
        }

        System.out.println(Arrays.toString(lr.toArray()));
        importantArgument = Arrays.toString(lr.toArray());

        return new ImmportantResult(importantArgument);
    }

}


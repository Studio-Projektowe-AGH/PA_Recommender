package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

public class SampleRecommender  {

	 
	public static void main(String[] args) throws IOException, TasteException  {
	
		 int idUz=0;
		 boolean blnFound = false;
		
		FileInputStream fis = new FileInputStream("dane.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		 
		String line = null;
		ArrayList<Long> t = new ArrayList<Long>();

		while ((line = br.readLine()) != null) {
			//System.out.print("Found Index :" );
			int indPrz=line.indexOf( ',' );
		    //System.out.println(indPrz);
		    String idU = line.substring(0, indPrz);
		    //System.out.println("idUz: " + idU);
		    idUz = Integer.parseInt(idU);
		   // System.out.println("idUz: " + idUz);
		    Long val = Long.valueOf(idUz);
		    blnFound = t.contains(val);
		   
		    if (!blnFound){
		    	t.add(val);
		    }
		 }
		 
		 System.out.println(t);
				
		br.close();
		
			 
		DataModel model = new FileDataModel(new File("dane.csv"));
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		
		for(Long item : t){
		   // System.out.println(item + ", ");
			List <RecommendedItem> recommendations = recommender.recommend(item, 3);
			for (RecommendedItem recommendation : recommendations) {
			  System.out.println(recommendation + "for user id " + item);
			}

		}
		
		
		List <RecommendedItem> recommendations = recommender.recommend(4, 3);
		for (RecommendedItem recommendation : recommendations) {
		  System.out.println(recommendation);
		}
		
		
	}

}

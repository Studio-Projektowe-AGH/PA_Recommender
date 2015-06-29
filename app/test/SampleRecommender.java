package test;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import play.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;
import java.util.*;

@Singleton
public class SampleRecommender {

    @Inject
    private HashMap<Long, String> idMap;

    private static final long[] createLookupTable() {
        long[] internalbyteTable = new long[256];
        long h = 0x544B2FBACAAF1684L;
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 31; j++) {
                h = (h >>> 7) ^ h;
                h = (h << 11) ^ h;
                h = (h >>> 10) ^ h;
            }
            internalbyteTable[i] = h;
        }
        return internalbyteTable;
    }

	private static final long[] byteTable = createLookupTable();
	private static final long HSTART = 0xBB40E64DA205B064L;
	private static final long HMULT = 7664345821815920749L;

    private Optional<GenericUserBasedRecommender> recommender = Optional.empty();

    public Optional<GenericUserBasedRecommender> getRecommender() {
        return recommender;
    }

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

    public String long2id(long number) {
        return idMap.get(number);
    }

    public Long id2long(String id) throws UnsupportedEncodingException {
        byte[] bytesOfMessage = id.getBytes("UTF-8");
        Long val = hash(bytesOfMessage);
        idMap.put(val, id);
        return val;
    }

    public static long hash(byte[] data) {
		long h = HSTART;
		final long hmult = HMULT;
		final long[] ht = byteTable;
		for (int len = data.length, i = 0; i < len; i++) {
			h = (h * hmult) ^ ht[data[i] & 0xff];
		}
		return h;
	}

    public void importantFunction(String importantArgument) throws IOException, TasteException {

    	Logger.debug("zmien moja nazwe: " + importantArgument);

		int idUz = 0;
		boolean blnFound = false;
		String line = null;
		ArrayList<Long> t = new ArrayList<Long>();

        Scanner scanner = new Scanner(importantArgument);

		//ImmportantArgument newImportantArgument;
		String newImportantArgument = "";
		try {
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				// 			while ((line = scanner.nextLine()) != null) {
				Logger.debug("petla while");
				int indPrz = line.indexOf(',');
				String idU = line.substring(0, indPrz);
				// idUz = Integer.parseInt(idU);
				//Long val = Long.valueOf(idUz);


				//byte[] bytesOfMessage = idU.getBytes("UTF-8");

				Long val = id2long(idU);

				newImportantArgument += val.toString() + line.substring(indPrz) + "\n";
				blnFound = t.contains(val);

				if (!blnFound) {
					t.add(val);
				}
			}

			importantArgument = newImportantArgument;

			System.out.println(importantArgument);

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
				e.printStackTrace();
			} finally {
				try {
					if (writer != null)
						writer.close();
				} catch (IOException e) {
					e.printStackTrace();

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
        recommender = Optional.of(new GenericUserBasedRecommender(model, neighborhood, similarity));
		}
        catch(Exception e){
			e.printStackTrace();
		}

//        List<Rekomendacja> lr = new ArrayList<Rekomendacja>();
//
//        for (Long item : t) {
//            List<RecommendedItem> recommendations = recommender.recommend(item, 3);
//            for (RecommendedItem recommendation : recommendations) {
//                String tmp = recommendation.toString();
//                int indPrz = tmp.indexOf(',');
//                int indDwuKr = tmp.indexOf(':');
//                String idKl = tmp.substring(indDwuKr + 1, indPrz);
////					System.out.println(idKl + " for user id " + item);
////					System.out.println("id klubu " + idKl);
////					System.out.println("user id " + item);
//
//                Rekomendacja r = new Rekomendacja(item, idKl);
//                lr.add(r);
//            }
//        }
//
//        System.out.println(Arrays.toString(lr.toArray()));
//        importantArgument = Arrays.toString(lr.toArray());
//
//        return new ImmportantResult(importantArgument);
    }

}


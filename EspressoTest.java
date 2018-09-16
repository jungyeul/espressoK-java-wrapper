import org.cwnunlp.espresso.ko.tagger.tagger;
import org.cwnunlp.espresso.ko.tagger.TaggerConf;
import org.cwnunlp.espresso.ko.tagger.MorphUtils;
import org.cwnunlp.espresso.ko.tagger.MorphStruct;
import java.util.*;

public class EspressoTest {
	
	static { 
		try {
			System.loadLibrary("tagger"); 
		} catch (UnsatisfiedLinkError e) {
			System.err.println("error load 'libtagger'! : \n\t" + e);
			System.exit(1);
		}
	}
	
	public static void main(String args[])
	{
		TaggerConf taggerConf = 
			new TaggerConf("./dic/LEXICON.morph.midx", "./dic/LEXICON.morph.jidx", "./dic/LEXICON.morph.dat",
								"./dic/LEXICON.sym.idx", "./dic/LEXICON.sym.dat",
								"./dic/LEXICON.prob.hash",
								"./dic/LEXICON.user.idx", "./dic/LEXICON.user.dat");
		String ret;
		ArrayList<MorphStruct> ret2;
		String input;
		
		
		// 태거 설정
		//taggerConf.setPrintLemma(fp);	// 그래프의 확률정보를 파일에 기록
		//taggerConf.setModeMA(fp);		// 형태소 분석 수행 결과를 파일에 기록
		//taggerConf.setPrintBestPathOnlyCategory(1); // /* 0: 단순 형태소 분석, 1: 명사류/동사류, 2: 명사류, 3: 동사류 ; flag_notTok 가 0일 때만 사용 가능 */
		//taggerConf.setDisableTok(0); // Tokenizer 사용/사용안함
		
		// 문장 입력
		input = new String("나/그는 집에 갔다.");
		
		// POSTagging 실시
		ret = tagger.POSTagger(input, taggerConf);
		ret2= MorphUtils.parse_POSData(ret);	// 품사태깅 1-best를 MorphStruct 클래스의 ArrayList 로 만들기!
		
		
		// 결과 출력
		System.out.println("TaggerTest Start");
		System.out.println("=========================================================");
		
		System.out.println("taggerInput : " + input + "\n");
		System.out.println("taggerResult : " + ret);
		
		for(int i=0; i < ret2.size(); i++)
		{
			// 어절 정보 가져오기
			System.out.print("  eojeol " + i + " : ");
			System.out.println( ret2.get(i).getEojeol() );
			
			// 형태소 분석 결과 가져오기
			for(int j=0; j < ret2.get(i).getMorph().size(); j++)
			{
				ArrayList<String[]> morph = null;
				
				// 형태소 분석 결과 그냥 출력
				System.out.print("    morph " + i + ", " + j + " : ");
				System.out.println(ret2.get(i).getMorph().get(j));
				
				// 분석 결과를 처리하기 쉽도록 형태소/품사태그로 정보를 분리
				System.out.println("    parsed morph " + i + ", " + j + " : ");
				morph = MorphUtils.parse_morph(ret2.get(i).getMorph().get(j));
				for(int z=0; z < morph.size(); z++)
				{
					System.out.println("      morph (" + morph.get(z)[0] + "), postag (" + morph.get(z)[1] + ")");
				}
			}
			
		}
		
		System.out.println("=========================================================");
    	System.out.println("End of TaggerTest");
  


		// 형태소 분석 실시
		ret = tagger.MAnal(input, taggerConf);
		ret2= MorphUtils.parse_MAnalData(ret);
    	
		System.out.println("Morpological Analyzer Start");
		System.out.println("=========================================================");
		
		System.out.println("Input : " + input + "\n");
		System.out.println("AnalResult : " + ret);
		
		for(int i=0; i < ret2.size(); i++)
		{
			// 어절 정보 가져오기
			System.out.print("  eojeol " + i + " : ");
			System.out.println( ret2.get(i).getEojeol() );
			
			// 형태소 분석 결과 가져오기
			for(int j=0; j < ret2.get(i).getMorph().size(); j++)
			{
				ArrayList<String[]> morph = null;
				
				// 형태소 분석 결과 그냥 출력
				System.out.print("    morph " + i + ", " + j + " : ");
				System.out.println(ret2.get(i).getMorph()); //System.out.println(ret2.get(i).getMorph().get(j));
				
				// 분석 결과를 처리하기 쉽도록 형태소/품사태그로 정보를 분리
				System.out.println("    parsed morph " + i + ", " + j + " : ");
				morph = MorphUtils.parse_morph(ret2.get(i).getMorph().get(j));
				for(int z=0; z < morph.size(); z++)
				{
					System.out.println("      morph (" + morph.get(z)[0] + "), postag (" + morph.get(z)[1] + ")");
				}
			}
			
		}
		
		System.out.println("=========================================================");
    	System.out.println("End of TaggerTest");
	}
}



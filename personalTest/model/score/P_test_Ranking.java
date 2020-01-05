package model.score;

import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.assertNotNull;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import model.exceptions.score.EmptyRankingException;

public class P_test_Ranking {
	// TODO , getWinner tests.
	
	Ranking<CollectedItemsScore> r;
	CollectedItemsScore c;
	CollectedItemsScore c1;
	CollectedItemsScore c2;
	CollectedItemsScore c3;
	
	@Before
	public void previous_settings() {
		r = new Ranking<>();
		c = new CollectedItemsScore("Juan");
		c1 = new CollectedItemsScore("Jose");
		c2 = new CollectedItemsScore("Jorge");
		c3 = new CollectedItemsScore("Jesus");
		c3.score = 1;
		c2.score = 2;
		c1.score = 3;
		c.score = 4;
	}
	
	@Test 
	public void ranking_constructor_comprobation() {
		SortedSet<?> s = r.getSortedRanking();
		assertNotNull(s);
		assertEquals(0, s.size());
	}
	
	@Test
	public void addScore_comprobation() {
		CollectedItemsScore c = new CollectedItemsScore("Jefe");
		CollectedItemsScore c1 = new CollectedItemsScore("Juan");
		CollectedItemsScore c2 = new CollectedItemsScore("Jose");
		c.score = 0;
		c1.score = 1;
		c2.score = 2;
		r.addScore(c);
		r.addScore(c1);
		r.addScore(c2);
		assertEquals(3, r.getSortedRanking().size());	
	}
	
	@Test
	public void getSortedRanking_comprobation() {
		SortedSet<CollectedItemsScore> s1 = new TreeSet<CollectedItemsScore>();
		s1.add(c3);
		s1.add(c2);
		s1.add(c1);
		s1.add(c);
		r.addScore(c1);
		r.addScore(c3);
		r.addScore(c2);
		r.addScore(c);
		SortedSet<CollectedItemsScore> s = r.getSortedRanking();
		Object cc[] = s.toArray();
		for(int i=0; i< cc.length; i++) {
			if(i==0) {
				assertEquals(c, cc[i]);
			} else if(i == 1) {
				assertEquals(c1, cc[i]);
			} else if(i == 2) {
				assertEquals(c2, cc[i]);
			} else if(i == 3) {
				assertEquals(c3, cc[i]);
			}
		}
		
		assertEquals(s1, s);
	}
	
	@Test(expected=EmptyRankingException.class)
	public void getWinner_comprobation_exception() throws EmptyRankingException {
		r.getWinner();
	}
	
	@Test
	public void getWinner_comprobation() throws EmptyRankingException {
		r.addScore(c);
		r.addScore(c1);
		r.addScore(c2);
		r.addScore(c3);
		CollectedItemsScore res = r.getWinner();
		assertEquals(c, res);
	}
	
	// TODO MIRAR SI HACE FALTA QUE SEAN SIEMPRE DEL MISMO TIPO Y TENGA QUE 
	// COMPROBARLO
}

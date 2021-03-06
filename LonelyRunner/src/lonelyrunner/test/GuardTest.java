package lonelyrunner.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lonelyrunner.contract.EditableScreenContract;
import lonelyrunner.contract.EnvironmentContract;
import lonelyrunner.contract.GuardContract;
import lonelyrunner.contract.PlayerContract;
import lonelyrunner.contract.contracterr.ContractError;
import lonelyrunner.impl.EditableScreenImpl;
import lonelyrunner.impl.EnvironmentImpl;
import lonelyrunner.impl.GuardImpl;
import lonelyrunner.impl.PlayerImpl;
import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Move;

public class GuardTest {

	private PlayerService target;
	private GuardService guard;
	private EnvironmentService env;
	private EditableScreenService editscreen;
	
	@Before
	public void beforeTests() {
		env = new EnvironmentContract(new EnvironmentImpl());
		editscreen = new EditableScreenContract(new EditableScreenImpl());
		target = new PlayerContract(new PlayerImpl());
		guard = new GuardContract(new GuardImpl());

	}

	@After
	public void afterTests() {
		env = null;
		editscreen = null;
		target = null;
		guard = null;
	}
	
	public void testInvariant() {
		Cell c = env.getCellNature(guard.getWdt(), guard.getHgt());
		assertTrue(c == Cell.EMP || c== Cell.HOL || c== Cell.LAD || c== Cell.HDR) ;
		boolean in =env.getCellContent(guard.getWdt(), guard.getHgt()).isInside(guard);
		assertTrue(in);
		
	}
	
	@Test
	public void testInitialisationPositif() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		target.init(env, 1, 2);
		assertTrue(env.getCellNature(2, 2) == Cell.EMP);
		guard.init(env, 2, 2,target);
		testInvariant();
	}
	
	@Test(expected=ContractError.class)
	public void testInitialisationNegatif() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		target.init(env, 1, 2);
		guard.init(env, -1, 2,target);
		testInvariant();
	}
	
	@Test(expected=ContractError.class)
	public void testInitialisationNegatif2() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		target.init(env, 1, 2);
		guard.init(env, 2, -1,target);
		testInvariant();
	}
	
	@Test(expected=ContractError.class)
	public void testInitialisationNegatif3() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		target.init(env, 1, 2);
		guard.init(env, 1000, -1,target);
		testInvariant();
	}
	
	@Test
	public void testInitialisationNegatif4() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		
		boolean caught = false;
		try {
			editscreen.setNature(2, 2, Cell.PLT);
			env.init(editscreen);
			target.init(env, 1, 2);
			guard.init(env, 2, 2,target);
			testInvariant();
		}
		catch(ContractError e) {
			caught = true;
		}
		assertTrue(caught);
		caught = false;
		try {
			editscreen.setNature(2, 2, Cell.MTL);
			env.init(editscreen);
			target.init(env, 1, 2);
			guard.init(env, 2, 2,target);
			testInvariant();
		}
		catch(ContractError e) {
			caught = true;
		}
		assertTrue(caught);
		caught = false;
		try {
			editscreen.setNature(2, 2, Cell.HDR);
			env.init(editscreen);
			target.init(env, 1, 2);
			guard.init(env, 2, 2,target);
			testInvariant();
		}
		catch(ContractError e) {
			caught = true;
		}
		assertTrue(caught);
		caught = false;
		
		try {
			editscreen.setNature(2, 2, Cell.HOL);
			env.init(editscreen);
			target.init(env, 1, 2);
			guard.init(env, 2, 2,target);
			testInvariant();
		}
		catch(ContractError e) {
			caught = true;
		}
		assertTrue(caught);
		caught = false;
		try {
			editscreen.setNature(2, 2, Cell.LAD);
			env.init(editscreen);
			target.init(env, 1, 2);
			guard.init(env, 2, 2,target);
			testInvariant();
		}
		catch(ContractError e) {
			caught = true;
		}
		assertTrue(caught);
		caught = false;
		
	}
	
	@Test
	public void testGoLeftEdge() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		target.init(env, 9, 2);
		guard.init(env, 0, 2,target);

		testInvariant();
		int xbefore = guard.getWdt();
		int ybefore = guard.getHgt();
		
		testInvariant();
		guard.goLeft();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());
	}

	/*
	 * Test de GoLeft() avec un obstacle sur le chemin ( case MTL, PLT, un autre Guard ) 
	 * On s'attend a ce que sa position n'ai pas changer apres le GoLeft()
	 */
	@Test
	public void testGoLeftObstacle() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(0, 2, Cell.PLT);
		env.init(editscreen);
		target.init(env, 9, 2);
		guard.init(env, 1, 2,target);
		int xbefore = guard.getWdt();
		int ybefore = guard.getHgt();
		testInvariant();
		guard.goLeft();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());
		editscreen.setNature(0, 2, Cell.MTL);
		env.init(editscreen);
		target.init(env, 9, 2);
		guard.init(env, 1, 2,target);
		xbefore = guard.getWdt();
		ybefore = guard.getHgt();
		testInvariant();
		guard.goLeft();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());
		
		editscreen.setNature(0, 2, Cell.EMP);
		env.init(editscreen);
		GuardContract g2 = new GuardContract(new GuardImpl());
		
		target.init(env, 9, 2);
		g2.init(env, 0, 2,target);
		guard.init(env, 1, 2,target);

		xbefore = guard.getWdt();
		ybefore = guard.getHgt();
		testInvariant();
		guard.goLeft();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());
	}

	@Test
	public void testGoLeftFalling() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		target.init(env, 9, 2);
		guard.init(env, 5, 5,target);

		int xbefore = guard.getWdt();
		int ybefore = guard.getHgt();
		testInvariant();
		guard.goLeft();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore - 1 == guard.getHgt());
	}

	@Test
	public void testGoLeftNormal() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		target.init(env, 9, 2);
		guard.init(env, 2, 2,target);

		int xbefore = guard.getWdt();
		int ybefore = guard.getHgt();
		testInvariant();
		guard.goLeft();
		testInvariant();
		assertTrue(xbefore - 1 == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());
	}

	@Test
	public void testGoRightEdge() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		target.init(env, 1, 2);
		guard.init(env, 14, 2,target);
		int xbefore = guard.getWdt();
		int ybefore = guard.getHgt();
		testInvariant();
		guard.goRight();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());
	}

	/*
	 * Sym�trique de GoLeft
	 */
	@Test
	public void testGoRightObstacle() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(2, 2, Cell.PLT);
		env.init(editscreen);
		target.init(env, 9, 2);
		guard.init(env, 1, 2,target);
		
		int xbefore = guard.getWdt();
		int ybefore = guard.getHgt();
		testInvariant();
		guard.goRight();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());

		editscreen.setNature(2, 2, Cell.MTL);
		env.init(editscreen);
		target.init(env, 9, 2);
		guard.init(env, 1, 2,target);

		xbefore = guard.getWdt();
		ybefore = guard.getHgt();
		testInvariant();
		guard.goRight();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());
		
		editscreen.setNature(2, 2, Cell.EMP);
		env.init(editscreen);
		GuardContract g2 = new GuardContract(new GuardImpl());
		
		target.init(env, 9, 2);
		g2.init(env, 2, 2,target);
		guard.init(env, 1, 2,target);

		xbefore = guard.getWdt();
		ybefore = guard.getHgt();
		testInvariant();
		guard.goRight();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());
	}

	@Test
	public void testGoRightFalling() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		target.init(env, 9, 2);
		guard.init(env, 5, 5,target);

		int xbefore = guard.getWdt();
		int ybefore = guard.getHgt();
		testInvariant();
		guard.goRight();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore - 1 == guard.getHgt());
	}

	@Test
	public void testGoRightNormal() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		target.init(env, 9, 2);
		guard.init(env, 2, 2,target);

		int xbefore = guard.getWdt();
		int ybefore = guard.getHgt();
		testInvariant();
		guard.goRight();
		testInvariant();
		assertTrue(xbefore + 1 == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());
	}

	@Test
	public void testGoUpEdge() {
		editscreen.init(2, 3);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		editscreen.setNature(1, 1, Cell.LAD);
		env.init(editscreen);
		target.init(env, 2, 1);
		guard.init(env, 0, 1,target);
		testInvariant();
		guard.goLeft();
		testInvariant();
		int xbefore = guard.getWdt();
		int ybefore = guard.getHgt();
		testInvariant();
		guard.goUp();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());
	}

	@Test
	public void testGoUpObstacle() {
		editscreen.init(3, 4);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		editscreen.setNature(1, 1, Cell.LAD);
		editscreen.setNature(1, 2, Cell.MTL);
		
		env.init(editscreen);
		target.init(env, 2, 1);
		guard.init(env, 0, 1,target);
		
		testInvariant();
		guard.goLeft();
		testInvariant();
		int xbefore = guard.getWdt();
		int ybefore = guard.getHgt();
		testInvariant();
		guard.goUp();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());

		editscreen.setNature(1, 2, Cell.PLT);
		env.init(editscreen);
		env.init(editscreen);
		target.init(env, 2, 1);
		guard.init(env, 0, 1,target);
		testInvariant();
		guard.goRight();
		testInvariant();
		xbefore = guard.getWdt();
		ybefore = guard.getHgt();
		testInvariant();
		guard.goUp();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());

		editscreen.setNature(1, 2, Cell.HDR);
		env.init(editscreen);
		target.init(env, 2, 1);
		guard.init(env, 0, 1,target);
		
		testInvariant();
		guard.goRight();
		testInvariant();
		xbefore = guard.getWdt();
		ybefore = guard.getHgt();
		testInvariant();
		guard.goUp();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());
		
		GuardContract g2 = new GuardContract(new GuardImpl());
		
		editscreen.setNature(1, 2, Cell.EMP);
		
		env.init(editscreen);
		target.init(env, 2, 1);
		g2.init(env, 2, 1,target);
		guard.init(env, 0, 1,target);
		g2.goLeft();
		g2.goUp();
		
		testInvariant();
		guard.goRight();
		testInvariant();
		xbefore = guard.getWdt();
		ybefore = guard.getHgt();
		testInvariant();
		guard.goUp();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());
	}

	@Test
	public void testGoUpFalling() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		target.init(env, 9, 2);
		guard.init(env, 5, 5,target);

		int xbefore = guard.getWdt();
		int ybefore = guard.getHgt();
		testInvariant();
		guard.goUp();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore - 1 == guard.getHgt());
	}

	@Test
	public void testGoUpNormal() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 1; x < editscreen.getHeight(); x++) {
			editscreen.setNature(1, x, Cell.LAD);
		}
		env.init(editscreen);
		target.init(env, 9, 1);
		guard.init(env, 0, 1,target);
		testInvariant();
		guard.goRight();
		testInvariant();
		int xbefore = guard.getWdt();
		int ybefore = guard.getHgt();
		testInvariant();
		int j = 0;
		for (int x = 2; x < 9; x++) {
			guard.goUp();
			testInvariant();
			j++;
		}
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore + j == guard.getHgt());
	}

	@Test
	public void testGoDownObstacle() {
		editscreen.init(10, 14);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(1, 1, Cell.MTL);
		env.init(editscreen);
		target.init(env, 9, 2);
		guard.init(env, 1, 2,target);

		int xbefore = guard.getWdt();
		int ybefore = guard.getHgt();
		testInvariant();
		
		guard.goDown();
		
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());

		editscreen.setNature(1, 1, Cell.PLT);
		env.init(editscreen);
		target.init(env, 9, 2);
		guard.init(env, 1, 2,target);

		xbefore = guard.getWdt();
		ybefore = guard.getHgt();
		testInvariant();
		guard.goDown();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());
		
		editscreen.setNature(1, 1, Cell.EMP);
		GuardContract g2 = new GuardContract(new GuardImpl());
				
		env.init(editscreen);
		target.init(env, 2, 1);
		g2.init(env, 1, 1,target);
		guard.init(env, 1, 2,target);
		xbefore = guard.getWdt();
		ybefore = guard.getHgt();
		testInvariant();
		guard.goDown();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());
	}

	@Test
	public void testGoDownFalling() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		target.init(env, 9, 2);
		guard.init(env, 5, 5,target);

		int xbefore = guard.getWdt();
		int ybefore = guard.getHgt();
		testInvariant();
		guard.goDown();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore - 1 == guard.getHgt());
	}

	@Test
	public void testGoDownNormal() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(2, 2, Cell.MTL);
		editscreen.setNature(3, 2, Cell.LAD);
		
		env.init(editscreen);
		target.init(env, 9, 2);
		guard.init(env, 2, 3,target);

		guard.goRight();
		int xbefore = guard.getWdt();
		int ybefore = guard.getHgt();
		testInvariant();
		guard.goDown();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore - 1 == guard.getHgt());

		editscreen.setNature(2, 2, Cell.MTL);
		editscreen.setNature(3, 2, Cell.HDR);
		
		env.init(editscreen);
		target.init(env, 9, 2);
		guard.init(env, 2, 3,target);
		testInvariant();
		guard.goRight();
		testInvariant();
		xbefore = guard.getWdt();
		ybefore = guard.getHgt();
		testInvariant();
		guard.goDown();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore - 1 == guard.getHgt());

		editscreen.setNature(2, 2, Cell.MTL);
		editscreen.setNature(3, 2, Cell.EMP);
		
		env.init(editscreen);
		target.init(env, 9, 2);
		guard.init(env, 2, 3,target);
		testInvariant();
		guard.goRight();
		testInvariant();
		xbefore = guard.getWdt();
		ybefore = guard.getHgt();
		testInvariant();
		guard.goDown();
		testInvariant();
		assertTrue(xbefore == guard.getWdt());
		assertTrue(ybefore - 1 == guard.getHgt());
	}
	
	/*
	 * Test pour verifier qu'un Guard peut etre sur la m�me case qu'un player
	 */
	@Test
	public void guardOnPlayerCase() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		target.init(env, 3, 2);
		guard.init(env, 2, 2,target);
		int xbefore = guard.getWdt();
		int ybefore = guard.getHgt();
		testInvariant();
		guard.goRight();
		testInvariant();
		assertTrue(xbefore+1 == guard.getWdt());
		assertTrue(ybefore == guard.getHgt());
		assertTrue(target.getWdt() == guard.getWdt());
		assertTrue(target.getHgt() == guard.getHgt());
	}
	
	/*
	 * Test qui verifie qu'un Guard reste coincer dans un trou quand il tombe dedans et cela peut importe les operations qu'il fait
	 */
	@Test
	public void guardStuckOnHole() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(5, 5, Cell.PLT);
		editscreen.setNature(6, 5, Cell.HOL);
		editscreen.setNature(7, 5, Cell.PLT);
		
		env.init(editscreen);
		target.init(env, 3, 2);
		guard.init(env, 5, 6,target);
		
		testInvariant();
		guard.goRight();
		testInvariant();
		guard.goUp();
		testInvariant();
		guard.goLeft();
		testInvariant();
		guard.goDown();
		testInvariant();
		assertTrue(6 == guard.getWdt());
		assertTrue(5 == guard.getHgt());
	}
	
	/*
	 * Test qui verifie que faire un climbLeft alors qu'on est pas dans un trou declenche bien une erreur
	 */
	@Test
	public void testClimbLeftNotInHole() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		
		boolean caught = false;
		try {
			env.init(editscreen);
			target.init(env, 3, 2);
			guard.init(env, 13, 2,target);
			testInvariant();
			guard.climbLeft();
			testInvariant();
		}
		catch(ContractError e) {
			caught = true;
		}
		assertTrue(caught);
		caught = false;
		
		try {
			env.init(editscreen);
			target.init(env, 3, 2);
			guard.init(env, 5, 5,target);
			testInvariant();
			guard.climbLeft();
			testInvariant();
		}
		catch(ContractError e) {
			caught = true;
		}
		assertTrue(caught);
		caught = false;
		
		try {
			editscreen.setNature(0, 2, Cell.HDR);
			env.init(editscreen);
			target.init(env, 3, 2);
			guard.init(env, 1, 2,target);
			testInvariant();
			guard.goLeft();
			testInvariant();
			testInvariant();
			guard.climbLeft();
			testInvariant();
		}
		catch(ContractError e) {
			caught = true;
		}
		assertTrue(caught);
		caught = false;
		
		try {
			editscreen.setNature(0, 2, Cell.LAD);
			env.init(editscreen);
			target.init(env, 3, 2);
			guard.init(env, 1, 2,target);
			testInvariant();
			guard.goLeft();
			testInvariant();
			testInvariant();
			guard.climbLeft();
			testInvariant();
		}
		catch(ContractError e) {
			caught = true;
		}
		assertTrue(caught);
		caught = false;
		
	}
	
	/*
	 * Symetrique
	 */
	@Test
	public void testClimbRightNegatif() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		
		boolean caught = false;
		try {
			env.init(editscreen);
			target.init(env, 3, 2);
			guard.init(env, 10, 2,target);
			testInvariant();
			guard.climbRight();
			testInvariant();
		}
		catch(ContractError e) {
			caught = true;
		}
		assertTrue(caught);
		caught = false;
		
		try {
			env.init(editscreen);
			target.init(env, 3, 2);
			guard.init(env, 5, 5,target);
			testInvariant();
			guard.climbRight();
			testInvariant();
		}
		catch(ContractError e) {
			caught = true;
		}
		assertTrue(caught);
		caught = false;
		
		try {
			editscreen.setNature(2, 2, Cell.HDR);
			env.init(editscreen);
			target.init(env, 3, 2);
			guard.init(env, 1, 2,target);
			testInvariant();
			guard.goRight();
			testInvariant();
			guard.climbRight();
			testInvariant();
		}
		catch(ContractError e) {
			caught = true;
		}
		assertTrue(caught);
		caught = false;
		
		try {
			editscreen.setNature(2, 2, Cell.LAD);
			env.init(editscreen);
			target.init(env, 3, 2);
			guard.init(env, 1, 2,target);
			testInvariant();
			guard.goRight();
			testInvariant();
			testInvariant();
			guard.climbRight();
			testInvariant();
		}
		catch(ContractError e) {
			caught = true;
		}
		assertTrue(caught);
		caught = false;
	}
	
	/*
	 * Test qui verifie qu'un climbRight au bord de l'ecran ne fait rien et nous laisse dans le trou
	 */
	@Test
	public void testClimbRightEdge() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(14, 2, Cell.HOL);
		
		env.init(editscreen);
		target.init(env, 3, 2);
		guard.init(env, 13, 2,target);
		
		testInvariant();
		guard.goRight();
		testInvariant();
		guard.goUp();
		testInvariant();
		guard.climbRight();
		testInvariant();
		assertTrue(14 == guard.getWdt());
		assertTrue(2 == guard.getHgt());
	}
	
	/*
	 * Symetrie
	 */
	@Test
	public void testClimbLeftEdge() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(0, 2, Cell.HOL);
		env.init(editscreen);
		target.init(env, 3, 2);
		guard.init(env, 1, 2,target);
		testInvariant();
		guard.goLeft();
		testInvariant();
		guard.goUp();
		testInvariant();
		guard.climbLeft();
		testInvariant();
		assertTrue(0 == guard.getWdt());
		assertTrue(2 == guard.getHgt());
	}
	
	/*
	 * Test qui verifie qu'un climbRight alors qu'on est dans un trou nous fait bien grimper a droite du trou
	 */
	@Test
	public void testClimbRightPositif() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(2, 1, Cell.HOL);
		
		env.init(editscreen);
		target.init(env, 5, 2);
		guard.init(env, 1, 2,target);
		testInvariant();
		guard.goRight();
		testInvariant();
		guard.goUp();
		testInvariant();
		guard.climbRight();
		
		testInvariant();
		assertTrue(3 == guard.getWdt());
		assertTrue(2 == guard.getHgt());
	}
	
	@Test
	public void testClimbLeftPositif() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(2, 1, Cell.HOL);
		
		env.init(editscreen);
		target.init(env, 5, 2);
		guard.init(env, 1, 2,target);
		testInvariant();
		guard.goRight();
		testInvariant();
		guard.goUp();
		testInvariant();
		guard.climbLeft();
		
		testInvariant();
		assertTrue(1 == guard.getWdt());
		assertTrue(2 == guard.getHgt());
	}
	
	/*
	 * Test qui verifie qu'un climbRight alors qu'il y a un obstacle ne fait rien 
	 * un obstacle est un MTL, PLT, ou un autre Guard
	 */
	@Test
	public void testClimbRightObstacle() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(2, 1, Cell.HOL);
		editscreen.setNature(3, 2, Cell.PLT);
		env.init(editscreen);
		target.init(env, 5, 2);
		guard.init(env, 1, 2,target);
		testInvariant();
		guard.goRight();
		testInvariant();
		guard.goUp();
		testInvariant();
		guard.climbRight();
		testInvariant();
		assertTrue(2 == guard.getWdt());
		assertTrue(1 == guard.getHgt());
		
		editscreen.setNature(3, 2, Cell.MTL);
		env.init(editscreen);
		target.init(env, 5, 2);
		guard.init(env, 1, 2,target);
		testInvariant();
		guard.goRight();
		testInvariant();
		guard.goUp();
		testInvariant();
		guard.climbRight();
		testInvariant();
		assertTrue(2 == guard.getWdt());
		assertTrue(1 == guard.getHgt());
		
		GuardContract g2 = new GuardContract(new GuardImpl());
		editscreen.setNature(3, 2, Cell.EMP);
		
		env.init(editscreen);
		g2.init(env, 3, 2,target);
		target.init(env, 5, 2);
		guard.init(env, 1, 2,target);
		testInvariant();
		guard.goRight();
		testInvariant();
		guard.goUp();
		testInvariant();
		guard.climbRight();
		testInvariant();
		assertTrue(2 == guard.getWdt());
		assertTrue(1 == guard.getHgt());
	}
	@Test
	public void testClimbLeftObstacle() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(2, 1, Cell.HOL);
		editscreen.setNature(1, 2, Cell.PLT);
		env.init(editscreen);
		target.init(env, 5, 2);
		guard.init(env, 3, 2,target);
		testInvariant();
		guard.goLeft();
		testInvariant();
		guard.goUp();
		testInvariant();
		guard.climbLeft();
		testInvariant();
		assertTrue(2 == guard.getWdt());
		assertTrue(1 == guard.getHgt());
		
		editscreen.setNature(1, 2, Cell.MTL);
		env.init(editscreen);
		target.init(env, 5, 2);
		guard.init(env, 3, 2,target);
		testInvariant();
		guard.goLeft();
		testInvariant();
		guard.goUp();
		testInvariant();
		guard.climbLeft();
		testInvariant();
		assertTrue(2 == guard.getWdt());
		assertTrue(1 == guard.getHgt());
		
		GuardContract g2 = new GuardContract(new GuardImpl());
		editscreen.setNature(1, 2, Cell.EMP);
		
		env.init(editscreen);
		g2.init(env, 1, 2,target);
		target.init(env, 5, 2);
		guard.init(env, 3, 2,target);
		testInvariant();
		guard.goLeft();
		testInvariant();
		guard.goUp();
		testInvariant();
		guard.climbLeft();
		testInvariant();
		assertTrue(2 == guard.getWdt());
		assertTrue(1 == guard.getHgt());
	}
	
	/*
	 * Scenario ou le guard se dirige vers la target tombe dans un trou, il y reste pour quelque step puis sort en direction de la target
	 */
	@Test
	public void testStepWhileMoving() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(1, 1, Cell.HOL);
		env.init(editscreen);
		target.init(env, 3, 2);
		guard.init(env, 0, 2,target);
		
		assertTrue(guard.getBehaviour()==Move.NEUTRAL) ;
		guard.step();
		assertTrue(guard.getBehaviour()==Move.RIGHT);
		guard.step();
		assertTrue(guard.getBehaviour()==Move.RIGHT);
		guard.step();
		assertTrue(guard.getEnvi().getCellNature(guard.getWdt(), guard.getHgt())==Cell.HOL);
		assertTrue(guard.getBehaviour()==Move.RIGHT);
		guard.step();
		assertTrue(guard.getEnvi().getCellNature(guard.getWdt(), guard.getHgt())==Cell.HOL);
		assertTrue(guard.getBehaviour()==Move.RIGHT);
		guard.step();
		assertTrue(guard.getEnvi().getCellNature(guard.getWdt(), guard.getHgt())==Cell.HOL);
		assertTrue(guard.getBehaviour()==Move.RIGHT);
		guard.step();
		assertTrue(guard.getEnvi().getCellNature(guard.getWdt(), guard.getHgt())==Cell.HOL);
		assertTrue(guard.getBehaviour()==Move.RIGHT);
		guard.step();
		assertTrue(guard.getEnvi().getCellNature(guard.getWdt(), guard.getHgt())==Cell.HOL);
		assertTrue(guard.getBehaviour()==Move.RIGHT);
		guard.step();
		assertTrue(guard.getEnvi().getCellNature(guard.getWdt(), guard.getHgt())==Cell.HOL);
		assertTrue(guard.getBehaviour()==Move.RIGHT);
		guard.step();
		assertTrue(guard.getWdt()==2 && guard.getHgt()==2);
		assertTrue(guard.getEnvi().getCellNature(guard.getWdt(), guard.getHgt())==Cell.EMP);
		guard.step();
		assertTrue(guard.getBehaviour()==Move.NEUTRAL) ;
		assertTrue(guard.getWdt()==target.getWdt() && guard.getHgt()==target.getHgt());
		
	}
	
	@Test
	public void testStepWhileInHOL2() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);}
		editscreen.setNature(4, 1, Cell.HOL);
		
		env.init(editscreen);
		target.init(env, 2, 2);
		guard.init(env, 5, 2,target);	
		
		assertTrue(guard.getBehaviour()==Move.NEUTRAL) ;
		guard.step();
		assertTrue(guard.getBehaviour()==Move.LEFT);
		guard.step();
		assertTrue(guard.getBehaviour()==Move.LEFT);
		guard.step();
		assertTrue(guard.getEnvi().getCellNature(guard.getWdt(), guard.getHgt())==Cell.HOL);
		assertTrue(guard.getBehaviour()==Move.LEFT);
		guard.step();
		assertTrue(guard.getEnvi().getCellNature(guard.getWdt(), guard.getHgt())==Cell.HOL);
		assertTrue(guard.getBehaviour()==Move.LEFT);
		guard.step();
		assertTrue(guard.getEnvi().getCellNature(guard.getWdt(), guard.getHgt())==Cell.HOL);
		assertTrue(guard.getBehaviour()==Move.LEFT);
		guard.step();
		assertTrue(guard.getEnvi().getCellNature(guard.getWdt(), guard.getHgt())==Cell.HOL);
		assertTrue(guard.getBehaviour()==Move.LEFT);
		guard.step();
		assertTrue(guard.getEnvi().getCellNature(guard.getWdt(), guard.getHgt())==Cell.HOL);
		assertTrue(guard.getBehaviour()==Move.LEFT);
		guard.step();
		assertTrue(guard.getEnvi().getCellNature(guard.getWdt(), guard.getHgt())==Cell.HOL);
		assertTrue(guard.getBehaviour()==Move.LEFT);
		guard.step();
		assertTrue(guard.getWdt()==3 && guard.getHgt()==2);
		assertTrue(guard.getEnvi().getCellNature(guard.getWdt(), guard.getHgt())==Cell.EMP);
		guard.step();
		assertTrue(guard.getBehaviour()==Move.NEUTRAL) ;
		assertTrue(guard.getWdt()==target.getWdt() && guard.getHgt()==target.getHgt());
	}
	
	/*
	 * Scenario ou le guard monte une echelle pour rejoindre la target
	 */
	@Test
	public void testStepClimbingLadder() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		for (int y = 2; y < editscreen.getHeight(); y++) {
			editscreen.setNature(0, y, Cell.LAD);
		}
		editscreen.setNature(1, 5, Cell.PLT);
		env.init(editscreen);
		target.init(env, 1, 6);
		guard.init(env, 1, 2,target);
		guard.goLeft();

		assertTrue(guard.getBehaviour()==Move.NEUTRAL) ;
		guard.step();
		assertTrue(guard.getBehaviour()==Move.UP) ;
		guard.step();
		assertTrue(guard.getBehaviour()==Move.UP) ;
		guard.step();
		assertTrue(guard.getBehaviour()==Move.UP) ;
		guard.step();
		assertTrue(guard.getBehaviour()==Move.UP) ;
		guard.step();
		assertTrue(guard.getBehaviour()==Move.RIGHT) ;
		guard.step();
		assertTrue(guard.getBehaviour()==Move.NEUTRAL) ;
		assertTrue(guard.getWdt()==target.getWdt() && guard.getHgt()==target.getHgt());
		
	}
	
	/*
	 * Scenario ou le guard descend une echelle pour rejoindre la target
	 */
	@Test
	public void testStepDescendingLadder() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		for (int y = 2; y < editscreen.getHeight(); y++) {
			editscreen.setNature(0, y, Cell.LAD);
		}
		editscreen.setNature(1, 5, Cell.PLT);
		env.init(editscreen);
		target.init(env, 1, 2);
		guard.init(env, 1, 6,target);
		guard.goLeft();

		assertTrue(guard.getBehaviour()==Move.NEUTRAL) ;
		guard.step();
		assertTrue(guard.getBehaviour()==Move.DOWN) ;
		guard.step();
		assertTrue(guard.getBehaviour()==Move.DOWN) ;
		guard.step();
		assertTrue(guard.getBehaviour()==Move.DOWN) ;
		guard.step();
		assertTrue(guard.getBehaviour()==Move.DOWN) ;
		
	}
	
	
	
}

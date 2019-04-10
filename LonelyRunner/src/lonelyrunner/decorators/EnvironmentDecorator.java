package lonelyrunner.decorators;

import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Item;
import lonelyrunner.service.CharacterService;
import lonelyrunner.service.EnvironmentService;

public class EnvironmentDecorator extends ScreenDecorator implements EnvironmentService{
	
	

	public EnvironmentDecorator(EnvironmentService delegate) {
		super(delegate);
	}

	public EnvironmentService getDelegate() {
		return (EnvironmentService) super.getDelegate();
	}
	
	@Override
	public Couple<CharacterService, Item> getCellContent(int x, int y) {
		return getDelegate().getCellContent(x, y);
	}

	@Override
	public int getHeight() {
		return getDelegate().getHeight();
	}

	@Override
	public int getWidth() {
		return getDelegate().getWidth();
	}

	@Override
	public Cell getCellNature(int i, int j) {
		return getDelegate().getCellNature(i, j);
	}

	@Override
	public void init(int h, int w) {
		getDelegate().init(h, w);
		
	}

	@Override
	public void dig(int u, int v) {
		getDelegate().dig(u, v);
	}

	@Override
	public void fill(int x, int y) {
		getDelegate().fill(x, y);
	}

	

}

package lonelyrunner.contract;

import lonelyrunner.contract.contracterr.InvariantError;
import lonelyrunner.contract.contracterr.PostconditionError;
import lonelyrunner.contract.contracterr.PreconditionError;
import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.ItemType;
import lonelyrunner.service.utils.SetCharItem;

public class EnvironmentContract extends ScreenContract implements EnvironmentService {

	public EnvironmentContract(EnvironmentService delegate) {
		super(delegate);
	}

	public EnvironmentService getDelegate() {
		return (EnvironmentService) super.getDelegate();
	}

	public void checkInvariant() {
		super.checkInvariant();
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				if (getCellNature(x, y) == Cell.MTL || getCellNature(x, y) == Cell.PLT) {
					if (!(getCellContent(x, y).getCar().isEmpty() && getCellContent(x, y).getItem() == null)) {
						throw new InvariantError("forall x:int and y:int with 0<=x<getWidth() and 0<=y<getHeight() \n"
								+ "				if getCellNature(x,y) in [MTL,PLT] then we should normally have getCellContent(x,y) == Null");
					}
				}
			}
		}
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 1; y < getHeight(); y++) {
				if (getCellContent(x, y).getCar().isEmpty() && getCellContent(x, y).getItem() == null)
					continue;
				if (getCellContent(x, y).getItem() != null) {
					if (getCellContent(x, y).getItem().getNature() == ItemType.Treasure) {
						if (!(getCellNature(x, y) == Cell.EMP
								&& (getCellNature(x, y - 1) == Cell.PLT || getCellNature(x, y - 1) == Cell.MTL
										|| !getCellContent(x, y - 1).getCar().isEmpty()))) {
							throw new InvariantError(
									"forall x:int and y:int with 0<=x<getWidth() and 0<=y<getHeight() \n"
											+ "				if t:Treasure in getCellContent(x,y) then we should normally have getCellNature(x,y) == EMP and getCellNature(x,y-1) in [PLT,MTL] or there is a character below it");
						}
					}
				}
			}
		}

	}

	@Override
	public SetCharItem getCellContent(int x, int y) {

		// pre
		if (!(x >= 0 && x < getWidth())) {
			throw new PreconditionError("getCellContent(" + x + ", " + y + " )",
					"x must be between 0 and strictly inf to getWidth");
		}
		if (!(y >= 0 && y < getHeight())) {
			throw new PreconditionError("getCellContent(" + x + ", " + y + " )",
					"y must be between 0 and strictly inf to getHeight");
		}
		return getDelegate().getCellContent(x, y);

	}

	@Override
	public void init(EditableScreenService ess) {
		// Call
		getDelegate().init(ess);
		checkInvariant();
		// Post
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				if (!(getCellNature(x, y) == getDelegate().getCellNature(x, y))) {
					throw new PostconditionError("init(ess)", "cellNature of " + x + " " + y + " changed ");
				}
			}
		}

	}

}

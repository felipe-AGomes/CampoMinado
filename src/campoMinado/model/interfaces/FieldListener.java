package campoMinado.model.interfaces;

import campoMinado.model.Field;

public interface FieldListener {
	void onExplosionFieldEvent(Field field);

	void onMarkFieldEvent(Field field);

	void onOpenFieldEvent(Field field);
}

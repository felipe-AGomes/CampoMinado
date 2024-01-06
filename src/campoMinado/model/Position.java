package campoMinado.model;

import java.util.Objects;

public class Position {
	public final int X;
	public final int Y;

	public Position(int x, int y) {
		this.X = x;
		this.Y = y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(X, Y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		return X == other.X && Y == other.Y;
	}

}

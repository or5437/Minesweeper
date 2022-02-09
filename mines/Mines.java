package mines;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Mines {

	private class Place {
		int i, j;

		// constructor to create place
		public Place(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}

	private class Location {
		private Place p;
		private boolean open = false, mine = false, flag = false;
		private int numMinesNear;

		// set new location
		public Location(int i, int j) {
			p = new Place(i, j);
		}

		// set open=true
		public void setOpen() {
			open = true;

		}

		// set flag
		public void setflag() {
			if (flag == false)
				flag = true;
			else
				flag = false;
		}

		// set mine
		public boolean setMine() {
			if (mine)
				return false;
			mine = true;
			return true;
		}

		// get list of neighbors
		public Set<Location> neighbors() {
			Set<Location> s = new HashSet<>();
			int x = p.i, y = p.j;
			if (x > 0)
				s.add(new Location(x - 1, y));
			if (y > 0)
				s.add(new Location(x, y - 1));
			if (x < mines.length - 1)
				s.add(new Location(x + 1, y));
			if (y < mines[0].length - 1)
				s.add(new Location(x, y + 1));
			if (x > 0 && y > 0)
				s.add(new Location(x - 1, y - 1));
			if (x < mines.length - 1 && y < mines[0].length - 1)
				s.add(new Location(x + 1, y + 1));
			if (x > 0 && y < mines[0].length - 1)
				s.add(new Location(x - 1, y + 1));
			if (x < mines.length - 1 && y > 0)
				s.add(new Location(x + 1, y - 1));
			return s;
		}

//return string of the location
		@Override
		public String toString() {
			if (!open && !showAll) {
				if (flag)
					return "F";
				else
					return ".";
			} else {
				if (mine)
					return "X";
				else if (numMinesNear == 0)
					return " ";
				else
					return "" + numMinesNear;
			}
		}
	}

	private int height;
	private int width;
	private int numMines;
	private Location mines[][];
	private boolean showAll = false;

	// constructor that initialize plays at the width and height of the data, with
	// exactly numMines randomly placed mines.
	public Mines(int height, int width, int numMines) {
		this.height = height;
		this.width = width;
		this.numMines = numMines;
		mines = new Location[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				mines[i][j] = new Location(i, j);// set location in mines[i][j]
		Random r = new Random();// define random
		int x, y, num = this.numMines;
		while (num > 0) {
			x = r.nextInt(mines.length);// random number
			y = r.nextInt(mines[0].length);// random number
			addMine(x, y);// add mine in the random location
			num--;
		}
	}

//Inserts a mine instead of the given location.
	public boolean addMine(int i, int j) {
		if (mines[i][j].mine)// if the location is mine
			return false;
		for (Location location : mines[i][j].neighbors())// pass on all the neighbors
			mines[location.p.i][location.p.j].numMinesNear++;// add 1 to count of mines around
		return mines[i][j].setMine();// set mine
	}

//Marks that the user is opening this location. returns true if it is not a mine and open the neighbors if there is no mine.
	public boolean open(int i, int j) {
		if (mines[i][j].mine)// if there is mine
			return false;
		mines[i][j].setOpen();// open
		if (mines[i][j].numMinesNear == 0)// if there is no mines around
			for (Location p : mines[i][j].neighbors())// open the neighbors
				if (!mines[p.p.i][p.p.j].open)
					open(p.p.i, p.p.j);
		return true;

	}

//A flag name in the given location, or removes it if there is already a flag name.
	public void toggleFlag(int x, int y) {
		mines[x][y].setflag();// set flag in the location
	}

//Returns truth if all non-mine locations are open.
	public boolean isDone() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++)
				if (mines[i][j].mine == false && mines[i][j].open == false)// if the location is not mine and not open
					return false;
		}
		return true;
	}

//Returns a representation as a string of the position
	public String get(int i, int j) {
		return mines[i][j].toString();// return string of the location
	}

//Sets the value of the showAll field
	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}

//Returns a description of the board as a string by get per position.
	public String toString() {
		StringBuilder s = new StringBuilder();

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				s.append(get(i, j));
			}
			s.append("\n");
		}
		return s.toString();
	}
}

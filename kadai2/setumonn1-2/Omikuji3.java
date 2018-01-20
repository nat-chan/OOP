class Omikuji3 extends Omikuji2 {
    String second() {

	switch(rand()) {
	case 0:
	    return "アンラッキー";
	case 1:
	    return "ちょっとラッキー";
	case 2:
	    return "ラッキー";
	default:
	    return "大ラッキー";
	}
    }
}

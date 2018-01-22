public class Rectangle
{
    int left;
    int bottom;
    int width;
    int height;

    Rectangle (int left, int bottom, int width, int height) {
        this.left = left;
        this.bottom = bottom;
        this.width = width;
        this.height = height;
    }

    void printRectangle () {
        System.out.print("左下(" + left + "," + bottom + "),");
        System.out.println("右上(" + (left + width) + "," + (bottom + height) + ")");
    }
    int getArea () {
        return Math.abs(width * height);
    }
    boolean isLarger (Rectangle r) {
        if (this.getArea() > r.getArea()) {
            return true;
        } else {
            return false;
        }
    }
    boolean contains (Point p) {
        boolean inx = false;
        boolean iny = false;
        if (width < 0 &&
            (this.left >= p.x && p.x >= (this.left + this.width))) {
            inx = true;
        } else if (width >= 0 &&
                   (this.left <= p.x && p.x <= (this.left + this.width))) {
            inx = true;
        } else {}
        if (height < 0 &&
            (this.bottom >= p.y && p.y >= (this.bottom + this.height))) {
            iny = true;
        } else if (height >= 0 &&
                   (this.bottom <= p.y && p.y <= (this.bottom + this.height))) {
            iny = true;
        } else {}
        return (inx && iny);
    }
}

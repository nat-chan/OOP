import java.util.*;
public class RatioCalc extends OOGCalc<RatioCalc.Ratio>
{
    class Ratio implements Numeric<Ratio> {
        long num;
        long denom;
        Ratio(long num, long denom) {
            this.num = num;
            this.denom = denom;
        }
        public Ratio add(Ratio v) { return (new Ratio(this.num, this.denom)).add(v); }
        public Ratio subtract(Ratio v) { return (new Ratio(this.num, this.denom)).subtract(v); }
        public Ratio multiply(Ratio v) { return (new Ratio(this.num, this.denom)).multiply(v); }
        public Ratio divide(Ratio v) { return (new Ratio(this.num, this.denom)).divide(v); }
        public String toString() { return (new Ratio(this.num, this.denom)).toString(); }
    }
    protected Ratio fromRatio(long num, long denom) {
        
        return new Ratio(num, denom);
    }
    public static void main(String args[])
    {
        new RatioCalc().run();
    }
}

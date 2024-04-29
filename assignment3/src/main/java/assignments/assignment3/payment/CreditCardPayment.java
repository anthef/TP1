package assignments.assignment3.payment;

public class CreditCardPayment implements DepeFoodPaymentSystem {
    final private double TRANSACTIONFEEPERCENTAGE = 0.02;
    private double transactionFee;

    public double getTransactionFee(){
        return transactionFee;
    }

    public void setTransactionFee(double transactionFee){
        this.transactionFee = transactionFee;
    }

    public long countTransactionFee(long amount){
        long transactionFee = (long) TRANSACTIONFEEPERCENTAGE * amount;
        return transactionFee;
    }

    @Override
    public long processPayment(long amount){
        return countTransactionFee(amount) * amount;
    }

}
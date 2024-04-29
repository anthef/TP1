package assignments.assignment3.payment;

public class CreditCardPayment implements DepeFoodPaymentSystem {
    final private double TRANSACTIONFEEPERCENTAGE = 0.02;
    private double transactionFee;

    public long countTransactionFee(long amount){
        double transactionFee = TRANSACTIONFEEPERCENTAGE * amount;
        long tf = (long) transactionFee;
        return tf;
    }

    @Override
    public long processPayment(long amount){
        return countTransactionFee(amount) + amount;
    }

}
package assignments.assignment3.payment; //package

public class CreditCardPayment implements DepeFoodPaymentSystem {
    private static final double TRANSACTION_FEE_PERCENTAGE = 0.02;

    @Override
    public long processPayment(long saldo, long amount) {
        return amount + (long) (amount * TRANSACTION_FEE_PERCENTAGE);
    }

    public long countTransactionFee(long amount){ //Count transaction fee method
        double transactionFee = TRANSACTION_FEE_PERCENTAGE * amount;
        long tf = (long) transactionFee;
        return tf;
    }
}
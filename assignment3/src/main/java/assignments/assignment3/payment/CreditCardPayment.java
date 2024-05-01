package assignments.assignment3.payment; //package

public class CreditCardPayment implements DepeFoodPaymentSystem {
    //Datafield
    final private double TRANSACTIONFEEPERCENTAGE = 0.02; 

    public long countTransactionFee(long amount){ //Count transaction fee method
        double transactionFee = TRANSACTIONFEEPERCENTAGE * amount;
        long tf = (long) transactionFee;
        return tf;
    }

    //override process payment
    @Override
    public long processPayment(long amount){
        return countTransactionFee(amount) + amount;
    }

}
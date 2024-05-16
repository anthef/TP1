package assignments.assignment3.payment;

import assignments.assignment3.MainMenu;

public interface DepeFoodPaymentSystem {

    public long processPayment(long saldo, long amount) throws Exception;
}
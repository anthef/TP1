package assignments.assignment3.payment;

public class DebitPayment implements DepeFoodPaymentSystem {
    final private double MINIMUMTOTALPRICE = 50000;
    @Override
    public long processPayment(long amount) {
        if (amount >= MINIMUMTOTALPRICE) {
            System.out.print("Berhasil Membayar Bill sebesar " + amount);
            return amount;
        } else {
            System.out.println("Jumlah pesanan < 50000 mohon menggunakan metode pembayaran yang lain");
            return -1;
        }
    }
}
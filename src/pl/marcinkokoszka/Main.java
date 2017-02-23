package pl.marcinkokoszka;

public class Main {

    public static void main(String[] args) {

		VendingMachineKata vmk = new VendingMachineKata();
		vmk.service(2, 1, 1, 3, 4, 5);
		System.out.println(vmk.getA());
	}
}

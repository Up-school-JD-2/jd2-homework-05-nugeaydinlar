import java.util.Random;
import java.util.Scanner;


public class PaymentApp {
	
    public static void main(String[] args) {
    	
    	Scanner scanner = new Scanner(System.in);
    	try {
    		
    		System.out.println("odeme tutarini giriniz: ");
    		double paymentAmount = scanner.nextDouble();
    		scanner.nextLine(); // Bos deger oku
    		
    		if(!isValidPaymentAmount(paymentAmount)) {
    			throw new InvalidAmountException("odeme tutari negatif veya virgul iceremez");
    		}
    	
    		
    		System.out.println("kart numaranizi giriniz: ");
    		String cardNumber = scanner.nextLine();
    		
    		if(!isValidcardNumber(cardNumber))
    		{
    			throw new InvalidCardNumberException("kart numaraniz 16 haneli olmali ve yalnizca rakamlardan olusmalidir");
    		}
    		
    		
    		System.out.println("son kullanma tarihini giriniz: (MM/YYYY)");
    		String expirationDate = scanner.nextLine();
    		
    		// '/' karakterini kontrol et
    		if (!expirationDate.contains("/")) {
    		    throw new InvalidExpirationDateException("Geçersiz son kullanma tarihi formatı: ");
    		}
    		
    		if (!isValidExpirationDate(expirationDate) || !expirationDate.contains("/")) {
    		    throw new InvalidExpirationDateException("Gecersiz son kullanma tarihi/formati ");
    		}
    	
    		System.out.println("guvenlik kodunu giriniz: ");
    		String securityCode = scanner.nextLine();
    		
    		if(!isValidsecurityCode(securityCode)) {
    			throw new InvalidSecurityCodeException("guvenlik kodu 3 haneli olmali ve yalnizca rakamlardan olusmalidir");
    		}
    		
    		
    		pay();
    	 } 
    	catch (SystemNotWorkingException e) {
    		e.getMessage();

		} catch (InvalidAmountException | InvalidCardNumberException | InvalidExpirationDateException | InvalidSecurityCodeException e) {
			System.out.println(e.getMessage());
		}
    	finally {
            scanner.close();
        }  	
    	
    }
    	

    private static boolean isValidPaymentAmount(double paymentAmount) {
    	if(paymentAmount <= 0 || paymentAmount != (int) paymentAmount) {
    	return false;
    	}
		return true;
    }
	
    private static boolean isValidcardNumber(String cardNumber) {
        if (cardNumber.length() != 16) {
          return false;
        }

        for (char c : cardNumber.toCharArray()) {
          if (!Character.isDigit(c)) {
            return false;
          }
        }
        return true;
      }
    
    private static boolean isValidExpirationDate(String expirationDate) {
    	
    	String[] dateParts = expirationDate.split("/");
		int month = Integer.parseInt(dateParts[0]);
		int year = Integer.parseInt(dateParts[1]);
    	if (year < 2023 || month <= 0 || month > 12) {
    		return false;
    	}
    	return true;    	
    	}  	
    
    
    private static boolean isValidsecurityCode(String securityCode) {
        if (securityCode.length() != 3) {
          return false;
        }

        for (char c : securityCode.toCharArray()) {
          if (!Character.isDigit(c)) {
            return false;
          }
        }
        return true;
      }
    
    
    public static void pay() throws SystemNotWorkingException {
        int retryCount = 0;
        while (retryCount < 2) { // islem en fazla 2 kez tekrar eder
            try {
                int randomNumber = (int) (Math.random() * 101);
                System.out.println("random sayi: " + randomNumber);

                if (randomNumber > 75) {
                    throw new SystemNotWorkingException("Sistem Calismiyor!");
                } else {
                    System.out.println("İslem Basarili!");
                    break;
                }
            } catch (SystemNotWorkingException e) {
                System.out.println(e.getMessage());
                retryCount++;
            }
        }
        if (retryCount == 2) {
            throw new SystemNotWorkingException("İslem basarısız oldu!");
        }
    }
    }
    	
    
    
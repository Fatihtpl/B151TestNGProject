package techproed.tests.day27_SmokeTest_Excel;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import techproed.pages.BlueRentalPage;
import techproed.utilities.ConfigReader;
import techproed.utilities.Driver;
import techproed.utilities.ExcelReader;
import techproed.utilities.ReusableMethods;

public class C04_BlueRentalExcelTest {
    @Test
    public void test01() {
        //Dosya yolu ve sayfa ismi ile objemizi oluşturalım
        String dosyaYolu = ConfigReader.getProperty("dosyaYolu");
        String sayfaIsmi = ConfigReader.getProperty("sayfaIsmi");
        ExcelReader reader = new ExcelReader(dosyaYolu,sayfaIsmi);

        /*
        mysmoketestdata excel dosyasındaki tüm email ve passwordler ile
        Bluerentalcar sitesinde login olalım ve her kullanıcı için login olduğunu doğrulayalım
         */
        //Bluerentalcar sayfasına gidelim
        Driver.getDriver().get(ConfigReader.getProperty("blueRentalUrl"));
        BlueRentalPage rentalPage = new BlueRentalPage();//-->Locatelere ulaşabilmek için pageden obje oluşturduk

        //Bir loop oluşturup excel dosyasındaki tüm veriler ile login olalım
        for (int i = 1; i <=reader.rowCount() ; i++) {//-->i'yi 1'den baslattık çünkü 0. indexte başlıklar var
            String email = reader.getCellData(i,0);
            String password = reader.getCellData(i,1);
            System.out.println(email +" || "+password);// normalde burası yok sadece gormek ıcın yazdırdık.
            rentalPage.login.click();
            ReusableMethods.bekle(2);
            rentalPage.email.sendKeys(email, Keys.TAB, password, Keys.ENTER);
            ReusableMethods.bekle(2);
            rentalPage.loginVerify.click();
            ReusableMethods.bekle(1);
            Assert.assertTrue(rentalPage.logOut.isDisplayed());
            rentalPage.logOut.click();
            ReusableMethods.bekle(1);
            rentalPage.ok.click();
        }
        //Sayfayı kapatınız
        Driver.closeDriver();

    }
}
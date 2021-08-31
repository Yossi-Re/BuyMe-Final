import org.openqa.selenium.By;

public class HomeScreen extends BasePage {
    /**
     * Function that includes all steps required in "Step B"
     */

    public void setSearchForGift() {

        pickPrice();
        pickRegion();
        pickCategory();
        pressFindMeGift();
    }

    public void pickPrice() {
        clickElement(By.xpath("//span[text()='סכום']"));
        clickElement(By.xpath("//li[@data-option-array-index='5']"));
    }

    public void pickRegion() {
        clickElement(By.xpath("//span[text()='אזור']"));
        clickElement(By.xpath("//li[text()='ת\"א והסביבה']"));
    }

    public void pickCategory() {

        clickElement(By.xpath("//span[text()='קטגוריה']"));
        clickElement(By.xpath("//li[text()='גיפט קארד למסעדות שף']"));

    }

    public void pressFindMeGift() {
        clickElement(By.xpath("//a[text()='תמצאו לי מתנה']"));
    }
}
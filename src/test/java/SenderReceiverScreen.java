import org.openqa.selenium.By;

public class SenderReceiverScreen extends BasePage {
    /**
     * A set of functions in order to execute Step D.
     */

    public void giftForSomeoneElse() {
        System.out.println("before enter receiver name");
        enterReceiverName();
//         enterSenderName();
        pickAnEvent();
        enterBlessing();
        uploadPicture();
        continueAfterPurchaseDetails();
        pressRadioAfterPayment();
        pickEmailSMS();
        enterEmailAddress();
        pressSave();
    }

    public void enterReceiverName() {
        sendKeysToElement(By.xpath("//label[@id='friendName']//input"), "Biden");
    }

    /**
     * No need to enter sender's name. It is filled automatically by the system.
     */
//    public void enterSenderName(){
    public void pickAnEvent() {
        clickElement(By.className("selected-text"));
        clickElement(By.xpath("//ul//li//span[ text()='יום הולדת']"));

    }

    public void enterBlessing() {
        sendKeysToElement(By.xpath("//textarea[ @placeholder='מזל טוב, תודה רבה או פשוט מלא אהבה? כאן כותבים מילים טובות ואיחולים שמחים']"), "Happy birthday dear Jo");

    }

    public void uploadPicture() {
        sendKeysToElement(By.xpath("//input[@type='file']"), System.getProperty("user.dir") + "/The_Chicken.JPG");
    }

    /**
     * Press "Continue" button in order to move to the next page.
     */
    protected void continueAfterPurchaseDetails() {
        clickElement(By.xpath("//span[text()='המשך']"));
    }

    public void pressRadioAfterPayment() {
        clickElement(By.xpath("//span[text()=\"עכשיו\"]"));
    }

    public void pickEmailSMS() {
        clickElement(By.xpath("//div[@class='ember-view bm-sending-methods']//div[2]//div"));
    }

    public void enterEmailAddress() {
        clearText(By.xpath("//input[@placeholder='מייל מקבל/ת המתנה']"));
        sendKeysToElement(By.xpath("//input[@placeholder='מייל מקבל/ת המתנה']"), "biden@gmail.com");
    }

    /**
     * Press Save is: "Continue for payment button". We press it:
     */
    public void pressSave() {
        clickElement(By.xpath("//span[text()=\"המשך לתשלום\"]"));
    }
}

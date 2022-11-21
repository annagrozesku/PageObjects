package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TransferMoneyPage {
    private ElementsCollection heading = $$(".heading");
    private SelenideElement amountFiled = $("[data-test-id=amount] input");
    private SelenideElement fromFiled = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement errorMessage = $("[data-test-id=error-notification] .notification__content");

    public TransferMoneyPage() {
        heading.find(exactText("Пополнение карты")).shouldBe(visible);
    }

    public void transferMoney(String amountToTransfer, DataHelper.CardNumber cardNumber) {
        amountFiled.setValue(amountToTransfer);
        fromFiled.setValue(cardNumber.getCardNumber());
        transferButton.click();
    }

    public DashboardPage validTransferMoney(String amountToTransfer, DataHelper.CardNumber cardNumber) {
        transferMoney(amountToTransfer, cardNumber);
        return new DashboardPage();
    }

    public void findErrorMessage(String expectedText) {
        errorMessage.shouldHave(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }
}

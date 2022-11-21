package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var cardNumberFirst = DataHelper.getCardNumberFirst();
        var cardNumberSecond = DataHelper.getCardNumberSecond();
        var firstCardBalance = dashboardPage.getCardBalance(cardNumberFirst);
        var secondCardBalance = dashboardPage.getCardBalance(cardNumberSecond);
        var amount = DataHelper.generateValidAmount(firstCardBalance);
        var expectedFirstCardBalance = firstCardBalance - amount;
        var expectedSecondCardBalance = secondCardBalance + amount;
        var transferMoney = dashboardPage.transferTo(cardNumberSecond);
        dashboardPage = transferMoney.validTransferMoney(String.valueOf(amount), cardNumberFirst);
        var actualFirstCardBalance = dashboardPage.getCardBalance(cardNumberFirst);
        var actualSecondCardBalance = dashboardPage.getCardBalance(cardNumberSecond);
        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    void shouldFindErrorMessageIfTransferInvalidAmount() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var cardNumberFirst = DataHelper.getCardNumberFirst();
        var cardNumberSecond = DataHelper.getCardNumberSecond();
        var firstCardBalance = dashboardPage.getCardBalance(cardNumberFirst);
        var secondCardBalance = dashboardPage.getCardBalance(cardNumberSecond);
        var amount = DataHelper.generateInvalidAmount(secondCardBalance);
        var transferMoney = dashboardPage.transferTo(cardNumberFirst);
        transferMoney.transferMoney(String.valueOf(amount), cardNumberSecond);
        var actualFirstCardBalance = dashboardPage.getCardBalance(cardNumberFirst);
        var actualSecondCardBalance = dashboardPage.getCardBalance(cardNumberSecond);
        assertEquals(firstCardBalance, actualFirstCardBalance);
        assertEquals(secondCardBalance, actualSecondCardBalance);

    }
}

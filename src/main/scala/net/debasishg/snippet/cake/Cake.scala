package net.debasishg.snippet.cake

import java.util.Date

trait BalanceComponent {
  type Balance

  def balance(amount: Double, currency: Currency, asOf: Date): Balance

  def inBaseCurrency(b: Balance): Balance
}

trait SimpleBalanceComponent extends BalanceComponent {
  type Balance = (Double, Currency, Date)

  override def balance(amount: Double, currency: Currency, asOf: Date) = (amount, currency, asOf)

  override def inBaseCurrency(b: Balance) =
    (b._1 * baseCurrencyFactor.get(b._2).get, baseCurrency, b._3)
}

trait CustomBalanceComponent extends BalanceComponent {
  type Balance = BalanceRep

  case class BalanceRep(amount: Double, currency: Currency, asOf: Date)

  override def balance(amount: Double, currency: Currency, asOf: Date) =
    BalanceRep(amount, currency, asOf)

  override def inBaseCurrency(b: Balance) =
    BalanceRep(b.amount * baseCurrencyFactor.get(b.currency).get, baseCurrency, b.asOf)
}


case class Account(no: String, name: String, openedOn: Date, status: String)

trait Portfolio {
  val bal: BalanceComponent
  import bal._  // imports path dependent type: bal.Balance

  def currentPortfolio(account: Account): List[Balance] // Balance type is defined in BalanceComponent
}

trait ClientPortfolio extends Portfolio {
  val bal: BalanceComponent
  //import bal._ // imports path dependent types and methods that use them;
  // Referenced fully qualified bal.balance method below to show another writing style

  override def currentPortfolio(account: Account) = {
    import java.util.Calendar
    //.. an actual impl would fetch from a database
    List(
      bal.balance(1000, EUR, Calendar.getInstance.getTime),
      bal.balance(1500, AUD, Calendar.getInstance.getTime)
    )
  }
}

trait Auditing extends Portfolio {
  val semantics: Portfolio     // abstract value (no value assigned)
  val bal: semantics.bal.type  // path dependent type
  import bal._                 // path dependent type method: bal.inBaseCurrency

  override def currentPortfolio(account: Account) = {
    semantics.currentPortfolio(account) map inBaseCurrency
  }
}

object ClientPortfolioAuditService1 extends Auditing {
  object SimpleBalanceComponent extends SimpleBalanceComponent             // materialize an instance
  val semantics = new ClientPortfolio { val bal = SimpleBalanceComponent } // creates anonymous class
  val bal: semantics.bal.type = semantics.bal                              // forward ClientPortfolio's inner class member
}

object ClientPortfolioAuditService2 extends Auditing {
  val customBalanceComponent = new CustomBalanceComponent{}                // materialize by explicitly creating an anonymous class
  val semantics = new ClientPortfolio { val bal = customBalanceComponent } // creates anonymous class
  val bal: semantics.bal.type = semantics.bal                              // forward ClientPortfolio's inner class member
}

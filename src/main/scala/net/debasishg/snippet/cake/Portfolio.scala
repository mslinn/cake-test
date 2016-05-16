package net.debasishg.snippet.cake

import java.util.{Calendar, Date}

case class Account(no: String, name: String, openedOn: Date, status: String)

trait Portfolio {
  val bal: BalanceComponent // abstract value (no value assigned)
  import bal._              // imports path dependent type: bal.Balance

  def currentPortfolio(account: Account): List[Balance] // Balance type is defined in BalanceComponent
}

trait ClientPortfolio extends Portfolio {
  val bal: BalanceComponent // abstract value (no value assigned)
  //import bal._ // imports path dependent types and methods that use them;
  // Referenced fully qualified bal.balance method below to show another writing style

  override def currentPortfolio(account: Account) = {
    // An actual impl would fetch from a database
    List(
      bal.balance(1000, EUR, Calendar.getInstance.getTime),
      bal.balance(1500, AUD, Calendar.getInstance.getTime)
    )
  }
}

package net.debasishg.snippet.cake

trait Auditing extends Portfolio {
  val semantics: Portfolio     // abstract value (no value assigned)
  val bal: semantics.bal.type  // abstract path dependent type forwarded from superclass
  import bal._                 // import path dependent type (bal.Balance) and method (bal.inBaseCurrency)

  override def currentPortfolio(account: Account): List[Balance] =
    semantics.currentPortfolio(account) map inBaseCurrency
}

object ClientPortfolioAuditService1 extends Auditing {
  object SimpleBalanceComponent extends SimpleBalanceComponent  // materialize an instance
  val semantics = new ClientPortfolio {                         // creates anonymous class
    val bal: BalanceComponent = SimpleBalanceComponent
  }
  val bal: semantics.bal.type = semantics.bal                   // forward ClientPortfolio's inner class member
}

object ClientPortfolioAuditService2 extends Auditing {
  val customBalanceComponent = new CustomBalanceComponent{}     // materialize by explicitly creating an anonymous class
  val semantics = new ClientPortfolio {                         // creates anonymous class
    val bal: BalanceComponent = customBalanceComponent
  }
  val bal: semantics.bal.type = semantics.bal                   // forward ClientPortfolio's inner class member
}

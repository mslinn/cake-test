package net.debasishg.snippet.cake

trait Auditing extends Portfolio {
  val semantics: Portfolio     // abstract value (no value assigned)
  val bal: semantics.bal.type  // path dependent type
  import bal._                 // path dependent type method: bal.inBaseCurrency

  override def currentPortfolio(account: Account): List[Balance] = {
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

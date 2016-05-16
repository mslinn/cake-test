package net.debasishg.snippet.cake

import java.util.Calendar.getInstance

object Main extends App {
  val account = Account("100", "dg", getInstance.getTime, "a")

  // ClientPortfolioAuditService1.bal.Balance is a Tuple3[Double, Currency, Date]
  val audit1: List[ClientPortfolioAuditService1.bal.Balance] = ClientPortfolioAuditService1.currentPortfolio(account)
  println(s"audit1=$audit1")

  // ClientPortfolioAuditService2.bal.BalanceRep is a case class
  val audit2: List[ClientPortfolioAuditService2.bal.BalanceRep] = ClientPortfolioAuditService2.currentPortfolio(account)
  println(s"audit2=$audit2")
}

package net.debasishg.snippet.cake

import java.util.Calendar.getInstance
import java.util.Date

object Main extends App {
  val account = Account("100", "dg", getInstance.getTime, "a")

  val audit1: List[(Double, Currency, Date)] = ClientPortfolioAuditService1.currentPortfolio(account)
  println(s"audit1=$audit1")

  val audit2: List[ClientPortfolioAuditService2.bal.BalanceRep] = ClientPortfolioAuditService2.currentPortfolio(account)
  println(s"audit2=$audit2")
}

package net.debasishg.snippet.cake

import java.util.Calendar.getInstance

object Main extends App {
  val account = Account("100", "dg", getInstance.getTime, "a")
  val audit1 = ClientPortfolioAuditService1.currentPortfolio(account)
  println(s"audit1=$audit1")
  val audit2 = ClientPortfolioAuditService2.currentPortfolio(account)
  println(s"audit2=$audit2")
}

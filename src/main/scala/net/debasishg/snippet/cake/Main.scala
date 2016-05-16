package net.debasishg.snippet.cake

import java.util.Calendar.getInstance
import java.util.Date

object Main extends App {
  val account = Account("100", "dg", getInstance.getTime, "a")

  /** @param balance This type seems opaque, cannot figure out how to work with it */
  def toString(balance: ClientPortfolioAuditService1.bal.Balance): String = {
    val b = balance.asInstanceOf[(Double, Currency, Date)] // is this cast really necessary?
    s"SimpleBalanceComponent amount: ${ b._1 } in ${ b._2 } on ${ b._3 }"
  }

  // ClientPortfolioAuditService1.bal.Balance is a Tuple3[Double, Currency, Date]
  val audit1: List[ClientPortfolioAuditService1.bal.Balance] = ClientPortfolioAuditService1.currentPortfolio(account)
  println(s"** audit1 **\n" + audit1.map(toString).mkString("  ", "\n  ", ""))

  // ClientPortfolioAuditService2.bal.BalanceRep is a case class
  val audit2: List[ClientPortfolioAuditService2.bal.Balance] = ClientPortfolioAuditService2.currentPortfolio(account)
  println(s"\n** audit2 **\n" + audit2.mkString("  ", "\n  ", "" ))
}

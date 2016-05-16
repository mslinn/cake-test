package net.debasishg.snippet.cake

import java.util.Date

trait BalanceComponent {
  type Balance

  def balance(amount: Double, currency: Currency, asOf: Date): Balance

  def inBaseCurrency(b: Balance): Balance
}

/** Define Balance type as a Tuple3[Double, Currency, Date] */
trait SimpleBalanceComponent extends BalanceComponent {
  type Balance = (Double, Currency, Date)

  override def balance(amount: Double, currency: Currency, asOf: Date) = (amount, currency, asOf)

  override def inBaseCurrency(b: Balance): (Double, Currency, Date) =
    (b._1 * baseCurrencyFactor(b._2), baseCurrency, b._3)
}

/** Define Balance type as a case class */
trait CustomBalanceComponent extends BalanceComponent {
  type Balance = BalanceRep

  case class BalanceRep(amount: Double, currency: Currency, asOf: Date)

  override def balance(amount: Double, currency: Currency, asOf: Date) =
    BalanceRep(amount, currency, asOf)

  override def inBaseCurrency(b: Balance) =
    BalanceRep(b.amount * baseCurrencyFactor(b.currency), baseCurrency, b.asOf)
}

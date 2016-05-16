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

  override def inBaseCurrency(b: Balance): (Double, Currency, Date) =
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

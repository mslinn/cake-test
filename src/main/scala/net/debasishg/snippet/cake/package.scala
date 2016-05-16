package net.debasishg.snippet

package object cake {
  val baseCurrency: Currency = USD

  val baseCurrencyFactor: Map[Currency, Double] = Map(USD -> 1, EUR -> 1.3, AUD -> 1.2)
}

package cake {
  sealed trait Currency
  case object USD extends Currency
  case object EUR extends Currency
  case object AUD extends Currency
}

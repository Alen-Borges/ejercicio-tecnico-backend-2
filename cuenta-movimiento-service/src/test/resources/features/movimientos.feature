# language: es
@movimientos
Característica: Gestión de Movimientos Bancarios
  Como cliente del banco
  Quiero realizar depósitos y retiros
  Para gestionar mi dinero de forma efectiva

  Antecedentes:
    Dado que existe una cuenta con número "478758" y saldo inicial de 2000

  @deposito
  Escenario: Realizar un depósito exitoso
    Cuando realizo un depósito de 600 a la cuenta "478758"
    Entonces el saldo disponible debe ser 2600

  @retiro
  Escenario: Realizar un retiro exitoso
    Cuando realizo un retiro de 575 de la cuenta "478758"
    Entonces el saldo disponible debe ser 1425

  @saldo_insuficiente
  Escenario: Intento de retiro con saldo insuficiente
    Cuando realizo un retiro de 3000 de la cuenta "478758"
    Entonces recibo un error con el mensaje "Saldo no disponible"

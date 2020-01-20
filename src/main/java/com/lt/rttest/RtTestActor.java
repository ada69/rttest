package com.lt.rttest;

public class RtTestActor {


	public RtTestActor() {
	}

//	@Override
	public void receive() {
//		if (cmd instanceof DevSrvCommands.CreateFuellingController) {
//			createFuellingController(sender, (DevSrvCommands.CreateFuellingController) cmd);
//		} else if (cmd instanceof DevSrvCommands.CreateTankGauge) {
//			createTankGauge(sender, (DevSrvCommands.CreateTankGauge) cmd);
//		} else if (cmd instanceof DevSrvCommands.CreateFiscalBlock) {
//			createFiscalBlock(sender, (DevSrvCommands.CreateFiscalBlock) cmd);
//		} else if (cmd instanceof DevSrvCommands.CreateCashAcceptor) {
//			createCashAcceptor(sender, (DevSrvCommands.CreateCashAcceptor) cmd);
//		} else if (cmd instanceof DevSrvCommands.CreatePaymentTerminal) {
//			createPaymentTerminal(sender, (DevSrvCommands.CreatePaymentTerminal) cmd);
//		}
	}


//	private void createFuellingController(ActorRef sender, DevSrvCommands.CreateFuellingController cmd) {
//		// appSrvRef = sender;
//		FuellingPointFactory fuellingPointFactory = (fc, fpName, appRef) -> fc.createActor(FuellingPointActor.class, fpName, appRef);
//		ActorRef fcActor = getSelfRef().createActor(FuellingControllerActor.class, cmd.getName(), cmd.getCfg(),
//				fuellingPointFactory, new FcDeviceFactoryImpl());
//		sender.send(fcActor, new DevSrvEvents.FuellingControllerCreated(cmd.getCfg()));
//	}
//
//	private void createTankGauge(ActorRef sender, DevSrvCommands.CreateTankGauge cmd) {
//		TankFactory tankFactory = (tg, tankName, tankId) -> tg.createActor(TankActor.class, tankName, tankId);
//		ActorRef tgActor = getSelfRef().createActor(TankGaugeActor.class, cmd.getName(), cmd.getCfg(), tankFactory,
//				new TgDeviceFactoryImpl());
//		sender.send(tgActor, new DevSrvEvents.TankGaugeCreated(cmd.getCfg()));
//	}
//
//	private void createFiscalBlock(ActorRef sender, DevSrvCommands.CreateFiscalBlock cmd) {
//		ActorRef fbActor = getSelfRef().createActor(FiscalBlockActor.class, cmd.getCfg(), new FbDeviceFactoryImpl());
//		sender.send(fbActor, new DevSrvEvents.FiscalBlockCreated(cmd.getCfg()));
//	}
//
//	private void createCashAcceptor(ActorRef sender, DevSrvCommands.CreateCashAcceptor cmd) {
//		ActorRef caActor = getSelfRef().createActor(CashAcceptorActor.class, cmd.getCfg(), new CaDeviceFactoryImpl());
//		sender.send(caActor, new DevSrvEvents.CashAcceptorCreated(cmd.getCfg()));
//	}
//
//	private void createPaymentTerminal(ActorRef sender, DevSrvCommands.CreatePaymentTerminal cmd) {
//		ActorRef ptActor = getSelfRef().createActor(PaymentTerminalActor.class, cmd.getCfg(),
//				new PtDeviceFactoryImpl());
//		sender.send(ptActor, new DevSrvEvents.PaymentTerminalCreated(cmd.getCfg()));
//	}
}

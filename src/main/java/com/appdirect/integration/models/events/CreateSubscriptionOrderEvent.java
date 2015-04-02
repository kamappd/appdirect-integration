package com.appdirect.integration.models.events;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlRootElement(name = "event")
@XmlAccessorType(FIELD)
public class CreateSubscriptionOrderEvent extends Event<CreateSubscriptionOrderPayload> {
}

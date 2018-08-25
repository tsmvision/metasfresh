package de.metas.vertical.pharma.msv3.server.order.v2;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import de.metas.vertical.pharma.msv3.protocol.order.OrderStatusResponse;
import de.metas.vertical.pharma.msv3.protocol.order.v2.OrderJAXBConvertersV2;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.Id;
import de.metas.vertical.pharma.msv3.server.MSV3ServerConstants;
import de.metas.vertical.pharma.msv3.server.order.OrderService;
import de.metas.vertical.pharma.msv3.server.security.MSV3ServerAuthenticationService;
import de.metas.vertical.pharma.msv3.server.util.JAXBUtils;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellstatusAbfragen;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.BestellstatusAbfragenResponse;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.ObjectFactory;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.msv3.server
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Endpoint
public class OrderStatusWebService
{
	public static final String WSDL_BEAN_NAME = "Msv3BestellstatusAbfragenService";

	private static final Logger logger = LoggerFactory.getLogger(OrderStatusWebService.class);

	private final MSV3ServerAuthenticationService authService;
	private final OrderJAXBConvertersV2 jaxbConverters;
	private final OrderService orderService;

	public OrderStatusWebService(
			@NonNull final MSV3ServerAuthenticationService authService,
			@NonNull final ObjectFactory jaxbObjectFactory,
			@NonNull final OrderService orderService)
	{
		this.authService = authService;
		jaxbConverters = new OrderJAXBConvertersV2(jaxbObjectFactory);
		this.orderService = orderService;
	}

	@PayloadRoot(localPart = "bestellstatusAbfragen", namespace = MSV3ServerConstants.SOAP_NAMESPACE)
	public @ResponsePayload JAXBElement<BestellstatusAbfragenResponse> getOrderStatus(@RequestPayload final JAXBElement<BestellstatusAbfragen> jaxbRequest)
	{
		logXML("getOrderStatus - request", jaxbRequest);

		final BestellstatusAbfragen soapRequest = jaxbRequest.getValue();
		authService.assertValidClientSoftwareId(soapRequest.getClientSoftwareKennung());

		final Id orderId = Id.of(soapRequest.getBestellId());
		final BPartnerId bpartner = authService.getCurrentBPartner();
		final OrderStatusResponse response = orderService.getOrderStatus(orderId, bpartner);

		final JAXBElement<BestellstatusAbfragenResponse> jaxbResponse = jaxbConverters.toJAXB(response);
		logXML("getOrderStatus - response", jaxbResponse);
		return jaxbResponse;
	}

	private void logXML(final String name, final JAXBElement<?> element)
	{
		if (!logger.isDebugEnabled())
		{
			return;
		}

		logger.debug("{}: {}", name, JAXBUtils.toXml(element));
	}
}

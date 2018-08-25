package de.metas.vertical.pharma.msv3.protocol.stockAvailability.v1;

import javax.xml.bind.JAXBElement;

import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.protocol.stockAvailability.AvailabilityType;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.RequirementType;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityJAXBConverters;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityQuery;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityQueryItem;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponse;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItem;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItemPart;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItemPartType;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilitySubstitution;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilitySubstitutionReason;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilitySubstitutionType;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.ClientSoftwareId;
import de.metas.vertical.pharma.msv3.protocol.types.FaultInfo;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.protocol.types.Quantity;
import de.metas.vertical.pharma.msv3.protocol.util.JAXBDateUtils;
import de.metas.vertical.pharma.msv3.protocol.util.v1.MiscJAXBConvertersV1;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.ArtikelMenge;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.ObjectFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.VerfuegbarkeitAnfragen;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.VerfuegbarkeitAnfragenResponse;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.VerfuegbarkeitAnteil;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.VerfuegbarkeitSubstitution;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.VerfuegbarkeitsanfrageEinzelne;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.VerfuegbarkeitsanfrageEinzelneAntwort;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.VerfuegbarkeitsantwortArtikel;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.msv3.commons
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

public class StockAvailabilityJAXBConvertersV1 implements StockAvailabilityJAXBConverters
{
	public static final transient StockAvailabilityJAXBConvertersV1 instance = new StockAvailabilityJAXBConvertersV1();

	private static final RequirementType DEFAULT_RequirementType = RequirementType.NON_SPECIFIC;
	private final ObjectFactory jaxbObjectFactory;

	private StockAvailabilityJAXBConvertersV1()
	{
		this(new ObjectFactory());
	}

	public StockAvailabilityJAXBConvertersV1(@NonNull final ObjectFactory jaxbObjectFactory)
	{
		this.jaxbObjectFactory = jaxbObjectFactory;
	}

	@Override
	public FaultInfo extractFaultInfoOrNull(final Object value)
	{
		return MiscJAXBConvertersV1.extractFaultInfoOrNull(value);
	}

	@Override
	public JAXBElement<?> encodeRequest(final StockAvailabilityQuery query, final ClientSoftwareId clientSoftwareId)
	{
		return toJAXBElement(query, clientSoftwareId);
	}

	@Override
	public Class<?> getResponseClass()
	{
		return VerfuegbarkeitAnfragenResponse.class;
	}

	@Override
	public StockAvailabilityResponse decodeResponse(final Object soap)
	{
		final VerfuegbarkeitAnfragenResponse soapResponse = (VerfuegbarkeitAnfragenResponse)soap;
		return fromJAXB(soapResponse);
	}

	public StockAvailabilityQuery fromJAXB(@NonNull final VerfuegbarkeitsanfrageEinzelne soapAvailabilityRequest, @NonNull final BPartnerId bpartner)
	{
		return StockAvailabilityQuery.builder()
				.id(soapAvailabilityRequest.getId())
				.bpartner(bpartner)
				.items(soapAvailabilityRequest.getArtikel().stream()
						.map(this::fromJAXB)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	public JAXBElement<VerfuegbarkeitAnfragen> toJAXBElement(final StockAvailabilityQuery query, final ClientSoftwareId clientSoftwareId)
	{
		final VerfuegbarkeitAnfragen soap = jaxbObjectFactory.createVerfuegbarkeitAnfragen();
		soap.setVerfuegbarkeitsanfrage(toJAXB(query));
		soap.setClientSoftwareKennung(clientSoftwareId.getValueAsString());

		return jaxbObjectFactory.createVerfuegbarkeitAnfragen(soap);
	}

	public VerfuegbarkeitsanfrageEinzelne toJAXB(final StockAvailabilityQuery query)
	{
		final VerfuegbarkeitsanfrageEinzelne soap = jaxbObjectFactory.createVerfuegbarkeitsanfrageEinzelne();
		soap.setId(query.getId());
		soap.getArtikel().addAll(query.getItems().stream()
				.map(this::toJAXB)
				.collect(ImmutableList.toImmutableList()));
		return soap;
	}

	private StockAvailabilityQueryItem fromJAXB(final ArtikelMenge artikel)
	{
		return StockAvailabilityQueryItem.builder()
				.pzn(PZN.of(artikel.getPzn()))
				.qtyRequired(Quantity.of(artikel.getMenge()))
				.requirementType(DEFAULT_RequirementType)
				.build();
	}

	private ArtikelMenge toJAXB(final StockAvailabilityQueryItem queryItem)
	{
		final ArtikelMenge soap = jaxbObjectFactory.createArtikelMenge();
		soap.setPzn(queryItem.getPzn().getValueAsLong());
		soap.setMenge(queryItem.getQtyRequired().getValueAsInt());
		// soap.setBedarf(queryItem.getRequirementType().getCode());
		return soap;
	}

	public JAXBElement<VerfuegbarkeitAnfragenResponse> toJAXBElement(final StockAvailabilityResponse stockAvailabilityResponse)
	{
		final VerfuegbarkeitAnfragenResponse response = toJAXB(stockAvailabilityResponse);
		return jaxbObjectFactory.createVerfuegbarkeitAnfragenResponse(response);
	}

	private VerfuegbarkeitAnfragenResponse toJAXB(final StockAvailabilityResponse stockAvailabilityResponse)
	{
		final VerfuegbarkeitsanfrageEinzelneAntwort soapContent = jaxbObjectFactory.createVerfuegbarkeitsanfrageEinzelneAntwort();
		soapContent.setId(stockAvailabilityResponse.getId());
		soapContent.setRTyp(stockAvailabilityResponse.getAvailabilityType().getV1SoapCode());
		soapContent.getArtikel().addAll(stockAvailabilityResponse.getItems().stream()
				.map(this::toJAXB)
				.collect(ImmutableList.toImmutableList()));

		final VerfuegbarkeitAnfragenResponse soap = jaxbObjectFactory.createVerfuegbarkeitAnfragenResponse();
		soap.setReturn(soapContent);
		return soap;
	}

	public StockAvailabilityResponse fromJAXB(final VerfuegbarkeitAnfragenResponse soap)
	{
		return fromJAXB(soap.getReturn());
	}

	private StockAvailabilityResponse fromJAXB(final VerfuegbarkeitsanfrageEinzelneAntwort soap)
	{
		return StockAvailabilityResponse.builder()
				.id(soap.getId())
				.availabilityType(AvailabilityType.fromV1SoapCode(soap.getRTyp()))
				.items(soap.getArtikel().stream()
						.map(this::fromJAXB)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private VerfuegbarkeitsantwortArtikel toJAXB(final StockAvailabilityResponseItem item)
	{
		final VerfuegbarkeitsantwortArtikel soapItem = jaxbObjectFactory.createVerfuegbarkeitsantwortArtikel();
		soapItem.setAnfragePzn(item.getPzn().getValueAsLong());
		soapItem.setAnfrageMenge(item.getQty().getValueAsInt());
		soapItem.setSubstitution(toJAXB(item.getSubstitution()));
		soapItem.getAnteile().addAll(item.getParts().stream()
				.map(this::toJAXB)
				.collect(ImmutableList.toImmutableList()));
		return soapItem;
	}

	private StockAvailabilityResponseItem fromJAXB(final VerfuegbarkeitsantwortArtikel soap)
	{
		return StockAvailabilityResponseItem.builder()
				.pzn(PZN.of(soap.getAnfragePzn()))
				.qty(Quantity.of(soap.getAnfrageMenge()))
				.substitution(fromJAXB(soap.getSubstitution()))
				.parts(soap.getAnteile().stream()
						.map(this::fromJAXB)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private VerfuegbarkeitSubstitution toJAXB(final StockAvailabilitySubstitution substitution)
	{
		if (substitution == null)
		{
			return null;
		}

		final VerfuegbarkeitSubstitution soap = jaxbObjectFactory.createVerfuegbarkeitSubstitution();
		soap.setLieferPzn(substitution.getPzn().getValueAsLong());
		soap.setGrund(substitution.getReason().getV1SoapCode());
		soap.setSubstitutionsgrund(substitution.getType().getV1SoapCode());
		return soap;
	}

	private StockAvailabilitySubstitution fromJAXB(final VerfuegbarkeitSubstitution soap)
	{
		return StockAvailabilitySubstitution.builder()
				.pzn(PZN.of(soap.getLieferPzn()))
				.reason(StockAvailabilitySubstitutionReason.fromV1SoapCode(soap.getGrund()))
				.type(StockAvailabilitySubstitutionType.fromV1SoapCode(soap.getSubstitutionsgrund()))
				.build();
	}

	private VerfuegbarkeitAnteil toJAXB(final StockAvailabilityResponseItemPart itemPart)
	{
		final VerfuegbarkeitAnteil soap = jaxbObjectFactory.createVerfuegbarkeitAnteil();
		soap.setMenge(itemPart.getQty().getValueAsInt());
		soap.setTyp(itemPart.getType().getV1SoapCode());
		soap.setLieferzeitpunkt(itemPart.getDeliveryDate() != null ? JAXBDateUtils.toXMLGregorianCalendar(itemPart.getDeliveryDate()) : null);
		soap.setTour(itemPart.getTour());
		soap.setGrund(itemPart.getReason() != null ? itemPart.getReason().getV1SoapCode() : null);
		// soap.setTourabweichung(itemPart.isTourDeviation());
		return soap;
	}

	private StockAvailabilityResponseItemPart fromJAXB(final VerfuegbarkeitAnteil soap)
	{
		return StockAvailabilityResponseItemPart.builder()
				.qty(Quantity.of(soap.getMenge()))
				.type(StockAvailabilityResponseItemPartType.fromV1SoapCode(soap.getTyp()))
				.deliveryDate(JAXBDateUtils.toLocalDateTime(soap.getLieferzeitpunkt()))
				.reason(StockAvailabilitySubstitutionReason.fromV1SoapCode(soap.getGrund()))
				.tour(soap.getTour())
				// .tourDeviation(soap.isTourabweichung())
				.build();
	}
}

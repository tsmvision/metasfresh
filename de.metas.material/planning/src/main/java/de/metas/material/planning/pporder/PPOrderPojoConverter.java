package de.metas.material.planning.pporder;

import static org.compiere.util.TimeUtil.asInstant;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.eevolution.api.BOMComponentType;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Service
public class PPOrderPojoConverter
{
	private final IPPOrderBOMDAO ppOrderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
	private final ModelProductDescriptorExtractor productDescriptorFactory;

	private static final ModelDynAttributeAccessor<I_PP_Order, Integer> //
	ATTR_PPORDER_REQUESTED_EVENT_GROUP_ID = new ModelDynAttributeAccessor<>(I_PP_Order.class.getName(), "PPOrderRequestedEvent_GroupId", Integer.class);

	public PPOrderPojoConverter(@NonNull final ModelProductDescriptorExtractor productDescriptorFactory)
	{
		this.productDescriptorFactory = productDescriptorFactory;
	}

	public PPOrder toPPOrder(@NonNull final I_PP_Order ppOrderRecord)
	{
		return PPOrder.builder()
				.datePromised(asInstant(ppOrderRecord.getDatePromised()))
				.dateStartSchedule(asInstant(ppOrderRecord.getDateStartSchedule()))
				.docStatus(ppOrderRecord.getDocStatus())
				.orgId(ppOrderRecord.getAD_Org_ID())
				.plantId(ppOrderRecord.getS_Resource_ID())
				.ppOrderId(ppOrderRecord.getPP_Order_ID())
				.productDescriptor(productDescriptorFactory.createProductDescriptor(ppOrderRecord))
				.productPlanningId(ppOrderRecord.getPP_Product_Planning_ID())
				.qtyRequired(ppOrderRecord.getQtyOrdered())
				.qtyDelivered(ppOrderRecord.getQtyDelivered())
				.warehouseId(ppOrderRecord.getM_Warehouse_ID())
				.bPartnerId(ppOrderRecord.getC_BPartner_ID())
				.orderLineId(ppOrderRecord.getC_OrderLine_ID())
				.materialDispoGroupId(getMaterialDispoGroupIdOrZero(ppOrderRecord))
				//
				.lines(toPPOrderLinesList(ppOrderRecord))
				//
				.build();
	}

	private List<PPOrderLine> toPPOrderLinesList(final I_PP_Order ppOrderRecord)
	{
		final List<PPOrderLine> lines = new ArrayList<>();
		for (final I_PP_Order_BOMLine ppOrderLineRecord : ppOrderBOMsRepo.retrieveOrderBOMLines(ppOrderRecord))
		{
			final PPOrderLine ppOrderLinePojo = toPPOrderLine(ppOrderLineRecord, ppOrderRecord);
			lines.add(ppOrderLinePojo);
		}

		return lines;
	}

	private PPOrderLine toPPOrderLine(final I_PP_Order_BOMLine ppOrderLineRecord, final I_PP_Order ppOrderRecord)
	{
		final BOMComponentType componentType = BOMComponentType.ofCode(ppOrderLineRecord.getComponentType());
		final boolean receipt = PPOrderUtil.isReceipt(componentType);
		final Instant issueOrReceiveDate = asInstant(receipt ? ppOrderRecord.getDatePromised() : ppOrderRecord.getDateStartSchedule());

		return PPOrderLine.builder()
				.productDescriptor(productDescriptorFactory.createProductDescriptor(ppOrderLineRecord))
				.description(ppOrderLineRecord.getDescription())
				.ppOrderLineId(ppOrderLineRecord.getPP_Order_BOMLine_ID())
				.productBomLineId(ppOrderLineRecord.getPP_Product_BOMLine_ID())
				.qtyRequired(ppOrderLineRecord.getQtyRequiered())
				.qtyDelivered(ppOrderLineRecord.getQtyDelivered())
				.issueOrReceiveDate(issueOrReceiveDate)
				.receipt(receipt)
				.build();
	}

	@VisibleForTesting
	public static int getMaterialDispoGroupIdOrZero(@NonNull final I_PP_Order ppOrderRecord)
	{
		return ATTR_PPORDER_REQUESTED_EVENT_GROUP_ID.getValue(ppOrderRecord, 0);
	}

	public static void setMaterialDispoGroupId(@NonNull final I_PP_Order ppOrderRecord, final int materialDispoGroupId)
	{
		ATTR_PPORDER_REQUESTED_EVENT_GROUP_ID.setValue(ppOrderRecord, materialDispoGroupId);
	}
}

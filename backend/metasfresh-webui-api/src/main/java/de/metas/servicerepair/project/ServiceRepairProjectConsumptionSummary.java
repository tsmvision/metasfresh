/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.servicerepair.project;

import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.servicerepair.project.service.AddQtyToProjectTaskRequest;
import de.metas.uom.UomId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class ServiceRepairProjectConsumptionSummary
{
	@NonNull ProjectId projectId;
	@NonNull ProductId productId;
	@NonNull Quantity qtyReserved;
	@NonNull Quantity qtyConsumed;

	public UomId getUomId()
	{
		return Quantity.getCommonUomIdOfAll(qtyReserved, qtyConsumed);
	}

	public ServiceRepairProjectConsumptionSummary reduce(@NonNull final AddQtyToProjectTaskRequest request)
	{
		Check.assumeEquals(projectId, request.getTaskId().getProjectId(), "projectId not matching: {}, {}", this, request);

		return toBuilder()
				.qtyReserved(getQtyReserved().add(request.getQtyReserved()))
				.qtyConsumed(getQtyConsumed().add(request.getQtyConsumed()))
				.build();
	}

}

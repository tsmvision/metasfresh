package de.metas.bpartner.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery.Type;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class BPartnerDAO_retrieveBPartnerLocationTests
{
	private BPartnerDAO bpartnerDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		bpartnerDAO = new BPartnerDAO();
	}

	/** verifies that also with {@code applyTypeStrictly(false)}, if we have the desired type, it is returned. */
	@Test
	void billTo_notStrict_alsoShipTo()
	{
		final BPartnerId bpartnerId1 = createBPartnerWithName("BPartner 1");

		final I_C_BPartner_Location shipLocationRecord1 = newInstance(I_C_BPartner_Location.class);
		shipLocationRecord1.setC_BPartner_ID(bpartnerId1.getRepoId());
		shipLocationRecord1.setIsShipTo(true);
		shipLocationRecord1.setIsBillTo(false);
		saveRecord(shipLocationRecord1);

		final I_C_BPartner_Location billLocationRecord1 = newInstance(I_C_BPartner_Location.class);
		billLocationRecord1.setC_BPartner_ID(bpartnerId1.getRepoId());
		billLocationRecord1.setIsShipTo(false);
		billLocationRecord1.setIsBillTo(true);
		saveRecord(billLocationRecord1);

		final BPartnerLocationQuery query = BPartnerLocationQuery.builder()
				.bpartnerId(bpartnerId1)
				.type(Type.BILL_TO)
				.applyTypeStrictly(false)
				.build();
		final BPartnerLocationId bpartnerLocationId = bpartnerDAO.retrieveBPartnerLocationId(query);
		assertThat(bpartnerLocationId).isNotNull();
		assertThat(bpartnerLocationId.getRepoId()).isEqualTo(billLocationRecord1.getC_BPartner_Location_ID());
	}

	/** verifies that with {@code applyTypeStrictly(false)}, if we don't have a location with the desired type, then one with another type is returned */
	@Test
	void billTo_notStrict_onlyShipTo()
	{
		final BPartnerId bpartnerId1 = createBPartnerWithName("BPartner 1");

		final I_C_BPartner_Location shipLocationRecord1 = newInstance(I_C_BPartner_Location.class);
		shipLocationRecord1.setC_BPartner_ID(bpartnerId1.getRepoId());
		shipLocationRecord1.setIsShipTo(true);
		shipLocationRecord1.setIsBillTo(false);
		saveRecord(shipLocationRecord1);

		final I_C_BPartner_Location shipLocationRecord2 = newInstance(I_C_BPartner_Location.class);
		shipLocationRecord2.setC_BPartner_ID(bpartnerId1.getRepoId());
		shipLocationRecord2.setIsShipTo(true);
		shipLocationRecord2.setIsBillTo(false);
		saveRecord(shipLocationRecord2);

		final BPartnerLocationQuery query = BPartnerLocationQuery.builder()
				.bpartnerId(bpartnerId1)
				.type(Type.BILL_TO)
				.applyTypeStrictly(false)
				.build();
		final BPartnerLocationId bpartnerLocationId = bpartnerDAO.retrieveBPartnerLocationId(query);
		assertThat(bpartnerLocationId).isNotNull();
		assertThat(bpartnerLocationId.getRepoId()).isEqualTo(shipLocationRecord1.getC_BPartner_Location_ID());
	}

	private BPartnerId createBPartnerWithName(final String name)
	{
		final I_C_BPartner record = newInstance(I_C_BPartner.class);
		record.setName(name);
		saveRecord(record);

		return BPartnerId.ofRepoId(record.getC_BPartner_ID());
	}
}

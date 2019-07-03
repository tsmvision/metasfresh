package de.metas.payment;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_Order;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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

public enum PaymentRule implements ReferenceListAwareEnum
{
	Cash(X_C_Order.PAYMENTRULE_Cash), //
	CreditCard(X_C_Order.PAYMENTRULE_CreditCard), //
	DirectDeposit(X_C_Order.PAYMENTRULE_DirectDeposit), //
	Check(X_C_Order.PAYMENTRULE_Check), //
	OnCredit(X_C_Order.PAYMENTRULE_OnCredit), //
	DirectDebit(X_C_Order.PAYMENTRULE_DirectDebit), //
	Mixed(X_C_Order.PAYMENTRULE_Mixed) //

	;

	@Getter
	private final String code;

	PaymentRule(@NonNull final String code)
	{
		this.code = code;
	}

	public static PaymentRule ofNullableCode(final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static PaymentRule ofCode(@NonNull final String code)
	{
		PaymentRule type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + PaymentRule.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, PaymentRule> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), PaymentRule::getCode);

	public boolean isCashOrCheck()
	{
		return isCash() || isCheck();
	}

	public boolean isCash()
	{
		return this == Cash;
	}

	public boolean isCheck()
	{
		return this == Check;
	}

	public boolean isDirectDebit()
	{
		return this == DirectDebit;
	}
}

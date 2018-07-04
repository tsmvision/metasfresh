package de.metas.calendar.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Period;

import de.metas.calendar.ICalendarBL;
import de.metas.calendar.ICalendarDAO;

public abstract class AbstractCalendarDAO implements ICalendarDAO
{
//	@Override
//	@Cached
//	public I_C_Calendar getById(int calendarId)
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}

	protected abstract List<I_C_Period> retrievePeriods(
			final Properties ctx,
			final int calendarId,
			final Timestamp begin,
			final Timestamp end,
			final String trxName);
	
	@Override
	public List<I_C_Period> retrievePeriods(
			final Properties ctx,
			final I_C_Calendar cal,
			final Timestamp begin,
			final Timestamp end,
			final String trxName)
	{
		Check.assume(cal != null, "Param 'cal' is not null");
		final int calendarId = cal.getC_Calendar_ID();
		return retrievePeriods(ctx, calendarId, begin, end, trxName);

	}


	@Override
	public I_C_Period findByCalendar(final Properties ctx, final Timestamp date, final int calendarId, final String trxName)
	{
		final List<I_C_Period> periodsAll = retrievePeriods(ctx, calendarId, date, date, trxName);
		for (final I_C_Period period : periodsAll)
		{
			if (!Services.get(ICalendarBL.class).isStandardPeriod(period))
			{
				continue;
			}

			return period;
		}

		return null;
	}

}

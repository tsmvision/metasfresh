// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Project_Repair_CostCollector
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Project_Repair_CostCollector extends org.compiere.model.PO implements I_C_Project_Repair_CostCollector, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1043057514L;

    /** Standard Constructor */
    public X_C_Project_Repair_CostCollector (final Properties ctx, final int C_Project_Repair_CostCollector_ID, @Nullable final String trxName)
    {
      super (ctx, C_Project_Repair_CostCollector_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Project_Repair_CostCollector (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
	}

	@Override
	public void setC_Project_Repair_CostCollector_ID (final int C_Project_Repair_CostCollector_ID)
	{
		if (C_Project_Repair_CostCollector_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_Repair_CostCollector_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_Repair_CostCollector_ID, C_Project_Repair_CostCollector_ID);
	}

	@Override
	public int getC_Project_Repair_CostCollector_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_Repair_CostCollector_ID);
	}

	@Override
	public org.compiere.model.I_C_Project_Repair_Task getC_Project_Repair_Task()
	{
		return get_ValueAsPO(COLUMNNAME_C_Project_Repair_Task_ID, org.compiere.model.I_C_Project_Repair_Task.class);
	}

	@Override
	public void setC_Project_Repair_Task(final org.compiere.model.I_C_Project_Repair_Task C_Project_Repair_Task)
	{
		set_ValueFromPO(COLUMNNAME_C_Project_Repair_Task_ID, org.compiere.model.I_C_Project_Repair_Task.class, C_Project_Repair_Task);
	}

	@Override
	public void setC_Project_Repair_Task_ID (final int C_Project_Repair_Task_ID)
	{
		if (C_Project_Repair_Task_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_Repair_Task_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_Repair_Task_ID, C_Project_Repair_Task_ID);
	}

	@Override
	public int getC_Project_Repair_Task_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_Repair_Task_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setQtyConsumed (final BigDecimal QtyConsumed)
	{
		set_Value (COLUMNNAME_QtyConsumed, QtyConsumed);
	}

	@Override
	public BigDecimal getQtyConsumed() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyConsumed);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyReserved (final BigDecimal QtyReserved)
	{
		set_Value (COLUMNNAME_QtyReserved, QtyReserved);
	}

	@Override
	public BigDecimal getQtyReserved() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReserved);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_C_Order getQuotation_Order()
	{
		return get_ValueAsPO(COLUMNNAME_Quotation_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setQuotation_Order(final org.compiere.model.I_C_Order Quotation_Order)
	{
		set_ValueFromPO(COLUMNNAME_Quotation_Order_ID, org.compiere.model.I_C_Order.class, Quotation_Order);
	}

	@Override
	public void setQuotation_Order_ID (final int Quotation_Order_ID)
	{
		if (Quotation_Order_ID < 1) 
			set_Value (COLUMNNAME_Quotation_Order_ID, null);
		else 
			set_Value (COLUMNNAME_Quotation_Order_ID, Quotation_Order_ID);
	}

	@Override
	public int getQuotation_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Quotation_Order_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getQuotation_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_Quotation_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setQuotation_OrderLine(final org.compiere.model.I_C_OrderLine Quotation_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_Quotation_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, Quotation_OrderLine);
	}

	@Override
	public void setQuotation_OrderLine_ID (final int Quotation_OrderLine_ID)
	{
		if (Quotation_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_Quotation_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_Quotation_OrderLine_ID, Quotation_OrderLine_ID);
	}

	@Override
	public int getQuotation_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Quotation_OrderLine_ID);
	}

	@Override
	public void setVHU_ID (final int VHU_ID)
	{
		if (VHU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_VHU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_VHU_ID, VHU_ID);
	}

	@Override
	public int getVHU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_VHU_ID);
	}
}
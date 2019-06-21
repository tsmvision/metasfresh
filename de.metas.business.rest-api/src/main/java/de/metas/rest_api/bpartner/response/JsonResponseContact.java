package de.metas.rest_api.bpartner.response;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.MetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
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

@Value
public class JsonResponseContact
{
	@ApiModelProperty(dataType = "java.lang.Long")
	MetasfreshId metasfreshId;

	@ApiModelProperty(dataType = "java.lang.String")
	JsonExternalId externalId;

	@JsonInclude(Include.NON_NULL)
	@ApiModelProperty(dataType = "java.lang.Integer")
	private MetasfreshId metasfreshBPartnerId;

	@JsonInclude(Include.NON_NULL)
	String code;

	@JsonInclude(Include.NON_NULL)
	String name;

	@JsonInclude(Include.NON_NULL)
	String lastName;

	@JsonInclude(Include.NON_NULL)
	String firstName;

	@JsonInclude(Include.NON_NULL)
	String email;

	@JsonInclude(Include.NON_NULL)
	String phone;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonResponseContact(
			@JsonProperty("metasfreshId") @Nullable final MetasfreshId metasfreshId,
			@JsonProperty("externalId") @Nullable final JsonExternalId externalId,
			@JsonProperty("metasfreshBPartnerId") @Nullable final MetasfreshId metasfreshBPartnerId,
			@JsonProperty("code") @Nullable final String code,
			@JsonProperty("name") final String name,
			@JsonProperty("firstName") final String firstName,
			@JsonProperty("lastName") final String lastName,
			@JsonProperty("email") final String email,
			@JsonProperty("phone") final String phone)
	{
		this.metasfreshId = metasfreshId;
		this.externalId = externalId;
		this.metasfreshBPartnerId = metasfreshBPartnerId;
		this.code = code;
		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
	}

	public JsonResponseContact withExternalId(@NonNull final JsonExternalId externalId)
	{
		return toBuilder().externalId(externalId).build();
	}
}
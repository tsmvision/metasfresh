export class PriceListVersion {
  constructor(name) {
    this.Name = name;
  }

  setName(name) {
    cy.log(`Pricelist - set Name = ${name}`);
    this.Name = name;
    return this;
  }

  setDescription(description) {
    cy.log(`Pricelist - set Description = ${description}`);
    this.Description = description;
    return this;
  }

  setActive(isActive) {
    cy.log(`Pricelist - set isActive = ${isActive}`);
    this.IsActive = isActive;
    return this;
  }

  setValidFrom(validFrom) {
    cy.log(`Pricelist - set ValidFrom = ${validFrom}`);
    this.ValidFrom = validFrom;
    return this;
  }

  apply() {
    cy.log(`PriceListVersion - apply - START (name=${this.name})`);
    applyPriceListVersion(this);
    cy.log(`PriceListVersion - apply - END (name=${this.name})`);
    return this;
  }
}

function applyPriceListVersion(pricelistversion) {
  const timestamp = new Date().getTime();

  describe(`Create new PriceListVersion ${pricelistversion.Name}`, function() {
    cy.visitWindow('540321', 'NEW');
    cy.writeIntoStringField('Name', `${pricelistversion.Name} ${timestamp}`);
    cy.writeIntoStringField('ValidFrom', `${pricelistversion.ValidFrom}{enter}`, false, null, false);
  });
}

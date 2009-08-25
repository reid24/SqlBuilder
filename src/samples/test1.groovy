package samples;

import com.beckettit.sqlbuilder.*;

//System.setProperty(SqlBuilder.GROOVY_CONFIG_PROPERTY, "src/samples/config.groovy,src/samples/config.groovy")
//System.setProperty("java.class.path", System.getProperty("java.class.path") + ":" + "/Users/reid/.grails/1.1.1/projects/SqlBuilder/classes/")
println System.getProperty("java.class.path")
println Class.forName("config")
System.setProperty(SqlBuilder.GROOVY_CONFIG_PROPERTY, "config")

/** load a query that is named (i.e. defined in the config file) **/
Query q = SqlBuilder.namedQuery("users")
//build some conditions, ordering, etc.
q = q.build {
	eq 'id',1
	ne 'username', 'badman'
	or {
		ne 'firstname', 'joe'
		ne 'lastname', 'public'
	}
	order "firstname", "desc"
}

println q.toSql()
println q.parameters

//clear the existing conditions, ordering, etc.
q.clear()

println q.toSql()
println q.parameters

//add conditions the java way...
q.addExpression(ExpressionFactory.eq('id', 10)).addOrderClause(OrderFactory.asc("firstname"))
println q.toSql()
println q.parameters

//create and register a new query with the key 'myGreatQuery'
SqlBuilder.registerQuery("myGreatQuery", { query ->
	"SELECT * FROM TABLE1"
})
//load the named query and add conditions
Query q2 = SqlBuilder.namedQuery("myGreatQuery").build {
	eq "column1", 250
}
println q2.toSql()
println q2.parameters

//create a new query (but don't register it), and add conditions
Query q3 = SqlBuilder.query { query ->
	def sql = "select * from TABLE2"
	//an alias
	query.addAlias("column5", "t3.column5")
	//a conditional join
	if(query.hasConditionOn("column5")){
		sql += " INNER JOIN TABLE3 t3 ON t3.column5 = TABLE2.column5"
	}
	sql
}.build {
	gt "id", 10
	or{
		eq "column2", 250
		inList "column3", [1,2,3]
	    notInList "column4", [4,5,6]
	}
	eq "column5", 100
	maxResults 10
	firstResult 20
	order "firstname", 'asc'
	order "id", 'desc'
}
println q3.toSql()
println q3.parameters


//build a complex query with many conditional joins and aliases
Query ipa = SqlBuilder.query { query ->
	query.addAlias("streetName", "caddress.StreetName")
	query.addAlias("maturityDate", "lenderd.MaturityDate")
	
	def sql = """
		SELECT DISTINCT lot_type.description as lotTypeDescription, ce.EventDate, ce.SubDivisionName as subDivisionName, ce.AlternatePropertyName as alternatePropertyName, ce.RecordStatusID as recordStatusId, ce.TotalPrice as totalPrice, rld.*, marketlu.MarketName AS marketName, clu_1.Description AS recordStatus, blu.BuilderName AS builder, mlu.Name AS municipality, rld.FootageOrBlocks as footageOrBlocks, lot_type.Description as lotType
		 FROM ResLotDetails rld
		 INNER JOIN (CommercialEvent ce INNER JOIN MarketsLookUp marketlu ON ce.MarketID = marketlu.id) ON rld.CommercialEventID = ce.id
		 LEFT OUTER JOIN CommercialLookUp clu_1 ON ce.RecordStatusID = clu_1.id 
		 LEFT OUTER JOIN ( CommercialEventBuilder ceb INNER JOIN BuildersLookUp blu ON ceb.BuilderID = blu.id ) ON ceb.CommercialEventID = ce.id
		 LEFT OUTER JOIN MunicipalitiesLookUp mlu ON ce.MunicipalityID = mlu.id 
		 LEFT OUTER JOIN CommercialLookup lot_type ON rld.LotTypeID = lot_type.id
		"""
	if(query.hasConditionOn("streetName"))
		sql += "INNER JOIN CommercialAddress caddress ON caddress.CommercialEventID = ce.id"
	if(query.hasConditionOn("vendorLegalName"))
		sql += "INNER JOIN ( CommercialEventVendor cevendor INNER JOIN VendorsLookUp vendorlu ON cevendor.VendorID = vendorlu.id ) ON cevendor.CommercialEventID = ce.id"
	if(query.hasConditionOn("purchaserLegalName"))
		sql += "INNER JOIN ( CommercialEventPurchaser cepurchaser INNER JOIN PurchaserLookUp purchaserlu ON cepurchaser.PurchaserID = purchaserlu.id ) ON cepurchaser.CommercialEventID = ce.id"
	if(query.hasConditionOn("purchaserProfile"))
		sql += "INNER JOIN ( CommercialEventPurchaser cepurchaser INNER JOIN PurchaserLookUp purchaserlu ON cepurchaser.PurchaserID = purchaserlu.id INNER JOIN CommercialLookUp clu_3 on purchaserlu.PurchaserProfileId = clu_3.id) ON cepurchaser.CommercialEventID = ce.id"
	if(query.hasConditionOn("brokerageCompanyName"))
		sql += "INNER JOIN ( CommercialEventBrokerageCompany cebrokerage_1 INNER JOIN BrokerageCompanyLookUp brokeragelu ON cebrokerage_1.BrokerageCompanyID = brokeragelu.id ) ON cebrokerage_1.CommercialEventID = ce.id"
	if(query.hasConditionOn("brokerageAgentName"))
		sql += "INNER JOIN ( CommercialEventBrokerageCompany cebrokerage_2 INNER JOIN CommercialDynamicLookUp cdlu_1 ON cebrokerage_2.BrokerAgentId = cdlu_1.id ) ON cebrokerage_2.CommercialEventID = ce.id"
	if(query.hasConditionOn("maturityDate"))
		sql += """ INNER JOIN ( LenderDetails lenderd INNER JOIN CommercialDynamicLookUp cdlu_2 ON 
			( lenderd.PrimaryLenderID = cdlu_2.id OR lenderd.SecondaryLenderID = cdlu_2.id OR lenderd.TertiaryLenderID = cdlu_2.id )) 
			ON lenderd.CommercialEventID = ce.id"""
	if(query.hasConditionOn("portfolioName"))
		sql += "INNER JOIN CommercialDynamicLookUp cdlu_3 ON ce.PortfolioID = cdlu_3.id"

	sql
}
//add conditions to our query
ipa.build {
	gte "maturityDate", Date.parse("yyyy-MM-dd", "2009-01-10")
	eq "streetName", "Yonge"
	order "streetName", "asc"
	firstResult 0
	maxResults 10
}
//add more conditions to our query
ipa.build {
	lte "maturityDate", Date.parse("yyyy-MM-dd", "2012-01-10")
	maxResults 20
}
println ipa.toSql()
println ipa.parameters
/**
 * DB Model:
 * user hasOne city (FK from user.city_id to city.id)
 */
users = { query ->
	"select * from user"
}

usersPlusCity = { query ->
	def sql = "select c.city_name, u.* from user u"

	//aliases for certain columns
	query.aliases = [city: "c.city_name", id: "u.id", name: "u.name"]

    //conditional join
	if(query.hasConditionOn("city")){
		sql += "\ninner join city c on c.id = u.city_id"
	}else {
		sql += "\nleft outer join city c on c.id = u.city_id"
	}
	sql
}

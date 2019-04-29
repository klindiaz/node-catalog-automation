package util

import com.xlson.groovycsv.CsvParser

class LocationUtil {
    private static final String LOCATIONS_FILE = "../sample-excel-reader/src/main/resources/LocationReferences.csv"
    private static Map<String,String> locationVariations = [:]
    private static Map<String,LocationInfo> locations = [:]
    private static Map<String,String> marketConversion = [:]

    static {
        locationVariations.put("CHANDLER AU".toUpperCase(),"Chandler")
        locationVariations.put("Newport News".toUpperCase(),"Newport")
        locationVariations.put("Aliso Viejo".toUpperCase(),"Aliso Viejo (AV)")
        locationVariations.put("Dana Point".toUpperCase(),"Dana Point (DP)")
        locationVariations.put("Irvine".toUpperCase(),"Irvine (IR)")
        locationVariations.put("Orange".toUpperCase(),"Orange (OR)")
        locationVariations.put("Orange Hub".toUpperCase(),"Orange (OR)")
        locationVariations.put("Rancho Santa Margarita".toUpperCase(),"Rancho Santa Margarita (RSM)")
        locationVariations.put("Palos Verdes".toUpperCase(),"Palos Verdes (PV)")
        locationVariations.put("South Scottsdale".toUpperCase(),"S Scottsdale")
        locationVariations.put("North Scottsdale".toUpperCase(),"N Scottsdale")
        locationVariations.put("East Mesa".toUpperCase(),"E Mesa")
        locationVariations.put("Central".toUpperCase(),"Ernest May")
        locationVariations.put("HR".toUpperCase(),"Hampton Roads")
        locationVariations.put("Phoenix".toUpperCase(),"Arizona")
        locationVariations.put("Gainesville/Ocala".toUpperCase(),"Central Florida")
        locationVariations.put("Fort Smith".toUpperCase(),"Ft Smith")
        locationVariations.put("Sara Road".toUpperCase(),"Sara Rd")
        locationVariations.put("CA Wickenburg".toUpperCase(),"Wickenburg")
        locationVariations.put('OMAHA - WEST HUB'.toUpperCase(),'West')
        locationVariations.put('SCOTTSDALE SOUTH'.toUpperCase(),'S Scottsdale')
        locationVariations.put('Southeast'.toUpperCase(),'South East')
        locationVariations.put('Southwest'.toUpperCase(),'South West')

        marketConversion.put('Lafayette'.toUpperCase(),'Baton Rouge')
        marketConversion.put('Baton Rouge'.toUpperCase(),'Baton Rouge')
        marketConversion.put('Erwinville'.toUpperCase(),'Baton Rouge')
        marketConversion.put('Gonzales'.toUpperCase(),'Baton Rouge')
        marketConversion.put('South Baton Rouge'.toUpperCase(),'Baton Rouge')
        marketConversion.put('Fort Smith'.toUpperCase(),'NW Arkansas')
        marketConversion.put('Newton'.toUpperCase(),'Kansas')
        marketConversion.put('Wichita'.toUpperCase(),'Kansas')
        marketConversion.put('Salina'.toUpperCase(),'Kansas')
        marketConversion.put('Siloam Springs'.toUpperCase(),'NW Arkansas')
        marketConversion.put('Harrison'.toUpperCase(),'NW Arkansas')
        marketConversion.put('Bentonville'.toUpperCase(),'NW Arkansas')
        marketConversion.put('Berryville'.toUpperCase(),'NW Arkansas')
        marketConversion.put('Johnson'.toUpperCase(),'NW Arkansas')
        marketConversion.put('Sara Road'.toUpperCase(),'Oklahoma City')
        marketConversion.put('MTC'.toUpperCase(),'Oklahoma City')
//        marketConversion.put('OMAHA - WEST HUB'.toUpperCase(),'Omaha')

        def locationsCSV = CsvParser.parseCsv( new File(LOCATIONS_FILE).text )
        locationsCSV.each {
            String uniqueID = it[0].toString().toUpperCase()
            String facility = it[1].toString().toUpperCase()
            String facilityKey = it[2].toString().toUpperCase()
            String marketKey = it[3].toString().toUpperCase()
            String market = it[4].toString().toUpperCase()
            String regionKey = it[5].toString().toUpperCase()
            String region = it[6].toString()//.toUpperCase()

            locations.put(uniqueID , (new LocationInfo(
                                            [
                                                uniqueID: uniqueID,
                                                facility: facility,
                                                facilityKey: facilityKey,
                                                market: market,
                                                marketKey: marketKey,
                                                region: region,
                                                regionKey: regionKey
                                            ]
                                        ))
            )
        }
    }

    static String getStandardizedName(String location) {
        locationVariations.getOrDefault(location.toUpperCase(),location)
    }

    static String getMarketName(String market , String facility) {
        marketConversion.getOrDefault(facility,market)
    }

    static String getRegionName(String market , String facility) {
        String region
        LocationInfo info = getLocationInfo(market,facility)
        if (info) {
            region = info.region
        } else {
            region = null
        }
        region
    }

    static LocationInfo getLocationInfo(String market, String facility) {
        market && facility ?
                (getLocationInfo("${market.toUpperCase()} - ${facility.toUpperCase()}" as String) ?:
                getLocationInfo("${getStandardizedName(getMarketName(market,facility)).toUpperCase()} - ${getStandardizedName(facility).toUpperCase()}" as String)) : null
    }

    static LocationInfo getLocationInfo(String uniqueID) {
        locations.get(uniqueID)
    }

    static class LocationInfo {
        String uniqueID
        String facility
        String facilityKey
        String market
        String marketKey
        String region
        String regionKey
    }
}

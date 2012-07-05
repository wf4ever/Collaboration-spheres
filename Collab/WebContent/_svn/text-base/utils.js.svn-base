/**
 * Dado el nombre de un parámetro obtiene su valor asociado
 * 
 * @param queryString
 * @param parameterName
 * @returns Valor asociado
 */
function getParameter (queryString, parameterName ) {
// Add "=" to the parameter name (i.e. parameterName=value)
	var parameterName = parameterName + "=";
    if ( queryString.length > 0 ) {
    // Find the beginning of the string
    	begin = queryString.indexOf ( parameterName );
    	// If the parameter name is not found, skip it, otherwise return the
		// value
    	if ( begin != -1 ) {
    	// Add the length (integer) to the beginning
    		begin += parameterName.length;
    		// Multiple parameters are separated by the "&" sign
    		end = queryString.indexOf ( "&" , begin );
    		if ( end == -1 ) {
    			end = queryString.length
    		}
    		// Return the string
    		var value = unescape ( queryString.substring ( begin, end ) );
    		return value;
    	}
    	// Return undefined if no parameter has been found
    	return undefined;
    }
} 
/**
 * Reemplazar en todo el texto
 * 
 * @param txt
 * @param replace
 * @param with_this
 * @returns el texto
 */
function replaceAll(txt, replace, with_this) {
	return txt.replace(new RegExp(replace, 'g'),with_this);
}
/**
 * Earth's equatorial radius
 */
const a = 6378137; 

/**
 * Earth's polar radius
 */
const b = 6356752.3; 

/**
 * The equivalent in meters for a distance of one latitude second
 */
const LATITUDE_DEGREE = 30.82;
/**
 * 
 * @param lat1
 * @param long1
 * @param lat2
 * @param long2
 * @returns grados
 */
function calculateResourceAzimuth(lat1,long1, lat2,long2){
	var azimuth;
    var dist_lat;
    var dist_lng;
	
	var lat_degree = LATITUDE_DEGREE * 3600;
	var long_degree = ((Math.PI/180)*Math.cos(Math.PI * (lat1) / 180) *
			Math.sqrt((Math.pow(a, 4)*Math.pow(Math.cos(Math.PI * (lat1) / 180),2) +
					Math.pow(b, 4)*Math.pow(Math.sin(Math.PI * (lat1) / 180),2))/
					(Math.pow(a, 2)*Math.pow(Math.cos(Math.PI * (lat1) / 180),2) +
							Math.pow(b, 2)*Math.pow(Math.sin(Math.PI * (lat1) / 180),2))
					));
	
	dist_lat = (lat2 - lat1) * lat_degree;
	dist_lng = (long2 - long1) * long_degree;
	
	azimuth = 180 * Math.atan2(dist_lng, dist_lat) / Math.PI;
	
	if(azimuth<0)
		azimuth += 360;
	
	return azimuth;
}   

/**
 * Calcula la distancia entre dos puntos
 * 
 * @param lat1
 * @param long1
 * @param lat2
 * @param long2
 * @returns distancia en metros
 */
function calculateDistance (lat1,long1,lat2,long2)
{
	var lat_degree = LATITUDE_DEGREE * 3600;
	var long_degree = ((Math.PI/180)*Math.cos(Math.PI * (lat1) / 180) *
			Math.sqrt((Math.pow(a, 4)*Math.pow(Math.cos(Math.PI * (lat1) / 180),2) +
					Math.pow(b, 4)*Math.pow(Math.sin(Math.PI * (lat1) / 180),2))/
					(Math.pow(a, 2)*Math.pow(Math.cos(Math.PI * (lat1) / 180),2) +
							Math.pow(b, 2)*Math.pow(Math.sin(Math.PI * (lat1) / 180),2))
					));
    
    var dist = Math.sqrt(Math.pow((lat2 - lat1) * lat_degree, 2) + Math.pow((long2 - long1) * long_degree, 2));
    
    return dist;
}



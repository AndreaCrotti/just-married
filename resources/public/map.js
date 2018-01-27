var config = window.config;

function addMarker(map, place) {
    var marker = new google.maps.Marker({
        position: new google.maps.LatLng(place.lat, place.lng),
        icon: place.icon,
        map: map
    });
    var infowindow = new google.maps.InfoWindow({
        // replace it with this and use hiccup to generate the content
        // in the geo_info.cljc file
        // content: place.description[config.language]
        content: place.info
    });
    marker.addListener('click', function() {
        infowindow.open(map, marker)
    });
}

function addMap(mapConfig) {
    var mapOptions = {
        zoom: mapConfig.zoom,
        center: new google.maps.LatLng(mapConfig.center.lat, mapConfig.center.lng),
        mapTypeId: mapConfig['map-type-id']
    }
    var map = new google.maps.Map(document.getElementById(mapConfig['element-id']), mapOptions);

    mapConfig.places.forEach(function(placeName) {
        var placeConfig = config.places[placeName];
        addMarker(map, placeConfig);
    });
}

addMap(config['maps']['wedding']);

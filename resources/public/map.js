var config = window.config;

var mapOptions = {
    zoom: config.zoom,
    center: new google.maps.LatLng(config.center.lat, config.center.lng),
    mapTypeId: config['map-type-id']
}
var map = new google.maps.Map(document.getElementById('map'), mapOptions);

function addMarker(place) {
    var marker = new google.maps.Marker({
        position: new google.maps.LatLng(place.lat, place.lng),
        icon: place.icon,
        map: map
    });
    var infowindow = new google.maps.InfoWindow({
        content: place.info
    });
    marker.addListener('click', function() {
        infowindow.open(map, marker)
    });
}

addMarker(config.places.lepri);
addMarker(config.places.princi);

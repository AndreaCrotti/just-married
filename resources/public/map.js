var zoom = 12;

var places = [
    {
        'lat': 42.346799,
        'lng': 14.164534,
        'title': 'Palazzo Lepri',
        'icon': 'images/rings_small.png',
        'info': 'Palazzo Lepri'
    },
    {
        'lat': 42.423608,
        'lng': 14.231041,
        'title': 'Parco dei principi',
        'icon': 'images/party_small.png',
        'info': 'Parco dei Principi'
    },
    {
        'lat': 42.421552,
        'lng': 14.230742,
        'title': 'Parc Hotel Villa Immacolata',
        'icon': 'images/hotel_small.png',
        'info': 'Hotel Villa Immacolata'
    }
]
var mapOptions = {
    zoom: zoom,
    center: new google.maps.LatLng(42.4, 14.2),
    mapTypeId: 'roadmap'
}
var map = new google.maps.Map(document.getElementById('map'), mapOptions);

places.forEach(function(place) {
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
});

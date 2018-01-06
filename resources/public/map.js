var zoom = 12;

var places = [
    {
        'lat': 42.346799,
        'lng': 14.164534,
        'title': 'Palazzo Lepri',
        'icon': 'images/rings_small.png'
    },
    {
        'lat': 42.423608,
        'lng': 14.231041,
        'title': 'Parco dei principi',
        'icon': 'images/party_small.png'
    },
    {
        'lat': 42.421552,
        'lng': 14.230742,
        'title': 'Parc Hotel Villa Immacolata',
        'icon': 'images/hotel_small.png'
    }
]
var mapOptions = {
    zoom: zoom,
    center: new google.maps.LatLng(42.4, 14.2),
    mapTypeId: 'roadmap'
}
var map = new google.maps.Map(document.getElementById('map'), mapOptions);

places.forEach(function(place) {
    new google.maps.Marker({
        position: new google.maps.LatLng(place.lat, place.lng),
        icon: place.icon,
        map: map
    });
});

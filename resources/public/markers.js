// the variables and the title itself should be rendered by the server
// ideally

var zoom = 14;
var coordinates = {
    'parco': {
        'lat': 42.423608,
        'lng': 14.231041,
        'title': 'Parco dei principi'
    },
    'villa-immacolata': {
        'lat': 42.421552,
        'lng': 14.230742,
        'title': 'Parc Hotel Villa Immacolata'
    }
}
var center = coordinates['parco'];

var map = new GMaps({
    div: '#map',
    lat: center['lat'],
    lng: center['lng'],
    zoom: zoom
});

for (place in coordinates) {
    map.addMarker(coordinates[place])
}

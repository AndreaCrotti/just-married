// the variables and the title itself should be rendered by the server
// ideally

var latitude = 42.423608;
var longitude = 14.231041;
var zoom = 14;

var map = new GMaps({
    el: '#map',
    lat: latitude,
    lng: longitude,
    zoom: zoom
});

map.addMarker({
    lat: latitude,
    lng: longitude,
    title: 'Parco dei Principi',
});

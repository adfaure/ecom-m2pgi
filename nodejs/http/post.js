var request = require('request-promise');

module.exports = function(data) {
  var options = {
    method: 'POST',
    uri: 'http://localhost:8080/ecom-web/api/photos/seller/1',
    body: data,
    json: true // Automatically stringifies the body to JSON
  };
  return request(options);
};
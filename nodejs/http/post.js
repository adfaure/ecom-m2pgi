var request = require('request-promise');
var nconf   = require('./../nconfLoader');

module.exports = function(data, sellerID) {
  var options = {
    method: 'POST',
    uri: nconf.get("upload_route") + sellerID,
    body: data,
    json: true // Automatically stringifies the body to JSON
  };
  return request(options);
};

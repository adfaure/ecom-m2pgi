var request = require('request-promise');
var nconf   = require('./../nconfLoader');

module.exports = function(data, sellerID, auth) {
  var options = {
    method: 'POST',
    uri: nconf.get("upload_route") + sellerID,
    body: data,
    headers : {'authtoken': auth},
    json: true // Automatically stringifies the body to JSON
  };
  return request(options);
};

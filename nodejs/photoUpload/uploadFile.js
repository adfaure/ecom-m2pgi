var multer       = require('multer');
var randomstring = require('randomstring');
var uuid         = require('node-uuid');

var allowedMime = [
    "image/jpeg",
    "image/png"
];

var ext = {
    "image/jpeg" : '.jpg',
    "image/png"  : '.png'
};

var filter  = function(req, file, cb) {
    if(allowedMime.indexOf(file.mimetype) == -1) {
        cb(null, false);
        req.params.fileAccepted = false;
        return;
    }
    file.ext = ext[file.mimetype];
    req.params.fileAccepted = true;
    cb(null, true);
};

var storage = multer.diskStorage({

    destination: function (req, file, cb) {
        cb(null, "/opt/wildfly-9.0.2.Final/standalone/data")
    },

    filename: function (req, file, cb) {
        cb(null, uuid.v4() + randomstring.generate(64) + ext[file.mimetype]);
    }

});

module.exports = multer({
    fileFilter : filter,
    storage : storage
});

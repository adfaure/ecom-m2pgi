var multer       = require('multer');

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

var storage = require('./inMemoryStorage')();

module.exports = multer({
    fileFilter : filter,
    storage : storage
});

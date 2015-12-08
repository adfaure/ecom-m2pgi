var concat = require('concat-stream')
var path   = require('path')
var uuid   = require('node-uuid');
var randomstring = require('randomstring');



function MemoryStorage (opts) {

    this.destination =  function (req, file, cb) {
        cb(null, "/opt/wildfly-9.0.2.Final/standalone/data")
    };

    this.filename = function (req, file, cb) {
        cb(null, uuid.v4() + randomstring.generate(64) + file.ext);
    };
}

MemoryStorage.prototype._handleFile = function _handleFile (req, file, cb) {
    var that = this;
    that.destination(req, file, function(err, destination)  {
        if(err) return cb(err);
        that.filename(req, file, function(err, filename) {
            if(err) return cb(err);
            filepath = path.join(destination, filename);
            file.stream.pipe(concat(function (data) {
                cb(null, {
                    filename : filename,
                    path : filepath,
                    buffer: data,
                    size: data.length
                })
            }))
        })
    })
};

MemoryStorage.prototype._removeFile = function _removeFile (req, file, cb) {
    delete file.buffer;
    cb(null)
};

module.exports = function (opts) {
    return new MemoryStorage(opts)
};


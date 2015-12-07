var express = require('express');

var fs      = require('fs')
var gm      = require('gm').subClass({imageMagick: true});
var Photo   = require('./photo');
var rp      = require('request-promise');
var post    = require('./http/post');
var upload  = require('./photoUpload/uploadFile');
var uuid    = require('node-uuid');
var nconf   = require('nconf');

nconf.argv()
.env()
.file({ file: 'config.json' });

var app     = express();

app.post('/upload/:id', upload.single('photo'), function (req, res, next) {
  res.setHeader('Content-Type', 'application/json');
  var tempPath           = req.file.path;
  var photoName          = req.params.id + uuid.v4() + req.file.ext;
  var photo              = new Photo();
  photo.webLocation      = nconf.get("web_uri") + photoName,
  photo.thumbnail        = nconf.get("thumb_uri") + photoName,
  photo.price            = req.body.price;
  photo.name             = photoName;
  photo.description      = req.body.description;
  photo.sellerID         = req.params.id;
  photo.ext              = req.file.ext;
  photo.privateLocation  = nconf.get("private_uri") + req.file.filename

  if (req.params.fileAccepted) {
    res.status(200);
    post(photo).then(function(serverRes) {
      var img       = gm(tempPath);
      var watermark = gm(nconf.get("watermark_img"));
      img.resize(400 , 400).write(nconf.get("thumbnail_location") + photoName, function() { //gm("img.png").thumb(width, height, outName, quality, callback)
      var size = img.size(function(err, value) {
        var tempWatermarkName = "watermark-sized/"+ value.width +".png";
        if(fs.existsSync(tempWatermarkName)) {
          img.draw(['image Over 0,0 0,0 ' + tempWatermarkName ])
          .write(nconf.get("file_location") + photoName , function(e){
            res.send(serverRes);
          });
        } else {
          watermark.resize(value.width).write(tempWatermarkName, function() {
            img.draw(['image Over 0,0 0,0 ' + tempWatermarkName ])
            .write(nconf.get("file_location") + photoName , function(e){
              res.send(serverRes);
            });
          });
        }
      });
    });
  });
} else {
  res.status(415);
  res.send({message: "unaccepted"});
}

});

app.use('/static', express.static('static'));
app.listen(3000);

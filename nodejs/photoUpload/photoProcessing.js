var gm              = require('gm').subClass({imageMagick: true});
var nconf           = require('./../nconfLoader');
var fs              = require('fs');


module.exports = function (photo, tempPath, file , finalCb) {

    var img = gm(tempPath);
    var watermark = gm(nconf.get("watermark_img"));
    fs.writeFile(file.path, file.buffer, 0, file.size, function(err) {  //fs.write(fd, buffer, offset, length[, position], callback)
        if(err) console.log(err);
        img.resize(400, 400).write(nconf.get("thumbnail_location") + photo.name, function (err) { //gm("img.png").thumb(width, height, outName, quality, callback)
            if(err) console.log(err);
            img.size(function (err, value) {
              if(err)  console.log(err);
                var tempWatermarkName = "watermark-sized/" + value.width + ".png";
                if (fs.existsSync(tempWatermarkName)) {
                    img.draw(['image Over 0,0 0,0 ' + tempWatermarkName])
                        .write(nconf.get("file_location") + photo.name, finalCb);
                } else {
                    watermark.resize(value.width).write(tempWatermarkName, function (err) {
                      if(err) console.log(err)
                        img.draw(['image Over 0,0 0,0 ' + tempWatermarkName])
                            .write(nconf.get("file_location") + photo.name, finalCb);
                    });
                }
            });
        });

        delete file.buffer;
    });
};

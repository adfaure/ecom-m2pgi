
module.exports  = function(photo, opts) {
  var tempPath = opts.tempPath;

  var img       = gm(tempPath);
  var watermark = gm('./../watermark.png');
  var size = img.size(function(err, value) {
    var tempWatermarkName = "./../" + uuid.v1() + "-temp.png";
    watermark.resize(value.width).write(tempWatermarkName, function() {
      img.draw(['image Over 0,0 0,0 ' + tempWatermarkName ])
      .write('test.jpg', function(e){
        console.log(e||'done'); // What would you like to do here?
      });
    });
  })
}

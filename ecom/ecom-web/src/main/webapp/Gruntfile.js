module.exports = function(grunt) {

  grunt.initConfig({
    jshint: {
      files: ['Gruntfile.js', 'js/**/*.js', 'js/**/*.js'],
    },
    browserify: {
          './js/dist.js': ['./js/main.js']
    }
  });

  grunt.loadNpmTasks('grunt-browserify');

  grunt.registerTask('default', ['browserify' ]);

};

var express = require('express');
var router = express.Router();
const ctrlMain = require('../controllers/main');

router.get('/', ctrlMain.index);
/* GET home page. */
const homepageController = (req, res) => {
  res.render('index', { title: 'AvatarS' });
};

router.get('/', homepageController);

module.exports = router;

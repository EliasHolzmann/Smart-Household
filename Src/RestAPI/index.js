var util = require('util'); var express = require('express')(); var mongoose = require('mongoose'); var assert = require('assert');

mongoose.connect('mongodb://localhost');

var ingredientSchema = new mongoose.Schema({
	name: String,
	amount: Number,
	amountType: String,
	price: Number,
	priceType: String
});

var stepSchema = new mongoose.Schema({
	name: String,
	description: String,
	lengthSeconds: Number
});

var recipeSchema = new mongoose.Schema({
	name: {
		type: String,
		required: true
	},
	ingredients: [ingredientSchema],
	steps: [stepSchema]
});

const Recipe = mongoose.model('recipe', recipeSchema);

express.use('/*', require('body-parser').json());

express.use(function(req, res, next) {
//	console.log("Request");
//	console.log(req);
	next();
});

express.get(/^\/recipe$/, function(req, res, next) {
	Recipe.find(function(err, recipes) {
		assert(err == null);
		res.json(recipes);
	});
});

express.get(/\/recipe\/search\/([a-zA-Z0-9-]+)$/, function(req, res, next) {
	Recipe.find({name: new RegExp(req.params[0], 'i')}, function(err, recipes) {
		assert(err == null);
		res.json(recipes);
	});
});

express.post(/^\/recipe$/, function(req, res, next) {
	Recipe.create(req.body, function(err) {
		if (err) {
			res.send({success: false, error: err});
		} else {
			res.send({success: true});
		}
	});
});

express.post(/^\/recipe\/flush$/, function(req, res, next) {
	Recipe.find().remove().exec();
	res.send({success: true, message: "Flushed database"});
});

express.post(/^\/recipe\/([0-9a-f]{24})\/add\/ingredient$/, function(req, res, next) {
	Recipe.find({'_id': req.params[0]}, function(err, recipes) {
                assert(err == null);
                recipe = recipes[0];
		recipe.ingredients.push(req.body);
		recipe.save(function(err) {
			if (err) {
				res.send({success: false, error: err});
			} else {
				res.send({success: true, message: "Written object " + util.inspect(req.body)});
			}
		});
	});
});

express.get(/^\/recipe\/([0-9a-f]{24})$/, function(req, res, next) {
      Recipe.find({'_id': req.params[0]}, function(err, recipes) {
              assert(err == null);
              res.send(recipes[0]);
      });
});

express.use(function(req, res, next) {
	console.log("Unhandled");
	console.log(req);
	next();
});

express.listen(8080);

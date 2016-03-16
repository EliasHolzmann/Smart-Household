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

// parses json request body
express.use('/*', require('body-parser').json());

// echoes every request object. Helpful for debugging.
express.use(function(req, res, next) {
//	console.log("Request");
//	console.log(req);
	next();
});

/*
 * GET /recipe
 * 
 * Returns an array of all recipes saved here.
 */
express.get(/^\/recipe$/, function(req, res, next) {
	Recipe.find(function(err, recipes) {
		assert(err == null);
		res.json(recipes);
	});
});

/*
 * GET /recipe/search/[a-zA-Z0-9-]+
 *
 * Returns an array of all recipes matching the regex.
 */
express.get(/\/recipe\/search\/([a-zA-Z0-9-]+)$/, function(req, res, next) {
	Recipe.find({name: new RegExp(req.params[0], 'i')}, function(err, recipes) {
		assert(err == null);
		res.json(recipes);
	});
});

/*
 * POST /recipe
 *
 * Adds a new recipe to the database.
 */
express.post(/^\/recipe$/, function(req, res, next) {
	Recipe.create(req.body, function(err) {
		if (err) {
			res.send({success: false, error: err});
		} else {
			res.send({success: true});
		}
	});
});

/*
 * POST /recipe/flush
 * 
 * Flushes database, removes all saved recipes.
 */
express.post(/^\/recipe\/flush$/, function(req, res, next) {
	Recipe.find().remove().exec();
	res.send({success: true, message: "Flushed database"});
});

/*
 * POST /recipe/[0-9a-f]{24}/add/ingredient
 *
 * Adds the ingredient given via JSON to the meal specified in the regex.
 */
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

/*
 * GET /recipe/[0-9a-f]{24}
 * 
 * Returns the meal specified with this regex.
 */
express.get(/^\/recipe\/([0-9a-f]{24})$/, function(req, res, next) {
      Recipe.find({'_id': req.params[0]}, function(err, recipes) {
              assert(err == null);
              res.send(recipes[0]);
      });
});

// Returns request objects for all unhandled objects. Helpful for debugging
express.use(function(req, res, next) {
	console.log("Unhandled");
	console.log(req);
	next();
});

express.listen(8080);

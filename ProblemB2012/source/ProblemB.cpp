/**
 * Implementation of ProblemB classes
 * @juanvallejo
 */

#include "../include/ProblemB.h"

Component::Component(int totalReviews) {
	this->reviewsLeft = totalReviews;
}

Component::~Component() {}

int Component::getReviewsLeft() {
	return this->reviewsLeft;
}

bool Component::hasReviewsLeft() {
	return (this->reviewsLeft > 0);
}

/**
 * A component is reviewed by decreasing
 * the number of reviews it has left
 */
void Component::review() {
	this->reviewsLeft--;
}

Engineer::Engineer(int totalReviews) {
	this->reviewsLeft = totalReviews;
}

Engineer::~Engineer() {}

int Engineer::getReviewsLeft() {
	return this->reviewsLeft;
}

bool Engineer::canReview(Component& component) {

	if(!component.hasReviewsLeft()) {
		return false;
	}

	bool cExists = false;

	for(std::vector<Component *>::iterator iter = this->componentsReviewed.begin(); iter != this->componentsReviewed.end(); iter++) {
		if(*iter == &component) {
			cExists = true;
		}
	}

	return !cExists;
}

bool Engineer::hasReviewsLeft() {
	return (this->reviewsLeft > 0);
}

void Engineer::review(Component& component) {
	this->reviewsLeft--;
	component.review();
	this->componentsReviewed.push_back(&component);
}
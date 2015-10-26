#ifndef _PROBLEMB_H_
#define _PROBLEMB_H_

/**
 * Component and Engineer class definitions.
 *
 * Components require several reviews by different engineers
 * each in its own class.
 * 
 * A component will not be reviewed by the same engineer more than once. 
 * Although not required by this algorithm, each engineer object is
 * cross-listed with a list of engineer object pointers that have reviewed
 * each component before as a redundant precaution. A review will fail if
 * such pointer exists in that component's list.
 *
 * @juanvallejo
 */

#include <iostream>
#include <vector>

class Component {

	private:
		int reviewsLeft;

	public:
		Component(int);
		virtual ~Component();

		int getReviewsLeft();

		/**
		 * A component has reviews left if reviewsLeft > 0
		 */
		bool hasReviewsLeft();

		/**
		 * A component is reviewed by
		 * having its reviewsLeft decreased
		 */
		void review();

};

class Engineer {

	private:
		int reviewsLeft;
		std::vector<Component *> componentsReviewed;

	public:
		Engineer(int);
		virtual ~Engineer();

		int getReviewsLeft();

		/**
		 * An engineer can review a component only
		 * if that component is not in the vector of
		 * componentsReviewed and that component has
		 * reviews left
		 */
		bool canReview(Component&);

		/**
		 * An engineer has reviews left if reviewsLeft > 0
		 */
		bool hasReviewsLeft();

		/**
		 * An engineer reviews a component by decreasing its
		 * reviewsLeft, calling that component's review()
		 * method and adding that component's address to the vector 
		 * of componentsReviewed
		 */
		void review(Component&);

};

#endif
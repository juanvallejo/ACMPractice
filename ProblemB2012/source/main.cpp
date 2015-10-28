#include <fstream>
#include <sstream>
#include "../include/ProblemB.h"

void log(std::string line) {
	std::cout << line << std::endl;
}

std::vector<std::string> split_str(std::string& string, const char delim) {

	std::vector<std::string> result;
	std::stringstream stream(string);
	std::string line;

	while(std::getline(stream, line, delim)) {
		result.push_back(line);
	}

	return result;

}

bool sortEngineers(Engineer& engineer1, Engineer& engineer2) {
	return engineer1.getReviewsLeft() > engineer2.getReviewsLeft();
}

bool sortComponents(Component& component1, Component& component2) {
	return component1.getReviewsLeft() > component2.getReviewsLeft();
}

int parseData(std::vector<Component>& components, std::vector<Engineer>& engineers) {

	int nEng 		= 0;
	int nComp 		= 0;
	int engsLeft 	= engineers.size();
	int exitCode 	= 0;

	bool done 		= false;

	// sort engineers from greatest reviews to least
	std::sort(engineers.begin(), engineers.end(), sortEngineers);
	std::sort(components.begin(), components.end(), sortComponents);

	// sort components from greatest reviews to least

	// iterate through each engineer, reviewing each component if possible
	for(; nEng < engineers.size() && !done; nEng++) {
		

		// compsLeft = false when no components can be reviewed
		bool compsLeft = false;

		// for each engineer, iterate through all components
		for(nComp = 0; nComp < components.size(); nComp++) {

			// check to see if engineer has reviews left and has not
			// yet reviewed the current component
			if(engineers.at(nEng).canReview(components.at(nComp)) && engineers.at(nEng).hasReviewsLeft()) {
				engineers.at(nEng).review(components.at(nComp));
			}

			// compsLeft must be set to true at least once to indicate
			// there are still components needing reviews
			if(components.at(nComp).hasReviewsLeft()) {
				compsLeft = true;
			}

		}

		if(!engineers.at(nEng).hasReviewsLeft()) {
			engsLeft--;
		}

		if(!compsLeft) {
			done = true;
		}

	}

	// if we have iterated through all engineers and
	// there are still components left, return false
	// done is true if there are no component reviews
	// left, it is false otherwise

	// our exitCode is 1 if there are no components left
	// and 0 if there are, a 0 indicates input failure

	if(done) {
		exitCode = 1;
	}

	return exitCode;

}

bool parseInput(std::string filename) {

	bool done = false;

	std::ifstream file(filename);
	std::string line;

	if(!file) {
		return false;
	}

	// done once line read equals 0 0
	while(!done) {

		std::vector<Engineer> engineers;
		std::vector<Component> components;

		// read first line of file
		std::getline(file, line);
		std::vector<std::string> amounts = split_str(line, ' ');

		int nComponents = stoi(amounts.at(0));
		int nEngineers 	= stoi(amounts.at(1));
		int iterator 	= 0;

		// determine if end of file
		if(nComponents == 0 && nEngineers == 0) {
			done = true;
		} else {

			// populate components
			for(; iterator < nComponents; iterator++) {

				std::getline(file, line);

				// number of components in class
				std::vector<std::string> compKinds = split_str(line, ' ');
				int nComps = stoi(compKinds.at(0));
				int nCompReviews = stoi(compKinds.at(1));
				int compIter = 0;

				// create component and store in component array
				for(; compIter < nComps; compIter++) {
					Component component(nCompReviews);
					components.push_back(component);
				}
			}

			iterator = 0;

			for(; iterator < nEngineers; iterator++) {

				std::getline(file, line);

				// number of engineers in class
				std::vector<std::string> engKinds = split_str(line, ' ');
				int nEngs = stoi(engKinds.at(0));
				int nEngReviews = stoi(engKinds.at(1));
				int engIter = 0;

				// create engineer and store in component array
				for(; engIter < nEngs; engIter++) {
					Engineer engineer(nEngReviews);
					engineers.push_back(engineer);
				}
			}

			// log data
			std::cout << parseData(components, engineers) << std::endl;
		}

	}

	return true;
}

int main() {

	if(!parseInput("input.txt")) {
		log("ERR Unable to read file");
		return 1;
	}

	return 0;

}
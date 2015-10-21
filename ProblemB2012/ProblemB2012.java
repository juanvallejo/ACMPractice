import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class ProblemB2012 {

	public ArrayList<CompNode> components;
	public ArrayList<EngNode> engineers;

	int numNodesReviewed = 0;
	boolean isDone = false;

	public void init() {

		try {

			BufferedReader br = new BufferedReader(new FileReader("input.txt"));

			components = new ArrayList<CompNode>();
			engineers = new ArrayList<EngNode>();

			String line = br.readLine();

			int numCompKinds = Integer.parseInt(line.split(" ")[0]);
			int numEngKinds = Integer.parseInt(line.split(" ")[1]);

			for(int i = 0;i < numCompKinds;i++) {
				line = br.readLine();
				int numComps = Integer.parseInt(line.split(" ")[0]);
				int numReviews = Integer.parseInt(line.split(" ")[1]);

				for(int x = 0;x < numComps;x++) {
					CompNode node = new CompNode(numReviews);

					if(components.get(x-1) != null) {
						components.get(x-1).next = node;
					}

					components.add(node);
				}
			}

			for(int i = 0;i < numEngKinds;i++) {
				line = br.readLine();
				int numEngs = Integer.parseInt(line.split(" ")[0]);
				int numReviews = Integer.parseInt(line.split(" ")[1]);

				for(int x = 0;x < numEngs;x++) {
					EngNode node = new EngNode(numReviews);

					if(engineers.get(x-1) != null) {
						engineers.get(x-1).next = node;
					}

					engineers.add(node);
				}
			}

			int nodeIndex = 0;
			int numNodesReviewed = 0;

			boolean isDone = false;

			while(!isDone) {
				CompNode currentNode = components.get(nodeIndex);

				if(currentNode.reviewsLeft > 0) {
					for(int i = 0;i < engineers.size();i++) {
						boolean hasVisted = false;
						for(int x = 0;x < engineers.get(i).getVisitedNodes().size();x++) {
							if(engineers.get(i).getVisitedNodes().get(x) == currentNode) {
								hasVisted = true;
							}
						}

						if(!hasVisted) {
							engineers.get(i).reviewsLeft--;
							currentNode.reviewsLeft--;
							engineers.get(i).visitedNodes.add(currentNode);
						}
					}
				}
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

	ProblemB2012 problem = new ProblemB2012();

	problem.init();

	}
}

class CompNode {
	public int totalReviews;
	public int reviewsLeft;

	public CompNode next;

	public CompNode(int numReviews) {
		totalReviews = 0;
		reviewsLeft = 0;
		next = null;
	}
}

class EngNode {

	public int totalReviews;
	public int reviewsLeft;

	public EngNode next;
	public ArrayList<CompNode> visitedComps;

	public EngNode(int numReviews) {
		totalReviews = 0;
		reviewsLeft = 0;
		next = null;
		visitedComps = new ArrayList<CompNode>();
	}

	public ArrayList<CompNode> getCompsVisited() {
		return visitedComps;
	}
}




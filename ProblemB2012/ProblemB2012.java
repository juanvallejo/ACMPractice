import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class ProblemB2012 {

	public ArrayList<CompNode> components;
	public ArrayList<EngNode> engineers;

	int numNodesReviewed = 0;
	boolean isDone = false;

	public int init(String line, BufferedReader br) {
		components = new ArrayList<CompNode>();
		engineers = new ArrayList<EngNode>();

		int numCompKinds = Integer.parseInt(line.split(" ")[0]);
		int numEngKinds = Integer.parseInt(line.split(" ")[1]);
		try {
			for(int i = 0;i < numCompKinds;i++) {
				line = br.readLine();
				int numComps = Integer.parseInt(line.split(" ")[0]);
				int numReviews = Integer.parseInt(line.split(" ")[1]);

				for(int x = 0;x < numComps;x++) {
					CompNode node = new CompNode(numReviews);

					if(x > 0) {
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

					if(x > 0) {
						engineers.get(x-1).next = node;
					}

					engineers.add(node);
				}
			}

			int nodeIndex = 0;
			int numNodesReviewed = 0;
			int numEngsLeft = engineers.size();

			boolean isDone = false;

			while(!isDone) {
				CompNode currentNode = components.get(nodeIndex);

				if(currentNode.reviewsLeft > 0) {
					if(numEngsLeft > 0) {
						for(int i = 0;i < engineers.size();i++) {
							boolean hasVisted = false;
							if (engineers.get(i).reviewsLeft > 0) {
								for(int x = 0;x < engineers.get(i).getCompsVisited().size();x++) {
									if(engineers.get(i).getCompsVisited().get(x) == currentNode) {
										hasVisted = true;
									}
								}

								if(!hasVisted && currentNode.reviewsLeft > 0) {
									engineers.get(i).reviewsLeft--;
									currentNode.reviewsLeft--;
									engineers.get(i).visitedComps.add(currentNode);
								}
								else if(currentNode.reviewsLeft == 0) {
									numNodesReviewed++;
								}
							}
							else {
								numEngsLeft--;
							}
						}
					}
					else {
						isDone = true;
						System.out.println(0);
						return 0;
					}
				}
				else {
					numNodesReviewed++;
					System.out.println("nodes reviewed = " + numNodesReviewed + " Total nodes = " + components.size());
					System.out.println("review remaining = " + currentNode.reviewsLeft);
					if (numNodesReviewed == components.size()) {
						isDone = true;
						return 1;
					}
				}
				nodeIndex++;
				if (nodeIndex >= components.size()) {
					nodeIndex = 0;
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void main(String[] args) {
		ProblemB2012 problem = new ProblemB2012();
		try {
			BufferedReader br = new BufferedReader(new FileReader("input.txt"));
			String line = br.readLine();
			while (!line.equals("0 0") && line != null) {
				System.out.println(problem.init(line, br));
				line = br.readLine();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
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




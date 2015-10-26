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
System.out.println("-------------");
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

					// System.out.println("Adding node component");
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
							Log.log("Now at node # " + nodeIndex + " (" + currentNode.reviewsLeft + " reviews left)");
							Log.log("Now at engineer # " + i + " (" + engineers.get(i).reviewsLeft + " reviews left)");

							boolean hasVisited = false;
							if (engineers.get(i).reviewsLeft > 0) {
								for(int x = 0;x < engineers.get(i).getCompsVisited().size();x++) {
									if(engineers.get(i).getCompsVisited().get(x) == currentNode) {
										hasVisited = true;
									}
								}

								if(!hasVisited && currentNode.reviewsLeft > 0) {
									Log.log("Node " + nodeIndex + " was not visited, visiting");
									engineers.get(i).reviewsLeft--;
									currentNode.reviewsLeft--;
									engineers.get(i).visitedComps.add(currentNode);
									Log.log("Node reviews left = " + currentNode.reviewsLeft + "; Engineer reviews left = " + engineers.get(i).reviewsLeft);
									Log.log("----------");
								} else if(!hasVisited && currentNode.reviewsLeft == 0) {
									Log.log("Node " + nodeIndex + " has not been visited by engineer " + i + " but has no reviews left, incrementing node count...");
									numNodesReviewed++;
								}

								if(hasVisited) {
									Log.log("Node " + nodeIndex + " has already been visited by this engineer, ignoring");
								}

								Log.log("\nAmount of nodes visited = " + numNodesReviewed + "\n");
									
							}
							else {
								System.out.println(numEngsLeft + " engineers left");
								numEngsLeft--;
							}
						}
					}
					else {
						isDone = true;
						if(numNodesReviewed == components.size()) {
							Log.log("\nNo engineers left. All nodes have been reviewed\n");
							return 1;
						} else {							
							Log.log("\nNo engineers left. Nodes are still pending for review. Fail!\n");
							System.out.println(numNodesReviewed + " total nodes reviewed");
							return 0;
						}
					}
				}
				else {
					Log.log("No reviews left for node " + nodeIndex + ", skipping...");
					numNodesReviewed++;
					if (numNodesReviewed == components.size()) {
						isDone = true;
						return 1;
					}
				}

				nodeIndex++;
				if (nodeIndex >= components.size()) {
					Log.log("Too many nodes to review. There are still components left to review. Fail");
					return 0;
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
			int i = 0;
			while (!line.equals("0 0") && line != null) {
				i++;
				Log.log("\n\nNEW PROBLEM " + i + "\n\n");
				Log.log(problem.init(line, br));
				line = br.readLine();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class Log {
	public static void log(Object text) {
		// System.out.println(text);
	}
}

class CompNode {
	public int totalReviews;
	public int reviewsLeft;

	public CompNode next;

	public CompNode(int numReviews) {
		totalReviews = numReviews;
		reviewsLeft = numReviews;
		next = null;
	}
}

class EngNode {

	public int totalReviews;
	public int reviewsLeft;

	public EngNode next;
	public ArrayList<CompNode> visitedComps;

	public EngNode(int numReviews) {
		totalReviews = numReviews;
		reviewsLeft = numReviews;
		next = null;
		visitedComps = new ArrayList<CompNode>();
	}

	public ArrayList<CompNode> getCompsVisited() {
		return visitedComps;
	}
}




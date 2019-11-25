public class WarshallAlgorithm {
    public int[][] findShortestPaths(int[][] graph){
        int[][] result = graph.clone();
        for(int i = 0; i < result.length; i++){
            for(int j = 0; j < result.length; j++){
                for(int k = 0; k < result.length; k++){
                    if(result[j][i] + result[i][k] < result[j][k])
                        result[j][k] = result[j][i] + result[i][k];
                }
            }
        }
        return result;
    }
}

from experiment_3.listgraph import ListGraph, Edge
from experiment_3.matrixgraph import MatrixGraph


def matrix_to_list(matrix_graph: MatrixGraph) -> ListGraph:
    """ 邻接矩阵转换为邻接表 """
    n = matrix_graph.n
    matrix = matrix_graph.matrix
    list_graph: ListGraph = ListGraph(n)
    i: int
    j: int
    for i in range(1, n + 1):
        for j in range(1, n + 1):
            if i != j and matrix[i][j] < matrix_graph.INF:
                list_graph.add(Edge(i, j, matrix[i][j]))
    return list_graph


def list_to_matrix(list_graph: ListGraph) -> MatrixGraph:
    """ 邻接表转换为邻接矩阵 """
    n = list_graph.n
    matrix_graph: MatrixGraph = MatrixGraph(n)
    for node in list_graph.nodes:
        for edge in node:
            matrix_graph.set(edge.s, edge.e, edge.dis)
    return matrix_graph


if __name__ == '__main__':
    n: int = int(input('Input number of nodes: '))
    e: int = int(input('Input number of edges: '))

    listGraph: ListGraph = ListGraph(n)
    matrixGraph: MatrixGraph = MatrixGraph(n)

    for i in range(e):
        edgeString: str = input('Input edge #%i: ' % i)
        s: int
        e: int
        dis: int
        edgeParams: list = [int(i) for i in edgeString.split()]
        listGraph.add(Edge(*edgeParams))
        matrixGraph.set(*edgeParams)

    print(listGraph)
    print(matrixGraph)
    print(matrix_to_list(matrixGraph))
    print(list_to_matrix(listGraph))
    list_dfs_sequence: list = listGraph.dfs(1)
    print(list_dfs_sequence)

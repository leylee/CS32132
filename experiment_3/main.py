from typing import List

from experiment_3.mygraph import ListGraph, Edge, MatrixGraph


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
        edgeParams: List[int] = [int(i) for i in edgeString.split()]
        listGraph.add(Edge(*edgeParams))
        matrixGraph.set(*edgeParams)

    # 输出两个图
    print("The adjacency list graph:")
    print(listGraph)
    print("The adjacency matrix graph:")
    print(matrixGraph)
    # 两种表示方法转换
    print("The list graph from the matrix:")
    print(matrix_to_list(matrixGraph))
    print("The matrix graph from the list:")
    print(list_to_matrix(listGraph))
    # 邻接表存储的搜索
    list_recursive_dfs_sequence: List[Edge] = listGraph.recursive_dfs(1)
    list_iterative_dfs_sequence: List[Edge] = listGraph.iterative_dfs(1)
    list_bfs_sequence: List[Edge] = listGraph.bfs()
    print("The recursive dfs sequence on the list graph:")
    print(list_recursive_dfs_sequence)
    print("The iterative dfs sequence on the list graph:")
    print(list_iterative_dfs_sequence)
    print("The bfs sequence on the list graph:")
    print(list_bfs_sequence)
    # 邻接矩阵存储的搜索\
    matrix_recursive_dfs_sequence: List[Edge] = matrixGraph.recursive_dfs(1)
    matrix_iterative_dfs_sequence: List[Edge] = matrixGraph.iterative_dfs(1)
    matrix_bfs_sequence: List[Edge] = matrixGraph.bfs(1)
    print("The recursive dfs sequence on the matrix graph:")
    print(matrix_recursive_dfs_sequence)
    print("The iterative dfs sequence on the matrix graph:")
    print(matrix_iterative_dfs_sequence)
    print("The bfs sequence on the matrix graph:")
    print(matrix_bfs_sequence)

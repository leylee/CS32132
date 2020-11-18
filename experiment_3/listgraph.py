

class Edge:
    def __init__(self, s: int, e: int, length: int = 1):
        self.s: int = s
        self.e: int = e
        self.dis: int = length

    def __str__(self) -> str:
        return 'Edge:{s: %i, e: %i, dis: %i}' % (self.s, self.e, self.dis)


class ListGraph:

    def __init__(self, n: int):
        """ 初始化邻接表图, 复杂度 O(n) """
        self.n: int = n
        self.nodes: list[list[Edge]] = [[] for i in range(n + 1)]

    def add(self, edge: Edge) -> None:
        """ 添加边, 复杂度 O(1) """
        self.nodes[edge.s].append(edge)

    def dfs(self) -> list[int]:
        """ 深度优先搜索 """
        return []

    def bfs(self):
        """ 广度优先搜索 """


    def __str__(self) -> str:
        # return self.nodes.__str__()
        result = ''
        for i in range(1, self.n + 1):
            node: list = self.nodes[i]
            for edge in node:
                result += str(edge) + '\n'

        return result

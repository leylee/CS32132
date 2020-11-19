from typing import List, Union

from experiment_3.myqueue import MyQueue


class Edge:
    def __init__(self, s: int, e: int, length: int = 1):
        self.s: int = s
        self.e: int = e
        self.dis: int = length

    def __str__(self) -> str:
        return 'Edge:{s: %i, e: %i, dis: %i}' % (self.s, self.e, self.dis)

    def __repr__(self):
        return str(self)


class ListGraph:

    def __init__(self, n: int):
        """ 初始化邻接表图, 复杂度 O(n) """
        self.n: int = n
        self.nodes: List[List[Edge]] = [[] for i in range(n + 1)]

    def add(self, edge: Edge) -> None:
        """ 添加边, 复杂度 O(1) """
        self.nodes[edge.s].append(edge)

    def dfs(self, begin: int, visited: List[bool] = None, sequence: List[Edge] = None) -> Union[List[Edge], None]:
        """ 深度优先搜索 """
        if (visited is None and sequence is not None) or (visited is not None and sequence is None):
            return None
        if visited is None and sequence is None:
            sequence = [Edge(0, begin, 0)]
            visited = [False for i in range(self.n + 1)]
            visited[begin] = True

        for edge in self.nodes[begin]:
            if not visited[edge.e]:
                visited[edge.e] = True
                sequence.append(edge)
                self.dfs(edge.e, visited, sequence)

        return sequence

    def bfs(self, begin: int = 1) -> List[Edge]:
        """ 广度优先搜索 """
        visited: List[bool] = [False for i in range(self.n + 1)]
        visited[begin] = True
        queue: MyQueue = MyQueue()
        queue.push(begin)
        sequence: List[Edge] = [Edge(0, begin, 0)]

        while len(queue):
            node_id: int = queue.pop()
            for edge in self.nodes[node_id]:
                if not visited[edge.e]:
                    visited[edge.e] = True
                    sequence.append(edge)
                    queue.push(edge.e)

        return sequence

    def __str__(self) -> str:
        # return self.nodes.__str__()
        result = ''
        for i in range(1, self.n + 1):
            node: list = self.nodes[i]
            for edge in node:
                result += str(edge) + '\n'

        return result

    def __repr__(self):
        return str(self)

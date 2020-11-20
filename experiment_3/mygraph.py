from typing import List, Union

from python_modules.datastructure import MyQueue, MyStack


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

    def recursive_dfs(self, begin: int = 1, visited: List[bool] = None, sequence: List[Edge] = None) -> Union[
        List[Edge], None]:
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
                self.recursive_dfs(edge.e, visited, sequence)

        return sequence

    def iterative_dfs(self, begin: int = 1) -> List[Edge]:
        """ 非递归深度优先搜索 """

        class Status:
            def __init__(self, node: int, index: int):
                self.node_id: int = node
                self.index: int = index

        sequence: List[Edge] = [Edge(0, begin, 0)]
        visited: List[bool] = [False for i in range(self.n + 1)]
        visited[begin] = True

        stack: MyStack = MyStack()
        stack.push(Status(begin, 0))
        while stack:
            status: Status = stack.top()
            index = status.index
            node_id = status.node_id
            if index < len(self.nodes[node_id]):
                edge: Edge = self.nodes[node_id][index]
                if not visited[edge.e]:
                    visited[edge.e] = True
                    sequence.append(edge)
                    stack.push(Status(edge.e, 0))
                status.index += 1
            else:
                stack.pop()

        return sequence

    def bfs(self, begin: int = 1) -> List[Edge]:
        """ 广度优先搜索 """
        visited: List[bool] = [False for i in range(self.n + 1)]
        visited[begin] = True
        queue: MyQueue = MyQueue()
        queue.push(begin)
        sequence: List[Edge] = [Edge(0, begin, 0)]

        while queue:
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


class MatrixGraph:
    INF = 0x7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f

    def __init__(self, n: int, inf: int = INF):
        """ 初始化邻接矩阵图, 复杂度 O(n^2) """
        self.INF = inf
        self.n: int = n
        self.matrix: List[List[int]] = [[0 if i == j else inf for i in range(n + 1)] for j in range(n + 1)]

    def set(self, s: int, e: int, dis=INF):
        """ 添加边, 复杂度 O(1) """
        self.matrix[s][e] = dis

    def recursive_dfs(self, begin: int = 1, visited: List[bool] = None, sequence: List[Edge] = None) -> Union[
        List[Edge], None]:
        """ 深度优先搜索 """
        if (visited is None and sequence is not None) or (visited is not None and sequence is None):
            return None
        if visited is None and sequence is None:
            sequence = [Edge(0, begin, 0)]
            visited = [False for i in range(self.n + 1)]
            visited[begin] = True

        for i in range(1, self.n + 1):
            if not visited[i] and self.matrix[begin][i] < self.INF:
                visited[i] = True
                sequence.append(Edge(begin, i, self.matrix[begin][i]))
                self.recursive_dfs(i, visited, sequence)

        return sequence

    def iterative_dfs(self, begin: int = 1) -> List[Edge]:
        """ 非递归深度优先搜索 """

        class Status:
            def __init__(self, node: int, index: int):
                self.node_id: int = node
                self.index: int = index

        sequence: List[Edge] = [Edge(0, begin, 0)]
        visited: List[bool] = [False for i in range(self.n + 1)]
        visited[begin] = True

        stack: MyStack = MyStack()
        stack.push(Status(begin, 1))
        while stack:
            status: Status = stack.top()
            index = status.index
            node_id = status.node_id
            if index <= self.n:
                if not visited[index] and self.matrix[node_id][index] < self.INF:
                    edge: Edge = Edge(node_id, index, self.matrix[node_id][index])
                    visited[edge.e] = True
                    sequence.append(edge)
                    stack.push(Status(edge.e, 1))
                status.index += 1
            else:
                stack.pop()

        return sequence

    def bfs(self, begin: int = 1) -> List[Edge]:
        """ 广度优先搜索 """
        visited: List[bool] = [False for i in range(self.n + 1)]
        visited[begin] = True
        queue: MyQueue = MyQueue()
        queue.push(begin)
        sequence: List[Edge] = [Edge(0, begin, 0)]

        while queue:
            node_id: int = queue.pop()
            for i in range(1, self.n + 1):
                if not visited[i] and self.matrix[node_id][i] < self.INF:
                    visited[i] = True
                    sequence.append(Edge(node_id, i, self.matrix[node_id][i]))
                    queue.push(i)

        return sequence

    def __str__(self) -> str:
        result = ''
        for i in range(1, self.n + 1):
            for j in range(1, self.n + 1):
                if i != j and self.matrix[i][j] < self.INF:
                    result += '[s: %i, e: %i, dis: %i]\n' % (i, j, self.matrix[i][j])
        return result

    def __repr__(self):
        return str(self)

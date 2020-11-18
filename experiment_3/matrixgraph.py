class MatrixGraph:
    INF = 0x7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f

    def __init__(self, n: int, inf: int = INF):
        """ 初始化邻接矩阵图, 复杂度 O(n^2) """
        self.INF = inf
        self.n: int = n
        self.matrix: list[list[int]] = [[0 if i == j else inf for i in range(n + 1)] for j in range(n + 1)]

    def set(self, s: int, e: int, dis=INF):
        """ 添加边, 复杂度 O(1) """
        self.matrix[s][e] = dis

    def __str__(self) -> str:
        result = ''
        for i in range(1, self.n + 1):
            for j in range(1, self.n + 1):
                if i != j and self.matrix[i][j] < self.INF:
                    result += '[s: %i, e: %i, dis: %i]\n' % (i, j, self.matrix[i][j])
        return result

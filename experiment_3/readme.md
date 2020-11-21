```flow
st=>start: Experiment_3
input=>inputoutput: 输入节点数, 
边数, 各边的
顶点和边权
build=>subroutine: 生成图的
邻接表实现和
邻接矩阵实现
print=>inputoutput: 输出邻接表
和邻接矩阵
convert=>subroutine: 邻接矩阵和
邻接表相互
转换并打印
listsearch=>subroutine: 对邻接表进行
广度优先搜索,
递归/非递归 
深度优先搜索
并打印
matrixsearch=>subroutine: 对邻接矩阵
广度优先搜索,
递归/非递归
深度优先搜索
并打印
end=>end: End

st->input->build->print->convert->listsearch->matrixsearch->end
```


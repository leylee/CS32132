```flow
st=>start: Experiment_2
input=>inputoutput: 输入二叉树先序序列
build=>subroutine: 生成二叉树
print=>inputoutput: 打印二叉树的结构
rpre=>subroutine: 生成递归先序遍历
prpre=>inputoutput: 打印递归先序遍历结果
rin=>subroutine: 生成递归中序遍历
prin=>inputoutput: 打印递归中序遍历结果
rpost=>subroutine: 生成递归后序遍历
prpost=>inputoutput: 打印递归后序遍历结果
ipre=>subroutine: 生成非递归先序遍历
pipre=>inputoutput: 打印非递归先序遍历结果
iin=>subroutine: 生成非递归中序遍历
piin=>inputoutput: 打印非递归中序遍历结果
ipost=>subroutine: 生成非递归后序遍历
pipost=>inputoutput: 打印非递归后序遍历结果
cbin=>subroutine: 检查是否为满二叉树
pcbin=>inputoutput: 输出检查结果
idin=>inputoutput: 读入两个节点序号
find=>subroutine: 找到这两个节点的公共祖先
pfind=>inputoutput: 输出公共祖先

st->input->build->print->rpre->prpre->rin->prin->rpost->prpost->ipre->pipre->iin->piin->ipost->pipost->cbin->pcbin->idin->find->pfind->idin
```


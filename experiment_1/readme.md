```flow
st=>start: Experiment_1
read=>inputoutput: 输入中缀表达式
checkFormat=>condition: 表达式合法?
printError=>inputoutput: 输出错误信息
split=>subroutine: 分割中缀表达式
convert=>subroutine: 转换为后缀表达式
printPostfix=>inputoutput: 输出后缀表达式
calculate=>subroutine: 计算后缀表达式
hasError=>condition: 计算出错?
printResult=>inputoutput: 输出计算结果
e=>end: End

st->read->checkFormat(yes)->split->convert->printPostfix->calculate
calculate->hasError(no,bottom)->printResult(left)->read
checkFormat(no)->printError(bottom)->read
hasError(yes)->printError
```

